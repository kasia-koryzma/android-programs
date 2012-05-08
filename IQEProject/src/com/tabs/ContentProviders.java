package com.tabs;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
/**
 * @author Katarzyna Koryzma
 */

public class ContentProviders extends ContentProvider
{
	final String reset = 
			"ALTER TABLE " + DATABASE_TABLE 
			+ " ADD COLUMN challengTitle text "
			+ ";";
public static final String PROVIDER_NAME =   "com.tabs.ContentProviders";  //authority
public static final Uri CONTENT_URI_1 =
Uri.parse("content://"+PROVIDER_NAME+"/URI1");
public static final Uri CONTENT_URI_2 =
Uri.parse("content://"+PROVIDER_NAME+"/URI2");
public static final Uri Content_Uri_3  =
Uri.parse("content://"+PROVIDER_NAME+"/URI3");
public static final Uri Content_Uri_4  =
Uri.parse("content://"+PROVIDER_NAME+"/URI4");
public static final Uri Content_Uri_5  =
Uri.parse("content://"+PROVIDER_NAME+"/URI5");
public static final String _ID = "_id";
public static final String USER = "user";
public static final String SCORE = "score";
public static final String TOTPOINTS = "total";
public static final String CHALLENGELIST = "taskstodo";
public static final String LOGIN = "login";
public static final String PASSW = "password";
public static String userID = "id";
			//TABLES and DB
private static final String DATABASE_NAME = "no";
private static final String DATABASE_TABLE = "task2";
static String 		TASK_TABLE = "trial";
private static final String USERid_TABLE = "register";
private static final String itemList = "itemList";
private static final String points = "points";

private static final int MATCH_ALL = 1;
private static final int MATCH_ID = 2;
private static final UriMatcher uriMatcher;
static{
	//Sets the integer value for multiple rows in tables tasks and total to 1 and 2
uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
uriMatcher.addURI(PROVIDER_NAME, "URI1", 1);
uriMatcher.addURI(PROVIDER_NAME, "URI1/#", 2);
uriMatcher.addURI(PROVIDER_NAME, "URI3", 5);
uriMatcher.addURI(PROVIDER_NAME, "URI3/#", 6);

uriMatcher.addURI(PROVIDER_NAME, "URI2", 3);
uriMatcher.addURI(PROVIDER_NAME, "URI2/#", 4);
uriMatcher.addURI(PROVIDER_NAME, "URI4", 7);
uriMatcher.addURI(PROVIDER_NAME, "URI5", 8);


}
//---for database use---
static SQLiteDatabase cpDB;
private static int DATABASE_VERSION = 23;
private static final String DATABASE_CREATE =
"create table " + DATABASE_TABLE +
" (_id integer primary key autoincrement, "+
" user text, total text, userNum integer);" ;

private static String DATABASE_CREATE2 =
" create table " + TASK_TABLE +
" (_id integer primary key autoincrement, " +
"taskstodo text, listNo integer );" ;

static final String USER_REG_DB =
"create table " + USERid_TABLE +
" (_id integer primary key autoincrement, "+
" password text, name text);" ;

static final String challList = 
"create table "+itemList+
" (_id integer primary key autoincrement, "+
" item text, userId integer, score integer, listId integer, timeToFinish text, name text);" ;

static final String UserPoints = 
"create table "+points+
" (_id integer primary key autoincrement, "+
" userId text, point integer, name text);" ;


