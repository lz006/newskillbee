package de.hdm.skillbee.fragments;


/**
 * Klasse, die ein Listenelement ohne Icons repräsentiert
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class RowItemLLOverview {

	private String title;
	
	/**
	 * Konstruktor
	 * @param title
	 */
	public RowItemLLOverview(String title) {
        this.title = title;
     

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
	
}
