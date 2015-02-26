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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * Fragmentklasse, die für das Anzeigen des Webviews für Google Medien zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class MedienGFragment extends Fragment implements OnClickListener{
	
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	private WebView webView; // Anlegen des Webview-Members.
	
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
		View v = inflater.inflate(R.layout.medien_fragment, container, false);
		

		webView = (WebView) v.findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(false); // Wichtig, damit jQuery funktioniert. 
        webView.getSettings().setDomStorageEnabled(false);
        
        // Hier binden wir die index.html ein.
        webView.loadUrl("http://" + kn.getGoogleLink());
		
		
		
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