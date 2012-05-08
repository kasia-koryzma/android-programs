package com.tabs;

import java.util.regex.PatternSyntaxException;
import java.util.regex.Pattern;

import com.tabs.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class strings extends Activity{
	
	@Override
	public void onCreate(Bundle r){
        super.onCreate(r);
	setContentView(R.layout.readcp);
	
	TextView tv= (TextView) findViewById(R.id.text);
	
	String t = "{\"data\":{\"results\":{\"labels\": \"myresult\"}, \"error\": 0}}";
	int ind =t.indexOf("labels\": \"");
	int last =t.indexOf("error");
	last = last-5;
	ind = ind +10;
	
	//substring returns the desired result, drops anyting surrounding lablel word
	String finalExtractedString= t.substring(ind, last);
	
	try{
	//String repl = t.replaceAll("\\{\"data\":\\{\"results\":\\{\"labels\": \"", " ");
	//String chars = t.replaceAll("\"\\}, \"error\": 0\\}\\}", "");
	tv.setText(finalExtractedString);
	
	}catch(PatternSyntaxException e){ tv.setText(e.getDescription());  }
	//t.replaceAll("\"}, \"error\": 0}}", "");
	
	
	
	}
}
