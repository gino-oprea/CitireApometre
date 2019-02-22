package com.gino.citireapometre.DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ApometreDataSource
{
	private SQLiteDatabase	database;
	private MySQLiteHelper dbHelper;
	private String[] ColumnsCamere={MySQLiteHelper.COLUMN_TBLCAMERE_ID,MySQLiteHelper.COLUMN_TBLCAMERE_NUME};
	private String[] ColumnsPersoana={MySQLiteHelper.COLUMN_TBLPERSOANE_ID,MySQLiteHelper.COLUMN_TBLPERSOANE_NUME,
										MySQLiteHelper.COLUMN_TBLPERSOANE_BLOC,MySQLiteHelper.COLUMN_TBLPERSOANE_SCARA,MySQLiteHelper.COLUMN_TBLPERSOANE_ETAJ,										
										MySQLiteHelper.COLUMN_TBLPERSOANE_APARTAMENT,MySQLiteHelper.COLUMN_TBLPERSOANE_NRPERSOANE,
										MySQLiteHelper.COLUMN_TBLPERSOANE_EMAIL};
	private String[] ColumnsConsum={MySQLiteHelper.COLUMN_TBLCONSUM_ID,MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA,
			MySQLiteHelper.COLUMN_TBLCONSUM_RECE,MySQLiteHelper.COLUMN_TBLCONSUM_CALDA,
			MySQLiteHelper.COLUMN_TBLCONSUM_DATA};
	
	public ApometreDataSource(Context context)
	{
		dbHelper=new MySQLiteHelper(context);
	}
	
	public void open() throws SQLException 
	{
	    database = dbHelper.getWritableDatabase();
		database.disableWriteAheadLogging();
	}

	public void close() 
	{
	  dbHelper.close();
	}	
	public Camera createCamera(String nume) 
	{
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_TBLCAMERE_NUME, nume);
	    long insertId = database.insert(MySQLiteHelper.TABLE_CAMERE, null, values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_CAMERE, ColumnsCamere, MySQLiteHelper.COLUMN_TBLCAMERE_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Camera newCamera = cursorToCamera(cursor);
	    cursor.close();
	    return newCamera;
	 }
	public void updateCamera(Camera camera)
	{
		ContentValues values = new ContentValues();			
		values.put(MySQLiteHelper.COLUMN_TBLCAMERE_NUME, camera.getName());
		
		int rowsUpdated = database.update(MySQLiteHelper.TABLE_CAMERE, values, 
				MySQLiteHelper.COLUMN_TBLCAMERE_ID + " = " + Long.toString(camera.getId())
				, null);
	}
	public void deleteCamera(Camera camera) 
	{
	    long id = camera.getId();
	    String nume = camera.getName();
	    System.out.println("Camera " + nume+ " a fost stearsa");
	    database.delete(MySQLiteHelper.TABLE_CONSUM, MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + " = " + id, null);
	    database.delete(MySQLiteHelper.TABLE_CAMERE, MySQLiteHelper.COLUMN_TBLCAMERE_ID + " = " + id, null);	    
	}
	
	public List<Camera> getAllCamere() 
	{
	    List<Camera> camere = new ArrayList<Camera>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_CAMERE, ColumnsCamere, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	    	Camera camera = cursorToCamera(cursor);
	      camere.add(camera);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return camere;
	}
	
	private Camera cursorToCamera(Cursor cursor) 
	{
		Camera camera = new Camera();
		camera.setId(cursor.getLong(0));
		camera.setName(cursor.getString(1));
	    return camera;
	}
	
	
	public Persoana createPersoana (String nume, String bloc, String scara, String etaj, String apartament, String nrPersoane, String email)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_NUME, nume);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_BLOC, bloc);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_SCARA, scara);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_ETAJ, etaj);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_APARTAMENT, apartament);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_NRPERSOANE, nrPersoane);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_EMAIL, email);
		
		
		long insertId=database.insert(MySQLiteHelper.TABLE_PERSOANE, null, values);
		Cursor cursor=database.query(MySQLiteHelper.TABLE_PERSOANE, ColumnsPersoana, MySQLiteHelper.COLUMN_TBLPERSOANE_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		
		Persoana persoana = cursorToPersoana(cursor);
		cursor.close();
		return persoana;
	}
	public Persoana updatePersoana (String nume, String bloc,String scara, String etaj,String apartament,String nrPersoane, String email)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_NUME, nume);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_BLOC, bloc);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_SCARA, scara);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_ETAJ, etaj);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_APARTAMENT, apartament);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_NRPERSOANE, nrPersoane);
		values.put(MySQLiteHelper.COLUMN_TBLPERSOANE_EMAIL, email);		
		
		//se ia prima persoana
		Cursor cursor=database.query(MySQLiteHelper.TABLE_PERSOANE, ColumnsPersoana, null, null, null, null, null);
		cursor.moveToFirst();		
		Persoana persoana = cursorToPersoana(cursor);
		//se updateaza prima persoana
		database.update(MySQLiteHelper.TABLE_PERSOANE, values, MySQLiteHelper.COLUMN_TBLPERSOANE_ID + " = " + Long.toString(persoana.getId()), null);
		
		//se ia prima persoana modificata
		cursor=database.query(MySQLiteHelper.TABLE_PERSOANE, ColumnsPersoana, null, null, null, null, null);
		cursor.moveToFirst();		
		persoana = cursorToPersoana(cursor);
		
		cursor.close();
		return persoana;
	}
	
	public Persoana GetPersoana()
	{		
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_PERSOANE, ColumnsPersoana, null, null, null, null, null);	    
	    
	    Persoana persoana= new Persoana();
	    if(cursor.getCount()>0)
	    {
	    	cursor.moveToFirst();		
	    	persoana = cursorToPersoana(cursor);	    	
	    	// make sure to close the cursor
	    	cursor.close();
	    }
	    else 
	    {
			persoana = null;
		}
	    
	    return persoana;
	}
	
	private Persoana cursorToPersoana(Cursor cursor)
	{
		Persoana persoana = new Persoana();
		persoana.setId(cursor.getLong(0));
		persoana.setName(cursor.getString(1));
		persoana.setBloc(cursor.getString(2));
		persoana.setScara(cursor.getString(3));
		persoana.setEtaj(cursor.getString(4));
		persoana.setApartament(cursor.getString(5));
		persoana.setNrPersoane(cursor.getString(6));
		persoana.setEmail(cursor.getString(7));
		return persoana;
	}

	///asta se va apela pt fiecare camera in parte
	public void createConsum(long idCamera,Float consumCalda, Float consumRece, String data)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA, idCamera);
		values.put(MySQLiteHelper.COLUMN_TBLCONSUM_CALDA, consumCalda);
		values.put(MySQLiteHelper.COLUMN_TBLCONSUM_RECE, consumRece);
		values.put(MySQLiteHelper.COLUMN_TBLCONSUM_DATA, data);
		
		//List<Consum> list = getAllConsum();
		String sql="SELECT *  FROM " + MySQLiteHelper.TABLE_CONSUM +  
	    		" WHERE " + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + " = '" + data + "'" +
	    		" AND " + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + " = " + Long.toString(idCamera) +
	    		" ORDER BY " + MySQLiteHelper.COLUMN_TBLCONSUM_DATA;
		Cursor cursor=database.rawQuery(sql, null);
		
		///trebuie verificat daca mai exista inregistrat consum pt camera respectiva in aceeasi data si daca da, sa se faca update
