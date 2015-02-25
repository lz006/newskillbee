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
    
    public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	 View view = inflater.inflate(R.layout.fragment_store_menu, container, false);
    	 lv = (ListView)view.findViewById(R.id.storemenulist);
    	 doSomething();

        return view;
    }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {

    	//getListView().setClickable(false);
        Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT)
                .show();
        

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        ChooseLLFragment llof = new ChooseLLFragment();
        llof.setBaseActivity(baseActivity);
     	
        switch(position) {
	        case 0: 
	        	llof.setDownload();
	        	fragmentTransaction.replace(R.id.fragmentcontainer, llof);
	        	fragmentTransaction.addToBackStack(null);
	         	fragmentTransaction.commit();
	            	
	    	
	         	break;
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
