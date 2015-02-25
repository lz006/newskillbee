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

/**
 * Fragmentklasse, die für die Funktionen des Learningline Editors(Learningline auswählen) zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class LpgotoQuestionFragment extends ListFragment implements OnItemClickListener, OnItemLongClickListener {

	String[] menutitles;
	Vector<Learningline> alllines = new Vector<Learningline>(); 
    Activity baseActivity;
    ControllerDBLokal cdbl = null;
    Learningline selectedlearningline;
    Button llcreatebutton = null;
    

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
    	View v = inflater.inflate(R.layout.fragment_lpgotoquestion_fragment, null, false);
    	cdbl = ControllerDBLokal.get();
    	
    	llcreatebutton = (Button)v.findViewById(R.id.btnllerstellen);
    	setOnClickListener();
        return v;
        
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
        //Füge der Arraylist alle Learning Lines als rowitem hinzu
        for (int i = 0; i < menutitles.length; i++) {
            RowItemLLOverview items = new RowItemLLOverview(menutitles[i]);

            rowItemslloverview.add(items);
        }
        }
      //Adapter instanziieren
        adapter = new Adapterwithouticons(getActivity(), rowItemslloverview);
      //Adapter setzen
        setListAdapter(adapter);
      //Clicklistener Event zur Liste für kurze Clicks hinzufügen
        getListView().setOnItemClickListener(this);
      //Clicklistener Event zur Liste für lange Clicks hinzufügen
        getListView().setOnItemLongClickListener(this);

    }
	
    /**
     * Click Listener setzen, der aufgerufen wird sobald der "Learningline erstellen" Button angeklickt wird
     */
	public void setOnClickListener() {
		llcreatebutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				 //Initialisiere Fragmentaustausch
		    	FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			 
		    	//Instanziiere LearninglineErstellenFragment
			    LearninglineErstellenFragment lle = new LearninglineErstellenFragment();
			    //Übergebe die Hauptaktivität an eine Methode im LearningLineErstellenFragment
		    	lle.setBaseActivity(baseActivity);
		    	//Ersetze das Fragment
		    	fragmentTransaction.replace(R.id.fragmentcontainer, lle);
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
        
		 //Verweis auf die selektierte/angeklickte Learningline anhand der Position des angeklickten Listenelements 
    	this.selectedlearningline=alllines.elementAt(position);
    	//Ruft die Methode shortclick auf und übergibt die position und die Id des Listenelements
    	shortclick(position,id);
        
}

    /**
	 * ClickListener Event Handler
	 * Wird ausgeführt sobald lang auf ein Listenelement geklickt wird und gibt den boolschen wert true zurück 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 * @return
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		//Verweis auf die selektierte/angeklickte Learningline anhand der Position des angeklickten Listenelements 
		this.selectedlearningline=alllines.elementAt(position);
		//Ruft die Methode longclick auf und übergibt die position und die Id des Listenelements
		longclick(position,id);
		return true;
	}
	
	/**
	 * Wird vom Event Handler aufgerufen, wenn ein kurzer Klick getätigt wird
	 * @param position
	 * @param id
	 */
	public void shortclick(int position, long id){
		//Zeigt an, dass ein kurzer Klick getätigt wurde 
		Toast.makeText(getActivity(),"shortclick: "+ position, Toast.LENGTH_SHORT)
         .show();
		//Initialisiere Fragmentaustausch
		 FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		 //Instanziiere Bundle um Parameter an nächstes Fragment übergeben zu können
		 Bundle args = new Bundle();
		 //Setze die Learningline Bezeichnung als Argument von dem Bundle
		 args.putString("llbezeichnung", selectedlearningline.getBezeichnung());
		//Setze die Learningline Id als Argument von dem Bundle
		 args.putInt("llid", selectedlearningline.getId());
		//Setze die Learningline Kategorie als Argument von dem Bundle
		 args.putInt("llkategorie",selectedlearningline.getKategorieID());
		//Instanziiere LearningLineBearbeitenFragment
		 LearninglineBearbeitenFragment llb = new LearninglineBearbeitenFragment();
		 //Übergebe die Hauptaktivität an eine Methode im LearningLineBearbeitenFragment
		 llb.setBaseActivity(baseActivity);
		 //Übergebe die Argumente an eine Methode im LearningLineBearbeitenFragment
     	llb.setArguments(args);
     	//Fragment ersetzen
     	fragmentTransaction.replace(R.id.fragmentcontainer, llb);
     	fragmentTransaction.addToBackStack(null);
      	fragmentTransaction.commit();
		
	}
	
	/**
	 * Wird vom Event Handler aufgerufen, wenn ein langer Klick getätigt wird
	 * @param position
	 * @param id
	 */
	public void longclick(int position, long id){
		//Zeigt an, dass ein kurzer Klick getätigt wurde 
		 Toast.makeText(getActivity(),"longclick: "+ position, Toast.LENGTH_SHORT)
         .show();
		//Initialisiere Fragmentaustausch
		 FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		//Instanziiere LearningPunktEditorFragment
		 LearningPunktEditorFragment lpef = new LearningPunktEditorFragment();
		//Übergebe die ausgewählte Learningline an eine Methode im LearningPunktEditorFragment
     	lpef.setLL(selectedlearningline);
     	//Übergebe die Hauptaktivität an eine Methode im LearningPunktEditorFragment
		 lpef.setBaseActivity(baseActivity);
		 //Ersetze Fragment
     	fragmentTransaction.replace(R.id.fragmentcontainer, lpef);
     	fragmentTransaction.addToBackStack(null);
      	fragmentTransaction.commit();
	}

}
