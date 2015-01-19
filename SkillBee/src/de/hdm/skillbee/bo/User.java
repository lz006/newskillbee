package de.hdm.skillbee.bo;

public class User extends BusinessObject{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String passwort;
	
	private String email;
	
	private String vorname;
	
	private String name;
	

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
