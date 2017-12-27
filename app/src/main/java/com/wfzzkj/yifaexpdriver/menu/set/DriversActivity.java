package com.wfzzkj.yifaexpdriver.menu.set;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.modle.DriverForYifa;
import com.wfzzkj.yifaexpdriver.modle.GoodsForYifa;
import com.wfzzkj.yifaexpdriver.utils.FailedlWrite;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class DriversActivity extends Activity {
    ListView lv_hongbao_detail_list;
    List<DriverForYifa> arrayList ;
     MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);
        initView();
        initData();
    }

    private void initView() {
        // TODO Auto-generated method stub
        ((TextView)findViewById(R.id.title)).setText("选择司机");
        //返回按钮
        findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        lv_hongbao_detail_list = (ListView)findViewById(R.id.lv_hongbao_detail_list);
        adapter = new MyAdapter();
        lv_hongbao_detail_list.setAdapter(adapter);

    }
    private void initData(){
        BmobQuery<DriverForYifa> query = new BmobQuery<DriverForYifa>();
        query.setLimit(99);
        query.addWhereEqualTo("type", 2);
        query.findObjects(this, new FindListener<DriverForYifa>() {
            @Override
            public void onSuccess(List<DriverForYifa> object) {
                // TODO Auto-generated method stub
                arrayList = object;
                adapter.notifyDataSetChanged();
                 //System.out.println(object.size()+"aaaaaaaaaaaaaa"+object.size());

            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                FailedlWrite.writeCrashInfoToFile("BMOB订单查询失败  "+code+msg);
                // System.out.println("bbbbbbbbbbbbbbbbb");
            }
        });
    }


    public class MyAdapter extends BaseAdapter {
        //		public void setList(ArrayList<DataHongbaoDetaile>  listString){
//			this.notifyDataSetChanged();
//		}
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (arrayList ==null){
                return  0;
            }else
            return arrayList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                convertView = LayoutInflater.from(DriversActivity.this)
                        .inflate(R.layout.listitem_hongbao_details, null, false);
            }// tv_hongbao_detail_name    tv_hongbao_detail_states tv_hongbao_detail_coust  tv_hongbao_detail_time
            TextView   tv_hongbao_detail_name =(TextView)convertView.findViewById(R.id.tv_hongbao_detail_name);
            TextView   tv_hongbao_detail_states =(TextView)convertView.findViewById(R.id.tv_hongbao_detail_state);
            TextView   tv_hongbao_detail_coust =(TextView)convertView.findViewById(R.id.tv_hongbao_detail_coust);
            TextView   tv_hongbao_detail_time =(TextView)convertView.findViewById(R.id.tv_hongbao_detail_time);

            try {
                tv_hongbao_detail_name.setText(arrayList.get(position).getCompany());
                tv_hongbao_detail_states.setText("");//tv_hongbao_detail_coust

                tv_hongbao_detail_coust.setText(arrayList.get(position).getPhone());
                tv_hongbao_detail_time.setText(arrayList.get(position).getCarInfo());
            }catch (Exception e){
                e.printStackTrace();
            }


            return convertView;
        }

    }
}//地址   电话   公司 信息
