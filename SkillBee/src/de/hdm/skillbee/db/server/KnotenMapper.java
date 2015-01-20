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
	
	public String findByLLOnIDQuery(int llonid) {
		
		return new String("SELECT * FROM Knoten WHERE llIDOn="+llonid);
	
	}
	

	public String findAllQuery() {					
		return new String("SELECT * FROM Knoten");
	}

	public String insertQuery(Knoten kn) {
		int status = 0;
		if (kn.isStatus()) {
			status = 1;
		}
		
		return new String("INSERT INTO Knoten (`llIDLoc`, `llIDOn`, `ueberschrift`, `inhalt`,`kurzInhalt`,`status`,`position`,`medienG`,`medienY`) VALUES ("+kn.getLlIDLoc()+", "+kn.getLlIDOn()+", '"+kn.getUeberschrift()+"', '"+kn.getInhalt()+"', '"+kn.getKurzInhalt()+"', "+status+", "+kn.getPosition()+", '"+kn.getGoogleLink()+"', '"+kn.getYoutubeLink()+"');");
	}

	public String updateQuery(Knoten kn) {
		int status = 0;
		if (kn.isStatus()) {
			status = 1;
		}
		return new String("UPDATE Knoten SET llIDLoc="+kn.getLlIDLoc()+", llIDOn="+kn.getLlIDOn()+", ueberschrift='"+kn.getUeberschrift()+"', inhalt='"+kn.getInhalt()+"', kurzInhalt='"+kn.getKurzInhalt()+"', status="+status+", position="+kn.getPosition()+", medienG="+kn.getGoogleLink()+", medienY="+kn.getYoutubeLink()+" WHERE id="+kn.getId()+" AND llIDLoc="+kn.getLlIDLoc()+" AND llIDOn="+kn.getLlIDOn()+";");
	}

	public String deleteQuery(Knoten kn) {
		return new String("DELETE FROM Knoten WHERE id="+kn.getId()+" AND llIDLoc="+kn.getLlIDLoc()+" AND llIDOn="+kn.getLlIDOn()+";");
		
	}
	
	public String deleteByLLQuery(Learningline ll) {
		return new String("DELETE FROM Knoten WHERE llIDOn="+ll.getIdOn()+";");
		
	}
	
	public String getMaxKategorieIDQuery() {
		return new String("SELECT MAX(id) AS maxid FROM Knoten;");	
	}
	
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
