package de.hdm.skillbee.db.server;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdm.skillbee.bo.Kategorie;

/**
 * Klasse um Kategorie-Objekte auf die Server-DB
 * abzubilden und umgekehrt
 * 
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class KategorieMapper {
	
	/**
	 * Gibt ein SELECT Statement anhand eines oder mehreren Id Schl�ssel als Zeichenkette zur�ck
	 * Dieses Statement ist daf�r verantwortlich, ein oder mehrere Kategorien aus der Online Datenbank anhand eines 
	 * oder mehrerer Id Schl�ssel zur�ckzugeben 
	 * @param keys
	 * @return
	 */
	public String findByKeysQuery(Vector<Integer> keys) {
		StringBuffer ids = new StringBuffer();
		
		if (keys.size() > 1) {
			for (int i = 0; i < keys.size()-1; i++) {
			ids.append(keys.elementAt(i));	
			ids.append(",");
			}		
		}
			
		ids.append(keys.elementAt(keys.size()-1));
		
		return new String("SELECT * FROM Kategorie WHERE id IN (" + ids.toString() + ")");	
	}

	/**
	 * Gibt ein SELECT Statement als Zeichenkette zur�ck
	 * Dieses Statement ist daf�r verantwortlich alle Kategorien aus der Online Datenbank zur�ckzugeben 
	 * @return
	 */
	public String findAllQuery() {		
		return new String("SELECT * FROM Kategorie");
	}

	/**
	 * Gibt ein INSERT INTO Statement als Zeichenkette zur�ck
	 * Dieses Statement ist daf�r verantwortlich eine bestimmte Kategorie in die Online Datenbank hinzuzuf�gen
	 * @param kat
	 * @return
	 */
	public String insertQuery(Kategorie kat) {
		return new String("INSERT INTO Kategorie (`id`,`bezeichnung`) VALUES ("+kat.getId()+", '"+ kat.getBezeichnung() +"');");
	}

	/**
	 * Gibt ein UPDATE Statement als Zeichenkette zur�ck
	 * Dieses Statement ist daf�r verantwortlich eine bestimmte Kategorie in der Online Datenbank zu ver�ndern und zu aktualisieren
	 * @param kat
	 * @return
	 */
	public String updateQuery(Kategorie kat) {
		return new String("UPDATE Kategorie SET bezeichnung='"+kat.getBezeichnung()+"' WHERE id="+kat.getId()+";");
	}

	/**
	 * Gibt ein DELETE Statement als Zeichenkette zur�ck
	 * Dieses Statement ist daf�r verantwortlich eine bestimmte Kategorie in der Online Datenbank zu l�schen
	 * @param kat
	 * @return
	 */
	public String deleteQuery(Kategorie kat) {
		return new String("DELETE FROM Kategorie WHERE id="+kat.getId()+";");		
	}
	
	/**
	 * Gibt ein SELECT Statement als Zeichenkette zur�ck
	 * Dieses Statement ist daf�r verantwortlich die h�chste existierende Id einer Kategorie zur�ckzugeben
	 * @return
	 */
	public String getMaxKategorieIDQuery() {
		return new String("SELECT MAX(id) AS maxid FROM Kategorie;");	
	}
	
	/**
	 * Gibt eine oder mehrere Kategorien anhand eines JSON Objektes zur�ck
	 * @param json
	 * @return
	 */
	public Vector<Kategorie> json2Kategorie(JSONObject json) {
		Vector<Kategorie> kategorien = new Vector<Kategorie>();
		
		try {
			JSONArray jsonArray = json.getJSONArray("User");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject c = jsonArray.getJSONObject(i);

				Kategorie kat = new Kategorie();
				kat.setId(c.getInt("id"));
				kat.setBezeichnung(c.getString("bezeichnung"));
				kategorien.add(kat);

			}
		} catch (JSONException e) {
			// Toast...
		}
		return kategorien;
	}
	
	
	/**
	 * Gibt die h�chste ID anhand eines JSON Objektes zur�ck
	 * @param json
	 * @return
	 */
	public int json2NewID(JSONObject json) {
		
		Integer newID = null;
		
		try {
			JSONArray jsonArray = json.getJSONArray("Maxid");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject c = jsonArray.getJSONObject(i);
				newID = c.getInt("maxid");			
			}
		} catch (JSONException e) {
			// Toast...
		}
		
		return newID;
	}

}
