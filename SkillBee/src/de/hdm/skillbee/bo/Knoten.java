package de.hdm.skillbee.bo;

/**
 * Klasse, deren Instanz einen real existierenden Learning Line Knoten repräsentiert
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class Knoten extends BusinessObject {

	
	private static final long serialVersionUID = 1L;

	private int llIDLoc;
	
	private int llIDOn;
	
	private String ueberschrift;
	
	private boolean status;
	
	private String kurzInhalt;
	
	private String inhalt;
	
	
	private String GoogleLink;
	
	/**
	 * GoogleLink auslesen
	 * @return
	 */
	public String getGoogleLink() {
		return GoogleLink;
	}

	/**
	 * GoogleLink setzen
	 * @param googleLink
	 */
	public void setGoogleLink(String googleLink) {
		GoogleLink = googleLink;
	}

	/**
	 * YoutubeLink auslesen
	 * @return
	 */
	public String getYoutubeLink() {
		return YoutubeLink;
	}

	/**
	 * YoutubeLink setzen
	 * @param youtubeLink
	 */
	public void setYoutubeLink(String youtubeLink) {
		YoutubeLink = youtubeLink;
	}

	private String YoutubeLink;
	
	
	private int position;
	
	/**
	 * lokale Learning Line id auslesen
	 * @return
	 */
	public int getLlIDLoc() {
		return llIDLoc;
	}

	/**
	 * lokale Learning Line id setzen
	 * @param llIDLoc
	 */
	public void setLlIDLoc(int llIDLoc) {
		this.llIDLoc = llIDLoc;
	}

	/**
	 * Externe Learning Line id auslesen
	 * @return
	 */
	public int getLlIDOn() {
		return llIDOn;
	}

	/**
	 * Externe Learning Line id setzen
	 * @param llIDOn
	 */
	public void setLlIDOn(int llIDOn) {
		this.llIDOn = llIDOn;
	}

	/**
	 * Kurzinhalt auslesen
	 * @return
	 */
	public String getKurzInhalt() {
		return kurzInhalt;
	}

	/**
	 * Kurzinhalt setzen
	 * @param kurzInhalt
	 */
	public void setKurzInhalt(String kurzInhalt) {
		this.kurzInhalt = kurzInhalt;
	}

	/**
	 * Langinhalt auslesen
	 * @return
	 */
	public String getInhalt() {
		return inhalt;
	}

	/**
	 * Langinhalt setzen
	 * @param inhalt
	 */
	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	/**
	 * Position auslesen
	 * @return
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Position setzen
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Überschrift auslesen
	 * @return
	 */
	public String getUeberschrift() {
		return ueberschrift;
	}

	/**
	 * Überschrift setzen
	 * @param ueberschrift
	 */
	public void setUeberschrift(String ueberschrift) {
		this.ueberschrift = ueberschrift;
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
	
	
}
