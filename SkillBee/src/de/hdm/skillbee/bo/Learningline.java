package de.hdm.skillbee.bo;

/**
 * Klasse, deren Instanz eine real existierende Learningline repräsentiert 
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class Learningline extends BusinessObject {

	
	
	
	private static final long serialVersionUID = 1L;
	
	private int idOn;
	
	private int kategorieID;
	
	private int authorID;

	private int fortschritt;
	
	private boolean status;
	
	private String bezeichnung;

	/**
	 * Kategorie ID auslesen
	 * @return
	 */
	public int getKategorieID() {
		return kategorieID;
	}

	/**
	 * Kategorie ID setzen
	 * @param kategorieID
	 */
	public void setKategorieID(int kategorieID) {
		this.kategorieID = kategorieID;
	}

	/**
	 * Autor ID auslesen
	 * @return
	 */
	public int getAuthorID() {
		return authorID;
	}

	/**
	 * Autor ID setzen
	 * @param authorID
	 */
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	/**
	 * Externe ID auslesen
	 * @return
	 */
	public int getIdOn() {
		return idOn;
	}

	/**
	 * Externe ID setzen
	 * @param idOn
	 */
	public void setIdOn(int idOn) {
		this.idOn = idOn;
	}

	/**
	 * Fortschritt auslesen
	 * @return
	 */
	public int getFortschritt() {
		return fortschritt;
	}

	/**
	 * Fortschritt setzen
	 * @param fortschritt
	 */
	public void setFortschritt(int fortschritt) {
		this.fortschritt = fortschritt;
	}

	/**
	 * Status auslesen
	 * @return
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * Status setzen
	 * @param status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

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
