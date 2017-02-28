package com.wfzzkj.yifaexpdriver.modle;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class DriverForYifa  extends BmobUser{
	 private BmobGeoPoint gpsAdd;//当前位置
	 private String company;//出租车公司
	 private String carNum;//车牌号
	 private String carInfo;//基本信息
	 private String phone;//基本信息
	 private String nickeyName;//昵称 也就是司机注册的名子
	 private String ImageVal;//审核图片
	 private Integer type=2;//客户类型 1用户  2司机
	 public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	private Integer state;//用户状态 0 正常 1封停  2审核中
	 public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getImageVal() {
		return ImageVal;
	}
	public void setImageVal(String imageVal) {
		ImageVal = imageVal;
	}
	public String getNickeyName() {
		return nickeyName;
	}
	public void setNickeyName(String nickeyName) {
		this.nickeyName = nickeyName;
	}
	public BmobGeoPoint getGpsAdd() {
		return gpsAdd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setGpsAdd(BmobGeoPoint gpsAdd) {
		this.gpsAdd = gpsAdd;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getCarInfo() {
		return carInfo;
	}
	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}
	public String getCarJia() {
		return carJia;
	}
	public void setCarJia(String carJia) {
		this.carJia = carJia;
	}
	private String carJia;//车架号
}
