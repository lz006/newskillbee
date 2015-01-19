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
import android.widget.Toast;

public class LearningPunktFragment extends Fragment implements OnClickListener{
	
	
	
	Activity baseActivity = null;
	
	ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	
	Knoten kn = null;
	String MedienG = null;
	String MedienY = null;
	String LearningInhaltLong = null;

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
		
		
		
		
		
		
		View v = inflater.inflate(R.layout.learningpunkt_fragment, container, false);
		Button InhaltLong = (Button) v.findViewById(R.id.InhaltShort);
		InhaltLong.setOnClickListener(this);
		
		Button InhaltLong2 = (Button) v.findViewById(R.id.LearningInhaltLong);
		InhaltLong2.setOnClickListener(this);
		
		Button MedienG = (Button) v.findViewById(R.id.MedienG);
		MedienG.setOnClickListener(this);
		
		Button MedienY = (Button) v.findViewById(R.id.MedienY);
		MedienY.setOnClickListener(this);
	
		
		EditText lp = (EditText)v.findViewById(R.id.LearningPunkt);
		EditText is = (EditText)v.findViewById(R.id.InhaltShort);
		
		lp.setText(kn.getUeberschrift());
		is.setText(kn.getKurzInhalt());
		
		
		
		
		

//		
//		EditText MedienGInhalt = kn.getGoogleLink();
//		EditText MedienGInhalt = kn.getYoutubeLink()
//			
		
	
		
		return v;
		
		
}

	@Override
public void onClick(View v) {
		
    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

		
        switch (v.getId()) {

        case R.id.LearningInhaltLong:
        	             	
			LearningPunktInhaltFragment lpif = new LearningPunktInhaltFragment();
			lpif.setKnoten(kn);
			lpif.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, lpif);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
        
        case R.id.InhaltShort:
         	
			LearningPunktInhaltFragment lpif2 = new LearningPunktInhaltFragment();
			lpif2.setKnoten(kn);
			lpif2.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, lpif2);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
        
        	
        	
        	
        break;
        }
	}
	
	public void setKnoten(Knoten kn) {
		
		this.kn = kn;
		
		

		
	}
	
	

	
}