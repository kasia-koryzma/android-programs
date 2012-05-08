package com.tabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * @author Katarzyna Koryzma
 */
public class MyDBHelper extends SQLiteOpenHelper {

	final protected static String DATABASE_NAME="picturesStorage";
	final protected static String DB_TABLENAME="picturesArray";

	public MyDBHelper(Context context) {
		super(context, DATABASE_NAME, null, 3);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE "+DB_TABLENAME+" (_id INTEGER PRIMARY KEY, image BLOB, tag TEXT, name text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS picturesArray"); 
	    onCreate(db);
	}}