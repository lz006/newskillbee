package de.hdm.skillbee.fragments;

import java.util.List;

import de.hdm.skillbee.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract.Colors;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Adapterklasse, die für die Darstellung einer Liste bestehend aus verschiedenen Elementen zuständig ist
 * Diese Elemente besitzen keine Icons
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class Adapterwithouticons extends BaseAdapter {

	Context context;
    List<RowItemLLOverview> rowItemlloverview;

    /**
     * Konstruktor
     * @param context
     * @param rowItemlloverview
     */
   public Adapterwithouticons(Context context, List<RowItemLLOverview> rowItemlloverview) {
        this.context = context;
        this.rowItemlloverview = rowItemlloverview;

    }
    
   /**
    * Anzahl der Listenelemente auslesen
    * @return
    */
    @Override
    public int getCount() {

        return rowItemlloverview.size();
    }

    /**
     * Listenelement anhand der Elementposition auslesen
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {

        return rowItemlloverview.get(position);
    }

    
    /**
     * Elementid anhand der Elementposition auslesen
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {

        return rowItemlloverview.indexOf(getItem(position));
    }

    /**
     * Sicht anhand der elementposition, der aktuellen Sicht und der ViewGroup auslesen
     * @param position, convertView, parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.fragment_item_list_withouticons, null);
        }

        
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        RowItemLLOverview row_pos = rowItemlloverview.get(position);
        // Den Titel des Elements anhand der Position setzen
        txtTitle.setText(row_pos.getTitle());
        //Die Farbe des Textes auf gelb setzen
        txtTitle.setTextColor(Color.parseColor("#FFCC00"));

        return convertView;

    }
	
}
