package de.hdm.skillbee.db.local;

import java.util.Vector;

import android.database.Cursor;

import de.hdm.skillbee.bo.Kategorie;
import de.hdm.skillbee.bo.Learningline;

/**
 * Klasse Learningline-Objekte auf die lokale SQLite-DB
 * abzubilden und umgekehrt
 * 
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class LearninglineMapper {
	
	private DBConnection dbConLocal = null;
	
	public LearninglineMapper(DBConnection dbConLocal) {
		this.dbConLocal = dbConLocal;
	}

	
	public Vector<Learningline> findByKeys(Vector<Integer> keys) {
		StringBuffer ids = new StringBuffer();
		
		if (keys.size() > 1) {
			for (int i = 0; i < keys.size()-1; i++) {
			ids.append(keys.elementAt(i));	
			ids.append(",");
			}		
		}
			
		ids.append(keys.elementAt(keys.size()-1));			
			
		Vector<Learningline> lls = new Vector<Learningline>();
		
		Cursor c = dbConLocal.readQuery("SELECT * FROM Learningline WHERE id IN (" + ids.toString() + ")",null);
		if (c.moveToFirst()) {
			do {
				Learningline ll = new Learningline();
				ll.setId(c.getInt(0));
				ll.setIdOn(c.getInt(1));
				
				if (c.getInt(2) == 1) {
					ll.setStatus(true);
				}
				else {
					ll.setStatus(false);
				}
				ll.setFortschritt(c.getInt(3));
				ll.setAuthorID(c.getInt(4));
				ll.setKategorieID(c.getInt(5));
				ll.setBezeichnung(c.getString(6));
				lls.add(ll);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return lls;
	
	}

	public Vector<Learningline> findAll() {
		Vector<Learningline> lls = new Vector<Learningline>();
		
		Cursor c = dbConLocal.readQuery("SELECT * FROM Learningline",null);
		if (c.moveToFirst()) {
			do {
				Learningline ll = new Learningline();
				ll.setId(c.getInt(0));
				ll.setIdOn(c.getInt(1));
				
				if (c.getInt(2) == 1) {
					ll.setStatus(true);
				}
				else {
					ll.setStatus(false);
				}
				ll.setFortschritt(c.getInt(3));
				ll.setAuthorID(c.getInt(4));
				ll.setKategorieID(c.getInt(5));
				ll.setBezeichnung(c.getString(6));
				lls.add(ll);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return lls;
	}

	/**
	 * Diese Methode muss verwendet werden, wenn eine LearningLine aus der
	 * Online-DB heruntergeladen wurde und in der Lokalen-DB gespeichert
	 * werden soll
	 * 
	 * @param ll
	 * @return
	 */
	public Learningline insertFromOnlineDB(Learningline ll) {
		int status = 0;
		if (ll.isStatus()) {
			status = 1;
		}
		dbConLocal.writeQuery("INSERT INTO Learningline (`idOn`, `status`, `fortschritt`, `authorID`,`kategorieID`,`bezeichnung`) VALUES ("+ll.getIdOn()+", "+status+", "+ll.getFortschritt()+", "+ll.getAuthorID()+", "+ll.getKategorieID()+", '"+ ll.getBezeichnung() +"');");
		
		return ll;
	}
	
	public Learningline insert(Learningline ll) {
		int status = 0;
		if (ll.isStatus()) {
			status = 1;
		}
		
		Cursor c = dbConLocal.readQuery("SELECT MAX(idLoc) AS maxid FROM Learningline;",null);
		if (c.moveToFirst()) {
			
			
			
			if (c.getCount() == 0) {	
					
				ll.setId(c.getInt(0));
				
				
				
			}
			
			else {
			
			if (c.getCount() > 0 && c.getInt(0) == 0) {	
				
				ll.setId(1);
			}
			
			
			
			else {
				
				ll.setId(c.getInt(0)+1);
				
			}
			}
		}
		
		
		
		else {
		}
		
		dbConLocal.writeQuery("INSERT INTO Learningline (`idLoc`, `idOn`, `status`, `fortschritt`, `authorID`,`kategorieID`,`bezeichnung`) VALUES ("+ll.getId()+", "+ll.getIdOn()+", "+status+", "+ll.getFortschritt()+", "+ll.getAuthorID()+", "+ll.getKategorieID()+", '"+ ll.getBezeichnung() +"');");

		
		
		return ll;
	}

	/**
	 * Nachdem eine LearningLine ver�ffentlicht wurde muss diese
	 * mit dieser Methode in der lokalen DB wieder aktualisiert werden,
	 * damit auch der Online-DB-Prim�schl�ssel der ver�ffentlichten Entit�t 
	 * hinzugef�gt wird.
	 * @param ll
	 * @return
	 */
	public Learningline updateAfterRelease(Learningline ll) {
		int status = 0;
		if (ll.isStatus()) {
			status = 1;
		}
		dbConLocal.writeQuery("UPDATE Learningline SET idOn="+ll.getIdOn()+", status="+status+", fortschritt='"+ll.getFortschritt()+"', authorID="+ll.getAuthorID()+", kategorieID="+ll.getKategorieID()+", bezeichnung='"+ll.getBezeichnung()+"' WHERE idLoc="+ll.getId()+" AND idOn=0;");
				
		return ll;
	}
	
	public Learningline update(Learningline ll) {
		int status = 0;
		if (ll.isStatus()) {
			status = 1;
		}
		dbConLocal.writeQuery("UPDATE Learningline SET status="+status+", fortschritt='"+ll.getFortschritt()+"', authorID="+ll.getAuthorID()+", kategorieID="+ll.getKategorieID()+", bezeichnung='"+ll.getBezeichnung()+"' WHERE idLoc="+ll.getId()+" AND idOn="+ll.getIdOn()+";");
				
		return ll;
	}

	public void delete(Learningline ll) {
		dbConLocal.writeQuery("DELETE FROM Learningline WHERE idLoc="+ll.getId()+" AND idOn="+ll.getIdOn()+";");
		
	}

}
