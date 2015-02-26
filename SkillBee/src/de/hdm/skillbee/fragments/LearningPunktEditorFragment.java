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


/**
 * 
 * Fragmentklasse, die zum Anzeigen des Editor-Übersicht der Learning-Knoten zuständig ist.
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class LearningPunktEditorFragment extends ListFragment implements OnItemClickListener {

	String[] menutitles;
	Vector<Knoten> allnodes = new Vector<Knoten>(); 
    Activity baseActivity;
    ControllerDBLokal cdbl = null;
    Knoten selectednode;
    Learningline ll =null;
    Button nodecreatebutton = null;
    int llid= 0;
    int llkategorie = 0;
    String llbezeichnung ="";
    

    Adapterwithouticons adapter;
    private List<RowItemLLOverview> rowItemslloverview;
    
    
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
    	//View der das Layout ladet 
    	View v = inflater.inflate(R.layout.fragment_learningpunkt_editor, null, false);
    	cdbl = ControllerDBLokal.get();
    
		//Button zum erstellen neuer Learning-Knoten.
    	nodecreatebutton = (Button)v.findViewById(R.id.btnnodeerstellen);
    	setOnClickListener();
        return v;
        
    }
    
    
    /**
 	  * Methode die alle Knoten der ausgewählten Learningline Anzeigt und beschriftet.
 	  * @param savedInstanceState
 	  */
    
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

     
        
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
    
    
    
    
    
    /**
     * Learninglineindex setzen von vorhergelagertem Fragment
     * @param ll
     */
    
    
    public void setLL(Learningline ll) {
		this.ll = ll;
	}
	
    
    
    
    
    
    /**
 	  * Methode die das Fragment wechselte wenn man auf einen Learning-Knoten klickt.
 	  */
    
    
	public void setOnClickListener() {
		nodecreatebutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			 
			
			    LearningPunktErstellenFragment lpef = new LearningPunktErstellenFragment();
		    	lpef.setLL(ll);
			    lpef.setBaseActivity(baseActivity);
		    	fragmentTransaction.replace(R.id.fragmentcontainer, lpef);
		    	fragmentTransaction.addToBackStack(null);
		     	fragmentTransaction.commit();
			}
		});
		
	       
    }
	
    
	 

    /**
	 * ClickListener Event Handler
	 * Wird ausgeführt sobald kurz auf ein Listenelement geklickt wird 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        
    	this.selectednode=allnodes.elementAt(position);
    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		 Bundle args = new Bundle();

		 LearningPunktBearbeitenFragment lpb = new LearningPunktBearbeitenFragment();
    	lpb.setKnoten(selectednode);
		 lpb.setBaseActivity(baseActivity);
    	fragmentTransaction.replace(R.id.fragmentcontainer, lpb);
    	fragmentTransaction.addToBackStack(null);
     	fragmentTransaction.commit();
    	
        
}


	
	
	
	
	

}
