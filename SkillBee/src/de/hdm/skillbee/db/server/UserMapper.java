package de.hdm.skillbee.db.server;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdm.skillbee.bo.User;




/**
 * Klasse User-Objekte auf die lokale SQLite-DB
 * abzubilden und umgekehrt
 * 
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class UserMapper {
	
		
	public String findByKeysQuery(Vector<Integer> keys) {
		StringBuffer ids = new StringBuffer();
		
		if (keys.size() > 1) {
			for (int i = 0; i < keys.size()-1; i++) {
			ids.append(keys.elementAt(i));	
			ids.append(",");
			}		
		}
			
		ids.append(keys.elementAt(keys.size()-1));			
			
		return new String("SELECT * FROM User WHERE id IN (" + ids.toString() + ")");
	
	}
	
	public String findByUserAndPW(User user) {
		return new String("SELECT * FROM User Where email='"+user.getEmail()+"' and passwort='"+user.getPasswort()+"'");
	}

	public String findAllQuery() {
		return new String("SELECT * FROM User");
	}
	public String insertQuery(User user) {
		return new String("INSERT INTO User (`vorname`, `name`, `email`,`passwort`) VALUES ('"+user.getVorname()+"', '"+user.getName()+"', '"+user.getEmail()+"', '"+user.getPasswort()+"');");
	}
	
	public String updateQuery(User user) {		
		return new String("UPDATE User SET id="+user.getId()+", vorname='"+user.getVorname()+"', name='"+user.getName()+"', email='"+user.getEmail()+"', passwort='"+user.getPasswort()+"' WHERE id="+user.getId()+";");		
	}

	public String deleteQuery(User user) {
		return new String("DELETE FROM User WHERE id="+user.getId()+";");	
	}
	
	public String getUserIDQuery(User user) {
		return new String("SELECT id AS maxid FROM User Where email='"+user.getEmail()+"' and passwort='"+user.getPasswort()+"'");	
	}
	
	public Vector<User> json2User(JSONObject json) {
		Vector<User> users = new Vector<User>();
		
		try {
			JSONArray jsonArray = json.getJSONArray("User");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject c = jsonArray.getJSONObject(i);

				User user = new User();
				user.setId(c.getInt("id"));
				user.setVorname(c.getString("vorname"));
				user.setName(c.getString("name"));
				user.setEmail(c.getString("email"));
				user.setPasswort(c.getString("passwort"));
				users.add(user);

			}
		} catch (JSONException e) {
			// Toast...
		}
		return users;
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
