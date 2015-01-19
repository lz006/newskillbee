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
	
	public KnotenMapper(DBConnection dbConLocal) {
		this.dbConLocal = dbConLocal;
	}

	
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
				
				if (c.getInt(6) == 1) {
					kn.setStatus(true);
				}
				else {
					kn.setStatus(false);
				}
				kn.setPosition(c.getInt(7));
				kn.setGoogleLink(c.getString(8));
				kn.setYoutubeLink(c.getString(9));
				
				knoten.add(kn);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return knoten;
	
	}

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
				
				if (c.getInt(6) == 1) {
					kn.setStatus(true);
				}
				else {
					kn.setStatus(false);
				}
				kn.setPosition(c.getInt(7));
				kn.setGoogleLink(c.getString(8));
				kn.setYoutubeLink(c.getString(9));
				knoten.add(kn);
			}
			while (c.moveToNext());
		}
		else {
			return null;
		}
		
		return knoten;
	}

	public Knoten insert(Knoten kn) {
		int status = 0;
		if (kn.isStatus()) {
			status = 1;
		}
		

		Cursor c = dbConLocal.readQuery("SELECT MAX(ID) AS maxid FROM Knoten;",null);
		if (c.moveToFirst()) {
			if (c.getInt(0) == 0) {
				kn.setId(c.getInt(0));
				
				
			}
	
			else {
				
				kn.setId(c.getInt(0)+1);
				
			}
		
		}
		
		else {
			
		}
		
		dbConLocal.writeQuery("INSERT INTO Knoten (`id`, `llIDLoc`, `llIDOn`, `ueberschrift`, `inhalt`,`kurzInhalt`,`status`,`position`, `medienG`, `medienY`) VALUES ("+kn.getId()+", "+kn.getLlIDLoc()+", "+kn.getLlIDOn()+", '"+kn.getUeberschrift()+"', '"+kn.getInhalt()+"', '"+kn.getKurzInhalt()+"', "+status+", "+kn.getPosition()+", "+kn.getGoogleLink()+", "+kn.getYoutubeLink()+");");
		
		return kn;
	}
	
	public Vector<Knoten> findByLL(Learningline ll) {

        

        Vector<Knoten> knoten = new Vector<Knoten>();

        Cursor c = dbConLocal.readQuery("SELECT * FROM Knoten WHERE llIDLoc="+ll.getId()+"  AND llIDOn="+ll.getIdOn(),null);

        if (c.moveToFirst()) {

               do {

                      Knoten kn = new Knoten();

                      kn.setId(c.getInt(0));

                      kn.setLlIDLoc(c.getInt(1));

                      kn.setLlIDOn(c.getInt(2));

                      kn.setUeberschrift(c.getString(3));

                      kn.setInhalt(c.getString(4));

                      kn.setKurzInhalt(c.getString(5));

                      

                      if (c.getInt(6) == 1) {

                             kn.setStatus(true);

                      }

                      else {

                             kn.setStatus(false);

                      }

                      kn.setPosition(c.getInt(7));

                      knoten.add(kn);

               }

               while (c.moveToNext());

        }

        else {

               return null;

        }

        

        return knoten;

  

  }
	

	public Knoten update(Knoten kn) {
		int status = 0;
		if (kn.isStatus()) {
			status = 1;
		}
		dbConLocal.writeQuery("UPDATE Knoten SET llIDLoc="+kn.getLlIDLoc()+", llIDOn="+kn.getLlIDOn()+", ueberschrift='"+kn.getUeberschrift()+"', inhalt='"+kn.getInhalt()+"', kurzInhalt='"+kn.getKurzInhalt()+"', status="+status+", position="+kn.getPosition()+", medienG="+kn.getGoogleLink()+", medienY="+kn.getYoutubeLink()+" WHERE id="+kn.getId()+" AND llIDLoc="+kn.getLlIDLoc()+" AND llIDOn="+kn.getLlIDOn()+";");
				
		return kn;
	}

	public void delete(Knoten kn) {
		dbConLocal.writeQuery("DELETE FROM Knoten WHERE id="+kn.getId()+" AND llIDLoc="+kn.getLlIDLoc()+" AND llIDOn="+kn.getLlIDOn()+";");
		
	}

}