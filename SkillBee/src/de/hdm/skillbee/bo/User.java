package de.hdm.skillbee.bo;

/**
 * Klasse, deren Instanz einen real existierenden User repräsentiert
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class User extends BusinessObject{

	
	
	
	private static final long serialVersionUID = 1L;

	private String passwort;
	
	private String email;
	
	private String vorname;
	
	private String name;
	
	/**
	 * Vorname auslesen
	 * @return
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Vorname setzen
	 * @param vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/**
	 * Nachname auslesen
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Nachname setzen
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Passwort auslesen
	 * @return
	 */
	public String getPasswort() {
		return passwort;
	}

	/**
	 * Passwort setzen
	 * @param passwort
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	/**
	 * Email auslesen
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Email setzen
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
