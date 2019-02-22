package com.gino.citireapometre.DB;

import com.gino.citireapometre.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper 
{
	
	public static final String TABLE_CAMERE="tblCamere";
	public static final String 	COLUMN_TBLCAMERE_ID="_id";
	public static final String 	COLUMN_TBLCAMERE_NUME="Nume";
	
	public static final String TABLE_PERSOANE="tblPersoane";
	public static final String 	COLUMN_TBLPERSOANE_ID="_id";
	public static final String 	COLUMN_TBLPERSOANE_NUME="Nume";
	public static final String 	COLUMN_TBLPERSOANE_BLOC="Bloc";
	public static final String 	COLUMN_TBLPERSOANE_SCARA="Scara";
	public static final String 	COLUMN_TBLPERSOANE_ETAJ="Etaj";
	public static final String 	COLUMN_TBLPERSOANE_APARTAMENT="Apartament";
	public static final String 	COLUMN_TBLPERSOANE_NRPERSOANE="NrPersoane";
	public static final String 	COLUMN_TBLPERSOANE_EMAIL="Email";
	
	public static final String TABLE_CONSUM="tblConsum";
	public static final String 	COLUMN_TBLCONSUM_ID="_id";
	public static final String 	COLUMN_TBLCONSUM_IDCAMERA="id_Camera";
	public static final String 	COLUMN_TBLCONSUM_RECE="Rece";
	public static final String 	COLUMN_TBLCONSUM_CALDA="Calda";
	public static final String 	COLUMN_TBLCONSUM_DATA="Data";
	
	private static final String DATABASE_NAME="Apometre.db";
	private static final int DATABASE_VERSION = 9;
	
	private static Context context;

	
	// Database creation sql statement
	  private static final String DATABASE_CREATE_TBLCAMERE = "create table "   + TABLE_CAMERE
			  + "(" + COLUMN_TBLCAMERE_ID + " integer primary key autoincrement, "
			  + COLUMN_TBLCAMERE_NUME  + " text not null)";			  
			  
	  private static final String DATABASE_CREATE_TBLPERSOANE = "create table " + TABLE_PERSOANE 
			  + "(" + COLUMN_TBLPERSOANE_ID  + " integer primary key autoincrement, " 
			  + COLUMN_TBLPERSOANE_NUME  + " text, "
			  + COLUMN_TBLPERSOANE_BLOC + " text, "
			  + COLUMN_TBLPERSOANE_SCARA + " text, "
			  + COLUMN_TBLPERSOANE_ETAJ + " text, "
			  +COLUMN_TBLPERSOANE_APARTAMENT + " text, "
			  +COLUMN_TBLPERSOANE_NRPERSOANE + " text, "
			  +COLUMN_TBLPERSOANE_EMAIL + " text)";
			  
	  private static final String DATABASE_CREATE_TBLCONSUM = "create table " + TABLE_CONSUM 
			  + "(" + COLUMN_TBLCONSUM_ID  + " integer primary key autoincrement, " 
			  + COLUMN_TBLCONSUM_IDCAMERA  + " integer, "
			  + COLUMN_TBLCONSUM_RECE + " real, "
			  + COLUMN_TBLCONSUM_CALDA + " real, "
			  + COLUMN_TBLCONSUM_DATA + " text)";
	  
	  public MySQLiteHelper(Context context)
	  {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);  
		this.context=context;
	  }
	  
		  
	  
	  
	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		database.execSQL(DATABASE_CREATE_TBLPERSOANE);
		try
        {      
		database.execSQL(DATABASE_CREATE_TBLCAMERE);	
		//insereaza Bucatarie si Baie default		
				ContentValues values = new ContentValues();				
			    values.put(MySQLiteHelper.COLUMN_TBLCAMERE_NUME, context.getResources().getString(R.string.Bucatarie));	    
			    long insertId = database.insert(MySQLiteHelper.TABLE_CAMERE, null, values);	    
			    ContentValues values2 = new ContentValues();
			    values2.put(MySQLiteHelper.COLUMN_TBLCAMERE_NUME, context.getResources().getString(R.string.Baie));	    
			    long insertId2 = database.insert(MySQLiteHelper.TABLE_CAMERE, null, values2);
				//////////////////////////////////////
		database.execSQL(DATABASE_CREATE_TBLCONSUM);
        } catch (Exception e)
        {
	        // TODO: handle exception
        }
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) 
	{
		Log.w(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		
		String dropSqlString1 = "DROP TABLE IF EXISTS " + TABLE_CONSUM;
		String dropSqlString2 = "DROP TABLE IF EXISTS " + TABLE_PERSOANE;
		String dropSqlString3 = "DROP TABLE IF EXISTS " + TABLE_CAMERE;
		//database.execSQL(dropSqlString1);
		database.execSQL(dropSqlString2);
		//database.execSQL(dropSqlString3);
		onCreate(database);
	}

}
