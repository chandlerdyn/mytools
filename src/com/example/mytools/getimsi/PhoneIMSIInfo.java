package com.example.mytools.getimsi;

public class PhoneIMSIInfo {

	//MtkDoubleSim
	private boolean mtkDoubleSim = false;
	
	private boolean gaotongDoubleSim = false;
	//0主卡，1副卡
	private int simId_1 = 0;
	private int simId_2 = 1;
	
	private String imsi_1;
	private String imsi_2;
	
	
	private String imei_1;
	private String imei_2;
	
	private int phoneType_1;
	private int phoneType_2;
	
	
	
	
	
	public boolean isGaotongDoubleSim() {
		return gaotongDoubleSim;
	}
	public void setGaotongDoubleSim(boolean gaotongDoubleSim) {
		this.gaotongDoubleSim = gaotongDoubleSim;
	}
	public int getPhoneType_1() {
		return phoneType_1;
	}
	public void setPhoneType_1(int phoneType_1) {
		this.phoneType_1 = phoneType_1;
	}
	public int getPhoneType_2() {
		return phoneType_2;
	}
	public void setPhoneType_2(int phoneType_2) {
		this.phoneType_2 = phoneType_2;
	}
	public String getImei_1() {
		return imei_1;
	}
	public void setImei_1(String imei_1) {
		this.imei_1 = imei_1;
	}
	public String getImei_2() {
		return imei_2;
	}
	public void setImei_2(String imei_2) {
		this.imei_2 = imei_2;
	}
	public String getImsi_1() {
		return imsi_1;
	}
	public void setImsi_1(String imsi_1) {
		this.imsi_1 = imsi_1;
	}
	public String getImsi_2() {
		return imsi_2;
	}
	public void setImsi_2(String imsi_2) {
		this.imsi_2 = imsi_2;
	}
	public boolean isMtkDoubleSim() {
		return mtkDoubleSim;
	}
	public void setMtkDoubleSim(boolean mtkDoubleSim) {
		this.mtkDoubleSim = mtkDoubleSim;
	}
	public int getSimId_1() {
		return simId_1;
	}
	public void setSimId_1(int simId_1) {
		this.simId_1 = simId_1;
	}
	public int getSimId_2() {
		return simId_2;
	}
	public void setSimId_2(int simId_2) {
		this.simId_2 = simId_2;
	}
	
	
	
	
	
}
