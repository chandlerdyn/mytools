package com.example.mytools.handwrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.example.mytools.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class WriteActivity extends Activity {
	
	PaintView mView;
	
	private String imageCache;
	
	
//	private PaintView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write);
		
//		mView = (PaintView) findViewById(R.id.paintView);
		
		mView = new PaintView(this);
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.tablet_view);
		frameLayout.addView(mView);
		mView.requestFocus();
		Button btnClear = (Button) findViewById(R.id.tablet_clear);
		btnClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 mView.clear();
			}
		});

		Button btnOk = (Button) findViewById(R.id.tablet_ok);
		btnOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					
					Bitmap  bitmap = mView.getCachebBitmap();
					
					
					saveMyBitmap(new Date().getTime()+"", bitmap);
					
//					dialogListener.refreshActivity(mView.getCachebBitmap());
//					WritePadDialog.this.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Button btnCancel = (Button)findViewById(R.id.tablet_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		//		cancel();
				
				WriteActivity.this.finish();
			}
		});
		
	
		Button btnShow = (Button) findViewById(R.id.tablet_show);
		btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WriteActivity.this, HandWriteShowActivity.class);
				intent.putExtra("imageCache", imageCache);
				startActivity(intent);
				
			}
		});
	}
	
	/**
	 * 将bitmap保存到sd卡
	 * @param bitName
	 * @param mBitmap
	 */
	public void saveMyBitmap(String bitName,Bitmap mBitmap){
		
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			 Toast.makeText(this, "请安装sd卡", Toast.LENGTH_SHORT).show();
			 return;
		}
		
		
		String dir = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"MyTools";
	
		File dirFile = new File(dir);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		imageCache = dir+File.separator + bitName + ".png";
		File f = new File(imageCache);
		  try {
		   f.createNewFile();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
			  
			  Toast.makeText(this, "图片缓存失败。"+e.toString(), Toast.LENGTH_SHORT).show();
		  }
		  FileOutputStream fOut = null;
		  try {
		   fOut = new FileOutputStream(f);
		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		  }
		  mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		  try {
		   fOut.flush();
		   
		   mView.clear();
		   Toast.makeText(WriteActivity.this, "图片缓存成功", Toast.LENGTH_SHORT).show();
		   
		   
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  try {
		   fOut.close();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }

}
