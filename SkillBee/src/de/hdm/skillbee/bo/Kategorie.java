package de.hdm.skillbee.bo;

/**
 * Klasse, deren Instanz eine real existierende Kategorie repräsentiert
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public  class Kategorie extends BusinessObject{

	
	
	
	
	private static final long serialVersionUID = 1L;
	
	private String bezeichnung;

	/**
	 * Bezeichnung auslesen
	 * @return
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * Bezeichnung setzen
	 * @param bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	
	
}
