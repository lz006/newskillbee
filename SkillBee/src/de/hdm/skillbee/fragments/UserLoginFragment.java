package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdm.skillbee.R;
import de.hdm.skillbee.bo.User;
import de.hdm.skillbee.controller.ControllerDBLokal;
import de.hdm.skillbee.controller.ControllerDBServer;
import de.hdm.skillbee.db.server.JSONParser;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLoginFragment extends Fragment implements OnClickListener {

	Activity baseActivity = null;
	
	JSONParser jsonParser = new JSONParser();
	
	EditText emailadress;
	EditText firstname;
	EditText lastname;
	EditText password;
	String email;
	boolean loginsuccess;
	
	User user = null;
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	String userMsg = null;
	
	
	
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
		
    	
    	//email =user.getEmail();
		
		View v = inflater.inflate(R.layout.user_login_fragment, container, false);
		Button btnlogin = (Button) v.findViewById(R.id.btnloginuser);
		btnlogin.setOnClickListener(this);
		 emailadress = (EditText)v.findViewById(R.id.textboxLogEmail);
		 password =(EditText)v.findViewById(R.id.textboxLogPW);
		
		return v;
	}
	
	
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
	        case R.id.btnloginuser:
	        	//Create User temporarily               	
		        user = new User();
		        user.setEmail(emailadress.getText().toString());
		        user.setPasswort(password.getText().toString());
		       	new loginUser().execute();        
		        break;
		        
		        
		        
		        
        }
    }
	
	private class loginUser extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Building Parameters
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			// SQL-Query wird erzeugt
			params1.add(new BasicNameValuePair("query", UserLoginFragment.this.cdbs.getUserMapper().findByUserAndPW(user)));
			
			// 1 -> Response with objects expected (If user is registered already)
			params1.add(new BasicNameValuePair("flag", "1"));
			
			// SQL-Query wird mittels Request an den Server übertragen
			JSONObject json = jsonParser.makeHttpRequest(cdbs.getUrl_user_queries(), "POST", params1);
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success != 1) {
					userMsg = "Login war nicht erfolgreich, bitte überprüfen Sie Ihre Anmeldedaten";
					loginsuccess =false;
				} else {
					UserLoginFragment.this.cdbl.getUserMapper().insert(UserLoginFragment.this.cdbs.getUserMapper().json2User(json).elementAt(0));
					userMsg = "Login erfolgreich";
					loginsuccess =true;
					return null;
				}
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			
			
			return null;
		}
		protected void onPostExecute(Void result) {
			
			Toast einToast = Toast.makeText(UserLoginFragment.this.baseActivity.getApplicationContext(), userMsg, Toast.LENGTH_LONG);
			einToast.show();
			
			if(loginsuccess)
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
