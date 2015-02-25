package de.hdm.skillbee.bo;

import java.io.Serializable;

/**
 * Abstrakte Klasse, welche als Elternklasse ihre Eigenschaften an ihre Unterklassen vererbt. 
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 *Implementiert das Markerinterface {@link Serializable} damit sämtliche Bo's serializierbar und deserializierbar sind
 *Vererbt an mehrere Klassen, die Business Objekte darstellen
 *@see Kategorie
 *@see Knoten
 *@see Learningline
 *@see User
 */

public abstract class BusinessObject implements Serializable {


	
	
	private static final long serialVersionUID = 1L;
	
	private int id;

	/**
	 *  Id auslesen
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 *  Id setzen
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
}
