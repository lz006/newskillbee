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
    	int llid = getArguments().getInt("llid");   
    	int llkategorie = getArguments().getInt("llkategorie");
		String llbezeichnung =getArguments().getString("llbezeichnung");
		View v = inflater.inflate(R.layout.learningline_bearbeiten_fragment, container, false);
//		Vector<Integer> vector = new Vector<Integer>();
//		vector.add(llid);
		
	learningline= new Learningline();
//		cdbl.getLearninglineMapper().findByKeys(vector);
		learningline.setId(llid);
		
	
	
		
	//	learningline.getBezeichnung();
	//	learningline.getKategorieID();
	
		
		Button btnregister = (Button) v.findViewById(R.id.btnllbsave);
		btnregister.setOnClickListener(this);
		Button btnlldelete = (Button) v.findViewById(R.id.btnlldelete);
		btnlldelete.setOnClickListener(this);
		titel = (EditText)v.findViewById(R.id.textboxLlTitel);
		kategorie = (Spinner)v.findViewById(R.id.kategorie);
		
		titel.setText(llbezeichnung);
		kategorie.setSelection(llkategorie);
		
	
//		spinner2 = (Spinner) v.findViewById(R.id.kategorie);
//		List<String> list = new ArrayList<String>();
//		list.add("list 1");
//		list.add("list 2");
//		list.add("list 3");
//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(null, android.R.layout.simple_spinner_item, list);
//		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner2.setAdapter(dataAdapter);

		
		
		
		
		
		return v;
		
//	      return inflater.inflate(
//	      R.layout.user_registration_fragment, container, false);
	}
	
	
	

	@Override
    public void onClick(View v) {
		
		
		
        switch (v.getId()) {
        case R.id.btnllbsave:
        	//Create User temporarily               	
        	
        	
        	learningline.setBezeichnung(titel.getText().toString());
        	learningline.setKategorieID(kategorie.getSelectedItemPosition());
        	learningline.setAuthorID(cdbl.getUserMapper().findAll().elementAt(0).getId());
        	
        	
        	
        	
        	
        	learningline = cdbl.getLearninglineMapper().update(learningline);
        	//onPostExecute2();
        	showMessage("Learning Line wurde erfolgreich ge�ndert");
        	
	//       	new learningsave().execute();        
	       	
        break;
        
        case R.id.btnlldelete:
        	
        	
        	cdbl.getLearninglineMapper().delete(learningline);
      //  	onPostExecute2();
        	showMessage("Learning Line wurde erfolgreich gel�scht");
        	
        break;
        }
        }
        
        
        
public void onPostExecute2() {
			
			Toast einToast = Toast.makeText(LearninglineBearbeitenFragment.this.baseActivity.getApplicationContext(), llMsg, Toast.LENGTH_LONG);
			einToast.show();
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			MainMenuFragment mf = new MainMenuFragment();
        	mf.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, mf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
        

         	
        
        
    }
	
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

	
	
	private class learningsave extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Building Parameters
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
		
			JSONObject json = null;
			
	
			
			// Create new User
			params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("query", LearninglineBearbeitenFragment.this.cdbs.getLearninglineMapper().insertQuery(learningline)));
			
			// 0 -> No Response with objects expected
			params1.add(new BasicNameValuePair("flag", "0"));
			
			json = jsonParser.makeHttpRequest(cdbs.getUrl_learningline_queries(), "POST", params1);
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					llMsg = "Es ist ein Datenbankfehler aufegtreten";
					return null;
				} else {
					
				}
			} catch (JSONException e) {
				llMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			// Get new User-ID
			params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("query", LearninglineBearbeitenFragment.this.cdbs.getLearninglineMapper().getMaxKategorieIDQuery()));
			
			// 1 -> Response with objects expected (If user is registered already)
			params1.add(new BasicNameValuePair("flag", "1"));
						
			json = jsonParser.makeHttpRequest(cdbs.getUrl_maxid_queries(), "POST", params1);
			
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					llMsg = "Es ist ein Datenbankfehler aufegtreten";
					return null;
				} else {
					learningline.setId(LearninglineBearbeitenFragment.this.cdbs.getLearninglineMapper().json2NewID(json));
					
					
				}
			} catch (JSONException e) {
				llMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			llMsg = "Neue Learningline erfolgreich erstellt";
			return null;
		}
		
		
		
		
		
		
	 

		
		
		public void onPostExecute() {
			
			Toast einToast = Toast.makeText(LearninglineBearbeitenFragment.this.baseActivity.getApplicationContext(), llMsg, Toast.LENGTH_LONG);
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