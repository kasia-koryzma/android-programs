package com.tabs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Katarzyna Koryzma
 */
public class Camera extends Activity implements View.OnClickListener {
	  String extStorageDirectory, value,resultingImage,name;
	ImageButton ib;
	ImageView iv, image;
	Intent i, iqeActivity;
	Bitmap bm;
	Preview preview;
	MyDBHelper myDBHelper;
	SQLiteDatabase db;
	Bundle extras,dataFromSCh;
	Uri uri ;
	int id, point;
	ProgressDialog progressBar;
	BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        //get ITEM NAME FROM LIST
         dataFromSCh = getIntent().getExtras();
         resultingImage = dataFromSCh.getString("itemPhotographed");
         
         //!!!
         id= dataFromSCh.getInt("_id");
         point = dataFromSCh.getInt("point");
         name=dataFromSCh.getString("name");
         
         uri = Uri.parse("content://com.tabs.ContentProviders/URI3");
         Cursor c = managedQuery(uri, null, null, null, "");     
         c.moveToLast();
       //Get value from content provider
       int nameIndex = c.getColumnIndexOrThrow("name");
        System.out.println(c.getString(nameIndex));
        
    	 //preview = new Preview(this);
    	ib = (ImageButton) findViewById(R.id.takePic);
    	ib.setOnClickListener(this);
    
    }
    

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
    	iv = (ImageView) findViewById(R.id.photo);
		   bmOptions.inSampleSize = 1;
    	
    	extras = data.getExtras();
    	bm = (Bitmap) extras.get("data");
    	ByteArrayOutputStream bs = new ByteArrayOutputStream();
    	bm.compress(Bitmap.CompressFormat.PNG, 100, bs);
    	
    	if (requestCode==0 ) {
    	
			//iv.setImageBitmap(bm);
			saveImageToSD(bm, 1, bmOptions);
			byte[] imageByte = ConvertToByteArray(bm);
			
		 		startActivity(iqeActivity);
					 
		 }
		  
		//readDatabase();

	}

	private  void saveImageToSD(Bitmap bm2, int picNumber,BitmapFactory.Options opt) {
		extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		
		 OutputStream outStream = null;
		   File file = new File(extStorageDirectory, picNumber+".PNG");
		   File f = new File("/sdcard/myim/", picNumber+".PNG");
	    	iqeActivity= new Intent(Camera.this, javaiqe_test.class);
		   iqeActivity.putExtra("picture", picNumber+".PNG");	    	
		   iqeActivity.putExtra("itemName", resultingImage);
		   iqeActivity.putExtra("id",id);
		   iqeActivity.putExtra("point", point);
		   iqeActivity.putExtra("name", name);
		    //*******Go to javaiqe_test class and check the description of the image******//	
		   try {
		    outStream = new FileOutputStream(file);
		    bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			  
		    outStream.flush();
		    outStream.close();
		    
		    Toast.makeText(Camera.this, "Saved", Toast.LENGTH_LONG).show();
		   } catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    
	}
		   
	}

	private byte[] ConvertToByteArray(Bitmap bit) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 50, bs);
		byte[] byteArray = stream.toByteArray();

		
		iqeActivity.putExtra("byteArray",byteArray );

		return byteArray;
	}


	public void onClick(View v) {
		
		 i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);		
		 

	     startActivityForResult(i, 0);

	}
	
	//Retrieves all pictures form db, one by one until the last one
	protected void readDatabase() {
		  BitmapFactory.Options bmOptions;
		   bmOptions = new BitmapFactory.Options();
		   bmOptions.inSampleSize = 1;
		   
		  SQLiteDatabase db = myDBHelper.getReadableDatabase();
		  String[] cols = new String[] {"image"};
		
		  Cursor cursor = db.query(MyDBHelper.DB_TABLENAME, cols, null, null, null, null, null);
		  TextView info = (TextView) findViewById(R.id.text);
		  info.setText("Integer.toString(cursor.getCount())");
		  info.setText(Integer.toString(cursor.getCount()));
		   ImageView image = (ImageView) findViewById(R.id.photo);

		  //loop through the rows in column 'image', from first saved to most recent photo n display it
		  cursor.moveToLast();
		  if (cursor.getCount()>0) {
			  byte[] data = cursor.getBlob(cursor.getColumnIndex("image"));
			  image.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length, bmOptions));
		  	   cursor.moveToPrevious();
}
	
		  cursor.close();
		  db.close();
		  
	  }
	public void onBackPressed()
	 {
	     Intent setIntent = new Intent(Camera.this,CustomMenuBar.class);
	     setIntent.putExtra("name", name);
	     setIntent.putExtra("_id", id);
	     startActivity(setIntent); 
	     return;
	 } 
	@Override
	 protected void onDestroy() {
	        super.onDestroy();
	        // The activity is no longer visible (it is now "stopped")
	        finish();
	    }
	@Override
	 protected void onStop() {
	        super.onStop();
	        // The activity is no longer visible (it is now "stopped")
	        finish();
	    }
	
	


}