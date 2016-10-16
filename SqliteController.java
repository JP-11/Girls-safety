package com.android.src.handwaving;

import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class SqliteController  extends SQLiteOpenHelper {
    private static final String LOGCAT = null;
    
    
    public SqliteController(Context applicationcontext) {
        super(applicationcontext, "Handwaving.db", null, 1);
        Log.d(LOGCAT,"Created");
    }
     
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE userdetails (slno TEXT,pass TEXT,emerPass TEXT,emailId TEXT,policeNo TEXT,guardianNo TEXT,ipaddress TEXT)";
        database.execSQL(query);
        Log.d(LOGCAT,"userdetails Created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS userdetails";
        database.execSQL(query);
        onCreate(database);
    }
    
    
    public String  getFirstRow(){
    	String  device1= null;
    	
    	 SQLiteDatabase database = this.getReadableDatabase();
    	 String selectQuery = "SELECT  * from userdetails where slno='1'";
    	 Cursor cursor = database.rawQuery(selectQuery, null);
    	 if(cursor.moveToFirst()){
    		 do {
    			 device1 = cursor.getString(0);
    			 device1 = "AVAILABLE";
    			
    			 Log.d(LOGCAT,"Test....."+device1);
    			 //Toast.makeText(context, altermobileNoEmailId, Toast.LENGTH_LONG).show();
    		 }while (cursor.moveToNext());
    	 }else{
    		 device1 = "NOTAVAILABLE";
    	 }
    	 database.close();
    	return device1;
    }
    
     
    public long insertdetail(String pass,String emerPass,String emailId,String policeNo,String guardianNo,String ipaddress) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("slno","1");
        values.put("pass", pass);
        values.put("emerPass", emerPass);
        values.put("emailId", emailId);
        values.put("policeNo", policeNo);
        values.put("guardianNo", guardianNo);
        values.put("ipaddress", ipaddress);
        
                    
        long i = database.insert("userdetails", null, values);
        if(i>0){
        	Log.d(LOGCAT, "Values are inserted successfully");
        }else{
        	Log.d(LOGCAT, "Values are Not inserted successfully");
        }
        database.close();
        return i;
    }
     
    
    public String  getPassword(){
    	String  password= null;
    	
    	 SQLiteDatabase database = this.getReadableDatabase();
    	 String selectQuery = "SELECT  pass from userdetails where SLNO='1'";
    	 Cursor cursor = database.rawQuery(selectQuery, null);
    	 if(cursor.moveToFirst()){
    		 do {
    			 password = cursor.getString(1);
    			Log.d(LOGCAT,"password....."+password);
    			 //Toast.makeText(context, altermobileNoEmailId, Toast.LENGTH_LONG).show();
    		 }while (cursor.moveToNext());
    	 }else{
    		 password = "NOTAVAILABLE";
    	 }
    	 database.close();
    	return password;
    }
    
    public String  getEmergencyPassword(){
    	String emerPass= null;
    	String pass = null;
    	String policeNo = null;
    	String guardianNo = null;
    	String ipaddress = null;
    	String details = "";
    	String emailId = null;
    	
    	 SQLiteDatabase database = this.getReadableDatabase();
    	 String selectQuery = "SELECT  pass,emerPass,emailId,policeNo,guardianNo,ipaddress from userdetails where SLNO='1'";
    	 Cursor cursor = database.rawQuery(selectQuery, null);
    	 if(cursor.moveToFirst()){
    		 do {
    			 pass = cursor.getString(0);
    			 emerPass = cursor.getString(1);
    			 emailId = cursor.getString(2);
    			 policeNo = cursor.getString(3);
    			 guardianNo = cursor.getString(4);
    			 ipaddress = cursor.getString(5);
    			 details = "VALID$"+pass+"$"+emerPass+"$"+emailId+"$"+policeNo+"$"+guardianNo+"$"+ipaddress;
    				
    			 Log.d(LOGCAT,"details....."+details);
    			 //Toast.makeText(context, altermobileNoEmailId, Toast.LENGTH_LONG).show();
    		 }while (cursor.moveToNext());
    	 }else{
    		 details = "INVALID";
    	 }
    	 database.close();
    	return details;
    }
    
       
    public int updatePassword(String pass,String emerPass,String emailId ,String policeNo,String guardianNo,String ipaddress) {
        SQLiteDatabase database = this.getWritableDatabase();   
        ContentValues values = new ContentValues();
        
        values.put("pass", pass);
        values.put("emerPass", emerPass);
        values.put("emailId", emailId);
        values.put("policeNo", policeNo);
        values.put("guardianNo", guardianNo);
        values.put("ipaddress", ipaddress);
        
        return database.update("userdetails", values, "SLNO" + " = ?", new String[] {"1"});
    }
    
      
}
