package de.hdm.skillbee.db.local;

import java.util.Vector;

import android.database.Cursor;

import de.hdm.skillbee.bo.Kategorie;

/**
 * Klasse Kategorie-Objekte auf die lokale SQLite-DB
 * abzubilden und umgekehrt
 * 
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class KategorieMapper {
	
	private DBConnection dbConLocal = null;
	
	public KategorieMapper(DBConnection dbConLocal) {
		this.dbConLocal = dbConLocal;
	}

	
	public Vector<Kategorie> findByKeys(Vector<Integer> keys) {
		StringBuffer ids = new StringBuffer();
		
		if (keys.size() > 1) {
			for (int i = 0; i < keys.size()-1; i++) {
			ids.append(keys.elementAt(i));	
			ids.append(",");
			}		
		}
			
		ids.append(keys.elementAt(keys.size()-1));			
			
		Vector<Kategorie> kategorien = new Vector<Kategorie>();
		
		Cursor c = dbConLocal.readQuery("SELECT * FROM Kategorie WHERE id IN (" + ids.toString() + ")",null);
		if (c.moveToFirst()) {
			do {
				Kategorie kat = new Kategorie();
				kat.setId(c.getInt(0));
				kat.setBezeichnung(c.getString(1));
				kategorien.add(kat);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return kategorien;
	
	}

	public Vector<Kategorie> findAll() {
		Vector<Kategorie> kategorien = new Vector<Kategorie>();
		
		Cursor c = dbConLocal.readQuery("SELECT * FROM Kategorie",null);
		if (c.moveToFirst()) {
			do {
				Kategorie kat = new Kategorie();
				kat.setId(c.getInt(0));
				kat.setBezeichnung(c.getString(1));
				kategorien.add(kat);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return kategorien;
	}

	public Kategorie insertFromOnlineDB(Kategorie kat) {
		dbConLocal.writeQuery("INSERT INTO Kategorie (`id`,`bezeichnung`) VALUES ("+kat.getId()+", '"+ kat.getBezeichnung() +"');");
				
		return kat;
	}

	public Kategorie update(Kategorie kat) {
		dbConLocal.writeQuery("UPDATE Kategorie SET bezeichnung='"+kat.getBezeichnung()+"' WHERE id="+kat.getId()+";");
				
		return kat;
	}

	public void delete(Kategorie kat) {
		dbConLocal.writeQuery("DELETE FROM Kategorie WHERE id="+kat.getId()+";");
		
	}

}
