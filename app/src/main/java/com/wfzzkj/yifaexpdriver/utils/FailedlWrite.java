package com.wfzzkj.yifaexpdriver.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class FailedlWrite {

	private static String errorPathName="yifadriver";
	/**
	 * 
	 * @param ex
	 * 将崩溃写入文件系统
	 */
	public static void writeCrashInfoToFile(String failedStr) {
		//String result = failedStr.toString();
		//这里把刚才异常堆栈信息写入SD卡的Log日志里面
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
		{
			String sdcardPath = Environment.getExternalStorageDirectory().getPath();
			String filePath = sdcardPath + errorPathName+"/failed/";
			 writeLog(failedStr, filePath);
		}
	}

	/**
	 * 
	 * @param log
	 * @param name
	 * @return 返回写入的文件路径
	 * 写入Log信息的方法，写入到SD卡里面
	 */
	private static String writeLog(String log, String name) 
	{
		CharSequence timestamp = new Date().toString().replace(" ", "");
		 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String time = formatter.format(new Date());
		timestamp  = "客户端"+time+"failed";
		String filename = name + timestamp + ".txt";
	
		File file = new File(filename);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try 
		{
			Log.d("TAG", "写入到SD卡里面");
			//			FileOutputStream stream = new FileOutputStream(new File(filename));
			//			OutputStreamWriter output = new OutputStreamWriter(stream);
			file.createNewFile();
			FileWriter fw=new FileWriter(file,true);   
			BufferedWriter bw = new BufferedWriter(fw);
			//写入相关Log到文件
			//errorLog = log;
			bw.write(log);
			bw.newLine();
			bw.close();
			fw.close();
			
			return filename;
		} 
		catch (IOException e) 
		{
			//Log.e(TAG, "an error occured while writing file...", e);
			e.printStackTrace();
			return null;
		}
	}
}
