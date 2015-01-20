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

 
public class MainMenuFragment extends ListFragment implements OnItemClickListener {

    String[] menutitles;
    TypedArray menuIcons;
    Activity baseActivity;

    CustomAdapter adapter;
    private List<RowItem> rowItems;
    
    public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_menu, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        menutitles = getResources().getStringArray(R.array.titles);
        menuIcons = getResources().obtainTypedArray(R.array.icons);

        rowItems = new ArrayList<RowItem>();

        for (int i = 0; i < menutitles.length; i++) {
            RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(
                    i, -1));

            rowItems.add(items);
        }

        adapter = new CustomAdapter(getActivity(), rowItems);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {

        Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT)
                .show();
        

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch(position)
        {
        case 0: 
        	
	        LearningLineOverviewFragment llof = new LearningLineOverviewFragment();
	    	llof.setBaseActivity(baseActivity);
	    	fragmentTransaction.replace(R.id.fragmentcontainer, llof);
	    	fragmentTransaction.addToBackStack(null);
	     	fragmentTransaction.commit();
	     	break;
        case 1:
        	LpgotoQuestionFragment lpgo = new LpgotoQuestionFragment();
        	lpgo.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, lpgo);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
         	break;
         	
        case 2:
        	StoreFragment sf = new StoreFragment();
        	sf.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, sf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
         	break;
         	
         	
       default: Toast.makeText(getActivity(),"???",Toast.LENGTH_SHORT).show();
       			break;
        }


    }


}
