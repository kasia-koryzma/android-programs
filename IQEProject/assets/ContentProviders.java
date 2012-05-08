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


public class ContentProviders extends ContentProvider
{
public static final String PROVIDER_NAME =
"com.tabs.ContentProviders";
public static final Uri CONTENT_URI =

Uri.parse("content://"+PROVIDER_NAME);
public static final String _ID = "_id";
public static final String USER = "user";
public static final String SCORE = "score";
public static final String TOTPOINTS = "total";
public static final String CHALLENGELIST = "tasks";
public static final String LOGIN = "login";
public static final String PASSW = "password";

private static final int MATCH_ALL = 1;
private static final int MATCH_ID = 2;
private static final UriMatcher uriMatcher;
static{
	
uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
uriMatcher.addURI(PROVIDER_NAME, "tasks", MATCH_ALL);
uriMatcher.addURI(PROVIDER_NAME, "task/#", MATCH_ID);
}
//---for database use---
private SQLiteDatabase cpDB;
private static final String DATABASE_NAME = "Books";
private static final String DATABASE_TABLE = "userData";
private static String TASK_TABLE = "tasks";

private static final int DATABASE_VERSION = 1;
private static final String DATABASE_CREATE =
"create table " + DATABASE_TABLE +
" (_id integer primary key autoincrement, "
+ "user text not null, total text not null);" ;

private static String DATABASE_CREATE2 =
" create table " + TASK_TABLE +
" (_id integer primary key autoincrement," +
" user text not null, tasks text not null);" ;

private static class DatabaseHelper extends SQLiteOpenHelper
{
DatabaseHelper(Context context) {
super(context, DATABASE_NAME, null, DATABASE_VERSION);
}
@Override
public void onCreate(SQLiteDatabase db)
{
db.execSQL(DATABASE_CREATE);
db.execSQL(DATABASE_CREATE2);

}
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion,
int newVersion) {
Log.w("Content provider database",
"Upgrading database from version " +
oldVersion + " to " + newVersion +
", which will destroy all old data");
db.execSQL("DROP TABLE IF EXISTS userData");
db.execSQL("DROP TABLE IF EXISTS tasks");

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
	case MATCH_ALL:
	count = cpDB.delete(
	DATABASE_TABLE,
	arg1,
	arg2);
	break;
	case MATCH_ID:
	String id = arg0.getPathSegments().get(1);
	count = cpDB.delete(
	TASK_TABLE,
	_ID + " = " + id +
	(!TextUtils.isEmpty(arg1) ? " AND (" +
	arg1 + ')' : ""),
	arg2);
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
	//---get all books---
	case MATCH_ALL:
	return "vnd.android.cursor.dir/vnd.com.tabs.ContentProviders";
	//---get a particular book---
	case MATCH_ID:
	return "vnd.android.cursor.item/vnd.com.tabs.ContentProviders";
	default:
	throw new IllegalArgumentException("Unsupported URI: " + uri);
	}
	}
@Override
public Uri insert(Uri uri, ContentValues values) {
	long rowID = cpDB.insert(
			TASK_TABLE, "", values);
			//---if added successfully---
			if (rowID>0)
			{
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
			}
			throw new SQLException("Failed to insert row into " + uri);
}
@Override
public boolean onCreate() {
	Context context = getContext();
	DatabaseHelper dbHelper = new DatabaseHelper(context);
	cpDB = dbHelper.getWritableDatabase();
	return (cpDB == null)? false:true;
}
@Override
public Cursor query(Uri uri, String[] projection, String selection,
String[] selectionArgs, String sortOrder) {
SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
sqlBuilder.setTables(TASK_TABLE);

if (uriMatcher.match(uri) == MATCH_ID)
//---if getting a particular book---
sqlBuilder.appendWhere(
_ID + " = " + uri.getPathSegments().get(1));
if (sortOrder==null || sortOrder=="")
sortOrder = "singleItem";
Cursor c = sqlBuilder.query(
cpDB,
projection,
selection,
selectionArgs,
null,
null,
sortOrder);
//---register to watch a content URI for changes---
c.setNotificationUri(getContext().getContentResolver(), uri);
return c;
}
@Override
public int update(Uri uri, ContentValues values, String selection,
		String[] selectionArgs) {
	int count = 0;
	switch (uriMatcher.match(uri)){
	case MATCH_ALL:
	count = cpDB.update(
	TASK_TABLE,
	values,
	selection,
	selectionArgs);
	break;
	case MATCH_ID:
	count = cpDB.update(
	TASK_TABLE,
	values,
	_ID + " = " + uri.getPathSegments().get(1) +
	(!TextUtils.isEmpty(selection) ? " AND (" +
	selection + ')' : ""),
	selectionArgs);
	break;
	default: throw new IllegalArgumentException(
	"Unknown URI " + uri);
	}
	getContext().getContentResolver().notifyChange(uri, null);
	return count;
}
}
