package de.hdm.skillbee.db.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * 
 * @author Moser, Roth, Sonntag, Zanella, Zimmermann
 *
 */

public class DBConnection extends SQLiteOpenHelper {

	private SQLiteDatabase db;
	private Context con;
	
	/**
	 * Baut die Verbindung zur lokalen Datenbank in Verbindung mit der 
	 * Hauptaktivität und dem Datenbanknamen auf
	 * @param activity
	 * @param dbName
	 */
	public DBConnection(Context activity, String dbName) {
		super(activity, dbName, null, 1);
		db = getWritableDatabase();
		//onCreate(db);
		con = activity;
	}
	/**
	 * Erzeugt die verschiedenen Tabellen der lokalen SQLite Datenbank
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			//Tabelle Kategorie
			String sql = "CREATE TABLE IF NOT EXISTS Kategorie " +
							"(id INTEGER PRIMARY KEY, " +
							"bezeichnung LONGTEXT)";
			db.execSQL(sql);			
						
			//Tabelle Learning Line
			sql = "CREATE TABLE IF NOT EXISTS Learningline " + 
					"(idLoc INTEGER, " +
					"idOn INTEGER," +
					"status BOOLEAN, " +
					"fortschritt INTEGER, " + 
					"authorID INTEGER, " +
					"kategorieID INTEGER, " +
					"bezeichnung LONGTEXT, " +
					"CONSTRAINT pk_LL PRIMARY KEY(idLoc,idOn))";

			
			db.execSQL(sql);
			
			//Tabelle Knoten
			sql = "CREATE TABLE Knoten " +
					"(id INTEGER, " +
					"llIDLoc INTEGER, " +
					"llIDOn INTEGER," +
					"ueberschrift LONGTEXT, " +
					"inhalt LONGTEXT, " +
					"kurzInhalt LONGTEXT, " +
					"medienG LONGTEXT, " +
					"medienY LONGTEXT, " +
					"status BOOLEAN, " +
					"position INTEGER, " +
					"CONSTRAINT pk_KN PRIMARY KEY (id,llIDLoc,llIDOn))";
			
			db.execSQL(sql);
			
			//Tabelle User
			sql = "CREATE TABLE User " +
					"(id INTEGER PRIMARY KEY, " +
					"vorname LONGTEXT, " +
					"name LONGTEXT," +
					"email LONGTEXT, " +
					"passwort LONGTEXT)";
			
			db.execSQL(sql);
			
		}
		catch (Exception e) {
			
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	public Cursor readQuery(String query, String[] args) {
		return db.rawQuery(query, args);
	}
	
	public void writeQuery(String query) {
		db.execSQL(query);
	}
	
}
