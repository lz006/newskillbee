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
import de.hdm.skillbee.bo.Knoten;
import de.hdm.skillbee.bo.Learningline;
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
 * 
 * Fragmentklasse, die zum erstellen eines Learning-Knoten zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class LearningPunktErstellenFragment extends Fragment implements OnClickListener{

	Activity baseActivity = null;
	
	JSONParser jsonParser = new JSONParser();
	
	Learningline ll=null;
	EditText Bezeichnung;
	EditText InhaltKurz;
	EditText InhaltLang;
	EditText GoogleLink;
	EditText YoutubeLink;
	EditText Position;
	int llIDLoc;
	int llIDOn;


	
	Knoten learningpunkt = null;
	
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	String lpMsg = null;
	
	
	
	

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
		
		//View der das Layout ladet
		View v = inflater.inflate(R.layout.learningpunkt_erstellen_fragment, container, false);
		
		//Button zum anlegen des Knotens.
		Button btnregister = (Button) v.findViewById(R.id.btnlpsave);
		btnregister.setOnClickListener(this);
		
		// Zuweisung der einzelnen EditTexts
		Bezeichnung = (EditText)v.findViewById(R.id.editTextBezeichnung);
		Position = (EditText)v.findViewById(R.id.editTextPosition);
		InhaltKurz = (EditText)v.findViewById(R.id.EditTextShort);
		InhaltLang = (EditText)v.findViewById(R.id.EditTextLong);
		GoogleLink = (EditText)v.findViewById(R.id.editTextGoogle);
		YoutubeLink = (EditText)v.findViewById(R.id.editTextYoutube);
		

		
		
		return v;
		

	}
	
	
	/**
     * Learninglineindex setzen von vorhergelagertem Fragment
     * @param ll
     */
	
	
	public void setLL(Learningline ll)
	{
	this.ll=ll;
	}
	
	
	
	
	  /**
		 * ClickListener Event Handler
		 * Wird ausgeführt sobald kurz auf ein Element geklickt wird. 
		 * @param v
		 */
	    
	
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
        
        /**
    	 * 
    	 * Fall 1 nach klick auf den Button wird der Knoten erstellt und in der Datenbank gespeichert.
    	 * 
    	 */
        
        case R.id.btnlpsave:
        	             	
        	learningpunkt = new Knoten();
        	learningpunkt.setUeberschrift(Bezeichnung.getText().toString());
        	learningpunkt.setKurzInhalt(InhaltKurz.getText().toString());
        	learningpunkt.setPosition(new Integer(Position.getText().toString()));
        	learningpunkt.setInhalt(InhaltLang.getText().toString());
        	learningpunkt.setGoogleLink(GoogleLink.getText().toString());
        	learningpunkt.setYoutubeLink(YoutubeLink.getText().toString());
        	learningpunkt.setLlIDLoc(ll.getId());
	       	learningpunkt.setLlIDOn(ll.getIdOn());
	       	LearningPunktErstellenFragment.this.cdbl.getKnotenMapper().insert(learningpunkt);
        	showMessage("Knoten erfolgreich erstellt");
        	
        	
      
	       	
        break;
        }
    }
	
	
	

	   /**
	  * Methode die dafür da ist eine Message auszugeben wenn Sie aufgerufen wird.
	  * Des Weiteren wird man wieder in die Learningline Übersicht zurückgeleitet.
	  * @param message
	  */
		
	
	
	public void showMessage(String message)
	{
	Toast einToast = Toast.makeText(LearningPunktErstellenFragment.this.baseActivity.getApplicationContext(), message, Toast.LENGTH_LONG);
	einToast.show();
	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
	LpgotoQuestionFragment llef = new LpgotoQuestionFragment();
	llef.setBaseActivity(baseActivity);
	fragmentTransaction.replace(R.id.fragmentcontainer, llef);
	fragmentTransaction.addToBackStack(null);
 	fragmentTransaction.commit();
	}
	
	
	
	
	
	

}
