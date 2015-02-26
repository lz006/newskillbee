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
 * 
 * Fragmentklasse, die für das Anzeigen Über uns zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */


public class Us_Fragment extends Fragment implements OnClickListener{
	
	
	
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
	      
		//View der das Layout ladet
		View v = inflater.inflate(R.layout.about_us_fragment, container, false);
		
		return v;
		
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