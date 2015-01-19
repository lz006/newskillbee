package de.hdm.skillbee.bo;

public class Learningline extends BusinessObject {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idOn;
	
	private int kategorieID;
	
	private int authorID;

	private int fortschritt;
	
	private boolean status;
	
	private String bezeichnung;

	public int getKategorieID() {
		return kategorieID;
	}

	public void setKategorieID(int kategorieID) {
		this.kategorieID = kategorieID;
	}

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public int getIdOn() {
		return idOn;
	}

	public void setIdOn(int idOn) {
		this.idOn = idOn;
	}

	public int getFortschritt() {
		return fortschritt;
	}

	public void setFortschritt(int fortschritt) {
		this.fortschritt = fortschritt;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	
}
