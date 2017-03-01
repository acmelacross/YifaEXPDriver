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
import com.wfzzkj.yifaexpdriver.modle.Constact;
import com.wfzzkj.yifaexpdriver.modle.GoodsForYifa;
import com.wfzzkj.yifaexpdriver.utils.MyMapUtil;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MapPositionKeRenActivity extends Activity implements AMap.OnInfoWindowClickListener {
	private AMap aMap;
	private MapView mapView;
	private EditText etPayRMB;
	private  TextView mapChangerDriver;
	private GoodsForYifa good = MyOrderActivity.goodTemp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_huo_wu);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		initMap();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.title)).setText("货物在哪儿");
		//返回按钮
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		 //输入金额
		 // = (EditText) findViewById(R.id.etPayRMB);
		 if (good.getExpHuoPay()==Constact.EXP_GoodPayStateDefault) 
			 ((TextView)findViewById(R.id.tvIsPay)).setText("客户未选择支付方式");
		 else if(good.getExpHuoPay()== Constact.EXP_GoodPayStateInt)
			 ((TextView)findViewById(R.id.tvIsPay)).setText("客户选择网上支付");
		 else if(good.getExpHuoPay()==Constact.EXP_GoodPayStateDestination)
			  ((TextView)findViewById(R.id.tvIsPay)).setText("客户选择货到付款");
		 
		 ((TextView)findViewById(R.id.tvMapHuoWhereInfo)).setText(good.getInfo());
		TextView mapChangerDriver= ((TextView)findViewById(R.id.mapChangerDriver));
		mapChangerDriver.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent().setClass(MapPositionKeRenActivity.this,ChangerDriverActivity.class));
			}
		});

		}
	private void initMap() {
		if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
		findViewById(R.id.mapPositionConfirm).setOnClickListener(new View.OnClickListener() {//确认收货按钮
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		findViewById(R.id.ivMapPositionCall).setOnClickListener(new View.OnClickListener() {//打电话按钮
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//System.out.println(good.getUser().getUsername()+good.getUser().getPhone());
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+good.getUser().getUsername()));  
                startActivity(intent);  
			}
		});
		TextView carNums =  (TextView) findViewById(R.id.tvMapPositionCheNums);//设置车牌号
		carNums.setVisibility(View.GONE);
	}
	/**
     * 		amap添加一些事件监听器
     */
    private void setUpMap() {
        	//	aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
        //		aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
        //aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
    	printPointWithMap();
    }






    private void printPointWithMap(){
    	//MyMapUtil.drawMarkerAndWinWithLon(aMap, 30.679879, 104.064855, "主人,我到这里啦", "外星地点", false).showInfoWindow();
    	aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.position(new LatLng(30.679879, 104.064855)).title("我在这里呀")
				.snippet("成都市:30.679879, 104.064855").draggable(true).icon(BitmapDescriptorFactory
						.fromResource(R.drawable.map_huo_where)));
         MyMapUtil.changeCamera(aMap,
					CameraUpdateFactory.newCameraPosition(new CameraPosition(
							new LatLng(30.679879, 104.064855), 18, 0, 30)));
    }

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	
	}
}
