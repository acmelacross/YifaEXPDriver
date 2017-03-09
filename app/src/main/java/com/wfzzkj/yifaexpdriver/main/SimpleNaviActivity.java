package com.wfzzkj.yifaexpdriver.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
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
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.utils.Utils;
import com.wfzzkj.yifaexpdriver.utils.xunfei.TTSController;

/**
 * 
 *导航界面
 * 
 * */
public class SimpleNaviActivity extends Activity implements
		AMapNaviListener,AMapNaviViewListener {
	//导航View
	private AMapNaviView mAmapAMapNaviView;
   //是否为模拟导航
	private boolean mIsEmulatorNavi = true;
	//记录有哪个页面跳转而来，处理返回键
	private int mCode=-1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplenavi);
		Bundle bundle = getIntent().getExtras();
		processBundle(bundle);
		init(savedInstanceState);   
	
	}

	private void processBundle(Bundle bundle) {
		if (bundle != null) {
			//mIsEmulatorNavi = bundle.getBoolean(Utils.ISEMULATOR, true);
			//mIsEmulatorNavi =false;
			mCode=bundle.getInt(Utils.ACTIVITYINDEX);
		}
	}

	/**
	 * 初始化
	 * 
	 * @param savedInstanceState
	 */
	private void init(Bundle savedInstanceState) {
		mAmapAMapNaviView = (AMapNaviView) findViewById(R.id.simplenavimap);
		mAmapAMapNaviView.onCreate(savedInstanceState);
		AMapNavi.getInstance(this).addAMapNaviListener(this);
		mAmapAMapNaviView.setAMapNaviViewListener(this);
		AMapNavi.getInstance(this).startGPS();
System.out.println(" mIsEmulatorNavi    "+ mIsEmulatorNavi);
		if (mIsEmulatorNavi) {//////////mIsEmulatorNavi
			// 设置模拟速度
			AMapNavi.getInstance(this).setEmulatorNaviSpeed(190);
			// 开启模拟导航
			AMapNavi.getInstance(this).startNavi(NaviType.EMULATOR);

		} else {
			// 开启实时导航
			AMapNavi.getInstance(this).startNavi(NaviType.GPS);
			System.out.println("-----"+new NaviInfo().getCurStepRetainDistance());
		}
	}

//-----------------------------导航界面回调事件------------------------
	/**
	 * 导航界面返回按钮监听
	 * */
	@Override
	public void onNaviCancel() {
		Intent intent = new Intent(SimpleNaviActivity.this,
				IndexActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		finish();
	}
   
	@Override
	public void onNaviSetting() {

	}

	@Override
	public void onNaviMapMode(int arg0) {
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
	/**
	 * 
	 * 返回键监听事件
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(mCode==Utils.SIMPLEROUTENAVI){
				Intent intent = new Intent(SimpleNaviActivity.this,
						SimpleGPSNaviActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				finish();
				
			}
			else if(mCode==Utils.SIMPLEGPSNAVI){
				Intent intent = new Intent(SimpleNaviActivity.this,
						SimpleGPSNaviActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				finish();
			}
			else{
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	// ------------------------------生命周期方法---------------------------
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mAmapAMapNaviView.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
		mAmapAMapNaviView.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
		mAmapAMapNaviView.onPause();
		AMapNavi.getInstance(this).stopNavi();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mAmapAMapNaviView.onDestroy();
		
		TTSController.getInstance(this).stopSpeaking();
		
	}

	@Override
	public void onLockMap(boolean arg0) {
		  
		// TODO Auto-generated method stub  
		
	}

	@Override
	public void onNaviViewLoaded() {

	}

	@Override
	public boolean onNaviBackClick() {
		return false;
	}

	@Override
	public void onInitNaviFailure() {
		System.out.println("onInitNaviFailure");
	}

	@Override
	public void onInitNaviSuccess() {
		System.out.println("onInitNaviSuccess");
	}

	@Override
	public void onStartNavi(int i) {
		System.out.println("onStartNavi"+i);
	}

	@Override
	public void onTrafficStatusUpdate() {

	}

	@Override
	public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

	}

	@Override
	public void onGetNavigationText(int i, String s) {

	}

	@Override
	public void onEndEmulatorNavi() {

	}

	@Override
	public void onArriveDestination() {

	}

	@Override
	public void onArriveDestination(NaviStaticInfo naviStaticInfo) {
		System.out.println("onArriveDestination"+naviStaticInfo);
	}

	@Override
	public void onArriveDestination(AMapNaviStaticInfo aMapNaviStaticInfo) {
		System.out.println("onArriveDestination"+aMapNaviStaticInfo);
	}

	@Override
	public void onCalculateRouteSuccess() {
		System.out.println("onCalculateRouteSuccess");
	}

	@Override
	public void onCalculateRouteFailure(int i) {

	}

	@Override
	public void onReCalculateRouteForYaw() {

	}

	@Override
	public void onReCalculateRouteForTrafficJam() {

	}

	@Override
	public void onArrivedWayPoint(int i) {

	}

	@Override
	public void onGpsOpenStatus(boolean b) {

	}

	@Override
	public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

	}

	@Override
	public void onNaviInfoUpdate(NaviInfo naviInfo) {
		System.out.println("onNaviInfoUpdate"+naviInfo);
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
	public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

	}

	@Override
	public void hideLaneInfo() {

	}

	@Override
	public void onCalculateMultipleRoutesSuccess(int[] ints) {

	}

	@Override
	public void notifyParallelRoad(int i) {

	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

	}

	@Override
	public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

	}

	@Override
	public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

	}
}
