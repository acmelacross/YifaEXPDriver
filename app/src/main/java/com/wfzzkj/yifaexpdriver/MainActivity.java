package com.wfzzkj.yifaexpdriver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.wfzzkj.yifaexpdriver.main.IndexActivity;
import com.wfzzkj.yifaexpdriver.modle.DriverForYifa;
import com.wfzzkj.yifaexpdriver.modle.UserForYifa;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.LogRecord;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图


//        initMob();
//        initBmob();
    h.sendEmptyMessageDelayed(0,2500);
    }

Handler h = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case  0:   isLogin();
                break;
        }
    }
};



    public void initMob(){
       SMSSDK.initSDK(this, "1b6ff869a9c09", "fbf6bcdfe2edb9b9f404af9dd62c4e2");
//       RegisterPage registerPage = new RegisterPage();
//       registerPage.setRegisterCallback(new EventHandler() {
//           public void afterEvent(int event, int result, Object data) {
//               // 解析注册结果
//               if (result == SMSSDK.RESULT_COMPLETE) {
//                   @SuppressWarnings("unchecked")
//                   HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
//                   String country = (String) phoneMap.get("country");
//                   String phone = (String) phoneMap.get("phone");
//                   // 提交用户信息
//                  // registerUser(country, phone);
//               }
//           }
//       });
//       registerPage.show(this);
        System.out.println("initMOB");
   }
    private  void initBmob(){
        //第一：默认初始化
        Bmob.initialize(this, "0230f4953d4ec60eb7f701dda12e0359");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);



//        Person p2 = new Person();
//        p2.setName("lucky");
//        p2.setAddress("北京海淀");
//        p2.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId,BmobException e) {
//                if(e==null){
//                    System.out.print("添加数据成功，返回objectId为："+objectId);
//                   // toast("添加数据成功，返回objectId为："+objectId);
//                }else{
//                    System.out.print("创建数据失败：" + e.getMessage());
//                  //  toast("创建数据失败：" + e.getMessage());
//                }
//            }
//        });
        System.out.println("initBMOB");
    }


    /**
     * 判断是否登录
     */
    private void isLogin() {


        UserForYifa user= BmobUser.getCurrentUser(getApplicationContext(),UserForYifa.class);
        if (user ==null) {
            //System.out.println("当前用户木有登陆");

            RegisterPage registerPage = new RegisterPage();
            registerPage.setRegisterCallback(new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    // 解析注册结果
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        @SuppressWarnings("unchecked")
                        HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                        String country = (String) phoneMap.get("country");
                        String phone = (String) phoneMap.get("phone");
                        // 提交用户信息
                        //ToastUtils.showShort(MainActivity.this,"success");
                        registerUser(country,phone);
                    }else{
                        ToastUtils.showShort(MainActivity.this,"mob error code "+result);
                        System.out.println("mob error code "+result);
                    }
                }
            });
            registerPage.show(this);
        }else{
            System.out.println("当前用户"+user.getObjectId());
            startActivity(new Intent().setClass(getApplicationContext(), IndexActivity.class));
            finish();
        }
    }

    // 提交用户信息
    private void registerUser(String country, String phone) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "易发快运安卓司机" + uid;
        String avatar = AVATARS[id % 12];
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
        System.out.println("registerUser 注册");

        // 登陆
        DriverForYifa bu2 = new DriverForYifa();
        bu2.setUsername(phone);
        bu2.setPassword("123456");
//        bu2.login(new SaveListener<BmobUser>() {
//
//            @Override
//            public void done(BmobUser bmobUser, BmobException e) {
//                if(e==null){
//                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
//                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
//                                    startActivity(new Intent().setClass(
//                        getApplicationContext(),
//                        IndexActivity.class));// 直接跳转
//                     ToastUtils.showShort(MainActivity.this, "验证通过直接登录 ");
//                    finish();
//                }else{
//                   // loge(e);
//                    System.out.println("bmob error code "+e);
//                    ToastUtils.showShort(MainActivity.this,"bmob error code "+e);
//
//                }
//            }
//        });

        bu2.login(getApplicationContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                startActivity(new Intent().setClass(
                        getApplicationContext(),
                        IndexActivity.class));// 直接跳转
//                ToastUtil
//                        .show(getApplicationContext(), "验证通过直接登录 ");
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                System.out.println();

                if (code == 101) {
                   // startActivity(new Intent().setClass(getApplicationContext(), Register2.class));
                    finish();
                    return;
                }
//                ToastUtil.show(getApplicationContext(), "登录失败:原因 "
//                        + msg + code);
            }
        });



    }


    // 短信注册，随机产生头像
    private static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };
}
