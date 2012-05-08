package com.tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * @author Katarzyna Koryzma
 */
public class Start extends Activity implements OnClickListener {
	
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);	
	setContentView(R.layout.start);
	       Button log =(Button) findViewById(R.id.button2);
	       Button reg = (Button) findViewById(R.id.button1);
	       Button ex = (Button) findViewById(R.id.button3);
	       log.setOnClickListener(Start.this);
	       ex.setOnClickListener(Start.this);
	       reg.setOnClickListener(Start.this);        	
	           
	}
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button1:
			
		Intent r= new Intent(Start.this, Register.class);
	    startActivity(r);
		break;
		
		case R.id.button2:
		Intent l= new Intent(Start.this, LoginActivity.class);
	    startActivity(l);
	    break;
		case R.id.button3:
			System.runFinalizersOnExit(true);
		    System.exit(0);
		    break;
		}
	}
	@Override
	 protected void onStop() {
	        super.onStop();
	        // The activity is no longer visible (it is now "stopped")
	        finish();
	    }
}
