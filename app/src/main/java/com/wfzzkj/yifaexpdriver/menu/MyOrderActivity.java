package com.wfzzkj.yifaexpdriver.menu;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.adapter.MyJourneyAdapter;
import com.wfzzkj.yifaexpdriver.modle.Constact;
import com.wfzzkj.yifaexpdriver.modle.GoodsForYifa;
import com.wfzzkj.yifaexpdriver.modle.UserForYifa;
import com.wfzzkj.yifaexpdriver.utils.FailedlWrite;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;



public class MyOrderActivity extends Activity {
	ListView lvMyJourney = null;
	List<GoodsForYifa> list = new ArrayList<GoodsForYifa>();
	MyJourneyAdapter adapter ;;
	public static GoodsForYifa goodTemp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_journey);
		initView();
		initData();
	}
	private void initView() {
		// TODO Auto-generated method stub

		((TextView)findViewById(R.id.journeyTvUName)).setText("账号:"+BmobUser.getCurrentUser(MyOrderActivity.this, UserForYifa.class).getUsername());

		((TextView)findViewById(R.id.title)).setText("我的订单");
		lvMyJourney = (ListView)findViewById(R.id.lvMyJourney);
		adapter= new MyJourneyAdapter(getApplicationContext());
		lvMyJourney.setAdapter(adapter);
		lvMyJourney.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long arg3) {
				if (list.get(position).getiGoodState() != Constact.EXP_GoodStateCancel) {
					goodTemp = list.get(position);
					startActivity(new Intent().setClass(getApplicationContext(), MapPositionKeRenActivity.class));
				}

			}
		});
		//返回按钮
		findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	private void initData(){
		UserForYifa user = BmobUser.getCurrentUser(this, UserForYifa.class);
		if (user ==null) {
			//startActivity( new Intent().setClass(packageContext, cls))
			//finish();
			ToastUtils.showShort(getApplicationContext(), "用户未登陆");
			return;
		}
		BmobQuery<GoodsForYifa> query = new BmobQuery<GoodsForYifa>();
		query.include("user");
		query.addWhereEqualTo("userDriver", user);    // 查询当前用户的所有帖子
		query.order("-updatedAt");

		//query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
		query.findObjects(this, new FindListener<GoodsForYifa>() {
			@Override
			public void onSuccess(List<GoodsForYifa> object) {
				// TODO Auto-generated method stub
				// System.out.println(object.size()+"aaaaaaaaaaaaaa"+object.get(0).getUserDriver());
				if (object.size() == 0) {
					ToastUtils.showShort(getApplicationContext(), "无数据返回");
					return;
				}
				list =object;
				adapter.setList(list);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				FailedlWrite.writeCrashInfoToFile("BMOB订单查询失败  "+code+msg);
				// System.out.println("bbbbbbbbbbbbbbbbb");
			}
		});
	}
}
