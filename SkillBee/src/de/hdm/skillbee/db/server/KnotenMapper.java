package de.hdm.skillbee.db.server;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import de.hdm.skillbee.bo.Knoten;
import de.hdm.skillbee.bo.Learningline;

/**
 * Klasse um Knoten-Objekte auf die Server-DB
 * abzubilden und umgekehrt
 * 
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class KnotenMapper {
	
	/**
	 * Gibt ein SELECT Statement als Zeichenkette zurück
	 * Dieses Statement ist dafür verantwortlich einen oder mehrere Knoten aus der Online Datenbank anhand der
	 * Online Learningline Id zurückzugeben
	 * @param llonid
	 * @return
	 */
	public String findByLLOnIDQuery(int llonid) {
		
		return new String("SELECT * FROM Knoten WHERE llIDOn="+llonid);
	
	}
	
	/**
	 * Gibt ein SELECT Statement als Zeichenkette zurück
	 * Dieses Statement ist dafür verantwortlich alle Knoten aus der Online Datenbank zurückzugeben 
	 * @return
	 */
	public String findAllQuery() {					
		return new String("SELECT * FROM Knoten");
	}

	/**
	 * Gibt ein INSERT INTO Statement als Zeichenkette zurück
	 * Dieses Statement ist dafür verantwortlich einen bestimmten Knoten in die Online Datenbank hinzuzufügen
	 * @param kn
	 * @return
	 */
	public String insertQuery(Knoten kn) {
		int status = 0;
		if (kn.isStatus()) {
			status = 1;
		}
		
		return new String("INSERT INTO Knoten (`llIDLoc`, `llIDOn`, `ueberschrift`, `inhalt`,`kurzInhalt`,`status`,`position`,`medienG`,`medienY`) VALUES ("+kn.getLlIDLoc()+", "+kn.getLlIDOn()+", '"+kn.getUeberschrift()+"', '"+kn.getInhalt()+"', '"+kn.getKurzInhalt()+"', "+status+", "+kn.getPosition()+", '"+kn.getGoogleLink()+"', '"+kn.getYoutubeLink()+"');");
	}

	
	/**
	 * Gibt ein UPDATE Statement als Zeichenkette zurück
	 * Dieses Statement ist dafür verantwortlich einen bestimmten Knoten in der Online Datenbank zu verändern und zu aktualisieren
	 * @param kn
	 * @return
	 */
	public String updateQuery(Knoten kn) {
		int status = 0;
		if (kn.isStatus()) {
			status = 1;
		}
		return new String("UPDATE Knoten SET llIDLoc="+kn.getLlIDLoc()+", llIDOn="+kn.getLlIDOn()+", ueberschrift='"+kn.getUeberschrift()+"', inhalt='"+kn.getInhalt()+"', kurzInhalt='"+kn.getKurzInhalt()+"', status="+status+", position="+kn.getPosition()+", medienG="+kn.getGoogleLink()+", medienY="+kn.getYoutubeLink()+" WHERE id="+kn.getId()+" AND llIDLoc="+kn.getLlIDLoc()+" AND llIDOn="+kn.getLlIDOn()+";");
	}

	/**
	 * Gibt ein DELETE Statement als Zeichenkette zurück
	 * Dieses Statement ist dafür verantwortlich einen bestimmten Knoten aus der Online Datenbank zu löschen
	 * @param kn
	 * @return
	 */
	public String deleteQuery(Knoten kn) {
		return new String("DELETE FROM Knoten WHERE id="+kn.getId()+" AND llIDLoc="+kn.getLlIDLoc()+" AND llIDOn="+kn.getLlIDOn()+";");
		
	}
	
	/**
	 * Gibt ein DELETE Statement als Zeichenkette zurück
	 * Dieses Statement ist dafür verantwortlich alle Knoten aus der Online Datenbank anhand einer bestimmten Learningline zu löschen
	 * @param ll
	 * @return
	 */
	public String deleteByLLQuery(Learningline ll) {
		return new String("DELETE FROM Knoten WHERE llIDOn="+ll.getIdOn()+";");
		
	}
	
	/**
	 * Gibt ein SELECT Statement als Zeichenkette zurück
	 * Dieses Statement ist dafür verantwortlich die höchste existierende Id eines Knoten zurückzugeben
	 * @return
	 */
	public String getMaxKategorieIDQuery() {
		return new String("SELECT MAX(id) AS maxid FROM Knoten;");	
	}
	
	/**
	 * Gibt eine oder mehrere Knoten anhand eines JSON Objektes zurück
	 * @param json
	 * @return
	 */
	public Vector<Knoten> json2Knoten(JSONObject json) {
		Vector<Knoten> knoten = new Vector<Knoten>();
		
		try {
			JSONArray jsonArray = json.getJSONArray("Knoten");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject c = jsonArray.getJSONObject(i);

				Knoten kn = new Knoten();
				kn.setId(c.getInt("id"));
				kn.setLlIDLoc(c.getInt("llIDLoc"));
				kn.setLlIDOn(c.getInt("llIDOn"));
				kn.setUeberschrift(c.getString("ueberschrift"));
				kn.setInhalt(c.getString("inhalt"));
				kn.setKurzInhalt(c.getString("kurzinhalt"));
				
				if (c.getInt("status") == 1) {
					kn.setStatus(true);
				}
				else {
					kn.setStatus(false);
				}
				kn.setPosition(c.getInt("position"));
				kn.setGoogleLink(c.getString("medienG"));
				kn.setYoutubeLink(c.getString("medienY"));
				knoten.add(kn);

			}
		} catch (JSONException e) {
			// Toast...
		}
		return knoten;
	}
	
	
	
	/**
	 * Gibt die höchste ID anhand eines JSON Objektes zurück
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
