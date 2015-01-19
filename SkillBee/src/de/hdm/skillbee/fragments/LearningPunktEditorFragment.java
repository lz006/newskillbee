package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import de.hdm.skillbee.R;
import de.hdm.skillbee.bo.Knoten;
import de.hdm.skillbee.bo.Learningline;
import de.hdm.skillbee.controller.ControllerDBLokal;

public class LearningPunktEditorFragment extends ListFragment implements OnItemClickListener {

	String[] menutitles;
	Vector<Knoten> allnodes = new Vector<Knoten>(); 
    Activity baseActivity;
    ControllerDBLokal cdbl = null;
    Knoten selectednode;
    Button nodecreatebutton = null;
    int llid= 0;
    int llkategorie = 0;
    String llbezeichnung ="";
    

    Adapterwithouticons adapter;
    private List<RowItemLLOverview> rowItemslloverview;
    
    public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}
	
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.fragment_learningpunkt_editor, null, false);
    	cdbl = ControllerDBLokal.get();
    	
         llid = getArguments().getInt("llid");   
    	 llkategorie = getArguments().getInt("llkategorie");
		 llbezeichnung =getArguments().getString("llbezeichnung");
    	
    	nodecreatebutton = (Button)v.findViewById(R.id.btnnodeerstellen);
    	setOnClickListener();
        return v;
        
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

     //   menutitles = getResources().getStringArray(R.array.titles);
        
        
        Learningline ll = new Learningline();
        ll.setId(llid);
        ll.setKategorieID(llkategorie);
        ll.setBezeichnung(llbezeichnung);
        
        allnodes = cdbl.getKnotenMapper().findByLL(ll);
        
        
        
        if(allnodes!=null)
        {
        menutitles = new String[allnodes.size()];
        for(int i=0; i<allnodes.size();i++)
        {
        menutitles[i]= allnodes.elementAt(i).getUeberschrift();
        }
        }
        else
        {
        	Toast.makeText(getActivity(),"Es sind keine Knoten in der lokalen Datenbank",Toast.LENGTH_SHORT);
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
	
	public void setOnClickListener() {
		nodecreatebutton.setOnClickListener(new OnClickListener() {
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
        
    	this.selectednode=allnodes.elementAt(position);
    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		 Bundle args = new Bundle();
		 args.putString("knotenbezeichnung", selectednode.getUeberschrift());
		 args.putInt("knotenid", selectednode.getId());
//		 LearninglineBearbeitenFragment llb = new LearninglineBearbeitenFragment();
//    	llb.setBaseActivity(baseActivity);
//    	llb.setArguments(args);
//    	fragmentTransaction.replace(R.id.fragmentcontainer, llb);
//    	fragmentTransaction.addToBackStack(null);
//     	fragmentTransaction.commit();
    	
        
}


	
	
	
	
	

}
