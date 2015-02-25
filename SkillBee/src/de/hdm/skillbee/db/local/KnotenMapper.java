package de.hdm.skillbee.db.local;

import java.util.Vector;

import android.database.Cursor;
import de.hdm.skillbee.bo.Knoten;
import de.hdm.skillbee.bo.Learningline;

/**
 * Klasse Knoten-Objekte auf die lokale SQLite-DB
 * abzubilden und umgekehrt
 * 
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class KnotenMapper {
	
	private DBConnection dbConLocal = null;
	
	/**
	 * Konstruktor
	 * @param dbConLocal
	 */
	public KnotenMapper(DBConnection dbConLocal) {
		this.dbConLocal = dbConLocal;
	}

	/**
	 * Gibt eine oder mehrere Knoten anhand eines oder mehrerer Id Schl�ssel zur�ck
	 * @param keys
	 * @return
	 */
	public Vector<Knoten> findByKeys(Vector<Integer> keys) {
		StringBuffer ids = new StringBuffer();
		
		if (keys.size() > 1) {
			for (int i = 0; i < keys.size()-1; i++) {
			ids.append(keys.elementAt(i));	
			ids.append(",");
			}		
		}
			
		ids.append(keys.elementAt(keys.size()-1));			
			
		Vector<Knoten> knoten = new Vector<Knoten>();
		
		Cursor c = dbConLocal.readQuery("SELECT * FROM Knoten WHERE id IN (" + ids.toString() + ")",null);
		if (c.moveToFirst()) {
			do {
				Knoten kn = new Knoten();
				kn.setId(c.getInt(0));
				kn.setLlIDLoc(c.getInt(1));
				kn.setLlIDOn(c.getInt(2));
				kn.setUeberschrift(c.getString(3));
				kn.setInhalt(c.getString(4));
				kn.setKurzInhalt(c.getString(5));
				kn.setGoogleLink(c.getString(6));
				kn.setYoutubeLink(c.getString(7));
				
				if (c.getInt(8) == 1) {
					kn.setStatus(true);
				}
				else {
					kn.setStatus(false);
				}
				kn.setPosition(c.getInt(9));
				
				
				knoten.add(kn);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return knoten;
	
	}

	/**
	 * Gibt alle Knoten zur�ck
	 * @return
	 */
	public Vector<Knoten> findAll() {			
		Vector<Knoten> knoten = new Vector<Knoten>();
		
		Cursor c = dbConLocal.readQuery("SELECT * FROM Knoten",null);
		if (c.moveToFirst()) {
			do {
				Knoten kn = new Knoten();
				kn.setId(c.getInt(0));
				kn.setLlIDLoc(c.getInt(1));
				kn.setLlIDOn(c.getInt(2));
				kn.setUeberschrift(c.getString(3));
				kn.setInhalt(c.getString(4));
				kn.setKurzInhalt(c.getString(5));
				kn.setGoogleLink(c.getString(6));
				kn.setYoutubeLink(c.getString(7));
				
				if (c.getInt(8) == 1) {
					kn.setStatus(true);
				}
				else {
					kn.setStatus(false);
				}
				kn.setPosition(c.getInt(9));
				
				knoten.add(kn);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return knoten;
	}

	/**
	 * F�gt einen bestimmten Knoten in die lokale SQLite Datenbank ein
	 * @param kn
	 * @return
	 */
	public Knoten insert(Knoten kn) {
		int status = 0;
		if (kn.isStatus()) {
			status = 1;
		}
		
	
		Cursor c = dbConLocal.readQuery("SELECT MAX(ID) AS maxid FROM Knoten;",null);
		if (c.moveToFirst()) {
			if (c.getCount() == 0) {
				kn.setId(c.getInt(0));
				
				
			}
			else {
				
				if (c.getCount() > 0 && c.getInt(0) == 0) {	
					
					kn.setId(1);
				}
	
			else {
				
				kn.setId(c.getInt(0)+1);
				
			}}
		
		}
		
		else {
			
		}
		
		dbConLocal.writeQuery("INSERT INTO Knoten (`id`, `llIDLoc`, `llIDOn`, `ueberschrift`, `inhalt`,`kurzInhalt`, `medienG`, `medienY`, `status`, `position`) VALUES ("+kn.getId()+", "+kn.getLlIDLoc()+", "+kn.getLlIDOn()+", '"+kn.getUeberschrift()+"', '"+kn.getInhalt()+"', '"+kn.getKurzInhalt()+"', '"+kn.getGoogleLink()+"', '"+kn.getYoutubeLink()+"', "+status+", "+kn.getPosition()+");");
		
		return kn;
	}
	
	
	 /**
	  *  F�gt eine bestimmte Learningline von der Online Datenbank in die lokale SQLite Datenbank ein
	  * @param ll
	  */
	public void updateAfterRelease(Learningline ll) {
		dbConLocal.writeQuery("UPDATE Knoten SET llIDOn="+ll.getIdOn()+"  WHERE llIDLoc="+ll.getId()+" AND llIDOn="+0+";");
		
	}
	
	/**
	 * Gibt alle Knoten einer bestimmten Learningline zur�ck
	 * @param ll
	 * @return
	 */
	public Vector<Knoten> findByLL(Learningline ll) {

        

        Vector<Knoten> knoten = new Vector<Knoten>();

        Cursor c = dbConLocal.readQuery("SELECT * FROM Knoten WHERE llIDLoc="+ll.getId()+"  AND llIDOn="+ll.getIdOn() +  "  ORDER BY position ",null);

        if (c.moveToFirst()) {

               do {

                      Knoten kn = new Knoten();

                      kn.setId(c.getInt(0));

                      kn.setLlIDLoc(c.getInt(1));

                      kn.setLlIDOn(c.getInt(2));

                      kn.setUeberschrift(c.getString(3));

                      kn.setInhalt(c.getString(4));

                      kn.setKurzInhalt(c.getString(5));
                      
                      kn.setGoogleLink(c.getString(6));
    					
                      kn.setYoutubeLink(c.getString(7));

                      
                  int s =   c.getInt(8);
                      if (c.getInt(8) == 1) {

                             kn.setStatus(true);

                      }

                      else {

                             kn.setStatus(false);

                      }

                      kn.setPosition(c.getInt(9));
                      
                     

                      knoten.add(kn);

               }

               while (c.moveToNext());

        }

        else {

               return null;

        }

        

        return knoten;

  

  }
	
/**
 * Ver�ndert und aktualisiert einen bestimmten Knoten in der lokalen SQLite Datenbank
 * @param kn
 * @return
 */
	public Knoten update(Knoten kn) {
		int status = 0;
		if (kn.isStatus()) {
			status = 1;
		}
		dbConLocal.writeQuery("UPDATE Knoten SET llIDLoc="+kn.getLlIDLoc()+", llIDOn="+kn.getLlIDOn()+", ueberschrift='"+kn.getUeberschrift()+"', inhalt='"+kn.getInhalt()+"', kurzInhalt='"+kn.getKurzInhalt()+"', medienG='"+kn.getGoogleLink()+"', medienY='"+kn.getYoutubeLink()+"', status="+status+", position="+kn.getPosition()+"  WHERE id="+kn.getId()+" AND llIDLoc="+kn.getLlIDLoc()+" AND llIDOn="+kn.getLlIDOn()+";");
				
		return kn;
	}

	/**
	 * L�scht einen bestimmten Knoten aus der lokalen SQLite 
	 * @param kn
	 */
	public void delete(Knoten kn) {
		dbConLocal.writeQuery("DELETE FROM Knoten WHERE id="+kn.getId()+" AND llIDLoc="+kn.getLlIDLoc()+" AND llIDOn="+kn.getLlIDOn()+";");
		
	}
	
	/**
	 * L�scht alle Knoten einer bestimmten Learningline
	 * @param ll
	 */
	public void deleteByLL(Learningline ll) {
		dbConLocal.writeQuery("DELETE FROM Knoten WHERE llIDLoc="+ll.getId()+" AND llIDOn="+ll.getIdOn()+";");
		
	}

}
