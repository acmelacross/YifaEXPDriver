package com.wfzzkj.yifaexpdriver.menu;



import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;

import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;


public class MenuMainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_main);
		findViewById(R.id.ivMenuDingdan).setOnClickListener(this);
		findViewById(R.id.ivMenuTuhao).setOnClickListener(this);
		findViewById(R.id.ivMenuPeisong).setOnClickListener(this);
		findViewById(R.id.ivMenuTongzhi).setOnClickListener(this);
		findViewById(R.id.ivMenuQita).setOnClickListener(this);
		findViewById(R.id.ivMenuYijianfankui).setOnClickListener(this);
		//返回按钮
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		 
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ivMenuDingdan:
			startActivity(new Intent().setClass(getApplicationContext(), MyOrderActivity.class));
			break;
	case R.id.ivMenuTuhao:
		ToastUtils.showShort(getApplicationContext(), "该功能暂未开放");
			break;

	case R.id.ivMenuYijianfankui:
		startActivity(new Intent().setClass(getApplicationContext(), MyFeedBackActivity.class));
		break;
	case R.id.ivMenuPeisong:
		startActivity(new Intent().setClass(getApplicationContext(), ZhouBianWuLiuActivity.class));
		break;
	case R.id.ivMenuTongzhi:
		
		break;
	case R.id.ivMenuQita:
		ToastUtils.showShort(getApplicationContext(), "该功能暂未开放");
		break;
		default:
			break;
		}
	}



}
