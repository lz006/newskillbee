package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import de.hdm.skillbee.R;

/**
 * 
 * Fragmentklasse, die für das Anzeigen des Stores zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */
 



public class StoreFragment extends Fragment implements OnItemClickListener {

    String[] menutitles;
    TypedArray menuIcons;
    Activity baseActivity;

    CustomAdapter adapter;
    private List<RowItem> rowItems;
    
    ListView lv = null;
    
    
    
    
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
    
    
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	//View der das Layout ladet
    	 View view = inflater.inflate(R.layout.fragment_store_menu, container, false);
    	 
    	 
    	 lv = (ListView)view.findViewById(R.id.storemenulist);
    	 doSomething();

        return view;
    }

    
    
    
    
    /**
     * Methode wird aufgerufen am ende der onCreateView Methode hier wird das Menü aufgebaut 
     * auf das man dann kilcken kann und dem eine int Wert zugewiesen.
     * 
     */
    
    
    public void doSomething() {


        menutitles = new String[2];
        menuIcons = getResources().obtainTypedArray(R.array.icons_store);
        
        menutitles[0] = "Download";
        menutitles[1] = "Upload";

        rowItems = new ArrayList<RowItem>();

        for (int i = 0; i < menutitles.length; i++) {
            RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(
                    i, -1));

            rowItems.add(items);
        }

        adapter = new CustomAdapter(getActivity(), rowItems);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

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
        

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        ChooseLLFragment llof = new ChooseLLFragment();
        llof.setBaseActivity(baseActivity);
     	
        switch(position) {
        
        
        /**
    	 * Fall 1 nach klick auf Download wird die Download Liste ausgegeben
    	 * 
    	 */
        
        
	        case 0: 
	        	llof.setDownload();
	        	fragmentTransaction.replace(R.id.fragmentcontainer, llof);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	         	break;
	         	
	         	/**
	        	 * Fall 2 nach klick auf Upload wird die Upload Liste ausgegeben
	        	 * 
	        	 */
	         	
	         	
	        case 1:
	        	llof.setUpload();
	        	fragmentTransaction.replace(R.id.fragmentcontainer, llof);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	         	break;
	         	
	         	
	         	
	       default: Toast.makeText(getActivity(),"???",Toast.LENGTH_SHORT).show();
	       			break;
        }


    }


}
