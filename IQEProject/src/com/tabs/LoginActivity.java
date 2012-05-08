
package com.tabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * @author Katarzyna Koryzma
 */
public class LoginActivity extends Activity implements OnClickListener {
	Uri uri;	
	int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login2);
		final EditText txtUserName = (EditText)findViewById(R.id.txtUsername);
		final EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
		Button btnLogin = (Button)findViewById(R.id.btnLogin);
	      uri = Uri.parse("content://com.tabs.ContentProviders/URI3"); 

		btnLogin.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				String username = txtUserName.getText().toString();
				String password = txtPassword.getText().toString();
				try{
					if(username.length() > 0 && password.length() >0)
					{
						//dbUser.AddUser(username, password);
						
						if(Login(username, password))
						{
							Toast.makeText(LoginActivity.this,"Successfully Logged In", Toast.LENGTH_LONG).show();
							
							
							Intent mainMenu = new Intent(LoginActivity.this, CustomMenuBar.class);
							mainMenu.putExtra("_id", id);
							mainMenu.putExtra("name", username);
							
							startActivity(mainMenu);
							
						}else{
							Toast.makeText(LoginActivity.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
						}
					}
					
				}catch(Exception e)
				{
					//Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
					Toast.makeText(LoginActivity.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();

					System.out.println("ERRRRRROR");
					System.out.println(e.getMessage());
				}
			}
			
		});
		
	}
	public boolean Login(String username, String password) 
    {
		Cursor mCursor=null;
		 mCursor = managedQuery(uri, new String[] {"_id", "password", "name"}, 
				"password=? AND name=?", new String[]{password,username}, null);
		mCursor.moveToFirst();
		//this is the ID of the user
		id= Integer.parseInt(mCursor.getString(0));
     System.out.println("  "+ id);
    

		if (mCursor != null ) {           
            if(mCursor.getCount() > 0)
            {
            	return true;
            } 
        	} return false;   
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}@Override
 protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        finish();
    }
}

