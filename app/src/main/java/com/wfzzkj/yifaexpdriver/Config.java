package com.wfzzkj.yifaexpdriver;


import android.app.Application;

import com.wfzzkj.yifaexpdriver.modle.GoodsForYifa;

public class Config {
	public  String phone="13105364454";
	public  GoodsForYifa good = null;
	public Application appContext;
	//public static void validateLogin(){
	private Config() {
	}

	private static volatile Config instance = null;

	public static Config getInstance() {
		if (instance == null) {
			synchronized (Config.class) {
				if (instance == null) {
					instance = new Config();
				}
			}
		}
		return instance;
	}

}
