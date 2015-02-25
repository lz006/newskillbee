package de.hdm.skillbee.fragments;


/**
 * Klasse, die ein Listenelement mit Icons repräsentiert
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class RowItem {

	    private String title;
	    private int icon;

	    /**
	     * Konstruktor
	     * @param title
	     * @param icon
	     */
	    public RowItem(String title, int icon) {
	        this.title = title;
	        this.icon = icon;

	    }

	    /**
	     * Titel bzw. Bezeichnung auslesen
	     * @return
	     */
	    public String getTitle() {
	        return title;
	    }

	    /**
	     * Titel bzw. Bezeichnung setzen
	     * @param title
	     */
	    public void setTitle(String title) {
	        this.title = title;
	    }

	    /**
	     * Iconindex auslesen
	     * @return
	     */
	    public int getIcon() {
	        return icon;
	    }

	    /**
	     * Iconindex setzen
	     * @param icon
	     */
	    public void setIcon(int icon) {
	        this.icon = icon;
	    }
	
}
