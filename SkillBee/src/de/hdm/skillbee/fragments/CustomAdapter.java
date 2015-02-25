package de.hdm.skillbee.fragments;


import java.util.List;

import de.hdm.skillbee.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapterklasse, die für die Darstellung einer Liste bestehend aus verschiedenen Elementen zuständig ist
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class CustomAdapter extends BaseAdapter {

	Context context;
    List<RowItem> rowItem;
    
    
    /**
     * Konstruktor
     * @param context
     * @param rowItem
     */
   public CustomAdapter(Context context, List<RowItem> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }

   
   /**
    * Anzahl der Listenelemente auslesen
    * @return
    */
    @Override
    public int getCount() {

        return rowItem.size();
    }

    /**
     * Listenelement anhand der Elementposition auslesen
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {

        return rowItem.get(position);
    }

    /**
     * Elementid anhand der Elementposition auslesen
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {

        return rowItem.indexOf(getItem(position));
    }

    /**
     * Sicht anhand der Elementposition, der aktuellen Sicht und der ViewGroup auslesen
     * @param position, convertView, parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.fragment_item_list, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        RowItem row_pos = rowItem.get(position);
        // setting the image resource and title
        imgIcon.setImageResource(row_pos.getIcon());
        txtTitle.setText(row_pos.getTitle());
        txtTitle.setTextColor(Color.parseColor("#FFCC00"));

        return convertView;

    }

}
