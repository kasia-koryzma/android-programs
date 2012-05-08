package com.tabs;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
/**
 * @author Katarzyna Koryzma
 */
public class CustomMenuBar extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the ID for the user who has logged in or registered
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("_id");
        String n = extras.getString("name");
       
        setContentView(R.layout.tabs_layout);
        Resources res = getResources();
        TabHost host = (TabHost)findViewById(android.R.id.tabhost);
        
        TabSpec spec = host.newTabSpec("tab1");
        Intent in1=new Intent().putExtra("_id", id).putExtra("name", n).setClass(this, ShowProfile.class);
        spec.setContent(in1);
        spec.setIndicator("Profile", res.getDrawable(R.drawable.profile));
        host.addTab(spec);
        
        spec = host.newTabSpec("tab2");
        Intent in2=new Intent().putExtra("_id", id).putExtra("name", n).setClass(this, ShowChallenge.class);
        spec.setContent(in2);
        spec.setIndicator("Challenges", res.getDrawable(R.drawable.task));
        host.addTab(spec);
        
        spec = host.newTabSpec("tab3");
        Intent in3=new Intent().putExtra("_id", id).putExtra("name", n).setClass(this, CPtest.class);
        spec.setContent(in3);
        spec.setIndicator("Leaders", res.getDrawable(R.drawable.winner));
        host.addTab(spec);
    }
    @Override
    protected void onDestroy() {
    super.onDestroy();
    finish();
    

}
    @Override
    protected void onStop() {
    super.onStop();
    finish();
    

}
}