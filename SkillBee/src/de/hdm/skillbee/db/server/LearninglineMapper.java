package de.hdm.skillbee.db.server;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import de.hdm.skillbee.bo.Learningline;

/**
 * Klasse um Learningline-Objekte auf die Server-DB
 * abzubilden und umgekehrt
 * 
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class LearninglineMapper {
		
	public String findByKeysQuery(Vector<Integer> keys) {
		StringBuffer ids = new StringBuffer();
		
		if (keys.size() > 1) {
			for (int i = 0; i < keys.size()-1; i++) {
			ids.append(keys.elementAt(i));	
			ids.append(",");
			}		
		}
			
		ids.append(keys.elementAt(keys.size()-1));
		
		return new String("SELECT * FROM LearningLine WHERE id IN (" + ids.toString() + ")");
	
	}

	public String findAllQuery() {
		return new String("SELECT * FROM LearningLine");
	}
	
	public String insertQuery(Learningline ll) {
		int status = 0;
		if (ll.isStatus()) {
			status = 1;
		}
		return new String("INSERT INTO LearningLine (`idLoc`, `status`, `fortschritt`, `authorID`,`kategorieID`,`bezeichnung`) VALUES ("+ll.getId()+", "+status+", "+ll.getFortschritt()+", "+ll.getAuthorID()+", "+ll.getKategorieID()+", '"+ ll.getBezeichnung() +"');");
	}
	
	public String updateQuery(Learningline ll) {
		int status = 0;
		if (ll.isStatus()) {
			status = 1;
		}
		return new String("UPDATE LearningLine SET status="+status+", fortschritt='"+ll.getFortschritt()+"', authorID="+ll.getAuthorID()+", kategorieID="+ll.getKategorieID()+", bezeichnung='"+ll.getBezeichnung()+"' WHERE idLoc="+ll.getId()+" AND idOn="+ll.getIdOn()+";");
	}

	public String deleteQuery(Learningline ll) {
		return new String("DELETE FROM LearningLine WHERE idLoc="+ll.getId()+" AND idOn="+ll.getIdOn()+";");
	}
	
	public String getMaxKategorieIDQuery() {
		return new String("SELECT MAX(idOn) AS maxid FROM LearningLine;");	
	}
	
	public Vector<Learningline> json2Learningline(JSONObject json) {
		Vector<Learningline> lls = new Vector<Learningline>();
		
		try {
			JSONArray jsonArray = json.getJSONArray("LearningLine");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject c = jsonArray.getJSONObject(i);

				Learningline ll = new Learningline();
				ll.setId(c.getInt("idLoc"));
				ll.setIdOn(c.getInt("idOn"));
				
				if (c.getInt("status") == 1) {
					ll.setStatus(true);
				}
				else {
					ll.setStatus(false);
				}
				ll.setFortschritt(c.getInt("fortschritt"));
				ll.setAuthorID(c.getInt("authorID"));
				ll.setKategorieID(c.getInt("kategorieID"));
				ll.setBezeichnung(c.getString("bezeichnung"));
				lls.add(ll);

			}
		} catch (JSONException e) {
			// Toast...
		}
		return lls;
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
