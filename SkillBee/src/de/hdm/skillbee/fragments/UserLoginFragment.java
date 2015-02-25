package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdm.skillbee.R;
import de.hdm.skillbee.bo.User;
import de.hdm.skillbee.controller.ControllerDBLokal;
import de.hdm.skillbee.controller.ControllerDBServer;
import de.hdm.skillbee.db.server.JSONParser;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Fragmentklasse, die für die Funktionen des Loginvorgangs des Users zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class UserLoginFragment extends Fragment implements OnClickListener {

	Activity baseActivity = null;
	
	JSONParser jsonParser = new JSONParser();
	
	EditText emailadress;
	EditText firstname;
	EditText lastname;
	EditText password;
	String email;
	boolean loginsuccess;
	
	User user = null;
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	String userMsg = null;
	
	
	  /**
     * Verweis zur Hauptaktivität setzen
     * @param baseActivity
     */
	public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}

	   /**
     * Methode wird aufgerufen sobald die Ansicht erzeugt werden soll, die dann hier auch zurückgegeben wird
     * Hier wird die Ansicht aufgebaut
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	     
		//Verweis zur lokalen Datenbank 
		cdbl = ControllerDBLokal.get();
		//Verweis zur externen Online Datenbank
    	cdbs = ControllerDBServer.get();
		
		View v = inflater.inflate(R.layout.user_login_fragment, container, false);
		Button btnlogin = (Button) v.findViewById(R.id.btnloginuser);
		//Clicklistener Event zum Button für Klicks hinzufügen
		btnlogin.setOnClickListener(this);
		 emailadress = (EditText)v.findViewById(R.id.textboxLogEmail);
		 password =(EditText)v.findViewById(R.id.textboxLogPW);
		
		return v;
	}
	
	/**
	 * Wird ausgeführt sobald der Button angeklickt wird
	 * @param v
	 */
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
	        case R.id.btnloginuser:
	        	//Erzeuge User temporär              	
		        user = new User();
		        user.setEmail(emailadress.getText().toString());
		        user.setPasswort(password.getText().toString());
		       	new loginUser().execute();        
		        break;
		        
		        
		        
		        
        }
    }
	
	/**
	 * Isolierte Klasse, die für den Loginvorgang des Users verantwortlich ist. Hierbei werden die vom User
	 * angegebenen Daten an das PHP Skript auf dem Server geschickt, welches wiederum als Service dient und 
	 * diese Daten in die Online Datenbank schreibt. Dies muss asynchron geschehen, da sonst der gesamte
	 * clientseitige Applikationsablauf einfrieren würde bis der Loginvorgang abgeschlossen wäre. Daher erbt 
	 * Klasse auch von der abstrakten Klasse AsyncTask  
	 */
	private class loginUser extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Bilde Parameter
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			// SQL-Query wird erzeugt
			params1.add(new BasicNameValuePair("query", UserLoginFragment.this.cdbs.getUserMapper().findByUserAndPW(user)));
			
			// 1 -> Antwort mit Objekten erwartet (Wenn der User bereits registriert ist)
			params1.add(new BasicNameValuePair("flag", "1"));
			
			// SQL-Query wird mittels Request an den Server übertragen
			JSONObject json = jsonParser.makeHttpRequest(cdbs.getUrl_user_queries(), "POST", params1);
			
			try {
				// Überprüfe auf SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					userMsg = "Login war nicht erfolgreich, bitte überprüfen Sie Ihre Anmeldedaten";
					loginsuccess =false;
				} else {
					UserLoginFragment.this.cdbl.getUserMapper().insert(UserLoginFragment.this.cdbs.getUserMapper().json2User(json).elementAt(0));
					userMsg = "Login erfolgreich";
					loginsuccess =true;
					return null;
				}
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			
			return null;
		}
		/**
		 * Wird aufgerufen sobald der Loginprozess abgeschlossen ist
		 * @param result
		 */
		protected void onPostExecute(Void result) {
			
			//Meldung wird angezeigt
			Toast einToast = Toast.makeText(UserLoginFragment.this.baseActivity.getApplicationContext(), userMsg, Toast.LENGTH_LONG);
			einToast.show();
			
			//Wenn der Login erfolgreich war tausche das Fragment mit dem MainMenuFragment aus
			if(loginsuccess)
			{
		    //Initialisiere Fragmentaustausch
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			//Instanziiere MainMenuFragment
			MainMenuFragment mf = new MainMenuFragment();
			//Übergebe die ausgewählte Learningline an eine Methode im MainMenuFragment
			mf.setBaseActivity(baseActivity);
			 //Ersetze Fragment
        	fragmentTransaction.replace(R.id.fragmentcontainer, mf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
			}

		}
	}
}
