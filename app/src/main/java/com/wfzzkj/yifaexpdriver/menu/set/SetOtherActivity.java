package com.wfzzkj.yifaexpdriver.menu.set;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wfzzkj.yifaexpdriver.R;

public class SetOtherActivity extends Activity {
    RelativeLayout rlSetWodeDongTai;
    RelativeLayout rlSetWodeTongZhi;
    RelativeLayout rlSetHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_other);
        initView();
    }

    private void initView() {
        // TODO Auto-generated method stub
        ((TextView)findViewById(R.id.title)).setText("设置");
        //返回按钮
        findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        rlSetHelp =(RelativeLayout) findViewById(R.id.rlSetHelp);
        rlSetWodeTongZhi =(RelativeLayout) findViewById(R.id.rlSetWodeTongZhi);
        rlSetWodeDongTai =(RelativeLayout) findViewById(R.id.rlSetWodeDongTai);

        rlSetWodeDongTai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(SetOtherActivity.this,DriversActivity.class));
            }
        });
        rlSetWodeTongZhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(SetOtherActivity.this,DriversActivity.class));
            }
        });
    }

}
