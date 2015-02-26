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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragmentklasse, die für das Anzeigen des langen Inhalts zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class LearningPunktInhaltFragment extends Fragment implements OnClickListener{
	
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	Knoten kn = null;

	
	   /**
	  * Methode wird aufgerufen sobald die Ansicht erzeugt werden soll, die dann hier auch zurückgegeben wird
	  * Hier wird die Ansicht aufgebaut
	  * @param inflater
	  * @param container
	  * @param savedInstanceState
	  * @return
	  */
		
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    
		
		//Verweis zur lokalen Datenbank 
		cdbl = ControllerDBLokal.get();
		//Verweis zur externen Online Datenbank
		cdbs = ControllerDBServer.get();
		
		//View der das Layout ladet
		View v = inflater.inflate(R.layout.inhalt_lang_fragment, container, false);
		
		
		//Textview mit der Knotenbezeichnung.
		TextView lp = (TextView)v.findViewById(R.id.LearningKnotenBezeichnung);
		
		
		//Textview mit dem Langen Inhalt.
		TextView is = (TextView)v.findViewById(R.id.LearningKnotenInhaltLang);
		
		//Inhalt den Textviews zuweisen.
		lp.setText(kn.getUeberschrift());
		is.setText(kn.getInhalt());
		
		
		return v;
		
}
	
	
	
	
	/**
     * Knotenindex setzen von vorhergelagertem Fragment
     * @param kn
     */
	
public void setKnoten(Knoten kn) {
		
		this.kn = kn;

	}
	
			
		
	
	

/**
 * Wird ausgeführt sobald etwas angeklickt wird
 * @param v
 */


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	

		
	
	
	 /**
     * Verweis zur Hauptaktivität setzen
     * @param baseActivity
     */

	public void setBaseActivity(Activity baseActivity) {
		// TODO Auto-generated method stub
		
	}
}