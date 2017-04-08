package com.wfzzkj.yifaexpdriver.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.wfzzkj.yifaexpdriver.Config;
import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.utils.Utils;
import com.wfzzkj.yifaexpdriver.utils.xunfei.TTSController;

import java.util.ArrayList;

/**
 * 目前真正的导航界面
 */
public class Navictivity extends Activity implements
        AMapNaviListener,AMapNaviViewListener, View.OnClickListener {

    //导航View
    private AMapNaviView mAmapAMapNaviView;
    //是否为模拟导航
    private boolean mIsEmulatorNavi = true;
    //记录有哪个页面跳转而来，处理返回键
    private int mCode=-1;

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
private LinearLayout ll_navi_buttons;
    private ProgressDialog mRouteCalculatorProgressDialog;// 路径规划过程显示状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navictivity);
        initView();
        init(savedInstanceState);

    }
    private void init(Bundle savedInstanceState) {
        mAmapAMapNaviView = (AMapNaviView) findViewById(R.id.avimap);
        mAmapAMapNaviView.onCreate(savedInstanceState);
        AMapNavi.getInstance(Config.getInstance().appContext).addAMapNaviListener(this);
        mAmapAMapNaviView.setAMapNaviViewListener(this);
        AMapNavi.getInstance(this).startGPS();
        System.out.println(" mIsEmulatorNavi    "+ mIsEmulatorNavi);
        if (mIsEmulatorNavi) {//////////mIsEmulatorNavi
            // 设置模拟速度
            AMapNavi.getInstance(Config.getInstance().appContext).setEmulatorNaviSpeed(190);
            // 开启模拟导航
            AMapNavi.getInstance(Config.getInstance().appContext).startNavi(NaviType.EMULATOR);

        } else {
            // 开启实时导航
            AMapNavi.getInstance(Config.getInstance().appContext).startNavi(NaviType.GPS);
            System.out.println("-----"+new NaviInfo().getCurStepRetainDistance());
        }
    }

    private void initView() {
        mStartPoints.add(mNaviStart);
        mEndPoints.add(mNaviEnd);
        ll_navi_buttons = (LinearLayout)findViewById(R.id.ll_navi_buttons);
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

       // AMapNavi.getInstance(this).setAMapNaviListener(this);

        //开启导航
        //updateInfo();
        gps_cancel_navi_button = (Button)findViewById(R.id.gps_cancel_navi_button);
        gps_cancel_navi_button.setOnClickListener(this);
        gps_call_user = (Button)findViewById(R.id.gps_call_user);
        gps_call_user.setOnClickListener(this);

    }

//-----------------------------导航界面回调事件------------------------
    /**
     * 导航界面返回按钮监听
     * */
    @Override
    public void onNaviCancel() {
        Intent intent = new Intent(Navictivity.this,
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
            if(mCode== Utils.SIMPLEROUTENAVI){
                Intent intent = new Intent(Navictivity.this,
                        SimpleGPSNaviActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();

            }
            else if(mCode==Utils.SIMPLEGPSNAVI){
                Intent intent = new Intent(Navictivity.this,
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
        AMapNavi.getInstance(Config.getInstance().appContext).stopNavi();
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mAmapAMapNaviView.onDestroy();
//
//        TTSController.getInstance(this).stopSpeaking();
//
//    }

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
        System.out.println(i+"s"+s);
        TTSController.getInstance(Navictivity.this).startSpeaking(s);

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
        mRouteCalculatorProgressDialog.dismiss();

        System.out.println("onCalculateRouteSuccess");
        //AMapNavi.getInstance(Config.getInstance().appContext).setEmulatorNaviSpeed(190);
        // 开启模拟导航
        ll_navi_buttons.setVisibility(View.GONE);
        AMapNavi.getInstance(Config.getInstance().appContext).startNavi(NaviType.GPS);
    }

    @Override
    public void onCalculateRouteFailure(int i) {
        System.out.println("onCalculateRouteFailure  " + i);
        mRouteCalculatorProgressDialog.dismiss();
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
      //  System.out.println("onNaviInfoUpdate"+naviInfo);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gps_start_navi_button:
                AMapNavi.getInstance(Config.getInstance().appContext).calculateDriveRoute(mStartPoints,
                        mEndPoints, null, NaviType.EMULATOR);
                mRouteCalculatorProgressDialog.show();
                break;
            case R.id.gps_cancel_navi_button:
                finish();
                break;
            case R.id.gps_call_user:	AlertDialog.Builder builder = new AlertDialog.Builder(Navictivity.this);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 删除导航监听

        AMapNavi.getInstance(Config.getInstance().appContext).removeAMapNaviListener(this);
        AMapNavi.getInstance(Config.getInstance().appContext).destroy();// 销毁导航

        mAmapAMapNaviView.onDestroy();

        TTSController.getInstance(this).stopSpeaking();

//		TTSController.getInstance(this).stopSpeaking();
//		TTSController.getInstance(this).destroy();
    }
}