 static class DatabaseHelper extends SQLiteOpenHelper
{
DatabaseHelper(Context context) {
super(context, DATABASE_NAME, null, DATABASE_VERSION);
}
@Override
public void onCreate(SQLiteDatabase db)
{//String reset2="ALTER TABLE tasks3 AUTO_INCREMENT = 0;";

db.execSQL(DATABASE_CREATE);
db.execSQL(DATABASE_CREATE2);
db.execSQL(USER_REG_DB);
db.execSQL(challList);
db.execSQL(UserPoints);


}
@Override		//when the database version, represent with the constant DATABASE_VERSION is changed
public void onUpgrade(SQLiteDatabase db, int oldVersion,
int newVersion) {
Log.w("Content provider database",
"Upgrading database from version " +
oldVersion + " to " + newVersion +
", which will destroy all old data");

db.execSQL("DROP TABLE IF EXISTS task2");
db.execSQL("DROP TABLE IF EXISTS trial");
db.execSQL("DROP TABLE IF EXISTS register");
db.execSQL("DROP TABLE IF EXISTS itemList");
db.execSQL("DROP TABLE IF EXISTS points");

//db.execSQL(ALTER_TBL);

onCreate(db);
}
}
//...
//...
@Override
public int delete(Uri arg0, String arg1, String[] arg2) {
	// arg0 = uri
	// arg1 = selection
	// arg2 = selectionArgs
	int count=0;
	switch (uriMatcher.match(arg0)){
	case 1:
	count = cpDB.delete(
	DATABASE_TABLE,
	arg1,
	arg2);
	break;
	
	case 3:
	String id = arg0.getPathSegments().get(1);
	count = cpDB.delete(
	TASK_TABLE,
	_ID + " = " + id +
	(!TextUtils.isEmpty(arg1) ? " AND (" +
	arg1 + ')' : ""),
	arg2);
	break;
	case 5:
		count = cpDB.delete(
				USER_REG_DB,
				arg1,
				arg2);
	break;
	case 7:
		count = cpDB.delete(
		itemList,
		arg1, arg2
		);
		break;
	default: throw new IllegalArgumentException(
	"Unknown URI " + arg0);
	}
	getContext().getContentResolver().notifyChange(arg0, null);
	return count;
}
@Override
public String getType(Uri uri) {
	switch (uriMatcher.match(uri)){
	case 1:
	return "vnd.android.cursor.dir/vnd.com.tabs.ContentProviders.URI1";
	case 5:
		return "vnd.android.cursor.dir/vnd.com.tabs.ContentProviders.URI3";
	
	case 3:
	return "vnd.android.cursor.dir/vnd.com.tabs.ContentProviders.URI2";
	case 7:
		return "vnd.android.cursor.dir/vnd.com.tabs.ContentProviders.URI4";
	case 8:
		return "vnd.android.cursor.dir/vnd.com.tabs.ContentProviders.URI5";

	default:
	throw new IllegalArgumentException("Unsupported URI: " + uri);
	}
	}
@Override
public Uri insert(Uri uri, ContentValues values) {
	 Uri _uri = null;
	    switch (uriMatcher.match(uri)){
	    case 1:
	long rowID = cpDB.insert(
			DATABASE_TABLE, "", values);
			//---if added successfully---
			if (rowID>0)
			{
			 _uri = ContentUris.withAppendedId(CONTENT_URI_1, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			}
			break;
	    case 3:			
	    	long rowID2 = cpDB.insert(
	    			TASK_TABLE, "", values);
	    	if (rowID2>0)
			{
			 _uri = ContentUris.withAppendedId(CONTENT_URI_2, rowID2);
			getContext().getContentResolver().notifyChange(_uri, null);
			}break;
	    case 5:
	    	long rowID3 = cpDB.insert(
	    			USERid_TABLE, "", values);
	    	if (rowID3>0)
			{
			 _uri = ContentUris.withAppendedId(Content_Uri_3, rowID3);
			getContext().getContentResolver().notifyChange(_uri, null);
			}
	    	break;
	    case 7:
	    	long rowID4 = cpDB.insert(
	    			itemList, "", values);
	    	if (rowID4>0)
			{
			 _uri = ContentUris.withAppendedId(Content_Uri_4, rowID4);
			getContext().getContentResolver().notifyChange(_uri, null);
			}
	    	break;
	    case 8:
	    	long rowID5 = cpDB.insert(
	    			points, "", values);
	    	if (rowID5>0)
			{
			 _uri = ContentUris.withAppendedId(Content_Uri_5, rowID5);
			getContext().getContentResolver().notifyChange(_uri, null);
			}
	    	break;
			default: throw new SQLException("Failed to insert row into " + uri);  }
    	return _uri;

}
@Override
public boolean onCreate() {
	Context context = getContext();
	DatabaseHelper dbHelper = new DatabaseHelper(context);
	cpDB = dbHelper.getWritableDatabase();
	//cpDB.execSQL(reset);
	return (cpDB == null)? false:true;
}
@Override
public Cursor query(Uri uri, String[] projection, String selection,
String[] selectionArgs, String sortOrder) {
SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();

Cursor c=null;

int match = uriMatcher.match(uri);
switch(match)
{
case 1:
	sqlBuilder.setTables(ContentProviders.DATABASE_TABLE);
 c = sqlBuilder.query(
cpDB,
projection,
selection,
selectionArgs,
null,
null,
sortOrder);
c.setNotificationUri(getContext().getContentResolver(), uri);		break;

case 5:
	sqlBuilder.setTables(ContentProviders.USERid_TABLE);
	 c = sqlBuilder.query(
	cpDB,
	projection,
	selection,
	selectionArgs,
	null,
	null,
	sortOrder);
	c.setNotificationUri(getContext().getContentResolver(), uri);	break;
case 3:
	sqlBuilder.setTables(ContentProviders.TASK_TABLE);
			 c = sqlBuilder.query(
			cpDB,
			projection,
			selection,
			selectionArgs,
			null,
			null,
			sortOrder);
				c.setNotificationUri(getContext().getContentResolver(), uri);	 break;
case 7:
	sqlBuilder.setTables(ContentProviders.itemList);
			 c = sqlBuilder.query(
			cpDB,
			projection,
			selection,
			selectionArgs,
			null,
			null,
			sortOrder);
				c.setNotificationUri(getContext().getContentResolver(), uri);	 break;
case 8:
	sqlBuilder.setTables(ContentProviders.points);
			 c = sqlBuilder.query(
			cpDB,
			projection,
			selection,
			selectionArgs,
			null,
			null,
			sortOrder);
				c.setNotificationUri(getContext().getContentResolver(), uri);	 break;


default:
    throw new IllegalArgumentException("Unknown URL " + uri);
}
return c;
}
@Override
public int update(Uri uri, ContentValues values, String selection,
		String[] selectionArgs) {
	int count = 0;
	switch (uriMatcher.match(uri)){
	case 1:
	count = cpDB.update(
	DATABASE_TABLE,
	values,
	selection,
	selectionArgs);
	break;
	case 3:
	count = cpDB.update(
	TASK_TABLE,
	values,
	  "_id= " + uri.getPathSegments().get(1) +
	(!TextUtils.isEmpty(selection) ? " AND (" +
	selection + ')' : ""),
	selectionArgs); break;
	case 5:
		count = cpDB.update(
				USER_REG_DB,
				values,
				selection,
				selectionArgs);
	break;
	case 8:
		count = cpDB.update(
				points,
				values,
				selection,
				selectionArgs);
	break;
	default: throw new IllegalArgumentException(
	"Unknown URI " + uri);
	}
	getContext().getContentResolver().notifyChange(uri, null);
	return count;
}
}
