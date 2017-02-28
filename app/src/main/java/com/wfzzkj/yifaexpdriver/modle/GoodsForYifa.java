package com.wfzzkj.yifaexpdriver.modle;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.integer;
import android.content.Context;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author whFeng
 *货物表
 */
public class GoodsForYifa extends BmobObject{
	
	private  Integer iExpWay ;//发货方式
	private  String sExpWayStr = "";//发货方式
	private  String sExpHuoType = "";//货物类型
	private  String sExpCheType = "";//所需车型
	private  double  dExpHuoWeight ;//货物重量
	private  String  sExpHuoInfoType = "";//重货 泡货
	private  String  sExpOrderTime = "";//预约时间
	private  String   sExpUserTime="";//客户发货时间
	private  String   sExpDriverTime="";//司机接货时间
	private  String   sExpArriverTime="";//签收时间
	private BmobGeoPoint gpsAddStart;//地理位置
	private BmobGeoPoint gpsAddFinish;//地理位置
	private  String gpsAddStartStr;//地理位置
	private  String gpsAddFinishStr;//地理位置
	private  Integer iGoodState ;//货物当前状态 0废弃 1接单中 2配送中 3已抵达  
	private UserForYifa user;
	private double price;//j价格
	private  int  ExpHuoPay = 0;//0 为默认  未选择 1货到付款 2 在线支付
	
	public int getExpHuoPay() {
		return ExpHuoPay;
	}
	public void setExpHuoPay(int expHuoPay) {
		ExpHuoPay = expHuoPay;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getiGoodState() {
		return iGoodState;
	}
	public void setiGoodState(Integer iGoodState) {
		this.iGoodState = iGoodState;
	}
	private UserForYifa userDriver;
	public UserForYifa getUserDriver() {
		return userDriver;
	}
	public void setUserDriver(UserForYifa userDriver) {
		this.userDriver = userDriver;
	}
	public String getGpsAddStartStr() {
		return gpsAddStartStr;
	}
	public void setGpsAddStartStr(String gpsAddStartStr) {
		this.gpsAddStartStr = gpsAddStartStr;
	}
	public String getGpsAddFinishStr() {
		return gpsAddFinishStr;
	}
	public void setGpsAddFinishStr(String gpsAddFinishStr) {
		this.gpsAddFinishStr = gpsAddFinishStr;
	}
	public BmobGeoPoint getGpsAddStart() {
		return gpsAddStart;
	}
	public void setGpsAddStart(BmobGeoPoint gpsAddStart) {
		this.gpsAddStart = gpsAddStart;
	}
	public BmobGeoPoint getGpsAddFinish() {
		return gpsAddFinish;
	}
	public void setGpsAddFinish(BmobGeoPoint gpsAddFinish) {
		this.gpsAddFinish = gpsAddFinish;
	}
	public String getsExpUserTime() {
		return sExpUserTime;
	}
	public void setsExpUserTime() {
		 SimpleDateFormat mFormatter = new SimpleDateFormat(
				"yyyy年MMMMdd日hh:mm aa");
		this.sExpUserTime =  mFormatter.format(new Date());
	}
	public String getsExpDriverTime() {
		return sExpDriverTime;
	}
	public void setsExpDriverTime(String sExpDriverTime) {
		this.sExpDriverTime = sExpDriverTime;
	}
	public String getsExpArriverTime() {
		return sExpArriverTime;
	}
	public void setsExpArriverTime(String sExpArriverTime) {
		this.sExpArriverTime = sExpArriverTime;
	}
	
	
	public UserForYifa getUser() {
		return user;
	}
	public void setUser(UserForYifa user) {
		this.user = user;
	}
	public Integer getiExpWay() {
		return iExpWay;
	}
	public void setiExpWay(Integer iExpWay) {
		this.iExpWay = iExpWay;
	}
	public String getsExpWayStr() {
		return sExpWayStr;
	}
	public void setsExpWayStr(String sExpWayStr) {
		this.sExpWayStr = sExpWayStr;
	}
	public String getsExpHuoType() {
		return sExpHuoType;
	}
	public void setsExpHuoType(String sExpHuoType) {
		this.sExpHuoType = sExpHuoType;
	}
	public String getsExpCheType() {
		return sExpCheType;
	}
	public void setsExpCheType(String sExpCheType) {
		this.sExpCheType = sExpCheType;
	}
	public double getdExpHuoWeight() {
		return dExpHuoWeight;
	}
	public void setdExpHuoWeight(double dExpHuoWeight) {
		this.dExpHuoWeight = dExpHuoWeight;
	}
	public String getsExpHuoInfoType() {
		return sExpHuoInfoType;
	}
	public void setsExpHuoInfoType(String sExpHuoInfoType) {
		this.sExpHuoInfoType = sExpHuoInfoType;
	}
	public String getsExpOrderTime() {
		return sExpOrderTime;
	}
	public void setsExpOrderTime(String sExpOrderTime) {
		this.sExpOrderTime = sExpOrderTime;
	}
	public String getString(){
		String str="收到订单,";
		
		if("".equals(sExpOrderTime))
			str+="实时";
		else
			str+="预约,我要,"+sExpOrderTime+"发货";
		str+=   (",方式"+sExpWayStr);
		str+=(",从"+gpsAddStartStr);
		str+=(",到"+gpsAddFinishStr);
		if(iExpWay!=50){//快递
			str+=(",货物类型"+sExpHuoType);
			str+=(",所需车型"+sExpCheType);
			//str+=(",货重"+dExpHuoWeight+"吨");
			//str+=(",所需车型"+sExpCheType);
			str+=(","+sExpHuoInfoType);//重货 泡货
		}
		str+=",请师傅火速赶往,易发将在稍后为您开启导航";
		return str;
	}
	public String getInfo(){
		//getString();
		return getString().replace(",请师傅火速赶往,易发将在稍后为您开启导航", "");
		
	}
	
//	public boolean updateGoodState(GoodsForYifa good,int state,Context context,String currentId){
//		good.setiGoodState(state);
//		good.update(context, currentId, new UpdateListener() {
//		    @Override
//		    public void onSuccess() {
//		        // TODO Auto-generated method stub
//		        //toast("更新成功：");
//		    	return true;
//		    }
//		    @Override
//		    public void onFailure(int code, String msg) {
//		        // TODO Auto-generated method stub
//		       // toast("更新失败："+msg);
//		    	return false;
//		    }
//		});
//		return false;
//		
//	} 
}
