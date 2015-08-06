package com.example.mytools;

import com.example.mytools.handwrite.WriteActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void onClick(View v) {
    	
    	switch (v.getId()) {
		case R.id.handwriteBtn:
			Intent i1 = new Intent(MainActivity.this, WriteActivity.class);
			startActivity(i1);
			
			break;

		default:
			break;
		}
    }
    
    
}
