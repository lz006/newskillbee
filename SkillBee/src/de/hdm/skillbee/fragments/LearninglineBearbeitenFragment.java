package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdm.skillbee.R;
import de.hdm.skillbee.R.layout;
import de.hdm.skillbee.activities.SkillBeeActivity;
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
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 
 * Fragmentklasse, die zum bearbeiten einer Learningline zuständig ist.
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class LearninglineBearbeitenFragment extends Fragment implements OnClickListener {

	Activity baseActivity = null;
	
	JSONParser jsonParser = new JSONParser();
	
	EditText titel;
	Spinner kategorie;
	Spinner spinner2;
	
	
	Learningline learningline = null;
	
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	String llMsg = null;
	
	
	
	
	
	
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
				View v = inflater.inflate(R.layout.learningline_bearbeiten_fragment, container, false);
    	
		
		// Zuweisung der einzelnen Elemente		
    	int llid = getArguments().getInt("llid");   
    	int llkategorie = getArguments().getInt("llkategorie");
		String llbezeichnung =getArguments().getString("llbezeichnung");
		

		learningline= new Learningline();
		learningline.setId(llid);
		
	
	

		//Button zum speichern der Learningline.
		Button btnregister = (Button) v.findViewById(R.id.btnllbsave);
		btnregister.setOnClickListener(this);
		
		//Button zum löschen der Learningline.
		Button btnlldelete = (Button) v.findViewById(R.id.btnlldelete);
		btnlldelete.setOnClickListener(this);
		
		
		// Zuweisung der einzelnen EditTexts
		titel = (EditText)v.findViewById(R.id.textboxLlTitel);
		kategorie = (Spinner)v.findViewById(R.id.kategorie);
		
		
		//Inhalt den Textviews zuweisen.
		titel.setText(llbezeichnung);
		kategorie.setSelection(llkategorie);
		

		
		
		
		
		
		return v;
		

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
      	 * Fall 1 nach klick auf den Button werden die änderungen der Learningline in der Datenbank gespeichert.
      	 * 
      	 */
        
        case R.id.btnllbsave:
        
        	
        	learningline.setBezeichnung(titel.getText().toString());
        	learningline.setKategorieID(kategorie.getSelectedItemPosition());
        	learningline.setAuthorID(cdbl.getUserMapper().findAll().elementAt(0).getId());
        	learningline = cdbl.getLearninglineMapper().update(learningline);
       
        	
        	// Ausgabe das die Learningline erfolgreich geändert wurde. Des Weiteren wird man wieder in die Learningline Übersicht zurückgeleitet.
        	showMessage("Learning Line wurde erfolgreich geändert");
        	
     
	       	
        break;
        
        /**
      	 * 
      	 * Fall 2 nach klick auf den Button wird die ausgewälte Learningline aus der Datenbank gelöscht.
      	 * 
      	 */
        
        case R.id.btnlldelete:
        	
        	
        	cdbl.getLearninglineMapper().delete(learningline);
        	
        	// Ausgabe das die Learningline erfolgreich gelöscht wurde. Des Weiteren wird man wieder in die Learningline Übersicht zurückgeleitet.
        	showMessage("Learning Line wurde erfolgreich gelöscht");
        	
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
	Toast einToast = Toast.makeText(LearninglineBearbeitenFragment.this.baseActivity.getApplicationContext(), message, Toast.LENGTH_LONG);
	einToast.show();
	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
	MainMenuFragment mf = new MainMenuFragment();
	mf.setBaseActivity(baseActivity);
	fragmentTransaction.replace(R.id.fragmentcontainer, mf);
	fragmentTransaction.addToBackStack(null);
 	fragmentTransaction.commit();
	}

	
	
	
	
	
	

}
