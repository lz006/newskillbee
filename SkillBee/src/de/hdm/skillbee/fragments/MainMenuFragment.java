package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import de.hdm.skillbee.R;

/**
 * 
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */
 
public class MainMenuFragment extends ListFragment implements OnItemClickListener {

    String[] menutitles;
    TypedArray menuIcons;
    Activity baseActivity;

    CustomAdapter adapter;
    private List<RowItem> rowItems;
    
    
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

        return inflater.inflate(R.layout.fragment_main_menu, null, false);
    }

    /**
     * Methode wird aufgerufen sobald die Aktivität erzeugt wird
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        
        //Alle Menütitel auslesen
        menutitles = getResources().getStringArray(R.array.titles);
        menuIcons = getResources().obtainTypedArray(R.array.icons);

        //Arraylist von Listenelementen
        rowItems = new ArrayList<RowItem>();

        //Füge der Arraylist alle Menütitel als rowitem hinzu
        for (int i = 0; i < menutitles.length; i++) {
            RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(
                    i, -1));

            rowItems.add(items);
        }
        
        //Adapter instanziieren
        adapter = new CustomAdapter(getActivity(), rowItems);
      //Adapter setzen
        setListAdapter(adapter);
      //Clicklistener Event zur Liste für Klicks hinzufügen
        getListView().setOnItemClickListener(this);

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

        Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT)
                .show();
        
      //Initialisiere Fragmentaustausch
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // In Abhängigkeit der Position des Listenelements wird das jeweilige Fragment aufgerufen und ausgetauscht
        switch(position)
        {
        //Wird aufgerufen, wenn das Listenelement die Position 0 hat. Dies entspricht dem ersten Listenelement
        case 0: 
        	//Instanziiere LearningLineOverviewFragment
	        LearningLineOverviewFragment llof = new LearningLineOverviewFragment();
	        //Übergebe die Hauptaktivität an eine Methode im LearningLineOverviewFragment
	    	llof.setBaseActivity(baseActivity);
	    	//Fragment ersetzen
	    	fragmentTransaction.replace(R.id.fragmentcontainer, llof);
	    	fragmentTransaction.addToBackStack(null);
	     	fragmentTransaction.commit();
	     	break;
	     	
	    //Wird aufgerufen, wenn das Listenelement die Position 1 hat. Dies entspricht dem zweiten Listenelement 	
        case 1:
        	//Instanziiere LpgotoQuestionFragment
        	LpgotoQuestionFragment lpgo = new LpgotoQuestionFragment();
        	//Übergebe die Hauptaktivität an eine Methode im LpgotoQuestionFragment
        	lpgo.setBaseActivity(baseActivity);
        	//Fragment ersetzen
        	fragmentTransaction.replace(R.id.fragmentcontainer, lpgo);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
         	break;
         	
        //Wird aufgerufen, wenn das Listenelement die Position 2 hat. Dies entspricht dem dritten Listenelement	
        case 2:
        	//Instanziiere StoreFragment
        	StoreFragment sf = new StoreFragment();
        	//Übergebe die Hauptaktivität an eine Methode im StoreFragment
        	sf.setBaseActivity(baseActivity);
        	//Fragment ersetzen
        	fragmentTransaction.replace(R.id.fragmentcontainer, sf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
         	break;
         	
        //Wird aufgerufen, wenn das Listenelement die Position 3 hat. Dies entspricht dem vierten Listenelement	
        case 3:
        	//Instanziiere Us_Fragment
        	Us_Fragment us = new Us_Fragment();
        	//Übergebe die Hauptaktivität an eine Methode im Us_Fragment
        	us.setBaseActivity(baseActivity);
        	//Fragment ersetzen
        	fragmentTransaction.replace(R.id.fragmentcontainer, us);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
         	break;
         	       	
         	//Wird aufgerufen, wenn das Listenelement weder die Position 0,1,2 oder 3 hat. Es wird eine Fehlermeldung ausgegeben
       default: Toast.makeText(getActivity(),"Ein Fehler ist aufgetreten",Toast.LENGTH_SHORT).show();
       			break;
        }


    }


}
