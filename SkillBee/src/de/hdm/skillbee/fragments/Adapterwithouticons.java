package de.hdm.skillbee.fragments;

import java.util.List;

import de.hdm.skillbee.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapterwithouticons extends BaseAdapter {

	Context context;
    List<RowItemLLOverview> rowItemlloverview;

    Adapterwithouticons(Context context, List<RowItemLLOverview> rowItemlloverview) {
        this.context = context;
        this.rowItemlloverview = rowItemlloverview;

    }
    
    @Override
    public int getCount() {

        return rowItemlloverview.size();
    }

    @Override
    public Object getItem(int position) {

        return rowItemlloverview.get(position);
    }

    @Override
    public long getItemId(int position) {

        return rowItemlloverview.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.fragment_item_list_withouticons, null);
        }

        
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        RowItemLLOverview row_pos = rowItemlloverview.get(position);
        // setting the image resource and title
        txtTitle.setText(row_pos.getTitle());

        return convertView;

    }
	
}
