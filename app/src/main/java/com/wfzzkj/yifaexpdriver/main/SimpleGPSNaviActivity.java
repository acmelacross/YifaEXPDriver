package com.wfzzkj.yifaexpdriver.main;

import java.util.ArrayList;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviStaticInfo;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;

import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.wfzzkj.yifaexpdriver.Config;
import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.utils.Utils;
import com.wfzzkj.yifaexpdriver.utils.xunfei.TTSController;


import android.app.Activity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 
 * 实时导航界面  跳转
 * */
public class SimpleGPSNaviActivity extends Activity implements OnClickListener,
		AMapNaviListener,AMapNaviViewListener {
	//起点终点坐标显示
	private TextView mStartPointTextView;
	private TextView mEndPointTextView;
	//实时导航按钮
	private Button mStartNaviButton,gps_cancel_navi_button,gps_call_user;

	//起点终点
	private NaviLatLng mNaviStart = new NaviLatLng(Config.getInstance().good.getGpsAddStart().getLatitude(),Config.getInstance().good.getGpsAddStart().getLongitude());
	private NaviLatLng mNaviEnd = new NaviLatLng(Config.getInstance().good.getGpsAddFinish().getLatitude(), Config.getInstance().good.getGpsAddFinish().getLongitude());
    //起点终点列表
	private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
	
	private ProgressDialog mRouteCalculatorProgressDialog;// 路径规划过程显示状态

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplegps_navi);


		initView();
		 
	}

	private void initView() {
		mStartPoints.add(mNaviStart);
		mEndPoints.add(mNaviEnd);
		mStartPointTextView = (TextView) findViewById(R.id.gps_start_position_textview);
		mEndPointTextView = (TextView) findViewById(R.id.gps_end_position_textview);
		
		mStartNaviButton = (Button) findViewById(R.id.gps_start_navi_button);
		mStartNaviButton.setOnClickListener(this);

		mStartPointTextView.setText(Config.getInstance().good.getGpsAddStartStr()+"--"+mNaviStart.getLatitude() + ","
				+ mNaviStart.getLongitude());
		mEndPointTextView.setText(Config.getInstance().good.getGpsAddFinishStr()+"--"+mNaviEnd.getLatitude() + ","
				+ mNaviEnd.getLongitude());
		
		mRouteCalculatorProgressDialog=new ProgressDialog(this);
		mRouteCalculatorProgressDialog.setCancelable(true);

		AMapNavi.getInstance(this).setAMapNaviListener(this);
		
		//开启导航
		//updateInfo();
		gps_cancel_navi_button = (Button)findViewById(R.id.gps_cancel_navi_button);
		gps_cancel_navi_button.setOnClickListener(this);
		gps_call_user = (Button)findViewById(R.id.gps_call_user);
		gps_call_user.setOnClickListener(this);
		
	}
	

//--------------------------------点击事件------------------
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gps_start_navi_button:
			AMapNavi.getInstance(this).calculateDriveRoute(mStartPoints,
					mEndPoints, null, NaviType.EMULATOR);
			mRouteCalculatorProgressDialog.show();
			break;
		case R.id.gps_cancel_navi_button:
			finish();
			break;
		case R.id.gps_call_user:	Builder builder = new Builder(SimpleGPSNaviActivity.this);
		builder.setMessage("确认拨打客户吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
								+ Config.getInstance().good.getUser().getUsername())));
					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.dismiss();
					}
				});
		builder.create().show();
			;
			break;	
		}
	}
//---------------------导航回调--------------------
	@Override
	public void onArriveDestination() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onArrivedWayPoint(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCalculateRouteFailure(int arg0) {
		mRouteCalculatorProgressDialog.dismiss();

	}

	@Override
	public void onCalculateRouteSuccess() {
		mRouteCalculatorProgressDialog.dismiss();
//		Intent intent = new Intent(SimpleGPSNaviActivity.this,
//				SimpleNaviActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//		Bundle bundle=new Bundle();
//		bundle.putInt(Utils.ACTIVITYINDEX, Utils.SIMPLEGPSNAVI);
//		bundle.putBoolean(Utils.ISEMULATOR, false);
//		intent.putExtras(bundle);
//		startActivity(intent);
//        finish();
		
	}

	@Override
	public void onEndEmulatorNavi() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetNavigationText(int arg0, String arg1) {
		// TODO Auto-generated method stub
		TTSController.getInstance(this).startSpeaking(arg1);
	}

	@Override
	public void onGpsOpenStatus(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInitNaviFailure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInitNaviSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChange(AMapNaviLocation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviInfoUpdated(AMapNaviInfo arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReCalculateRouteForTrafficJam() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReCalculateRouteForYaw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartNavi(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTrafficStatusUpdate() {
		// TODO Auto-generated method stub

	}
//---------------------导航View事件回调-----------------------------
	@Override
	public void onNaviCancel() {
	 
		
	}

	@Override
	public boolean onNaviBackClick() {
		return false;
	}

	@Override
	public void onNaviMapMode(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviSetting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviTurnClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextRoadClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScanViewButtonClick() {
		// TODO Auto-generated method stub
		
	}
	//返回键处理事件
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			Intent intent = new Intent(SimpleGPSNaviActivity.this,
//					MainStartActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//			startActivity(intent);
//		 
//			finish();
//		}
//		return super.onKeyDown(keyCode, event);
//		
//	}

	@Override
	public void onDestroy() {
		super.onDestroy(); 
	  // 删除导航监听
		 
		AMapNavi.getInstance(this).removeAMapNaviListener(this);
		AMapNavi.getInstance(this).destroy();// 销毁导航
//		TTSController.getInstance(this).stopSpeaking();
//		TTSController.getInstance(this).destroy();
	}

	@Override
	public void onLockMap(boolean arg0) {
		  
		// TODO Auto-generated method stub  
		
	}

	@Override
	public void onNaviViewLoaded() {

	}

	@Override
	public void onNaviInfoUpdate(NaviInfo arg0) {
		  
		// TODO Auto-generated method stub  
		
	}

	@Override
	public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

	}

	@Override
	public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

	}

	@Override
	public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

	}

	@Override
	public void showCross(AMapNaviCross aMapNaviCross) {

	}

	@Override
	public void hideCross() {

	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

	}

	@Override
	public void notifyParallelRoad(int i) {

	}

	@Override
	public void onCalculateMultipleRoutesSuccess(int[] ints) {

	}

	@Override
	public void hideLaneInfo() {

	}

	@Override
	public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

	}

	@Override
	public void onArriveDestination(AMapNaviStaticInfo aMapNaviStaticInfo) {

	}

	@Override
	public void onArriveDestination(NaviStaticInfo naviStaticInfo) {

	}
}
