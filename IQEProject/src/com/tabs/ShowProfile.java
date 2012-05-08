package com.tabs;

import java.util.ArrayList;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * @author Katarzyna Koryzma
 */
public class ShowProfile  extends Activity {
	MyDBHelper myDBHelper;
	int id,score;
	String username;
	long timeInDays=0,total,stillToF;
	TextView tv;
		@Override
	public void onCreate(Bundle savedInstanceState)
	{
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.readcp);
	        
	        Bundle extr= getIntent().getExtras();
			   id = extr.getInt("_id");
			   username = extr.getString("name");
	    	myDBHelper = new MyDBHelper(this);
		     Uri uri = Uri.parse("content://com.tabs.ContentProviders/URI5");
		     Button exit =(Button )findViewById(R.id.button1);
		     Button list = (Button) findViewById(R.id.button2);
		  tv = (TextView) findViewById(R.id.tv2);
		  
		  Cursor mCursor= managedQuery(uri, null, 
					"name=?",new String[] {username}, null);
		  mCursor.moveToFirst();
		     int p = Integer.parseInt((mCursor.getString(mCursor.getColumnIndex("point"))));
		     tv.setText("Your score is: "+p);
		     
		     list.setOnClickListener(new OnClickListener(){
					public void onClick(View v) {
						setContentView(R.layout.list_layout);
						
						final ListView lv =(ListView)findViewById(R.id.taskList);

						Uri allItems2 = Uri.parse(
					    		       "content://com.tabs.ContentProviders/URI4");
		    			   
						Cursor cr= managedQuery(allItems2, null, 
	        					"name=?",new String[] {username}, null);
						cr.moveToFirst();
						 score=cr.getInt(cr.getColumnIndex("score"));
						ArrayList<String > itemsLeft = new ArrayList<String>();
						if (!cr.equals(null)){
						while(cr.moveToNext()){
							itemsLeft.add(cr.getString(cr.getColumnIndex("item")));
						}
						}
						System.out.println(itemsLeft.size()+"in list");
						ArrayAdapter<String> innerA= new ArrayAdapter<String>(ShowProfile.this,
							    android.R.layout.simple_list_item_1,itemsLeft );			
						lv.setAdapter(innerA);					
				//******************once the user wants to take a picture of particular item on the list :***********************************
						lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						 public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
							 
						    Object o = lv.getItemAtPosition(position);
						     String it= o.toString();
						    Intent in = new Intent (ShowProfile.this, Camera.class);
						    in.putExtra("itemPhotographed", it);
						    in.putExtra("_id", id);
						    in.putExtra("point", score);
						    in.putExtra("name", username);
						    startActivity(in);
						
					}
				});
		     }});

		     exit.setOnClickListener(new OnClickListener(){

					public void onClick(View v) {
						System.exit(0);
						finish();
						android.os.Process.killProcess(android.os.Process.myPid());	
					}});
		   

	    	readDatabase();
	        
	}
	protected void readDatabase() {
		  BitmapFactory.Options bmOptions;
		   bmOptions = new BitmapFactory.Options();
		   bmOptions.inSampleSize = 1;

		  SQLiteDatabase db = myDBHelper.getReadableDatabase();
		  String[] cols = new String[] {"image"};
		  Cursor cursor = db.query(MyDBHelper.DB_TABLENAME, new String[] {"image","name", "tag"} , "tag=?", new String[] { String.valueOf(id) }, null, null, null);
		  
		  TextView info = (TextView) findViewById(R.id.text);
		  info.setText( "Hello "+username+"!");
		   ImageView image = (ImageView) findViewById(R.id.imageView);
		   ImageView image2 = (ImageView) findViewById(R.id.imageView2);
		   ImageView image3 = (ImageView) findViewById(R.id.imageView3);

		   TextView t = (TextView) findViewById(R.id.imageName);
		   TextView t2 = (TextView) findViewById(R.id.imageName2);
		   TextView t3 = (TextView) findViewById(R.id.imageName3);

		   System.out.println(cursor.getCount()+"total of pict");
		  //loop through the rows in column 'image', from first saved to most recent photo n display it
		  cursor.moveToLast();
		  byte[] data;
		  String tag;
		  
		  if (cursor.getPosition() != -1) {
			  
			  data = cursor.getBlob(cursor.getColumnIndex("image"));
			  tag = cursor.getString(cursor.getColumnIndex("name"));
			  image.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length, bmOptions));
			  t.setText(tag);  
		  }
		  	   cursor.moveToPrevious();
		  	   
		  	   if(cursor.getPosition() != -1){
		  	   data=cursor.getBlob(cursor.getColumnIndex("image"));
		  	   tag = cursor.getString(cursor.getColumnIndex("name"));
		  	   image2.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length, bmOptions));
		  	   t2.setText(tag);
		  	   }
		  	   cursor.moveToPrevious();
		  	   if(cursor.getPosition() != -1) {
		  	   	data=cursor.getBlob(cursor.getColumnIndex("image"));
		  	   tag = cursor.getString(cursor.getColumnIndex("name"));
		  	   image3.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length, bmOptions));
		  	   t3.setText(tag);}
		  	   
	
		  cursor.close();
		  db.close();
		  //saveImageToSD(bm, cursor.getCount(), bmOptions);

		  //}*/
	  }
	@Override
    protected void onStart() {
        super.onStart();
        if (timeInDays!=0){
        	
        }
    }

 @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
 @Override
 protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        finish();
    }
 @Override
 protected void onRestart() {
        Uri uri2 = Uri.parse("content://com.tabs.ContentProviders/URI4");
	 
 }
 @Override
 protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
        Uri uri2 = Uri.parse("content://com.tabs.ContentProviders/URI4");
        
       /* ContentValues times= new ContentValues();
        times.put("timeToFinish", total);
		getContentResolver().update(uri2,times , "userId=?",new String[]{Integer.toString(id)} );	  */  }
 public void onBackPressed()
 {
     Intent setIntent = new Intent(ShowProfile.this,CustomMenuBar.class);
     setIntent.putExtra("name", username);
     setIntent.putExtra("_id", id);
     startActivity(setIntent); 
     return;
 } 
 
 
}