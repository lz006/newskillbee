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
 * Fragmentklasse, die zum erstellen einer Learningline zuständig ist.
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class LearninglineErstellenFragment extends Fragment implements OnClickListener {

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
		View v = inflater.inflate(R.layout.learningline_erstellen_fragment, container, false);
		
		
		//Button zum anlegen der Learningline.
		Button btnregister = (Button) v.findViewById(R.id.btnllsave);
		btnregister.setOnClickListener(this);
		
		// Zuweisung der einzelnen EditTexts
		titel = (EditText)v.findViewById(R.id.textboxLlTitel);
		kategorie = (Spinner)v.findViewById(R.id.kategorie);
		
		
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
    	 * Fall 1 nach klick auf den Button wird die Learningline erstellt und in der Datenbank gespeichert.
    	 * 
    	 */
        
        case R.id.btnllsave:
                  	
        	
        	
        	learningline = new Learningline();
        	learningline.setBezeichnung(titel.getText().toString());
        	learningline.setKategorieID(kategorie.getSelectedItemPosition());
        	learningline.setAuthorID(cdbl.getUserMapper().findAll().elementAt(0).getId());

        	learningline = cdbl.getLearninglineMapper().insert(learningline);
        	

        	
        	// Ausgabe das die Learningline erfolgreich angelegt wurde. Des Weiteren wird man wieder in die Learningline Übersicht zurückgeleitet.
        	
        	Toast einToast = Toast.makeText(LearninglineErstellenFragment.this.baseActivity.getApplicationContext(), "Neue Learningline erfolgreich erstellt", Toast.LENGTH_LONG);
			einToast.show();
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			MainMenuFragment mf = new MainMenuFragment();
        	mf.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, mf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();

	       	
        break;
        }
    }
	

	
	
	
	
	
	

}
