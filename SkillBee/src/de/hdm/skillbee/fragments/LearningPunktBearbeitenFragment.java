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

public class LearningPunktBearbeitenFragment extends Fragment implements OnClickListener{

	Activity baseActivity = null;
	
	JSONParser jsonParser = new JSONParser();
	
	EditText Bezeichnung;
	EditText InhaltKurz;
	EditText InhaltLang;
	EditText GoogleLink;
	EditText YoutubeLink;
	EditText Position;
	Knoten kn=null;


	
	Knoten learningpunkt = null;
	
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	String lpMsg = null;
	
	
	
	public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	      /**
	       * Inflate the layout for this fragment
	       */
		cdbl = ControllerDBLokal.get();
    	cdbs = ControllerDBServer.get();
		
		
    	
    	

    	
    	
		View v = inflater.inflate(R.layout.learningpunkt_bearbeiten_fragment, container, false);
		Button btnregister = (Button) v.findViewById(R.id.btnlpsavee);
		btnregister.setOnClickListener(this);
		Button btndelete= (Button) v.findViewById(R.id.btnlpdelitee);
		btndelete.setOnClickListener(this);
		Bezeichnung = (EditText)v.findViewById(R.id.editTextBezeichnunge);
		Position = (EditText)v.findViewById(R.id.editTextPositione);
		InhaltKurz = (EditText)v.findViewById(R.id.EditTextShorte);
		InhaltLang = (EditText)v.findViewById(R.id.EditTextLonge);
		GoogleLink = (EditText)v.findViewById(R.id.editTextGooglee);
		YoutubeLink = (EditText)v.findViewById(R.id.editTextYoutubee);
		
		Bezeichnung.setText(kn.getUeberschrift());
		Position.setText(Integer.valueOf(kn.getPosition()).toString());
		InhaltKurz.setText(kn.getKurzInhalt());
		InhaltLang.setText(kn.getInhalt());
		GoogleLink.setText(kn.getGoogleLink());
		YoutubeLink.setText(kn.getYoutubeLink());
		
		
		
		// (R.id.spinner1)
		
		
		
		
		return v;
		
//	      return inflater.inflate(
//	      R.layout.user_registration_fragment, container, false);
	}
	
	public void setKnoten(Knoten kn)
	{
	this.kn=kn;
	}
	
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnlpsavee:
        	//Create User temporarily               	
        	learningpunkt = new Knoten();
        	learningpunkt.setUeberschrift(Bezeichnung.getText().toString());
        	learningpunkt.setKurzInhalt(InhaltKurz.getText().toString());
        	learningpunkt.setPosition(new Integer(Position.getText().toString()));
        	learningpunkt.setInhalt(InhaltLang.getText().toString());
        	learningpunkt.setGoogleLink(GoogleLink.getText().toString());
        	learningpunkt.setYoutubeLink(YoutubeLink.getText().toString());
        	learningpunkt.setId(kn.getId());
        	learningpunkt.setLlIDLoc(kn.getLlIDLoc());
	       	learningpunkt.setLlIDOn(kn.getLlIDOn());
	       	LearningPunktBearbeitenFragment.this.cdbl.getKnotenMapper().update(learningpunkt);
        	showMessage("Knoten wurde erfolgreich geändert");
        	
        	
	 //      	new learningPunktCreate().execute();        
	       	
        break;
        
        case R.id.btnlpdelitee:
	       	LearningPunktBearbeitenFragment.this.cdbl.getKnotenMapper().delete(kn);
        	showMessage("Knoten wurde erfolgreich gelöscht");
        	break;
        }
        
    }
	
	
	
	public void showMessage(String message)
	{
	Toast einToast = Toast.makeText(LearningPunktBearbeitenFragment.this.baseActivity.getApplicationContext(), message, Toast.LENGTH_LONG);
	einToast.show();
	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
	LpgotoQuestionFragment llef = new LpgotoQuestionFragment();
	llef.setBaseActivity(baseActivity);
	fragmentTransaction.replace(R.id.fragmentcontainer, llef);
	fragmentTransaction.addToBackStack(null);
 	fragmentTransaction.commit();
	}
	
	private class learningPunktCreate extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Building Parameters
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
	
			
			// SQL-Query wird mittels Request an den Server ï¿½bertragen
			JSONObject json = null;
			
		
			
			// Create new User
			params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("query", LearningPunktBearbeitenFragment.this.cdbs.getKnotenMapper().insertQuery(learningpunkt)));
			
			// 0 -> No Response with objects expected
			params1.add(new BasicNameValuePair("flag", "0"));
			
			json = jsonParser.makeHttpRequest(cdbs.getUrl_knoten_queries(), "POST", params1);
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					lpMsg = "Es ist ein Datenbankfehler aufegtreten";
					return null;
				} else {
					
				}
			} catch (JSONException e) {
				lpMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			// Get new User-ID
			params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("query", LearningPunktBearbeitenFragment.this.cdbs.getKnotenMapper().getMaxKategorieIDQuery()));
			
			// 1 -> Response with objects expected (If user is registered already)
			params1.add(new BasicNameValuePair("flag", "1"));
						
			json = jsonParser.makeHttpRequest(cdbs.getUrl_maxid_queries(), "POST", params1);
			
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					lpMsg = "Es ist ein Datenbankfehler aufegtreten";
					return null;
				} else {
					learningpunkt.setId(LearningPunktBearbeitenFragment.this.cdbs.getKnotenMapper().json2NewID(json));
					
					//Insert user to local db
					LearningPunktBearbeitenFragment.this.cdbl.getKnotenMapper().insert(learningpunkt);
				}
			} catch (JSONException e) {
				lpMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			lpMsg = "Neue Learningline erfolgreich erstellt";
			return null;
		}
		
		
		
		
		private class learningPunktEdit{
			
			
			
			
			
		}
		
		
		
		
		
		
		protected void onPostExecute(Void result) {
			
			Toast einToast = Toast.makeText(LearningPunktBearbeitenFragment.this.baseActivity.getApplicationContext(), lpMsg, Toast.LENGTH_LONG);
			einToast.show();
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			MainMenuFragment mf = new MainMenuFragment();
        	mf.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, mf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
			 
			
			
			
	    	   
		}
	}

}
