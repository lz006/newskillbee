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

public class UserRegistrationFragment extends Fragment implements OnClickListener{

	Activity baseActivity = null;
	
	JSONParser jsonParser = new JSONParser();
	
	EditText emailadress;
	EditText firstname;
	EditText lastname;
	EditText password;
	
	User user = null;
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	String userMsg = null;
	boolean useralreadyexists=false;
	
	
	
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
		
		
		View v = inflater.inflate(R.layout.user_registration_fragment, container, false);
		Button btnregister = (Button) v.findViewById(R.id.btnregisteruser);
		btnregister.setOnClickListener(this);
		 firstname = (EditText)v.findViewById(R.id.textboxfirstname);
		 lastname = (EditText)v.findViewById(R.id.textboxlastname);
		 emailadress = (EditText)v.findViewById(R.id.textboxemail);
		 password =(EditText)v.findViewById(R.id.textboxpassword);
		
		return v;
		
//	      return inflater.inflate(
//	      R.layout.user_registration_fragment, container, false);
	}
	
	
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnregisteruser:
        	//Create User temporarily               	
	        user = new User();
	        user.setVorname(firstname.getText().toString());
	        user.setName(lastname.getText().toString());
	        user.setEmail(emailadress.getText().toString());
	        user.setPasswort(password.getText().toString());
	       	new registerUser().execute();        
	       	
        break;
        }
    }
	
	private class registerUser extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Building Parameters
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			// SQL-Query wird erzeugt
			params1.add(new BasicNameValuePair("query", UserRegistrationFragment.this.cdbs.getUserMapper().findByUserAndPW(user)));
			
			// 1 -> Response with objects expected (If user is registered already)
			params1.add(new BasicNameValuePair("flag", "1"));
			
			// SQL-Query wird mittels Request an den Server übertragen
			JSONObject json = jsonParser.makeHttpRequest(cdbs.getUrl_user_queries(), "POST", params1);
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					// Success = 1 -> Means that password and user are not in use
				} else {
					userMsg = "Nutzername und Passwort sind bereits in Verwendung";
					useralreadyexists=true;
					return null;
				}
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			// Create new User
			params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("query", UserRegistrationFragment.this.cdbs.getUserMapper().insertQuery(user)));
			
			// 0 -> No Response with objects expected
			params1.add(new BasicNameValuePair("flag", "0"));
			
			json = jsonParser.makeHttpRequest(cdbs.getUrl_user_queries(), "POST", params1);
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					userMsg = "Es ist ein Datenbankfehler aufegtreten";
					return null;
				} else {
					
				}
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			// Get new User-ID
			params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("query", UserRegistrationFragment.this.cdbs.getUserMapper().getUserIDQuery(user)));
			
			// 1 -> Response with objects expected (If user is registered already)
			params1.add(new BasicNameValuePair("flag", "1"));
						
			json = jsonParser.makeHttpRequest(cdbs.getUrl_maxid_queries(), "POST", params1);
			
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					userMsg = "Es ist ein Datenbankfehler aufegtreten";
					return null;
				} else {
					user.setId(UserRegistrationFragment.this.cdbs.getUserMapper().json2NewID(json));
					
					//Insert user to local db
					UserRegistrationFragment.this.cdbl.getUserMapper().insert(user);
				}
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			userMsg = "Neuer User erfolgreich erstellt";
			return null;
		}
		protected void onPostExecute(Void result) {
			
			Toast einToast = Toast.makeText(UserRegistrationFragment.this.baseActivity.getApplicationContext(), userMsg, Toast.LENGTH_LONG);
			einToast.show();
			
			if(!useralreadyexists)
			{
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			MainMenuFragment mf = new MainMenuFragment();
        	mf.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, mf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
			}
			
			
			
	    	   
		}
	}

}
