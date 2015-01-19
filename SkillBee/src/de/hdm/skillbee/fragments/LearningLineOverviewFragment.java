package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.hdm.skillbee.R;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import de.hdm.skillbee.bo.*;
import de.hdm.skillbee.controller.ControllerDBLokal;
import de.hdm.skillbee.db.local.*;

public class LearningLineOverviewFragment extends ListFragment implements OnItemClickListener {

	String[] menutitles;
	String[] menutitles2;
	Vector<Learningline> alllines = new Vector<Learningline>(); 
	Object[] test;
    Activity baseActivity;
    ControllerDBLokal cdbl = null;
    Learningline selectedlearningline;

    Adapterwithouticons adapter;
    private List<RowItemLLOverview> rowItemslloverview;
    
    public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}
	
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

    	cdbl = ControllerDBLokal.get();
        return inflater.inflate(R.layout.fragment_lloverview, null, false);
        
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

    }
	
	
	 @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position,
	            long id) {

		 this.selectedlearningline=alllines.elementAt(position);
		 FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		 LearningLineFragment llf = new LearningLineFragment();
		 llf.setLL(selectedlearningline);
	    	llf.setBaseActivity(baseActivity);
//	    	Bundle args = new Bundle();
	    	fragmentTransaction.replace(R.id.fragmentcontainer, llf);
	    	fragmentTransaction.addToBackStack(null);
	     	fragmentTransaction.commit();
//	     	llf.setLL(selectedlearningline);
	        Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT)
	                .show();
	        
	
}
}
