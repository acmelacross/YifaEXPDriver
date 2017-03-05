package com.wfzzkj.yifaexpdriver.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.yoojia.qrcode.qrcode.QRCodeEncoder;
import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.modle.GoodsForYifa;
import com.wfzzkj.yifaexpdriver.modle.UserForYifa;
import com.wfzzkj.yifaexpdriver.utils.FailedlWrite;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;

//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import io.github.xudaojie.qrcodelib.CaptureActivity;

//@ContentView(R.layout.activity_changer_driver)
public class ChangerDriverActivity extends Activity {

   // @ViewInject(R.id.ivChangerDriverEWM)
    private ImageView mViewPager;
    private TextView tvChangerDriverOpenEWM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changer_driver);
        initErWeiMa();
    }

    private void initErWeiMa() {
        ((TextView)findViewById(R.id.title)).setText("更换司机");
        //返回按钮
        mViewPager = (ImageView)findViewById(R.id.ivChangerDriverEWM);
        final Bitmap centerImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        // 生成的二维码图案
        Bitmap qrCodeImage = new QRCodeEncoder.Builder()
                .width(200) // 二维码图案的宽度
                .height(200)
                .paddingPx(0) // 二维码的内边距
                .marginPt(3) // 二维码的外边距
                .centerImage(centerImage) // 二维码中间图标
                .build()
                .encode(MyOrderActivity.goodTemp.getObjectId());

//        mViewPager.setImageBitmap(qrCodeImage);
//        tvChangerDriverOpenEWM = (TextView) findViewById(R.id.tvChangerDriverOpenEWM);
//        tvChangerDriverOpenEWM.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(ChangerDriverActivity.this, CaptureActivity.class);
//                startActivityForResult(i, REQUEST_CODE);
//            }
//        });

    }

//    @Event(value = R.id.tvChangerDriverOpenEWM,
//            type = View.OnClickListener.class/*可选参数, 默认是View.OnClickListener.class*/)
//    private void onOpenEWMClick(View view) {
//        Intent i = new Intent(ChangerDriverActivity.this, CaptureActivity.class);
//        startActivityForResult(i, REQUEST_CODE);
//    }
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK
//                && requestCode == REQUEST_CODE
//                && data != null) {
//            String goodObjectId = data.getStringExtra("result");
//            if (goodObjectId==null||"".equals(goodObjectId)){
//                return;
//            }
////            BmobQuery<GoodsForYifa> query = new BmobQuery<GoodsForYifa>();
////            query.getObject(ChangerDriverActivity.this, goodObjectId, new GetListener<GoodsForYifa>() {
////                @Override
////                public void onSuccess(GoodsForYifa goodsForYifa) {
////                    GoodsForYifa gameScore = new GoodsForYifa();
////                    gameScore.setValue("userDriver", BmobUser.getCurrentUser(getApplicationContext(), UserForYifa.class));
////                    gameScore.update(ChangerDriverActivity.this,goodsForYifa.getObjectId());
////                }
////
////                @Override
////                public void onFailure(int i, String s) {
////                    System.out.println("更换司机 查询单条 数据 错误 ");
////                }
////            });
////
//
//            GoodsForYifa gameScore = new GoodsForYifa();
//            gameScore.setValue("userDriver", BmobUser.getCurrentUser(getApplicationContext(), UserForYifa.class));
//            gameScore.update(ChangerDriverActivity.this, goodObjectId, new UpdateListener() {
//                @Override
//                public void onSuccess() {
//                    ToastUtils.showLong(ChangerDriverActivity.this,"司机更换完毕");
//                }
//
//                @Override
//                public void onFailure(int i, String s) {
//                    System.out.println("更换司机 查询单条 数据 错误 "+i+s);
//                    FailedlWrite.writeCrashInfoToFile("更换司机 查询单条 数据 错误 "+i+s);
//                }
//            });
//
//
//
//
//
//            //Toast.makeText(ChangerDriverActivity.this, result, Toast.LENGTH_SHORT).show();
//        }
//    }
}
