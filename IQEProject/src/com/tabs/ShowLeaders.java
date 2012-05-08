package com.tabs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * @author Katarzyna Koryzma
 */
public class ShowLeaders extends Activity {
String username;
int id;

	@Override
	public void onCreate(Bundle s) {
		super.onCreate(s);
		setContentView(R.layout.act1);
		  final String num[] ={"1","2","3","4","5","6","7","8","9","10"};
		   Bundle extr= getIntent().getExtras();
		    username=extr.getString("name");
		    id= extr.getInt("_id");
				  
		List <String> list = new ArrayList<String>();
		List <String> score = new ArrayList<String>();
		
		ListView lview = (ListView) findViewById(R.id.listView2);  
		
	     Uri uri2 = Uri.parse("content://com.tabs.ContentProviders/URI5");
	     Cursor mCursor = managedQuery(uri2, new String[] {"point", "name","_id"}, 
					null,null,"point desc" );
	     mCursor.moveToLast();
	     System.out.println(mCursor.getString(mCursor.getColumnIndex("name")));
	     System.out.println(mCursor.getString(mCursor.getColumnIndex("point")));

	     mCursor.moveToFirst();
	     System.out.println(mCursor.getString(mCursor.getColumnIndex("name")));
	     System.out.println(mCursor.getString(mCursor.getColumnIndex("point")));

	     mCursor.moveToFirst();
	     System.out.println(mCursor.getCount()+" scores");
	     int limit =0;
	    	 
	    	 while (!mCursor.isAfterLast() ) {
	    		 list.add(mCursor.getString(mCursor.getColumnIndex("name")));
	    		 System.out.println(mCursor.getString(mCursor.getColumnIndex("point")));
	    		 score.add(mCursor.getString(mCursor.getColumnIndex("point")));
	    		 mCursor.moveToNext();
	    		 
	    	 }
	    	 
	    	 Product[] items =   {
	    		 new Product(1, list.get(0), score.get(0)), 
	    		 new Product(2, list.get(1), score.get(1)),
	    		 new Product(3, list.get(2), score.get(2)), 
	    		 new Product(4, list.get(3), score.get(3)), 
	    		 new Product(5, list.get(4), score.get(4)), 
	    		 new Product(6, list.get(5), score.get(5)), 
	    		 new Product(7, list.get(6), score.get(6)), 
	    		 new Product(8, list.get(7), score.get(7)), 
	    		 new Product(9, list.get(8), score.get(8)), 
	    		 new Product(10, list.get(9), score.get(9)), 

	            
	    	 };
	    	 ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,
	                    android.R.layout.simple_list_item_1, items);
	        
	        lview.setAdapter(adapter);

		
		}
	public void onBackPressed()
    {
        Intent setIntent = new Intent(ShowLeaders.this,CustomMenuBar.class);
        setIntent.putExtra("name", username);
        setIntent.putExtra("_id", id);
        startActivity(setIntent); 
        return;
    } 
	public class Product {
        private int id;
        private String name;
        private String price;
        
        public Product(){
            super();
        }
        
        public Product(int id, String name, String price) {
            super();
            this.id = id;
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return this.id + ". " + this.name + " " + this.price + "";
        }
    }
	@Override
	 protected void onStop() {
	        super.onStop();
	        // The activity is no longer visible (it is now "stopped")
	        finish();
	    }
  
}
