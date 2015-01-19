package de.hdm.skillbee.db.local;

import java.util.Vector;

import de.hdm.skillbee.bo.User;

import android.database.Cursor;



/**
 * Klasse User-Objekte auf die lokale SQLite-DB
 * abzubilden und umgekehrt
 * 
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class UserMapper {
	
	private DBConnection dbConLocal = null;
	
	public UserMapper(DBConnection dbConLocal) {
		this.dbConLocal = dbConLocal;
	}

	
	public Vector<User> findByKeys(Vector<Integer> keys) {
		StringBuffer ids = new StringBuffer();
		
		if (keys.size() > 1) {
			for (int i = 0; i < keys.size()-1; i++) {
			ids.append(keys.elementAt(i));	
			ids.append(",");
			}		
		}
			
		ids.append(keys.elementAt(keys.size()-1));			
			
		Vector<User> users = new Vector<User>();
		
		Cursor c = dbConLocal.readQuery("SELECT * FROM User WHERE id IN (" + ids.toString() + ")",null);
		if (c.moveToFirst()) {
			do {
				User user = new User();
				user.setId(c.getInt(0));
				user.setVorname(c.getString(1));
				user.setName(c.getString(2));
				user.setEmail(c.getString(3));
				user.setPasswort(c.getString(4));
				users.add(user);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return users;
	
	}

	public Vector<User> findAll() {
		Vector<User> users = new Vector<User>();
		
		Cursor c = dbConLocal.readQuery("SELECT * FROM User",null);
		if (c.moveToFirst()) {
			do {
				User user = new User();
				user.setId(c.getInt(0));
				user.setVorname(c.getString(1));
				user.setName(c.getString(2));
				user.setEmail(c.getString(3));
				user.setPasswort(c.getString(4));
				users.add(user);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return users;
	}

	/**
	 * Der User wird zuerst in der Online-DB angelegt und erh�lt dort
	 * einen eindeutigen Prim�rschl�ssel. Erst im Anschluss wird dieser
	 * dann in der Lokalen DB (hier) angelegt
	 * @param user
	 * @return
	 */
	public User insert(User user) {
		dbConLocal.writeQuery("INSERT INTO User (`id`, `vorname`, `name`, `email`,`passwort`) VALUES ("+user.getId()+", '"+user.getVorname()+"', '"+user.getName()+"', '"+user.getEmail()+"', '"+user.getPasswort()+"');");
				
		return user;
	}
	
	public User update(User user) {
		
		dbConLocal.writeQuery("UPDATE User SET id="+user.getId()+", vorname='"+user.getVorname()+"', name='"+user.getName()+"', email='"+user.getEmail()+"', passwort='"+user.getPasswort()+"' WHERE id="+user.getId()+";");
				
		return user;
	}

	public void delete(User user) {
		dbConLocal.writeQuery("DELETE FROM User WHERE id="+user.getId()+";");
		
	}

}
