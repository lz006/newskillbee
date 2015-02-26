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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * Fragmentklasse, die für das Anzeigen des Learning-Knoten zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class LearningPunktFragment extends Fragment implements OnClickListener{
	
	
	
	Activity baseActivity = null;
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	
	Knoten kn = null;
	
	
	
	
	
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
		View v = inflater.inflate(R.layout.learningpunkt_fragment, container, false);

		
		//Button für Inhaltlang.
		Button InhaltLong2 = (Button) v.findViewById(R.id.LearningInhaltLong2);
		InhaltLong2.setOnClickListener(this);
		
		//Button für Medien von Google.
		Button MedienG = (Button) v.findViewById(R.id.MedienG);
		MedienG.setOnClickListener(this);
		
		//Button für Medien von Youtube.
		Button MedienY = (Button) v.findViewById(R.id.MedienY);
		MedienY.setOnClickListener(this);
		
		//Textview mit der Knotenbezeichnung.
		TextView lp = (TextView)v.findViewById(R.id.LearningPunkt2);
		lp.setText(kn.getUeberschrift());
		
		//Textview mit dem kurzen Inhalt.
		TextView ihaltshort = (TextView)v.findViewById(R.id.InhaltShort);
		ihaltshort.setText(kn.getKurzInhalt());
		ihaltshort.setClickable(true);
		ihaltshort.setOnClickListener(this);

		
		return v;
		
		
}
	
	
	
	
	
	  /**
		 * ClickListener Event Handler
		 * Wird ausgeführt sobald kurz auf ein Element geklickt wird.
		 * @param v
		 */
	    
	

	@Override
public void onClick(View v) {
		
    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

		
        switch (v.getId()) {

        /**
    	 * 
    	 * Fall 1 nach klick auf den Button oben wird das Fragment gewechselt zum langen Inhalt
    	 * 
    	 */
        
        case R.id.LearningInhaltLong2:
        	             	
			LearningPunktInhaltFragment lpif = new LearningPunktInhaltFragment();
			lpif.setKnoten(kn);
			lpif.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, lpif);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
         	
         	break;
        
         	/**
        	 * 
        	 * Fall 2 nach klick auf den kurz Inahlt wird das Fragment gewechselt zum langen Inhalt
        	 * 
        	 */
         	
        case R.id.InhaltShort:
         	
			LearningPunktInhaltFragment lpif2 = new LearningPunktInhaltFragment();
			lpif2.setKnoten(kn);
			lpif2.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, lpif2);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
         	
         	break;
         	
         	/**
        	 * 
        	 * Fall 3 nach klick auf den Button des Google Logos wird 
        	 * das Fragment gewechselt zum Webview mit dem hinterlegten Google Link
        	 * 
        	 */
        
        case R.id.MedienG:
         	
        	MedienGFragment medg = new MedienGFragment();
			
        	medg.setKnoten(kn);
			medg.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, medg);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
        
         	break;
         	
         	/**
        	 * 
        	 * Fall 4 nach klick auf den Button des Youtube Logos wird 
        	 * das Fragment gewechselt zum Webview mit dem hinterlegten Youtube Link
        	 * 
        	 */
         	
        case R.id.MedienY:
         	
			MedienYFragment medy = new MedienYFragment();
			
			
			medy.setBaseActivity(baseActivity);
			medy.setKnoten(kn);
        	fragmentTransaction.replace(R.id.fragmentcontainer, medy);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
        
        	
        break;
        default: break;
        }
	}
	
	
	
	/**
     * Knotenindex setzen von vorhergelagertem Fragment
     * @param kn
     */
	
	
	public void setKnoten(Knoten kn) {
		
		this.kn = kn;
		
		
	}
	
	

	
}