package com.mti.graphics3d_6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnector {

	private static final String DATABASE_NAME = "eventinforamtion";
	private String tableName;
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase database; // database object
	private DatabaseOpenHelper databaseOpenHelper;  //  database helper
	private Context ctx;
	

	
	// public constructor for DatabaseConnector
	public DatabaseConnector(Context context) {
		// create new DatabaseOpenHelper
		this.ctx = context;
		databaseOpenHelper = new DatabaseOpenHelper(context);
	}
	
	private static class DatabaseOpenHelper extends SQLiteOpenHelper {
		
		public DatabaseOpenHelper(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		// create table when the database is created
		@Override
		public void onCreate(SQLiteDatabase db) {
	
			
			String createQuery = "CREATE TABLE testEvents" +
			"(loc_id integer primary key autoincrement," +
			"BHXlocation double, BHYlocation double)";
			
			try{
				db.execSQL(createQuery);
			} catch(Exception e) {

				e.printStackTrace();
				
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS testEvents");
			onCreate(db);
			
		}
		
	} // end of inner class
	
	public DatabaseConnector open() {
		database = databaseOpenHelper.getWritableDatabase(); 
		return this;
	}  // end method open

	public void close() {
		if(database != null) {
			databaseOpenHelper.close();  // close the database connection
		}
	}  // end method close
	
	public long insertData(String tableName, double BHXlocation, double BHYlocation){
		
		createTable(tableName);
		
		ContentValues newEventInfo = new ContentValues();
		newEventInfo.put("BHXlocation", BHXlocation);
		newEventInfo.put("BHYlocation", BHYlocation);
		return database.insertOrThrow(tableName, null, newEventInfo);
	}

	//  retrieve all the values in the database
	public Cursor returnData(String tableName) {
				
		return database.query(tableName, new String[] {"eventId", "BHXlocation", "BHYlocation"}, null, null, null, null, null);
		
	}

	public void createTable(String tableToCreate) {

		String createQuery = "CREATE TABLE" + tableToCreate +
			"(_eventId integer primary key autoincrement," +
			"BHXlocation double, BHYlocation double)";

		try {
			database.execSQL(createQuery);
		} catch(SQLException e){
			e.printStackTrace();			
		}

	
	}

	public void dropTable(String tableName) {
		String createQuery = "DROP TABLE" + tableName;

			try {
				database.execSQL(createQuery);
			} catch(SQLException e){
				e.printStackTrace();			
			}

	}
	
	
}

