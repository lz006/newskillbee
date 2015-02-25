package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdm.skillbee.R;
import de.hdm.skillbee.R.layout;
import de.hdm.skillbee.activities.SkillBeeActivity;
import de.hdm.skillbee.bo.User;
import de.hdm.skillbee.controller.ControllerDBLokal;
import de.hdm.skillbee.controller.ControllerDBServer;
import de.hdm.skillbee.db.server.JSONParser;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Fragmentklasse, die für die Funktionen des Registrationsvorgangs des Users zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class UserRegistrationFragment extends Fragment implements OnClickListener{

	Activity baseActivity = null;
	
	JSONParser jsonParser = new JSONParser();
	
	EditText emailadress;
	EditText firstname;
	EditText lastname;
	EditText password;
	
	User user = null;
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	String userMsg = null;
	boolean useralreadyexists=false;
	
	
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
		
		
		View v = inflater.inflate(R.layout.user_registration_fragment, container, false);
		Button btnregister = (Button) v.findViewById(R.id.btnregisteruser);
		//Clicklistener Event zum Button für Klicks hinzufügen
		btnregister.setOnClickListener(this);
		 firstname = (EditText)v.findViewById(R.id.textboxfirstname);
		 lastname = (EditText)v.findViewById(R.id.textboxlastname);
		 emailadress = (EditText)v.findViewById(R.id.textboxemail);
		 password =(EditText)v.findViewById(R.id.textboxpassword);
		
		return v;
		
	}
	
	/**
	 * Wird ausgeführt sobald der Button angeklickt wird
	 * @param v
	 */
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnregisteruser:
        	//Erzeuge User temporär               	
	        user = new User();
	        user.setVorname(firstname.getText().toString());
	        user.setName(lastname.getText().toString());
	        user.setEmail(emailadress.getText().toString());
	        user.setPasswort(password.getText().toString());
	       	new registerUser().execute();        
	       	
        break;
        }
    }
	
	/**
	 * Isolierte Klasse, die für den Registriervorgang des Users verantwortlich ist. Hierbei werden die vom User
	 * angegebenen Daten an das PHP Skript auf dem Server geschickt, welches wiederum als Service dient und 
	 * diese Daten in die Online Datenbank schreibt. Dies muss asynchron geschehen, da sonst der gesamte
	 * clientseitige Applikationsablauf einfrieren würde bis der Loginvorgang abgeschlossen wäre. Daher erbt 
	 * Klasse auch von der abstrakten Klasse AsyncTask  
	 */
	private class registerUser extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Bilde Parameter
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			// SQL-Query wird erzeugt
			params1.add(new BasicNameValuePair("query", UserRegistrationFragment.this.cdbs.getUserMapper().findByUserAndPW(user)));
			
			// 1 -> Antwort mit Objekten erwartet (Wenn der User bereits registriert ist)
			params1.add(new BasicNameValuePair("flag", "1"));
			
			// SQL-Query wird mittels Request an den Server übertragen
			JSONObject json = jsonParser.makeHttpRequest(cdbs.getUrl_user_queries(), "POST", params1);
			
			try {
				// Überprüfe auf SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					// Success = 1 -> Means that password and user are not in use
				} else {
					userMsg = "Nutzername und Passwort sind bereits in Verwendung";
					useralreadyexists=true;
					return null;
				}
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			// Erzeuge neuen User
			params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("query", UserRegistrationFragment.this.cdbs.getUserMapper().insertQuery(user)));
			
			// 0 -> Keine Antwort mit Objekten wird erwartet
			params1.add(new BasicNameValuePair("flag", "0"));
			
			json = jsonParser.makeHttpRequest(cdbs.getUrl_user_queries(), "POST", params1);
			
			try {
				// Überprüfe auf SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					userMsg = "Es ist ein Datenbankfehler aufegtreten";
					return null;
				} else {
					
				}
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			// Lese neue User-ID aus
			params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("query", UserRegistrationFragment.this.cdbs.getUserMapper().getUserIDQuery(user)));
			
			// 1 -> Antwort mit Objekten erwartet (Wenn der User bereits registriert ist)
			params1.add(new BasicNameValuePair("flag", "1"));
						
			json = jsonParser.makeHttpRequest(cdbs.getUrl_maxid_queries(), "POST", params1);
			
			
			try {
				// Überprüfe auf SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					userMsg = "Es ist ein Datenbankfehler aufegtreten";
					return null;
				} else {
					user.setId(UserRegistrationFragment.this.cdbs.getUserMapper().json2NewID(json));
					
					//Füge User zur lokalen Datenbank hinzu
					UserRegistrationFragment.this.cdbl.getUserMapper().insert(user);
				}
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			userMsg = "Neuer User erfolgreich erstellt";
			return null;
		}
		
		/**
		 * Wird aufgerufen sobald der Loginprozess abgeschlossen ist
		 * @param result
		 */
		protected void onPostExecute(Void result) {
			
			//Meldung wird angezeigt
			Toast einToast = Toast.makeText(UserRegistrationFragment.this.baseActivity.getApplicationContext(), userMsg, Toast.LENGTH_LONG);
			einToast.show();
			
			//Überprüft ob der User nicht bereits existiert
			if(!useralreadyexists)
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
