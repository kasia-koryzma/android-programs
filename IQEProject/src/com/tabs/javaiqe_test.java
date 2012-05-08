package com.tabs;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqengines.javaiqe.javaiqe;
import com.iqengines.javaiqe.javaiqe.IQEQuery;

/**
 * IQEngines Java API
 *
 * test file
 *
 * @author Vincent Garrigues
 * amended by Katarzyna Koryzma
 */
public  class javaiqe_test extends Activity {
	private static File f;
	public File sendIm;
	 IQEQuery query;
	Thread currentThread;
	String result, result2, itemName,name;
	Bitmap myBitmap;
	ImageView im;
	TextView tv;
	MyDBHelper myDBHelper;
	SQLiteDatabase db;
    static int id,point;
    private ProgressDialog progressDialog;

    /**
     * @param args the command line arguments
     */
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	         tv = (TextView) findViewById(R.id.resultImg);
		     setContentView(R.layout.maincams);
	    
	        final String KEY = "64bc6a7fd77643899d3af8b3059ae158";
	        final String SECRET = "c27e162ea7c24c619f85001454611498";
	        final javaiqe iqe;
	        myDBHelper = new MyDBHelper(this);
        /*
         * An API object is initialized using the API key and secret
         */
         iqe = new javaiqe(KEY, SECRET);
         im = (ImageView) findViewById(R.id.im);

        /*
         * You can quickly query an image and retrieve results by doing:
         */
        File dir = Environment.getExternalStorageDirectory();

       //Returns an array of files contained in the directory represented by this file.
        Bundle extras = getIntent().getExtras();
       String resultingImage = extras.getString("picture"); 
       itemName = extras.getString("itemName");
       id = extras.getInt("id");
       point=extras.getInt("point");
       name=extras.getString("name");
       Button back = (Button) findViewById(R.id.back);
       back.setOnClickListener( new OnClickListener() {
    		   public void onClick(View r){
    			   
    			   Intent setIntent = new Intent(javaiqe_test.this,CustomMenuBar.class);
    			     setIntent.putExtra("name", name);
    			     setIntent.putExtra("_id", id);
    			     startActivity(setIntent); 
    	   
       }});
       byte[] byteArray=extras.getByteArray("byteArray");    
       
       f= new File(dir+"/"+resultingImage);			

        
        if(f.exists()){      
        	//a query made to remote server (IQEngines)
	         query = iqe.query(f);                                                 
          
        	}else {AlertDialog d= new AlertDialog.Builder(this).create();
        			d.setMessage("Image not found. Internal error");
        			d.setCancelable(true); 
        			d.show();
        			}
        
        myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
    	
        		iqe.update();         		iqe.update(); 
                result2 = iqe.result(query.getQID(), true);


         itemName.toString();
         int r = itemName.indexOf(result2);
         int c= result2.indexOf(itemName);
         
         //check if the string returned is a match
         if (c!=-1 || r!=-1 ) { 
             result= "match!"; 
         	System.out.println(result2+" is a match"+id);
         	
        	db = myDBHelper.getWritableDatabase();
			ContentValues cv  = new ContentValues();
			cv.put("image", byteArray);
			cv.put("tag", Integer.toString(id));
			cv.put("name",itemName );
			db.insert(MyDBHelper.DB_TABLENAME, null, cv);
			System.out.println("matching pic saved!"); 
			Uri uriItem = Uri.parse(
	    		       "content://com.tabs.ContentProviders/URI4");
     	    int rows=getContentResolver().delete(uriItem,"item=?",new String[]{itemName}	);
     	    System.out.println(rows+" rows");
			db.close();
			Uri updateScore = Uri.parse(
	    		       "content://com.tabs.ContentProviders/URI5");
			
			Cursor mCursor = managedQuery(updateScore, new String[] {"userId", "point"}, 
					"userId=?", new String[]{Integer.toString(id)}, null);
			mCursor.moveToFirst();
			String total=mCursor.getString(mCursor.getColumnIndex("point"));
			mCursor.close();
			int newp=Integer.parseInt(total)+point;
			cv.clear();
			cv.put("point", newp);
			
			//updating the total score of the user
			int rowsNumber=getContentResolver().update(updateScore,cv , "userId=?",new String[]{Integer.toString(id)} );

         }else {
        	 AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        	 alertDialog.setTitle("Item photographed is not a match");
        	 alertDialog.setMessage("Want to take another photo?");
        	 alertDialog.show();
        	 
        	 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
        	 public void onClick(DialogInterface dialog, int which) {
        	       // here you can add functions
        	  Intent t= new Intent(javaiqe_test.this, Camera.class);
        	  t.putExtra("itemPhotographed", itemName);
        	  System.out.println("not recognized");
        	  startActivity(t);
        	    	
        	    }
        	 });
        	 alertDialog.setIcon(R.drawable.icon);
        	 }
	        TextView tv = (TextView) findViewById(R.id.resultImg);
	         ImageView im = (ImageView) findViewById(R.id.im);
	         im.setImageBitmap(myBitmap);
	         tv.setText("\n"+" "+itemName);
		 		
       
    }
	
    private static javaiqe iqe = null;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		  // TODO Auto-generated method stub
		  //return super.onKeyDown(keyCode, event);
		  if (keyCode == KeyEvent.KEYCODE_BACK){
		   //Process onKeyDown event of BACK key
		    finish();
		   return true; //processed
		  }else{
		   //Pass to original method to handle the key event
		   return super.onKeyDown(keyCode, event);
		  }
	

	}
	public void onBackPressed()
	 {
	     Intent setIntent = new Intent(javaiqe_test.this,CustomMenuBar.class);
	     setIntent.putExtra("name", name);
	     setIntent.putExtra("_id", id);
	     startActivity(setIntent); 
	     return;
	 }
	@Override
	 protected void onStop() {
	        super.onStop();
	        // The activity is no longer visible (it is now "stopped")
	        finish();
	    }
	 

}