//		Cursor cursor = database.query(MySQLiteHelper.TABLE_CONSUM, ColumnsConsum, 
//				MySQLiteHelper.COLUMN_TBLCONSUM_DATA + " = " + data + " AND " + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + " = " + Long.toString(idCamera), 
//				null, null, null, null);
		
		if(cursor.getCount() == 0)
		{
			long insertId=database.insert(MySQLiteHelper.TABLE_CONSUM, null, values);
			
			String sqlQuery="SELECT " +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_ID + "," +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + "," +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_RECE + "," +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_CALDA + "," +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + "," +
		    		MySQLiteHelper.TABLE_CAMERE + "." + MySQLiteHelper.COLUMN_TBLCAMERE_NUME +
		    		
	 	    		" FROM " + MySQLiteHelper.TABLE_CONSUM + " INNER JOIN " + MySQLiteHelper.TABLE_CAMERE + 
		    		" ON " + MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + " = "
		    		+ MySQLiteHelper.TABLE_CAMERE + "." + MySQLiteHelper.COLUMN_TBLCAMERE_ID + 
		    		" WHERE " + MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_ID + " = " + Long.toString(insertId) +
		    		" ORDER BY " + MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_DATA;
			//cursor=database.query(MySQLiteHelper.TABLE_CONSUM, ColumnsConsum, MySQLiteHelper.COLUMN_TBLCONSUM_ID + " = " + Long.toString(insertId), null, null, null, null);
			cursor = database.rawQuery(sqlQuery, null);		
			cursor.moveToFirst();
			
			Consum consum = cursorToConsum(cursor);
		}
		else 
		{
			values = new ContentValues();			
			values.put(MySQLiteHelper.COLUMN_TBLCONSUM_CALDA, consumCalda);
			values.put(MySQLiteHelper.COLUMN_TBLCONSUM_RECE, consumRece);			
			int rowsUpdated = database.update(MySQLiteHelper.TABLE_CONSUM, values, 
					MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + " = " + Long.toString(idCamera) + " AND " + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + " = '" + data + "'"
					, null);		
		}		
		cursor.close();		
	}
	//asta se apeleaza la edit
	public void updateConsum (long idConsum, String consumCalda, String consumRece) 
	{
		ContentValues values = new ContentValues();		
		values.put(MySQLiteHelper.COLUMN_TBLCONSUM_CALDA, consumCalda);
		values.put(MySQLiteHelper.COLUMN_TBLCONSUM_RECE, consumRece);			
		
		database.update(MySQLiteHelper.TABLE_CONSUM, values, MySQLiteHelper.COLUMN_TBLCONSUM_ID + " = " + Long.toString(idConsum), null);		
	}
	//se sterg toate consumurile din data respectiva
	public void deleteConsum(String data) 
	{	    
	   int x = database.delete(MySQLiteHelper.TABLE_CONSUM, MySQLiteHelper.COLUMN_TBLCONSUM_DATA + " = '" + data + "'", null);	   
	   int test=x;
	}
	public List<Consum> getAllConsum() 
	{
	    List<Consum> consumuri = new ArrayList<Consum>();

	    String sqlQuery="SELECT " +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_ID + "," +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + "," +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_RECE + "," +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_CALDA + "," +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + "," +
	    		MySQLiteHelper.TABLE_CAMERE + "." + MySQLiteHelper.COLUMN_TBLCAMERE_NUME +
	    		
 	    		" FROM " + MySQLiteHelper.TABLE_CONSUM + " INNER JOIN " + MySQLiteHelper.TABLE_CAMERE + 
	    		" ON " + MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + " = "
	    		+ MySQLiteHelper.TABLE_CAMERE + "." + MySQLiteHelper.COLUMN_TBLCAMERE_ID + 
	    		" GROUP BY " + MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_DATA;
	    
	    
	    //Cursor cursor = database.query(MySQLiteHelper.TABLE_CONSUM, ColumnsConsum, null, null, MySQLiteHelper.COLUMN_TBLCONSUM_DATA, null, null);
	    Cursor cursor = database.rawQuery(sqlQuery, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      Consum consum = cursorToConsum(cursor);
	      consumuri.add(consum);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return consumuri;
	}
	public List<Consum> getConsum(String data) 
	{
	    List<Consum> consumuri = new ArrayList<Consum>();

	    String sqlQuery="SELECT " +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_ID + "," +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + "," +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_RECE + "," +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_CALDA + "," +
	    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + "," +
	    		MySQLiteHelper.TABLE_CAMERE + "." + MySQLiteHelper.COLUMN_TBLCAMERE_NUME +
	    		
 	    		" FROM " + MySQLiteHelper.TABLE_CONSUM + " INNER JOIN " + MySQLiteHelper.TABLE_CAMERE + 
	    		" ON " + MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + " = "
	    		+ MySQLiteHelper.TABLE_CAMERE + "." + MySQLiteHelper.COLUMN_TBLCAMERE_ID + 
	    		" WHERE " + MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + " = '" + data + "'";
	    
	    
	    //Cursor cursor = database.query(MySQLiteHelper.TABLE_CONSUM, ColumnsConsum, null, null, MySQLiteHelper.COLUMN_TBLCONSUM_DATA, null, null);
	    Cursor cursor = database.rawQuery(sqlQuery, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {
	      Consum consum = cursorToConsum(cursor);
	      consumuri.add(consum);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return consumuri;
	}
	public List<ConsumListHelper> getConsumListHelper(String Tip) 
	{
	    List<ConsumListHelper> clhList = new ArrayList<ConsumListHelper>();

	    List<String> DateUnice=new ArrayList<String>();
//	    String sqlQuery1="SELECT DISTINCT " +
//	    		MySQLiteHelper.COLUMN_TBLCONSUM_DATA + 	    		
// 	    		" FROM " + MySQLiteHelper.TABLE_CONSUM +
// 	    		" ORDER BY " + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + " ASC";
	    
	    String sqlQuery1="";
	    if(Tip.equals("Lista"))
	    {
	     sqlQuery1="SELECT DISTINCT " +
	    		MySQLiteHelper.COLUMN_TBLCONSUM_DATA + 	    		
 	    		" FROM " + MySQLiteHelper.TABLE_CONSUM +
 	    		" ORDER BY substr(" + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + ", 7, 4) || " +
 	    		"substr(" + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + ", 4, 2) || " +
 	    		"substr(" + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + ", 1, 2) DESC";
	    }
	    if(Tip.equals("Grafic"))
	    {
	    	String subSelect="SELECT DISTINCT " + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + 	    		
	 	    		" FROM " + MySQLiteHelper.TABLE_CONSUM +
	 	    		" ORDER BY substr(" + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + ", 7, 4) || " +
	 	    		"substr(" + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + ", 4, 2) || " +
	 	    		"substr(" + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + ", 1, 2) DESC LIMIT 13";
	    	
	    	sqlQuery1="SELECT DISTINCT " +
		    		MySQLiteHelper.COLUMN_TBLCONSUM_DATA + 	    		
	 	    		" FROM " + //MySQLiteHelper.TABLE_CONSUM +
	 	    		"(" + subSelect + ")" +
	 	    		" ORDER BY substr(" + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + ", 7, 4)," +
	 	    		"substr(" + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + ", 4, 2)," +
	 	    		"substr(" + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + ", 1, 2) ASC";
	    }
	    
	    ////toate datele unice
	    Cursor cursor = database.rawQuery(sqlQuery1, null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) 
	    {	      
	      DateUnice.add(cursor.getString(0));
	      cursor.moveToNext();
	    }
	    
	    for (int i = 0; i < DateUnice.size(); i++)
        {
	        String data = DateUnice.get(i);
	        List<Consum> consumuri = new ArrayList<Consum>();
	        
	        String sqlQuery2="SELECT " +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_ID + "," +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + "," +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_RECE + "," +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_CALDA + "," +
		    		MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + "," +
		    		MySQLiteHelper.TABLE_CAMERE + "." + MySQLiteHelper.COLUMN_TBLCAMERE_NUME +
		    		
	 	    		" FROM " + MySQLiteHelper.TABLE_CONSUM + " INNER JOIN " + MySQLiteHelper.TABLE_CAMERE + 
		    		" ON " + MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_IDCAMERA + " = "
		    		+ MySQLiteHelper.TABLE_CAMERE + "." + MySQLiteHelper.COLUMN_TBLCAMERE_ID +
		    		" WHERE " + MySQLiteHelper.TABLE_CONSUM + "." + MySQLiteHelper.COLUMN_TBLCONSUM_DATA + " = '" + data + "'";
	        
	        cursor = database.rawQuery(sqlQuery2, null);
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) 
		    {
		      Consum consum = cursorToConsum(cursor);		      
		      consumuri.add(consum);
		      cursor.moveToNext();
		    }
		    ConsumListHelper clh = new ConsumListHelper();
		    clh.setData(data);
		    clh.setListConsum(consumuri);	
		    clhList.add(clh);
        }   
	    return clhList;
	}
		
	private Consum cursorToConsum(Cursor cursor)
	{
		Consum consum = new Consum();
		consum.setId(cursor.getLong(0));
		consum.setIdCamera(cursor.getLong(1));
		consum.setConsumRece(cursor.getFloat(2));
		consum.setConsumCalda(cursor.getFloat(3));
		consum.setData(cursor.getString(4));
		consum.setNumeCamera(cursor.getString(5));
		
		return consum;
	}
}

