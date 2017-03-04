package com.wfzzkj.yifaexpdriver.utils;

import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
	/**
	 * 判断edittext是否null
	 */
	public static String formetEditText(EditText editText) {
		if (editText != null && editText.getText() != null
				&& !(editText.getText().toString().trim().equals(""))) {
			return editText.getText().toString().trim();
		} else {
			return "";
		}
	}
	public static boolean checkEditText(EditText editText) {
		if (editText==null) 
			return false;
		if (editText != null && editText.getText() != null
				&& !(editText.getText().toString().trim().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	public static String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		return  df.format(new Date());
	}
	
	
}
