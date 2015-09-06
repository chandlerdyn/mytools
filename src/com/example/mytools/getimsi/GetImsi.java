package com.example.mytools.getimsi;

import java.lang.reflect.Method;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class GetImsi {

	/**
	 * 由于国内的运营商问题，双卡手机获取IMSI号问题要根据厂商API 来实现。
	 * 我们要判断手机的平台。
	 * 判断手机是否MTK平台，如果是，获取2个imsi值
	 * @param mContext
	 * @return
	 */
	public static PhoneIMSIInfo initMtkDoubleSim(Context mContext) {
		PhoneIMSIInfo mtkDoubleInfo = new PhoneIMSIInfo();
		try {
			TelephonyManager tm = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			Class<?> c = Class.forName("com.android.internal.telephony.Phone");
			
			Method m = TelephonyManager.class.getDeclaredMethod(
					"getSubscriberIdGemini", int.class);
			mtkDoubleInfo.setImsi_1((String) m.invoke(tm,
					mtkDoubleInfo.getSimId_1()));
			mtkDoubleInfo.setImsi_2((String) m.invoke(tm,
					mtkDoubleInfo.getSimId_2()));

			Method m1 = TelephonyManager.class.getDeclaredMethod(
					"getDeviceIdGemini", int.class);
			mtkDoubleInfo.setImei_1((String) m1.invoke(tm,
					mtkDoubleInfo.getSimId_1()));
			mtkDoubleInfo.setImei_2((String) m1.invoke(tm,
					mtkDoubleInfo.getSimId_2()));

			Method mx = TelephonyManager.class.getDeclaredMethod(
					"getPhoneTypeGemini", int.class);
			mtkDoubleInfo.setPhoneType_1((Integer) mx.invoke(tm,
					mtkDoubleInfo.getSimId_1()));
			mtkDoubleInfo.setPhoneType_2((Integer) mx.invoke(tm,
					mtkDoubleInfo.getSimId_2()));

			
		} catch (Exception e) {
			
			Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
			
			mtkDoubleInfo.setMtkDoubleSim(false);
			return mtkDoubleInfo;
		}
		mtkDoubleInfo.setMtkDoubleSim(true);
		return mtkDoubleInfo;
	}

	/**
	 * 判断手机是否高通平台,如果是，获取 2个imsi值
	 * @param mContext
	 * @return
	 */
	public static PhoneIMSIInfo initQualcommDoubleSim(Context mContext) {
		PhoneIMSIInfo gaotongDoubleInfo = new PhoneIMSIInfo();
		gaotongDoubleInfo.setSimId_1(0);
		gaotongDoubleInfo.setSimId_2(1);
		try {
			Class<?> cx = Class
					.forName("android.telephony.MSimTelephonyManager");
			Object obj = mContext.getSystemService("phone_msim");

			Method md = cx.getMethod("getDeviceId", int.class);
			Method ms = cx.getMethod("getSubscriberId", int.class);

			gaotongDoubleInfo.setImei_1((String) md.invoke(obj,
					gaotongDoubleInfo.getSimId_1()));
			gaotongDoubleInfo.setImei_2((String) md.invoke(obj,
					gaotongDoubleInfo.getSimId_2()));
			gaotongDoubleInfo.setImsi_1((String) ms.invoke(obj,
					gaotongDoubleInfo.getSimId_1()));
			gaotongDoubleInfo.setImsi_2((String) ms.invoke(obj,
					gaotongDoubleInfo.getSimId_2()));
		} catch (Exception e) {
			e.printStackTrace();
			gaotongDoubleInfo.setGaotongDoubleSim(false);
			return gaotongDoubleInfo;
		}
		
		gaotongDoubleInfo.setGaotongDoubleSim(true);
		return gaotongDoubleInfo;

	}
	
	/**
	 * 获取手机imsi号
	 * @param context
	 * @return
	 */
	public static PhoneImsi getPhoneImsi(Context context) {
		
		PhoneImsi info = null;
		//是否插sim卡
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if( TelephonyManager.SIM_STATE_READY != manager 
                .getSimState()) {
			return null;
		}else {
			info = new PhoneImsi();
			PhoneIMSIInfo gaoTong =  initQualcommDoubleSim(context);
			PhoneIMSIInfo mtk =  initMtkDoubleSim(context);
			
			if(gaoTong.isGaotongDoubleSim()) {
				// 高通芯片双卡  
				info.setImsi_1(gaoTong.getImsi_1());
				info.setImsi_2(gaoTong.getImsi_2());
				
			}else if(mtk.isMtkDoubleSim()) {
				// MTK芯片双卡
				info.setImsi_1(mtk.getImsi_1());
				info.setImsi_2(mtk.getImsi_2());
				
			}else {
				 //普通单卡手机  
				
				if(manager!= null) {
					String st = manager.getSubscriberId();
					info.setImsi_1(st+" 普通单卡");
				}else {
					return null;
				}
				
			}
			
			
			
			
		}
		
		
		
		return info;
	}
}
