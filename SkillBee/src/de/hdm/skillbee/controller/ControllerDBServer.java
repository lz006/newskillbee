package de.hdm.skillbee.controller;

/**
 * 
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

import android.content.Context;
import de.hdm.skillbee.db.server.*;

/**
 * Klasse welche die Mapper f�r die Server DB kapselt und
 * deren Singletoneingeschaft sicherstellt. Sie ist selbst
 * auch nur einmal instanziierbar durch den nicht sichtbaren
 * Konstruktor
 * @author Moser, Sonntag, Roth, Zimmermann, Zanella
 *
 */
public class ControllerDBServer {
	
	private static ControllerDBServer con = null;
	
	private KategorieMapper kategorieMapper = null;
	
	private KnotenMapper knotenMapper = null;
	
	private LearninglineMapper learninglineMapper = null;
	
	private UserMapper userMapper = null;
	
	private String url_kategorie_queries = "http://www.wi-stuttgart.de/android_connect/kategorie_queries.php";
	
	private String url_berechtigung_queries = "http://www.wi-stuttgart.de/android_connect/berechtigung_queries.php";
	
	private String url_knoten_queries = "http://www.wi-stuttgart.de/android_connect/knoten_queries.php";
	
	private String url_learningline_queries = "http://www.wi-stuttgart.de/android_connect/learningline_queries.php";
	
	private String url_user_queries = "http://www.wi-stuttgart.de/android_connect/user_queries.php";
	
	private String url_maxid_queries = "http://www.wi-stuttgart.de/android_connect/maxid_queries.php";
	
	private ControllerDBServer() {
		
	}
	
	/**
	 * Gibt einen Verweis auf die ControllerDBServer Instanz zur�ck, solange es diesen Verweis noch nicht gibt
	 * @return
	 */
	public static ControllerDBServer get() {
		if (con == null) {
			con = new ControllerDBServer();
		}
		return con;
	}
	
	/**
	 * Ein Verweis auf die KategorieMapper Instanz wird zur�ckgegeben
	 * @return
	 */
	public KategorieMapper getKategorieMapper() {
		if (kategorieMapper == null) {
			kategorieMapper = new KategorieMapper();
		}
		return kategorieMapper;
	}

	/**
	 * Ein Verweis auf die KnotenMapper Instanz wird zur�ckgegeben
	 * @return
	 */
	public KnotenMapper getKnotenMapper() {
		if (knotenMapper == null) {
			knotenMapper = new KnotenMapper();
		}
		return knotenMapper;
	}

	/**
	 * Ein Verweis auf die LearninglineMapper Instanz wird zur�ckgegeben
	 * @return
	 */
	public LearninglineMapper getLearninglineMapper() {
		if (learninglineMapper == null) {
			learninglineMapper = new LearninglineMapper();
		}
		return learninglineMapper;
	}

	/**
	 * Ein Verweis auf die UserMapper Instanz wird zur�ckgegeben
	 * @return
	 */
	public UserMapper getUserMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}

	public static ControllerDBServer getCon() {
		return con;
	}

	public String getUrl_kategorie_queries() {
		return url_kategorie_queries;
	}

	public String getUrl_berechtigung_queries() {
		return url_berechtigung_queries;
	}

	public String getUrl_knoten_queries() {
		return url_knoten_queries;
	}

	public String getUrl_learningline_queries() {
		return url_learningline_queries;
	}

	public String getUrl_user_queries() {
		return url_user_queries;
	}

	public String getUrl_maxid_queries() {
		return url_maxid_queries;
	}
	
	

}
