package com.tabs;
import java.util.ArrayList;
import java.util.ListIterator;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ShowChallenge extends Activity implements AdapterView.OnItemSelectedListener{
	String it,name;
	static String[] items = {"Easy", "Moderate", "Difficult", "Intense"}, day={"1","2","5","10"};
	String[] toArr=null;
	static Spinner spin,days;
	ListView lv;
	static Button setGame,getList;
	EditText time;
	ProgressBar progress;
	int id, score, listId=0;
	static ArrayList<String> myList ;
	ContentValues values;
	Uri uri, allItems, uri2;
	Intent in;
	 Uri allItems2;
	 long timeInDays;
    int[] image = { R.drawable.camera, R.drawable.camera, R.drawable.camera,
                                    R.drawable.camera, R.drawable.camera, R.drawable.camera, R.drawable.camera,
                                    R.drawable.camera, R.drawable.camera, R.drawable.camera };
	@Override
	public void onCreate(Bundle s){
		super.onCreate(s);
		setContentView(R.layout.challenges);
		
		Bundle ex = getIntent().getExtras();
		 id= ex.getInt("_id"); 
		 name=ex.getString("name");
		
		spin = (Spinner) findViewById(R.id.spinner);
		days = (Spinner) findViewById(R.id.spinner1);
		myList = new ArrayList<String>(10);
				
				 
						ItemsAdapter ia = new ItemsAdapter(
						ShowChallenge.this,
						android.R.layout.simple_spinner_item, 
						items);
						spin.setAdapter(ia);		
						spin.setOnItemSelectedListener(this);
						ItemsAdapter choseDay= new ItemsAdapter(this, android.R.layout.simple_spinner_item, day);
						days.setAdapter(choseDay);
						days.setOnItemSelectedListener(this);
										
	}
	 @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	    }
	 
	 @Override
	    protected void onDestroy() {
	    super.onPause();
	    finish();
	    

	}
	 
	 
	public void onItemSelected(AdapterView<?> adapterView, View viewClicked, int pos,
			long rowID) {
		switch(adapterView.getId()) {
		case R.id.spinner:
		
					SearchItems findList = new SearchItems();
		int selectedPosition = adapterView.getSelectedItemPosition();
		
		//retrieve random item list from SearchItems class
		if(selectedPosition == 0){
						score=5;
			 myList = findList.LevelOneTreasures();
					}if(selectedPosition == 1){
						score=10;
			 myList = SearchItems.LevelTwoTreasures();
					}if(selectedPosition == 2){
						score=15;
			 myList = SearchItems.LevelThreeTreasures();
					}if(selectedPosition == 3){
						score = 20;
			 myList = SearchItems.LevelFourTreasures();
					}
					 					
										values = new ContentValues();
									
										listId++;
				    		        allItems2 = Uri.parse(
				    		       "content://com.tabs.ContentProviders/URI4");
				    		   	
				    						values.clear();				    						
	//**********************************save the list generated in database : **************************************************************			    						
				    					ListIterator<String> itr = myList.listIterator();				    									
				    					while(itr.hasNext()) {
				    					String element = itr.next();
				    					values.put("item", element);
				    					values.put("userId", id);
				    					values.put("score", score);
				    					values.put("listId", listId);
				    					values.put("name", name);
				    					/*Toast.makeText(this,
				    					c.getString(c.getColumnIndex("taskstodo")),
				    					Toast.LENGTH_LONG).show();*/
		    					
		    					//c.moveToNext();
				    		}break;
							case R.id.spinner1:
								int selectedPositionDay = adapterView.getSelectedItemPosition();
								if (selectedPositionDay == 0){
									  timeInDays =86400000; 
									  values.put("timeToFinish", Long.toString(timeInDays));
									  uri2 = getContentResolver().insert(
			    								allItems2, values);	
									  new CountDownTimer(timeInDays, 1000) {

										     public void onTick(long millisUntilFinished) {
										         //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
										     }

										     public void onFinish() {
										         //mTextField.setText("done!");
										     }
										  }.start();
								}
								if (selectedPositionDay == 1){
									  timeInDays =172800000; 
									  values.put("timeToFinish", Long.toString(timeInDays));
									  uri2 = getContentResolver().insert(
			    								allItems2, values);	
									  
									  new CountDownTimer(timeInDays, 1000) {									
										     public void onTick(long millisUntilFinished) {
										     }

										     public void onFinish() {
										     }
										  }.start();
								}
								if (selectedPositionDay == 2){
									timeInDays = 432000000;
									values.put("timeToFinish", Long.toString(timeInDays));
									  uri2 = getContentResolver().insert(
			    								allItems2, values);	
									  new CountDownTimer(timeInDays, 1000) {

									     public void onTick(long millisUntilFinished) {
									     }

									     public void onFinish() {
									     }
									  }.start();
								}
								if (selectedPositionDay == 0){
									timeInDays=864000000; 
									values.put("timeToFinish", Long.toString(timeInDays));
									  uri2 = getContentResolver().insert(
			    					allItems2, values);	
									  
									  new CountDownTimer(timeInDays, 1000) {
									     public void onTick(long millisUntilFinished) {
									     }
									     public void onFinish() {
									     }
									  }.start();
								}
			break;
				    					
	}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class ItemsAdapter extends BaseAdapter {
		
		 String[] items;

		  public ItemsAdapter(Context context, int textViewResourceId,
		    String[] items) {
		   this.items = items;
		  }

		public int getCount() {
			   return items.length;

		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			 TextView mDescription;
			   View view = convertView;
			   if (view == null) {
			    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    view = vi.inflate(R.layout.list, null);
			   }
			   mDescription = (TextView) view.findViewById(R.id.desc);
			   mDescription.setText(items[position]);
			   return view;
		}
		
	}
	

		public void getListOfItems(View viewList) {
			setContentView(R.layout.list_layout);
			
			lv =(ListView)findViewById(R.id.taskList);

			
			ArrayAdapter<String> innerA= new ArrayAdapter<String>(ShowChallenge.this,
				    android.R.layout.simple_list_item_1,ShowChallenge.myList );			
		 lv.setAdapter(innerA);					
	//******************once the user wants to take a picture of particular item on the list :***********************************
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

			    Object o = lv.getItemAtPosition(position);
			     it= o.toString();
			     in = new Intent (ShowChallenge.this, Camera.class);
			    in.putExtra("itemPhotographed", it);
			    in.putExtra("_id", id);
			    in.putExtra("point", score);
			    in.putExtra("name", name);
			    startActivity(in);
			
		}
	});
		}
		public void onBackPressed()
	    {
	        Intent setIntent = new Intent(ShowChallenge.this,CustomMenuBar.class);
	        setIntent.putExtra("name", name);
	        setIntent.putExtra("_id", id);
	        startActivity(setIntent); 
	        return;
	    } 
	}
		
	


