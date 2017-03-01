package com.wfzzkj.yifaexpdriver.main;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNavi;
import com.wfzzkj.yifaexpdriver.Config;
import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.menu.MenuMainActivity;
import com.wfzzkj.yifaexpdriver.modle.GoodsForYifa;
import com.wfzzkj.yifaexpdriver.modle.UserForYifa;
import com.wfzzkj.yifaexpdriver.utils.FailedlWrite;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;
import com.wfzzkj.yifaexpdriver.utils.xunfei.TTSController;


public class IndexActivity extends Activity implements LocationSource,
		AMapLocationListener {
	ImageView ivIndexButton;// 界面旋转主按钮(接单不接单)
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	LatLng myLa = null;// 记录我目前的定位
	private boolean locationFirst = true;// 定位打印小蓝点只执行一次
	private boolean isBusy = false;// 司机接单标志
	private boolean isVisibleTraffic = false;// 司机接单标志
	private SoundPool soundPool;
	private GoodsForYifa good = new GoodsForYifa();
	private final int getOrderSuccess = 2;
	private final int GODAOHANG = 3;//去导航
	AMapLocationClient mlocationClient;
	AMapLocationClientOption mLocationOption;
	BmobRealTimeData rtd ;
	UserForYifa user = null;
	String currentId = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		user =  UserForYifa.getCurrentUser(getApplicationContext(),UserForYifa.class);


		initMap();
		initView();
		// 监听表更新
		getNewValue();

	}

	private void initView() {
		//语音播报开始
				TTSController ttsManager = TTSController.getInstance(this);// 初始化语音模块
				ttsManager.init();
		System.out.println("ttsManager   ttsManager   " + ttsManager);
		//ttsManager.startSpeaking("aaaaaaa");
		AMapNavi.getInstance(getApplicationContext()).addAMapNaviListener(ttsManager);
				//AMapNavi.getInstance(this).setAMapNaviListener(ttsManager);// 设置语音模块播报

		// TODO Auto-generated method stub
		rtd= new BmobRealTimeData();
		final Animation operatingAnim = AnimationUtils.loadAnimation(this,
				R.anim.index_rotate);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
		final TextView tvIndexStateText = (TextView) findViewById(R.id.tvIndexStateText);
		final ImageView ivIndexButton = (ImageView) findViewById(R.id.ivIndexButton);
		ivIndexButton.startAnimation(operatingAnim);
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(this, R.raw.stop_order, 1);
		soundPool.load(this, R.raw.start_order, 2);
		soundPool.play(1, 1, 1, 0, 0, 1);
		// 开关接单功能
		ivIndexButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub


				isBusy = !isBusy;
				UserForYifa userTemp = new UserForYifa();
				userTemp.setBusy(isBusy);
				//System.out.println("userTemp" +userTemp);
				userTemp.update(IndexActivity.this, user.getObjectId(), new UpdateListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						if (isBusy) {
							ivIndexButton.clearAnimation();
							tvIndexStateText.setText("关闭接单");
							soundPool.play(1, 1, 1, 0, 0, 1);
							// 取消监听表更新
							rtd.unsubTableUpdate("GoodsForYifa");
							// operatingAnim.setRepeatCount(1);
							// operatingAnim.reset();
						} else {
							soundPool.play(2, 1, 1, 0, 0, 1);
							tvIndexStateText.setText("接单中");
							ivIndexButton.startAnimation(operatingAnim);
							// 监听表更新
							if (rtd.isConnected())
								// 监听表更新
								rtd.subTableUpdate("GoodsForYifa");
						}
					}
					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						//toast("更新失败："+msg);
						System.out.println("失败 更新忙闲" + code + msg);
					}
				});
			}
		});

