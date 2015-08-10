package com.example.mytools.handwrite;

import com.example.mytools.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * 手写签名图片展示界面
 * @author Administrator
 *
 */
public class HandWriteShowActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hand_write_show);
		
		String fileStr = getIntent().getStringExtra("imageCache");
		if(!TextUtils.isEmpty(fileStr)) {
			Bitmap imageBitmap = BitmapFactory.decodeFile(fileStr);
			ImageView showImgv = (ImageView) findViewById(R.id.handwriteshowImgv);
			showImgv.setImageBitmap(imageBitmap);
		}
		
		
	}

}
