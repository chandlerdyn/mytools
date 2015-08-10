package com.example.mytools.swipelistview;

import java.util.ArrayList;
import java.util.List;

import com.example.mytools.R;


import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationPool {
	
	
	private static AnimationPool instance;
	
	

	private Context context;
	
	private List<Animation> lst = new ArrayList<Animation>();



	public AnimationPool(Context context) {
		super();
		this.context = context;
		
		for(int i = 0;i<10;i++) {
			Animation anim = AnimationUtils.loadAnimation(context, R.anim.item_zoom_in);
			lst.add(anim);
		}
		
	}
	
	public synchronized Animation getAnim() {
		Animation anim = null;
		if(lst.size()>0) {
			anim = lst.get(0);
			lst.remove(0);
		}else {
			anim = AnimationUtils.loadAnimation(context, R.anim.item_zoom_in);
		}
		return anim;
		
	}
	
	public synchronized void releaseAnim(Animation a) {
		
		lst.add(a);
		
	}
	
	public static synchronized AnimationPool getInstance(Context c) {
		if(instance == null) {
			instance = new AnimationPool(c);
		}
		return instance;
		
	}
	
	
	
}
