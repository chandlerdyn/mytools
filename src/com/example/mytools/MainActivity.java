package com.example.mytools;

import com.example.mytools.getimsi.GetImsi;
import com.example.mytools.getimsi.PhoneImsi;
import com.example.mytools.handwrite.WriteActivity;
import com.example.mytools.swipelistview.SwipeActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


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
			
		case R.id.swipelistBtn:
			
			Intent i2 = new Intent(MainActivity.this, SwipeActivity.class);
			startActivity(i2);
			
			break;
			
		case R.id.imsiBtn:
			PhoneImsi info = GetImsi.getPhoneImsi(this);
			if(info == null) {
				Toast.makeText(getApplicationContext(), "请确认sim卡是否插入或者sim卡暂时不可用！", Toast.LENGTH_LONG).show();
				
			}else {
			
				Toast.makeText(getApplicationContext(), "sim1: "+info.getImsi_1()+"\n sim2: "+info.getImsi_2(), Toast.LENGTH_LONG).show();
			
			}
			
			
			break;

		default:
			break;
		}
    }
    
    
}
