package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import de.hdm.skillbee.R;
import de.hdm.skillbee.bo.Learningline;
import de.hdm.skillbee.bo.User;
import de.hdm.skillbee.controller.ControllerDBLokal;
import android.view.View.OnClickListener;


public class LpgotoQuestionFragment extends ListFragment implements OnItemClickListener, OnItemLongClickListener {

	String[] menutitles;
	Vector<Learningline> alllines = new Vector<Learningline>(); 
    Activity baseActivity;
    ControllerDBLokal cdbl = null;
    Learningline selectedlearningline;
    Button llcreatebutton = null;
    

    Adapterwithouticons adapter;
    private List<RowItemLLOverview> rowItemslloverview;
    
    public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}
	
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.fragment_lpgotoquestion_fragment, null, false);
    	cdbl = ControllerDBLokal.get();
    	
    	llcreatebutton = (Button)v.findViewById(R.id.btnllerstellen);
    	setOnClickListener();
        return v;
        
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

     //   menutitles = getResources().getStringArray(R.array.titles);
        
        alllines = cdbl.getLearninglineMapper().findAll();
        
        
        
        if(alllines!=null)
        {
        menutitles = new String[alllines.size()];
        for(int i=0; i<alllines.size();i++)
        {
        menutitles[i]= alllines.elementAt(i).getBezeichnung();
        }
        }
        else
        {
        	Toast.makeText(getActivity(),"Es sind keine Learning Lines in der lokalen Datenbank",Toast.LENGTH_SHORT);
        }
        
        
        
        
        

        rowItemslloverview = new ArrayList<RowItemLLOverview>();

        
        if(menutitles!=null)
        {
        for (int i = 0; i < menutitles.length; i++) {
            RowItemLLOverview items = new RowItemLLOverview(menutitles[i]);

            rowItemslloverview.add(items);
        }
        }
        adapter = new Adapterwithouticons(getActivity(), rowItemslloverview);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);

    }
	
	public void setOnClickListener() {
		llcreatebutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			 
			
			    LearninglineErstellenFragment lle = new LearninglineErstellenFragment();
		    	lle.setBaseActivity(baseActivity);
		    	fragmentTransaction.replace(R.id.fragmentcontainer, lle);
		    	fragmentTransaction.addToBackStack(null);
		     	fragmentTransaction.commit();
			}
		});
		
	       
    }
	
    
	 


	
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        
    	this.selectedlearningline=alllines.elementAt(position);
    	shortclick(position,id);
        
}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		this.selectedlearningline=alllines.elementAt(position);
		longclick(position,id);
		return true;
	}
	
	
	public void shortclick(int position, long id){
		 Toast.makeText(getActivity(),"shortclick: "+ position, Toast.LENGTH_SHORT)
         .show();
		
		 FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		 Bundle args = new Bundle();
		 args.putString("llbezeichnung", selectedlearningline.getBezeichnung());
		 args.putInt("llid", selectedlearningline.getId());
		 args.putInt("llkategorie",selectedlearningline.getKategorieID());
		 LearninglineBearbeitenFragment llb = new LearninglineBearbeitenFragment();
     	llb.setBaseActivity(baseActivity);
     	llb.setArguments(args);
     	fragmentTransaction.replace(R.id.fragmentcontainer, llb);
     	fragmentTransaction.addToBackStack(null);
      	fragmentTransaction.commit();
		
	}
	
	public void longclick(int position, long id){
		 Toast.makeText(getActivity(),"longclick: "+ position, Toast.LENGTH_SHORT)
         .show();
		 FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//		 Bundle args = new Bundle();
//		 args.putString("llbezeichnung", selectedlearningline.getBezeichnung());
//		 args.putInt("llid", selectedlearningline.getId());
//		 args.putInt("llkategorie",selectedlearningline.getKategorieID());
		 LearningPunktEditorFragment lpef = new LearningPunktEditorFragment();
     	lpef.setLL(selectedlearningline);
		 lpef.setBaseActivity(baseActivity);
     	fragmentTransaction.replace(R.id.fragmentcontainer, lpef);
     	fragmentTransaction.addToBackStack(null);
      	fragmentTransaction.commit();
	}

}