//				userTemp.update(user.getObjectId(),new UpdateListener() {
//					@Override
//					public void done(BmobException e) {
//						if(e==null){
//							if (isBusy) {
//								ivIndexButton.clearAnimation();
//								tvIndexStateText.setText("关闭接单");
//								soundPool.play(1, 1, 1, 0, 0, 1);
//								// 取消监听表更新
//								rtd.unsubTableUpdate("GoodsForYifa");
//								// operatingAnim.setRepeatCount(1);
//								// operatingAnim.reset();
//							} else {
//								soundPool.play(2, 1, 1, 0, 0, 1);
//								tvIndexStateText.setText("接单中");
//								ivIndexButton.startAnimation(operatingAnim);
//								// 监听表更新
//								if (rtd.isConnected())
//									// 监听表更新
//									rtd.subTableUpdate("GoodsForYifa");
//							}
//						}else{
//							System.out.println("失败 更新忙闲" + e.getMessage());
//							FailedlWrite.writeCrashInfoToFile("失败 更新司机忙闲" + e.getMessage());
//						}
//					}
//				});
//			}
//		});



		// 跳转更多截界面
		findViewById(R.id.tvIndexMore).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View arg0) {
						startActivity(new Intent()
								.setClass(getApplicationContext(),
										MenuMainActivity.class));
					}
				});
		// 开启路况
		findViewById(R.id.tvIndexLukuang).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View arg0) {
						isVisibleTraffic = !isVisibleTraffic;
						aMap.setTrafficEnabled(isVisibleTraffic);
					}
				});

	}


	/**
	 * 获取服务器最新消息
	 */
	private void getNewValue() {

		rtd.start(this,new ValueEventListener() {
			@Override
			public void onDataChange(JSONObject data) {
				if (!isBusy) {
					JSONObject jsonObj;
					try {
						jsonObj = new JSONObject(data.toString());
						JSONObject obj = jsonObj.getJSONObject("data");

						currentId = obj.getString("objectId");
						// String name = obj.getString("sExpUserTime");
						// System.out.println(id+name);
						// String gender = jsonObj.getString("gender");
						BmobQuery<GoodsForYifa> query = new BmobQuery<GoodsForYifa>();
						query.include("user");
						query.include("userDriver");
						query.getObject(IndexActivity.this, currentId,
								new GetListener<GoodsForYifa>() {
									@Override
									public void onSuccess(GoodsForYifa object) {
										// TODO Auto-generated method stub
										//判断匹配用户是否为本司机
										if (object.getUserDriver().getObjectId().equals(BmobUser.getCurrentUser(getApplicationContext(),
												UserForYifa.class).getObjectId())) {
											good = object;
											Config.getInstance().good = good;
											//updateInfo();
											handler.sendEmptyMessage(getOrderSuccess);
//											TTSController.getInstance(IndexActivity.this).playText(good.getString());
//											   Timer timer = new Timer();
//											   timer.schedule(new TaskDaohang(), (good.getString().length()/3+1)*1000);
										}
									}
									@Override
									public void onFailure(int code, String arg0) {
										// TODO Auto-generated method stub
										ToastUtils.showShort(getApplicationContext(),
												"失败原因未知 错误码 " + code + arg0);
										FailedlWrite.writeCrashInfoToFile("bmob"+"失败："+ code + arg0);
									}
								});

//						query.getObject(currentId, new QueryListener<GoodsForYifa>() {
//
//							@Override
//							public void done(GoodsForYifa object, BmobException e) {
//								//System.out.println("done  "+e);
//								if(e==null){
//									if (object.getUserDriver().getObjectId().equals(BmobUser.getCurrentUser(
//												UserForYifa.class).getObjectId())) {
//											good = object;
//											Config.getInstance().good = good;
//											handler.sendEmptyMessage(getOrderSuccess);
//										}
//								}else{
//									FailedlWrite.writeCrashInfoToFile("bmob"+"失败："+e.getMessage()+","+e.getErrorCode());
//									Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//								}
//							}
//
//						});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

//			@Override
//			public void onConnectCompleted(Exception ex) {
//				Log.d("bmob", "连接成功:"+rtd.isConnected());
//				//System.out.println("bmob" + "连接成功:" + rtd.isConnected());
//				rtd.subTableUpdate("GoodsForYifa");
//			}
//		});
@Override
public void onConnectCompleted() {
	// TODO Auto-generated method stub
	System.out.println("bmob" + "连接成功:" + rtd.isConnected());
	rtd.subTableUpdate("GoodsForYifa");
}
		});

	}


	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case getOrderSuccess:
				if(rtd.isConnected()){
				    // 监听表更新
				    rtd.unsubTableUpdate("GoodsForYifa");
				}
				TTSController.getInstance(IndexActivity.this).startSpeaking(good.getString());

			handler.sendEmptyMessageDelayed(GODAOHANG, (good.getString().length()/3+1)*1000);

				break;
			case GODAOHANG:initDaoHang();//导航
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

/**
 * 导航初始化
 * @return
 */
private void initDaoHang(){
	startActivity(new Intent().setClass(getApplicationContext(), SimpleGPSNaviActivity.class));
}
	/**
	 * 初始化AMap对象
	 */
	private void initMap() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		// myLocationStyle.myLocationIcon(BitmapDescriptorFactory
		// .fromResource(R.drawable.ic_launcher));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细


		// 设置定位监听
		aMap.setLocationSource(this);
// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		aMap.setMyLocationEnabled(true);
// 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
//		aMap.setMyLocationStyle(myLocationStyle);
//		aMap.setLocationSource(this);// 设置定位监听
//
//		// aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
//		// aMap.setOnInfoWindowClickListener(this);// 设置点击marker事件监听器
//		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
//		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//		// aMap.setMyLocationType()

	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 此方法已经废弃
	 */


	/**
	 * 定位成功后回调函数 地图上显示marker，
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null && aLocation != null) {
			mListener.onLocationChanged(aLocation);// 显示系统小蓝点
			// System.out.println("onLocationChangedAMapLocation");
			// 执行一次 定位打印小蓝点
			if (locationFirst) {
				locationFirst = false;
				Marker marker = aMap.addMarker(new MarkerOptions()
						.anchor(0.5f, 0.5f)
						.position(
								new LatLng(aLocation.getLatitude(), aLocation
										.getLongitude()))
						.title("我在这里")
						.snippet(
								"当前:" + aLocation.getStreet()
										+ aLocation.getPoiName())
						.draggable(true));
				marker.showInfoWindow();
				myLa = new LatLng(aLocation.getLatitude(),
						aLocation.getLongitude());
				//System.out.println("-----"+UserForYifa.getCurrentUser(getApplicationContext(),UserForYifa.class) !=null);
				if (UserForYifa.getCurrentUser(getApplicationContext(),UserForYifa.class) !=null) {
					timer10putLocation(aLocation);
				}
			}

		}
	}
	//定时器
	  public  void timer10putLocation(final AMapLocation aLocation) {  //设定指定任务task在指定延迟delay后进行固定频率peroid的执行。
	        Timer timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	            public void run() {
	            	UserForYifa user =  UserForYifa.getCurrentUser(getApplicationContext(),UserForYifa.class);
	        		if(user != null){
	        		    // 允许用户使用应用
	        			//发送服务器定位信息

	        			UserForYifa newUser = new UserForYifa();
	        			newUser.setGpsAddStr(aLocation.getStreet()+aLocation.getPoiName());
	        			newUser.setGpsAdd(new BmobGeoPoint(aLocation.getLongitude(),aLocation.getLatitude()));
						newUser.update(getApplicationContext(),user.getObjectId(),new UpdateListener() {
							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								System.out.println("上传地理位置成功");
							}
							@Override
							public void onFailure(int code, String msg) {
								// TODO Auto-generated method stub
								//toast("更新用户信息失败:" + msg);
								FailedlWrite.writeCrashInfoToFile("上传地理位置失败"+code+msg);
							}
						});

//						newUser.update(user.getObjectId(),new UpdateListener() {
//							@Override
//							public void done(BmobException e) {
//								if(e==null){
//
//								}else{
//									FailedlWrite.writeCrashInfoToFile("上传地理位置失败"+e.getMessage());
//								}
//							}
//						});

	        		}else{
	        		    //缓存用户对象为空时， 可打开用户注册界面…
	        			System.out.println("null   ");
	        		}

	            }
	        }, 3, 1000*60*10);
	    }
	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			//初始化定位
			mlocationClient = new AMapLocationClient(this);
			//初始化定位参数
			mLocationOption = new AMapLocationClientOption();
			//设置定位回调监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();//启动定位
		}
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}
}