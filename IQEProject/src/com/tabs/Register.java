package com.tabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
/**
 * @author Katarzyna Koryzma
 */

public class Register extends Activity implements View.OnClickListener {

    EditText mText, Password, PassChk;
    Button mAdd;
    ListView mList;
    Uri uri, items;
    SQLiteDatabase mDb;
    Cursor mCursor, Cursor2;
    SimpleCursorAdapter mAdapter;
    String[] columns, columns2;
    SQLiteOpenHelper DatabaseHelper;
    ContentProviders cvs;
    int userNum;
    ProgressDialog pb ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        mText = (EditText)findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.txtPassword);        
                       
        Button showDB = (Button) findViewById(R.id.login);
		showDB.setOnClickListener(this);
        Button backLog = (Button) findViewById(R.id.backLog);
        backLog.setOnClickListener(this);
        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(this);


        
    }
	public void onClick(View v) {            	
		ContentValues cv ;
		AlertDialog ad = null;
		switch(v.getId()) {
		case R.id.login:
		
				Password = (EditText) findViewById(R.id.txtPassword);
        		PassChk = (EditText) findViewById(R.id.txtPassword2);
        		
        		if (!Password.toString().equals("") && !PassChk.toString().equals("") && Password.getText().toString().equals(PassChk.getText().toString()) ) {
					cv = new ContentValues();	
					ContentValues cvt = new ContentValues();
					Uri allItems = Uri.parse(
						"content://com.tabs.ContentProviders/URI3");
					String nam= mText.getText().toString();
					String passw = Password.getText().toString();
        			cv.put("password",passw );
            	    cv.put("name", nam);
        			
        			uri = getContentResolver().insert(
							allItems, cv);	
        			Cursor cr= managedQuery(allItems, new String[] {"_id", "name"}, 
        					null,null, null);
        			cr.moveToLast();
        			//this is the ID for a new user, will be used for allocating particular data in database 
        			String id= cr.getString (cr.getColumnIndex("_id"));
        			int idInt = Integer.parseInt(id);
        			System.out.println(idInt ); 
        			cv.clear(); 
        			int e=0;
        			cv.put("userId", id);
        			cv.put("name", nam);
        			cv.put("point", e);
        		     Uri uri2 = Uri.parse("content://com.tabs.ContentProviders/URI5");
        		     uri= getContentResolver().insert(uri2, cv);

				cv.clear();														//filter without, string to replace with, how to group,group filter, order by this row
    			pb = ProgressDialog.show(this, "Creating account..", "Please wait", true,
	                                false);
	 
	    				 new Thread() {

	    					 public void run() {

	    					 try{
	    					 sleep(2000);
	    					 } catch (Exception e) {
	    					 Log.e("tag", e.getMessage());
	    					 }
	    					 // dismiss the progress dialog
	    					 pb.dismiss();
	    					 }
	    					 }.start();
	    					 
	    					 Intent gotoProfile = new Intent (Register.this, CustomMenuBar.class);
	    					 gotoProfile.putExtra("_id", idInt);
	    					 gotoProfile.putExtra("name", nam);
	    					 startActivity(gotoProfile);
        		}else 
        		{  ad = new AlertDialog.Builder(Register.this).create();
        		   ad.setMessage("Invalid input. Make sure that the passwords are the same "); ad.setButton("OK", new DialogInterface.OnClickListener() {
        			   public void onClick(DialogInterface dialog, int which) {
        				   }
        				}); ad.show(); ad.setCancelable(true);  } break;
		case R.id.backLog:
			Intent y =new Intent(Register.this, LoginActivity.class);
			startActivity(y); break;
		case R.id.exit:
			System.runFinalizersOnExit(true);
		    System.exit(0); 
		    finish(); break;
		    
	    			
}
	}
}
