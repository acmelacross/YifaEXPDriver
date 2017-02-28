package com.wfzzkj.yifaexpdriver.menu;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.utils.MyMapUtil;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class ZhouBianWuLiuActivity extends Activity {
	private AMap aMap;
	private MapView mapView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhou_bian_wu_liu);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		initMap();
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.title)).setText("周边配送站");
		//返回按钮
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
	private void initMap() {
		if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
	}
	   /**
     * amap添加一些事件监听器
     */
    private void setUpMap() {
        	//	aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
        //		aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
        //aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
    	printPointWithMap();
    }
    private void printPointWithMap(){
    	//MyMapUtil.drawMarkerAndWinWithLon(aMap, 30.679879, 104.064855, "主人,我到这里啦", "外星地点", false).showInfoWindow();
    	Marker marker =  aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.position(new LatLng(30.679879, 104.064855)).title("成都市")
				.snippet("成都市:30.679879, 104.064855").draggable(true).icon(BitmapDescriptorFactory
						.fromResource(R.drawable.map_huo_where)));
    	marker.showInfoWindow();
         MyMapUtil.changeCamera(aMap,
					CameraUpdateFactory.newCameraPosition(new CameraPosition(
							new LatLng(30.679879, 104.064855), 18, 0, 30)));
    }

}
