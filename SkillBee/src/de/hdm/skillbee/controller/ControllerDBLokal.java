package de.hdm.skillbee.controller;

/**
 * 
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

import android.content.Context;
import de.hdm.skillbee.db.local.*;

/**
 * Klasse welche die Mapper f�r die lokale DB kapselt und
 * deren Singletoneingeschaft sicherstellt. Sie ist selbst
 * auch nur einmal instanziierbar durch den nicht sichtbaren
 * Konstruktor
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class ControllerDBLokal {
	
	private static ControllerDBLokal con = null;
	
	private DBConnection dbConLocal = null;
	
	private KategorieMapper kategorieMapper = null;
	
	private KnotenMapper knotenMapper = null;
	
	private LearninglineMapper learninglineMapper = null;
	
	private UserMapper userMapper = null;
	
	/**
	 * Nach au�en hin nicht sichtbarer Konstruktor
	 */
	private ControllerDBLokal() {
		
	}
	
	/**
	 * Gibt einen Verweis auf die ControllerDBLokal Instanz zur�ck, solange es diesen Verweis noch nicht gibt
	 * @return
	 */
	public static ControllerDBLokal get() {
		if (con == null) {
			con = new ControllerDBLokal();
		}
		return con;
	}
	
	/**
	 * Verbindung zur DB und beim ersten Aufruf wird die DB eingerichtet
	 * @param activity
	 * @param dbName
	 */
	public void connect2DB(Context activity, String dbName) {
		if (this.dbConLocal == null) {
			dbConLocal = new DBConnection(activity,dbName);
		}
	}

	/**
	 * Ein Verweis auf die KategorieMapper Instanz wird zur�ckgegeben
	 * @return
	 */
	public KategorieMapper getKategorieMapper() {
		if (kategorieMapper == null) {
			kategorieMapper = new KategorieMapper(dbConLocal);
		}
		return kategorieMapper;
	}

	/**
	 * Ein Verweis auf die KnotenMapper Instanz wird zur�ckgegeben
	 * @return
	 */
	public KnotenMapper getKnotenMapper() {
		if (knotenMapper == null) {
			knotenMapper = new KnotenMapper(dbConLocal);
		}
		return knotenMapper;
	}

	/**
	 * Ein Verweis auf die LearninglineMapper Instanz wird zur�ckgegeben
	 * @return
	 */
	public LearninglineMapper getLearninglineMapper() {
		if (learninglineMapper == null) {
			learninglineMapper = new LearninglineMapper(dbConLocal);
		}
		return learninglineMapper;
	}

	/**
	 * Ein Verweis auf die UserMapper Instanz wird zur�ckgegeben
	 * @return
	 */
	public UserMapper getUserMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper(dbConLocal);
		}
		return userMapper;
	}
	
	

}
