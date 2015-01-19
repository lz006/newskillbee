package de.hdm.skillbee.bo;

public class Knoten extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int llIDLoc;
	
	private int llIDOn;
	
	private String ueberschrift;
	
	private boolean status;
	
	private String kurzInhalt;
	
	private String inhalt;
	
	
	private String GoogleLink;
	
	public String getGoogleLink() {
		return GoogleLink;
	}

	public void setGoogleLink(String googleLink) {
		GoogleLink = googleLink;
	}

	public String getYoutubeLink() {
		return YoutubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		YoutubeLink = youtubeLink;
	}

	private String YoutubeLink;
	
	
	private int position;
	
	public int getLlIDLoc() {
		return llIDLoc;
	}

	public void setLlIDLoc(int llIDLoc) {
		this.llIDLoc = llIDLoc;
	}

	public int getLlIDOn() {
		return llIDOn;
	}

	public void setLlIDOn(int llIDOn) {
		this.llIDOn = llIDOn;
	}

	public String getKurzInhalt() {
		return kurzInhalt;
	}

	public void setKurzInhalt(String kurzInhalt) {
		this.kurzInhalt = kurzInhalt;
	}

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getUeberschrift() {
		return ueberschrift;
	}

	public void setUeberschrift(String ueberschrift) {
		this.ueberschrift = ueberschrift;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
