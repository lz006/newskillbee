package de.hdm.skillbee.fragments;

import java.util.Vector;

import de.hdm.skillbee.R;
import de.hdm.skillbee.bo.Knoten;
import de.hdm.skillbee.bo.Learningline;
import de.hdm.skillbee.controller.ControllerDBLokal;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

/**
 * 
 * Fragmentklasse, die zur grafischen Ansicht der ausgewählten Learningline zuständig ist und die Learning-Knoten anzeigt.
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class LearningLineFragment extends Fragment {

	Activity baseActivity = null;
	ControllerDBLokal cdbl = null;
	Vector<Knoten> knotenVector = null;
	RelativeLayout rl = null;
	TableLayout tl = null;
	ScrollView sv = null;
	int pointer = 0;
	Learningline ll = null;
	
	View v = null;
	
	
	
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
	    		
		
		//View der das Layout ladet
		v = inflater.inflate(R.layout.learningline_fragment, container, false);
				
		
		cdbl = ControllerDBLokal.get();
		rl = (RelativeLayout)v.findViewById(R.id.buttonpanel);
		pointer=0;
		buildLL();
			
		return v;
	}
	
	
	
	/**
     * Learninglineindex setzen von vorhergelagertem Fragment
     * @param ll
     */
	
	public void setLL(Learningline ll) {
		this.ll = ll;
	}
	
	
	

	  /**
	   * 
	  * Methode die zuständig ist die Learningline grafisch anzuzeigen und die Knoten auszugeben.
	  * 	 
	  */
	
	
	public void buildLL() {
		
		knotenVector= new Vector<Knoten>();
		knotenVector = cdbl.getKnotenMapper().findByLL(ll);
		
		if (knotenVector != null) {
			
			
			int y = 0;
			
			for(int i = 0; i < knotenVector.size(); i++) {
				
				Button button = new Button(baseActivity);
				Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.buttonsmall154);
				BitmapDrawable backgroundDrawable = new BitmapDrawable(getResources(), backgroundBitmap);
				backgroundDrawable.setGravity(Gravity.CENTER); // also LEFT, CENTER_VERTICAL, etc.
				//backgroundDrawable.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP));
				button.setBackground(backgroundDrawable);
				
				
				button.setText(knotenVector.elementAt(i).getUeberschrift());
				
				// hier wird die Knoten größe definiert
				button.setHeight(154);
				button.setWidth(154);
				
				
				button.setOnClickListener(new OnClickListener() {
					final int j = pointer;
					@Override
					public void onClick(View v) {
				    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

						LearningPunktFragment lpf = new LearningPunktFragment();
			        	fragmentTransaction.replace(R.id.fragmentcontainer, lpf);
			        	fragmentTransaction.addToBackStack(null);
			         	fragmentTransaction.commit();
			         	lpf.setKnoten(knotenVector.elementAt(j));
						
					}
					
				});
				
				pointer++;
				
	
				
				// Verbindungselement der Knoten als Bild mit einem Strich
				ImageView img = new ImageView(this.getActivity());
				img.setImageResource(R.drawable.verbindungmittel);
		
				
				button.setY(y);
				img.setY(y-27);
				img.setX(72);
				y += 154;
				
				button.setMinimumHeight(0);
				button.setMinimumWidth(0);
				button.setPadding(0, 0, 0, 0);
				button.setPaddingRelative(0, 0, 0, 0);
				
				rl.addView(button);
				if (i != 0) {
					rl.addView(img);
				}
				
				//RelativeLayout jeder Knoten verlängert die Ansicht
				rl.getLayoutParams().height = rl.getLayoutParams().height + 154;
				
				button.bringToFront();
				
			}
		}
		
		else{
			
			// Ausgabe einer Nachricht falls in der ausgewählten Learningline keine Knoten angelegt sind.
			Toast keineKnoten = Toast.makeText(LearningLineFragment.this.getActivity(), "Keine Knoten gefunden in der ausgewÃ¤hlten Learningline", Toast.LENGTH_SHORT);
			keineKnoten.show();
			
		}
			
		
	}
	
	
}
