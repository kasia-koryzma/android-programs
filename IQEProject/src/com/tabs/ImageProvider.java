package com.tabs;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class ImageProvider {
	
	static final String AUTHORITY=  "com.google.provider.Images";
	static final String pr= "taskLists";
	static final String DATABASE_NAME="picturesStorage";
	static final int DATABASE_VERSION=1;
	static final String USERPIC_TABLE_NAME="personalPic";
	
	private ImageProvider() {}
	
	public static final class PhotoTable implements BaseColumns{
		private PhotoTable()  {}
		public static final String TABLE_NAME="personalPic";
		
		//URI (content://com.google.provider.Images/perosnalPic) + MIME definition
		public static final Uri CONTENT_URI=Uri.parse("content://taskLists");
		
		  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/taskLists"; //collection of records (ROWS)
		  public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/taskLists"; 
	        public static final String DEFAULT_SORT_ORDER = "modified DESC";

	      //Columns
	        public static final String IMAGE_NAME = "title";
	        public static final String CREATED_DATE = "created";
	        public static final String MODIFIED_DATE = "modified";	}
	
	public static class ImageDbProvider  extends ContentProvider{
		
		private static HashMap<String, String> ImgProjectionMap;		
		
	 {
			ImgProjectionMap = new HashMap<String, String>();
			ImgProjectionMap.put(PhotoTable._ID, PhotoTable._ID);
			ImgProjectionMap.put(PhotoTable.IMAGE_NAME, PhotoTable.IMAGE_NAME);
			ImgProjectionMap.put(PhotoTable.MODIFIED_DATE, PhotoTable.MODIFIED_DATE);
			ImgProjectionMap.put(PhotoTable.CREATED_DATE, PhotoTable.CREATED_DATE);
		}
		
		private static final int COLLECTION= 1;
	    private static final int SINGLE = 2;
	    private static UriMatcher sUriMatcher;
	    {    
			    sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		        sUriMatcher.addURI(ImageProvider.pr, "personalPic", COLLECTION);
		        sUriMatcher.addURI(ImageProvider.pr, "personalPic/#", SINGLE);
	}
		 private DatabaseHelper mOpenHelper;
		    @Override
		    public boolean onCreate() {
		        mOpenHelper = new DatabaseHelper(getContext());
		        return true;
		    }
		 private class DatabaseHelper extends SQLiteOpenHelper  {

		        DatabaseHelper(Context context) {
		            super(context, ImageProvider.DATABASE_NAME, null, ImageProvider.DATABASE_VERSION);
		        }

		        @Override
		        public void onCreate(SQLiteDatabase db) {
		            db.execSQL("CREATE TABLE " + ImageProvider.USERPIC_TABLE_NAME + " ("
		                    + ImageProvider.PhotoTable._ID + " INTEGER PRIMARY KEY,"
		                    + PhotoTable.IMAGE_NAME + " TEXT,"
		                    + PhotoTable._COUNT + " TEXT,"
		                    + PhotoTable.MODIFIED_DATE + " INTEGER,"
		                    + PhotoTable.CREATED_DATE + " INTEGER"
		                    + ");");
		        }

		        @Override
		        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		            Log.w( "","Upgrading database from version " + oldVersion + " to "
		                    + newVersion + ", which will destroy all old data");
		            db.execSQL("DROP TABLE IF EXISTS "+ImageProvider.USERPIC_TABLE_NAME);
		            onCreate(db);
		        }
		    }

	

		@Override
		public int delete(Uri uri, String where, String[] whereArgs) {
			 SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		        int count;
		        switch (sUriMatcher.match(uri)) {
		        case COLLECTION:
		            count = db.delete(PhotoTable.TABLE_NAME, where, whereArgs);
		            break;

		        case SINGLE:
		            String rowId = uri.getPathSegments().get(1);
		            count = db.delete(PhotoTable.TABLE_NAME, PhotoTable._ID + "=" + rowId
		                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
		            break;

		        default:
		            throw new IllegalArgumentException("Unknown URI " + uri);
		        }

		        getContext().getContentResolver().notifyChange(uri, null);
		        return count;
		}

		@Override
		public String getType(Uri uri) {
			 switch (sUriMatcher.match(uri)) {
		        case COLLECTION:
		            return PhotoTable.CONTENT_TYPE;

		        case SINGLE:
		            return PhotoTable.CONTENT_ITEM_TYPE;

		        default:
		            throw new IllegalArgumentException("Unknown URI " + uri);
		        }
		}

		@Override
		public Uri insert(Uri uri, ContentValues values) {
			 if (sUriMatcher.match(uri) != COLLECTION) {
		            throw new IllegalArgumentException("Unknown URI " + uri);
		        }

		        if (values != null) {
		            values = new ContentValues(values);
		        } else {
		            values = new ContentValues();
		        }

		        Long now = Long.valueOf(System.currentTimeMillis());

		        // Make sure that the fields are all set
		        if (values.containsKey(ImageProvider.PhotoTable.CREATED_DATE) == false) {
		            values.put(ImageProvider.PhotoTable.CREATED_DATE, now);
		        }

		        if (values.containsKey(ImageProvider.PhotoTable.MODIFIED_DATE) == false) {
		            values.put(ImageProvider.PhotoTable.MODIFIED_DATE, now);
		        }

		        if (values.containsKey(ImageProvider.PhotoTable.IMAGE_NAME) == false) {
		            Resources r = Resources.getSystem();
		            values.put(ImageProvider.PhotoTable.IMAGE_NAME, r.getString(android.R.string.untitled));
		        }


		        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		        long rowId = db.insert(PhotoTable.TABLE_NAME,PhotoTable.IMAGE_NAME, values);
		        if (rowId > 0) {
		            Uri noteUri = ContentUris.withAppendedId(PhotoTable.CONTENT_URI, rowId);
		            getContext().getContentResolver().notifyChange(noteUri, null);
		            return noteUri;
		        }

		        throw new SQLException("Failed to insert row into " + uri);
		}

	
		@Override
		public Cursor query(Uri uri, String[] projection, String selection,
				String[] selectionArgs, String sortOrder) {
			
			        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

			        switch (sUriMatcher.match(uri)) {
			        case COLLECTION:
			            qb.setTables(ImageProvider.USERPIC_TABLE_NAME);
			            qb.setProjectionMap(ImgProjectionMap);
			            break;

			        case SINGLE:
			        	qb.setTables(ImageProvider.USERPIC_TABLE_NAME);
			            qb.setProjectionMap(ImgProjectionMap);
			            qb.appendWhere(ImageProvider.PhotoTable._ID + "=" + uri.getPathSegments().get(1));
			            break;

			        default:
			            throw new IllegalArgumentException("Unknown URI " + uri);
			        }

			        // If no sort order is specified use the default
			        String orderBy;
			        if (TextUtils.isEmpty(sortOrder)) {
			            orderBy = ImageProvider.PhotoTable.DEFAULT_SORT_ORDER;
			        } else {
			            orderBy = sortOrder;
			        }

			        // Get the database and run the query
			        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
			        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
			        int i = c.getCount();

			        // Tell the cursor what uri to watch, so it knows when its source data changes
			        c.setNotificationUri(getContext().getContentResolver(), uri);
			        return c;			
		}

		@Override
		public int update(Uri uri, ContentValues values, String where,
				String[] whereArgs) {
			SQLiteDatabase db = mOpenHelper.getWritableDatabase();
	        int count;
	        switch (sUriMatcher.match(uri)) {
	        case COLLECTION:
	            count = db.update(PhotoTable.TABLE_NAME, values, where, whereArgs);
	            break;

	        case SINGLE:
	            String noteId = uri.getPathSegments().get(1);
	            count = db.update(PhotoTable.TABLE_NAME, values, PhotoTable._ID + "=" + noteId
	                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
	            break;

	        default:
	            throw new IllegalArgumentException("Unknown URI " + uri);
	        }

	        getContext().getContentResolver().notifyChange(uri, null);
	        return count;
		}
		
	}
	

}
