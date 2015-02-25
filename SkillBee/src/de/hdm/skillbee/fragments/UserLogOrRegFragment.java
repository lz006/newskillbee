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


/**
 * Fragmentklasse, die f�r die Funktionen der Startseite zust�ndig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class UserLogOrRegFragment extends Fragment implements OnClickListener{

	Activity baseActivity = null;
		
	  /**
     * Verweis zur Hauptaktivit�t setzen
     * @param baseActivity
     */
	public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}

	   /**
     * Methode wird aufgerufen sobald die Ansicht erzeugt werden soll, die dann hier auch zur�ckgegeben wird
     * Hier wird die Ansicht aufgebaut
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	      		
		
		View v = inflater.inflate(R.layout.user_logorreg_fragment, container, false);
		Button loginButton = (Button) v.findViewById(R.id.buttonlogin);
		//Clicklistener Event zum Login Button f�r Klicks hinzuf�gen
		loginButton.setOnClickListener(this);
		Button registerButton = (Button) v.findViewById(R.id.buttonregister);
		//Clicklistener Event zum Register Button f�r Klicks hinzuf�gen
		registerButton.setOnClickListener(this);
		
		return v;
		
	}
	
	/**
	 * Wird ausgef�hrt sobald ein Button angeklickt wird
	 * Dabei wird durch eine Fallunterscheidung entschieden ob der Login Button oder der Register Button 
	 * gedr�ckt wurde. Abh�ngig davon werden unterschiedliche Fragmente aufgerufen und ausgetauscht
	 * @param v
	 */
	@Override
    public void onClick(View v) {
		//Initialisiere Fragmentaustausch
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            //Login Button wurde bet�tigt
	        case R.id.buttonlogin:
	        	//Instanziiere UserLoginFragment
	        	UserLoginFragment lf = new UserLoginFragment();
	        	//�bergebe die ausgew�hlte Learningline an eine Methode im UserLoginFragment
	        	lf.setBaseActivity(baseActivity);
	        	 //Ersetze Fragment
	        	fragmentTransaction.replace(R.id.fragmentcontainer, lf);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	         	break;
	        //Register Button wurde bet�tigt 	
	        case R.id.buttonregister:	
	        	//Instanziiere UserRegistrationFragment
	        	UserRegistrationFragment rf = new UserRegistrationFragment();
	        	//�bergebe die ausgew�hlte Learningline an eine Methode im UserRegistrationFragment
	        	rf.setBaseActivity(baseActivity);
	        	//Ersetze Fragment
	        	fragmentTransaction.replace(R.id.fragmentcontainer, rf);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	         	break;
        }
    }

/**
 * Falls ein User sich bereits angemeldet hat und zu "diesem Fragment hier" zur�ckkehrt muss
 * wird dies abgefangen und der User wird zum Hauptme� weitergeleitet
 */
	@Override
	public void onResume() {
		ControllerDBLokal con = ControllerDBLokal.get();
		if(con.getUserMapper().findAll()==null) {
			//Initialisiere Fragmentaustausch
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			/*
			MainMenuFragment mf = new MainMenuFragment();
        	mf.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, mf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
			 */
		}
		super.onResume();
	}
	
	

}
