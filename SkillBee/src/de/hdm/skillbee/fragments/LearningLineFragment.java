package de.hdm.skillbee.fragments;

import java.util.Vector;

import de.hdm.skillbee.R;
import de.hdm.skillbee.bo.Knoten;
import de.hdm.skillbee.bo.Learningline;
import de.hdm.skillbee.controller.ControllerDBLokal;
import android.app.Activity;
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
	
	
	public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    		
		v = inflater.inflate(R.layout.learningline_fragment, container, false);
				
		cdbl = ControllerDBLokal.get();
		rl = (RelativeLayout)v.findViewById(R.id.buttonpanel);
		buildLL();
			
		return v;
	}
	
	public void setLL(Learningline ll) {
		this.ll = ll;
	}
	
	public void buildLL() {
		
		knotenVector= new Vector<Knoten>();
		knotenVector = cdbl.getKnotenMapper().findByLL(ll);
		
		int y = 0;
		
		for(int i = 0; i < knotenVector.size(); i++) {
			
			Button button = new Button(baseActivity);
			Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.buttonsmall154);
			BitmapDrawable backgroundDrawable = new BitmapDrawable(getResources(), backgroundBitmap);
			backgroundDrawable.setGravity(Gravity.CENTER); // also LEFT, CENTER_VERTICAL, etc.
			//backgroundDrawable.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP));
			button.setBackground(backgroundDrawable);
			
			
			button.setText(knotenVector.elementAt(i).getUeberschrift());
			
			
			button.setHeight(154);
			button.setWidth(154);
			
			
			button.setOnClickListener(new OnClickListener() {
				final int j = pointer;
				@Override
				public void onClick(View v) {
					
					String number = knotenVector.elementAt(j).getUeberschrift();
					//Toast einToast = Toast.makeText(LearningLineFragment.this.getActivity(), number, Toast.LENGTH_SHORT);
					//einToast.show();
					
					//LearnunkingPtFragment lpf = new LearningPunktFragment();
		        	//fragmentTransaction.replace(R.id.fragmentcontainer, lpf);
		        	//fragmentTransaction.addToBackStack(null);
		         	//fragmentTransaction.commit();
					//lpg.setKnoten(knotenVector.elementAt(j));
					
				}
				
			});
			
			pointer++;
			
			//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			//	params.weight = 1.0f;
			//button.setLayoutParams(params);
			
			
			ImageView img = new ImageView(this.getActivity());
			img.setImageResource(R.drawable.verbindungmittel);
			//img.setLayoutParams(params);
			
			
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
			
			//RelativeLayout je Knoten verg��ern
			rl.getLayoutParams().height = rl.getLayoutParams().height + 154;
			
			button.bringToFront();
			
		}
	}
	
	
}
