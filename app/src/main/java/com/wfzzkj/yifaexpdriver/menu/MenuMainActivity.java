package com.wfzzkj.yifaexpdriver.menu;



import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.menu.set.SetOtherActivity;
import com.wfzzkj.yifaexpdriver.modle.GoodsForYifa;
import com.wfzzkj.yifaexpdriver.modle.UserForYifa;
import com.wfzzkj.yifaexpdriver.utils.FailedlWrite;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import io.github.xudaojie.qrcodelib.CaptureActivity;


public class MenuMainActivity extends Activity implements OnClickListener {
	private int REQUEST_CODE = 999;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_main);

		((TextView)findViewById(R.id.title)).setText("主菜单");
		//返回按钮
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
		//startActivity(new Intent().setClass(getApplicationContext(), ChangerDriverActivity.class));
		Intent i = new Intent(getApplicationContext(), CaptureActivity.class);
               startActivityForResult(i, REQUEST_CODE);
		break;
	case R.id.ivMenuQita:
		startActivity(new Intent().setClass(getApplicationContext(), SetOtherActivity.class));
		break;
		default:
			break;
		}
	}


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_CODE
                && data != null) {
            String goodObjectId = data.getStringExtra("result");
            if (goodObjectId==null||"".equals(goodObjectId)){
                return;
            }


            GoodsForYifa gameScore = new GoodsForYifa();
            gameScore.setValue("userDriver", BmobUser.getCurrentUser(getApplicationContext(), UserForYifa.class));
            gameScore.update(MenuMainActivity.this, goodObjectId, new UpdateListener() {
                @Override
                public void onSuccess() {
                    ToastUtils.showLong(MenuMainActivity.this,"司机更换完毕");
                }

                @Override
                public void onFailure(int i, String s) {
                    System.out.println("更换司机 查询单条 数据 错误 "+i+s);
                    FailedlWrite.writeCrashInfoToFile("更换司机 查询单条 数据 错误 "+i+s);
                }
            });





            //Toast.makeText(ChangerDriverActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
