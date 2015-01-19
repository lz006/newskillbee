package de.hdm.skillbee.fragments;

import de.hdm.skillbee.R;
import de.hdm.skillbee.controller.ControllerDBLokal;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class UserLogOrRegFragment extends Fragment implements OnClickListener{

	Activity baseActivity = null;
		
	public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	      		
		
		View v = inflater.inflate(R.layout.user_logorreg_fragment, container, false);
		Button loginButton = (Button) v.findViewById(R.id.buttonlogin);
		loginButton.setOnClickListener(this);
		Button registerButton = (Button) v.findViewById(R.id.buttonregister);
		registerButton.setOnClickListener(this);
		
		return v;
		
//	      return inflater.inflate(
//	      R.layout.user_registration_fragment, container, false);
	}
	
	
	@Override
    public void onClick(View v) {
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (v.getId()) {
	        case R.id.buttonlogin:
	        	UserLoginFragment lf = new UserLoginFragment();
	        	lf.setBaseActivity(baseActivity);
	        	fragmentTransaction.replace(R.id.fragmentcontainer, lf);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	         	break;
	        case R.id.buttonregister:	        
	        	UserRegistrationFragment rf = new UserRegistrationFragment();
	        	rf.setBaseActivity(baseActivity);
	        	fragmentTransaction.replace(R.id.fragmentcontainer, rf);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	         	break;
        }
    }


	@Override
	public void onResume() {
		ControllerDBLokal con = ControllerDBLokal.get();
		if(con.getUserMapper().findAll()==null) {
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			/*
			 * Falls ein User sich angemeldet hat und zu "diesem Fragment hier" zurückkehr muss 
			 * dies abgefangen werden und so dass der user bspw. zum Hauptmenu weitergleitet wird
			 */
		}
		super.onResume();
	}
	
	

}
