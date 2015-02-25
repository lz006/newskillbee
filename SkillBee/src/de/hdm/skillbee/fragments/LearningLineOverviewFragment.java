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


/**
 * Fragmentklasse, die für die Funktionen der Learningline Übersicht zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

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
    
    /**
     * Verweis zur Hauptaktivität setzen
     * @param baseActivity
     */
    public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}
	
    /**
     * Methode wird aufgerufen sobald die Ansicht erzeugt werden soll, die dann hier auch zurückgegeben wird
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

    	cdbl = ControllerDBLokal.get();
        return inflater.inflate(R.layout.fragment_lloverview, null, false);
        
    }
    
    /**
     * Methode wird aufgerufen sobald die Aktivität erzeugt wird
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

     //   menutitles = getResources().getStringArray(R.array.titles);
        
        //Alle lokalen Learninglines auslesen
        alllines = cdbl.getLearninglineMapper().findAll();
        
        
        //Überprüfen ob Learninglines lokal vorhanden sind
        if(alllines!=null)
        {
        menutitles = new String[alllines.size()];
        for(int i=0; i<alllines.size();i++)
        {
        //Bezeichnung der Learningline auslesen
        menutitles[i]= alllines.elementAt(i).getBezeichnung();
        }
        }
        //Wenn keine Learninglines lokal vorhanden sind, wird eine Meldung ausgegeben
        else
        {
        	Toast.makeText(getActivity(),"Es sind keine Learning Lines in der lokalen Datenbank",Toast.LENGTH_SHORT);
        }
        
        
        
        
        
        //Arraylist von Listenelementen
        rowItemslloverview = new ArrayList<RowItemLLOverview>();

        //Wenn lokale Learninglines vorhanden sind, dann fülle die Liste mit Elementen zusammen mit der Learningline Bezeichnung auf 
        if(menutitles!=null)
        {
        for (int i = 0; i < menutitles.length; i++) {
            RowItemLLOverview items = new RowItemLLOverview(menutitles[i]);

            rowItemslloverview.add(items);
        }
        }
        //Adapter instanziieren
        adapter = new Adapterwithouticons(getActivity(), rowItemslloverview);
        //Adapter setzen
        setListAdapter(adapter);
        //Clicklistener Event zur Liste hinzufügen
        getListView().setOnItemClickListener(this);

    }
	
	/**
	 * ClickListener Event Handler
	 * Wird ausgeführt sobald auf ein Listenelement geklickt wird 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	 @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position,
	            long id) {

		 //Verweis auf die selektierte/angeklickte Learningline anhand der Position des angeklickten Listenelements 
		 this.selectedlearningline=alllines.elementAt(position);
		 //Initialisiere Fragmentaustausch
		 FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		 //Instanziiere LearningLineFragment
		 LearningLineFragment llf = new LearningLineFragment();
		 //Übergebe die ausgewählte Learningline an eine Methode im LearningLineFragment
		 llf.setLL(selectedlearningline);
		 //Übergebe die Hauptaktivität an eine Methode im LearningLineFragment
	    	llf.setBaseActivity(baseActivity);
//	    	Bundle args = new Bundle();
	    	//Ersetze das Fragment
	    	fragmentTransaction.replace(R.id.fragmentcontainer, llf);
	    	fragmentTransaction.addToBackStack(null);
	     	fragmentTransaction.commit();
//	     	llf.setLL(selectedlearningline);
	     	//Zeige die Bezeichnung der ausgewählten Learningline an
	        Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT)
	                .show();
	        
	
}
}
