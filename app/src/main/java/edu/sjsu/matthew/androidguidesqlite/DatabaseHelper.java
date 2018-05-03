package edu.sjsu.matthew.androidguidesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "Student.db";
	public static final int VERSION_NUM = 3;
	
	public static final String TABLE_NAME = "student_table";
	public static final String COL_ID = "ID";
	public static final String COL_NAME_FIRST = "FIRST_NAME";
	public static final String COL_NAME_LAST = "LAST_NAME";
	public static final String COL_GRADE = "GRADE";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION_NUM);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, GRADE INTEGER)");
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public boolean insertData(String firstName, String lastName, String grade) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(COL_NAME_FIRST, firstName);
		contentValues.put(COL_NAME_LAST, lastName);
		contentValues.put(COL_GRADE, grade);
		
		long result = db.insert(TABLE_NAME, null, contentValues);
		
		if (result == -1) {
			return false;
		}
		
		return true;
	}
	
	public Cursor getAllData() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
		return result;
	}
	
	public boolean updateData(String id, String firstName, String lastName, String grade) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(COL_ID, id);
		contentValues.put(COL_NAME_FIRST, firstName);
		contentValues.put(COL_NAME_LAST, lastName);
		contentValues.put(COL_GRADE, grade);
		
		db.update(TABLE_NAME, contentValues, "ID = ?", new String[] { id } ); // ID = column we want to update if matches ; ? = next argument is replaced with
		return true;
	}
	
	public Integer deleteData(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_NAME, "ID = ?", new String[] { id } );
	}
}
