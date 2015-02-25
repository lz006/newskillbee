package de.hdm.skillbee.activities;


import de.hdm.skillbee.R;
import de.hdm.skillbee.R.id;
import de.hdm.skillbee.R.layout;
import de.hdm.skillbee.R.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdm.skillbee.bo.Kategorie;
import de.hdm.skillbee.controller.ControllerDBLokal;
import de.hdm.skillbee.fragments.LearningLineOverviewFragment;
import de.hdm.skillbee.fragments.LpgotoQuestionFragment;
import de.hdm.skillbee.fragments.MainMenuFragment;
import de.hdm.skillbee.fragments.StoreFragment;
import de.hdm.skillbee.fragments.UserLogOrRegFragment;
import de.hdm.skillbee.fragments.UserLoginFragment;
import de.hdm.skillbee.fragments.UserRegistrationFragment;
import de.hdm.skillbee.controller.ControllerDBServer;
import de.hdm.skillbee.db.server.JSONParser;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * Klasse, die für die Hauptaktivität der Skill Bee App verantwortlich ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class SkillBeeActivity extends Activity {
	
	// Deklarierung der Membervariablen
	JSONParser jsonParser = new JSONParser();
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	Kategorie kat = null;

	/*
	 * Standard Create Methode
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_skillbee);
	
	    //ControllerDBLokal wird instanziiert um auf Hilfsmethoden zugreifen zu können
	    ControllerDBLokal con = ControllerDBLokal.get();
	    //Verbindung zu lokaler Datenbank wird aufgebaut
	    con.connect2DB(this, "database");
	    
	    if(savedInstanceState==null) {
	    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
             //Wenn noch kein User vorhanden ist, rufe das Login oder Register Fragment auf   
	        if(con.getUserMapper().findAll()==null) {
	    	   //Display UserRegistrationFragment
	        	UserLogOrRegFragment lrf = new UserLogOrRegFragment();
	        	lrf.setBaseActivity(this);
	        	fragmentTransaction.replace(R.id.fragmentcontainer, lrf);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	        } 
	        else {
	    	   // Weiterleitung zum Hauptmenu falls User bereits vorhanden
	    	   MainMenuFragment mf = new MainMenuFragment();
	    	   mf.setBaseActivity(this);
	    	   fragmentTransaction.replace(R.id.fragmentcontainer, mf);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	        }
	    }


        
        cdbl = ControllerDBLokal.get();
        cdbl.connect2DB(this, "database");
    }

/**
 * Ist für die Erzeugung des Actionbar Menüs zuständig
 * @param menu
 * @return
 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Füllt das Menü und fügt Menüelemente hinzu
        getMenuInflater().inflate(R.menu.skill_bee, menu);
        return true;
    }

    
    
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
     
    
    /**
     * Definition der Auswahlmöglichkeiten im Actionbar Navigationsmenü
     * @param item
     * @return
     */
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Reagiert auf das Drücken von Actionbar Elementen

    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    	switch (item.getItemId()) {
	        
	    	// das Navigationsmenü wird aufgerufen
	    	case R.id.action_settings:
	        //das Learning Line Übersicht Fragment wird aufgerufen 	
	        case R.id.submenu1:
	        	LearningLineOverviewFragment llof= new LearningLineOverviewFragment();
	        	llof.setBaseActivity(this);
	        	fragmentTransaction.replace(R.id.fragmentcontainer, llof);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	
	        	return true;
	        	
	        //das Learning Line Editor Fragment wird aufgerufen
	        case R.id.submenu2:
	        	
	        	LpgotoQuestionFragment lqf= new LpgotoQuestionFragment();
	        	lqf.setBaseActivity(this);
	        	fragmentTransaction.replace(R.id.fragmentcontainer, lqf);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	        	
	        	return true;
	        
	        //das Learning Line Store Fragment wird aufgerufen
	        case R.id.submenu3:
	        	
	        	StoreFragment sf= new StoreFragment();
	        	sf.setBaseActivity(this);
	        	fragmentTransaction.replace(R.id.fragmentcontainer, sf);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	        	
	            return true;
	        	default: return false;
    	}

    

    }       }      

