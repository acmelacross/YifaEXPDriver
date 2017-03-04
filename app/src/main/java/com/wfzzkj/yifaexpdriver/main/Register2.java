package com.wfzzkj.yifaexpdriver.main;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wfzzkj.yifaexpdriver.Config;
import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.modle.DriverForYifa;
import com.wfzzkj.yifaexpdriver.utils.StringUtil;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;
import com.wfzzkj.yifaexpdriver.view.NumberProgressBar;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


public class Register2 extends Activity implements OnClickListener {
    DriverForYifa user = new DriverForYifa();
    int counter = 0;
    NumberProgressBar bnp;// 进度条控件
    private final String IMAGE_TYPE = "image/*";
    private final int IMAGE_CODE = 10; // 这里的IMAGE_CODE是自己任意定义的
    ImageView ivRegImage = null;
    private String fileNamePic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        initView();
        fileNamePic =Config.getInstance().phone+StringUtil.getCurrentTime()+".jpg";
    }

    private void initView() {
        // TODO Auto-generated method stub
        findViewById(R.id.tvRegConfirm).setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setText("司机验证");
        // startActivity( new Intent().setClass(getApplicationContext(),
        // IndexActivity.class));
        // finish();
        bnp = (NumberProgressBar) findViewById(R.id.numberbar1);
        ivRegImage = (ImageView) findViewById(R.id.ivRegImage);
        ivRegImage.setOnClickListener(this);
        findViewById(R.id.tvRegProtocol).setOnClickListener(this);
        // Timer timer = new Timer();
        // timer.schedule(new TimerTask() {
        // @Override
        // public void run() {
        // runOnUiThread(new Runnable() {
        // @Override
        // public void run() {
        // bnp.incrementProgressBy(1);
        // counter ++;
        // if (counter == 110) {
        // bnp.setProgress(0);
        // counter=0;
        // }
        // }
        // });
        // }
        // }, 1000, 100);

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.tvRegConfirm:// 确认注册
                EditText etRegCheInfo = (EditText) findViewById(R.id.etRegCheInfo);
                EditText etRegChePai = (EditText) findViewById(R.id.etRegChePai);
                EditText etRegCompany = (EditText) findViewById(R.id.etRegCompany);
                EditText etRegName = (EditText) findViewById(R.id.etRegName);
                EditText etRegCheJiaHao = (EditText) findViewById(R.id.etRegCheJiaHao);
                boolean b = StringUtil.checkEditText(etRegCheInfo)
                        || StringUtil.checkEditText(etRegChePai)
                        || StringUtil.checkEditText(etRegCompany)
                        || StringUtil.checkEditText(etRegName)
                        || StringUtil.checkEditText(etRegCheJiaHao);
                if (!b) {
                    ToastUtils.showShort(getApplicationContext(), "请将您的信息输入完整");
                    return;
                }
                user.setPhone(Config.getInstance().phone);
                user.setCarInfo(etRegCheInfo.getText().toString());
                user.setCarJia(etRegCheJiaHao.getText().toString());
                user.setCarNum(etRegChePai.getText().toString());
                user.setPassword("123456");
                user.setUsername(Config.getInstance().phone);
                user.setNickeyName(etRegChePai.getText().toString());
                user.setCompany(etRegCompany.getText().toString());
                regDriver();
                break;
            case R.id.ivRegImage:// 获取图片上传
                getImage();
                break;
            case R.id.tvRegProtocol:// 点击查看协议
                startActivity(new Intent().setClass(Register2.this, ProtocolInfoActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * z注册司机
     */
    private void regDriver() {
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                // ToastUtil.show(getApplicationContext(), "注册成功:");
                startActivity(new Intent().setClass(getApplicationContext(),
                        IndexActivity.class));// 直接跳转
                Log.e("----","注册成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                // ToastUtil.show(getApplicationContext(),
                // code+"注册失败:原因 "+msg+code);
                Log.e("----","code" + code + (code == 202) + msg);
                System.out.println("code" + code + (code == 202) + msg);
                if (code == 202) {
                    // 登陆
                    DriverForYifa bu2 = new DriverForYifa();
                    bu2.setUsername(Config.getInstance().phone);
                    bu2.setPassword("123456");
                    bu2.login(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            startActivity(new Intent().setClass(
                                    getApplicationContext(),
                                    IndexActivity.class));// 直接跳转
                            ToastUtils
                                    .showShort(getApplicationContext(), "验证通过直接登录 ");
                            finish();
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            System.out.println();
                            ToastUtils
                                    .showShort(getApplicationContext(), "登录失败:原因 "
                                            + msg + code);
                        }
                    });

                }

            }
        });

        uploadImage();
    }

    private void getImage() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Environment.getExternalStorageDirectory()
                        + "/" + "yifa");
                if (!dir.exists())
                    dir.mkdirs();

                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(dir, fileNamePic);// localTempImgDir和localTempImageFileName是自己定义的名字
                Uri u = Uri.fromFile(f);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                startActivityForResult(intent, IMAGE_CODE);
            } catch (ActivityNotFoundException e) {
                // TODO Auto-generated catch block
                Toast.makeText(Register2.this, "没有找到储存目录", Toast.LENGTH_LONG)
                        .show();
            }
        } else {
            Toast.makeText(Register2.this, "没有储存卡", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 上传图片
     */
    private void uploadImage() {
        // TODO Auto-generated method stub
        final String filePath = Environment.getExternalStorageDirectory() + "/"
                + "yifa" + "/"+fileNamePic;
        if (!new File(filePath).exists()) {
            return;
        }

        final BmobFile bmobFile = new BmobFile(new File(filePath));

        bmobFile.uploadblock(Register2.this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                user.setImageVal(bmobFile.getFileUrl(Register2.this));
                System.out.println("bmob" + "文件上传成功：" + bmobFile.getFilename()
                        + ",可访问的文件地址：" + bmobFile.getFileUrl(Register2.this));
                // Log.i("bmob","文件上传成功："+fileName+",可访问的文件地址："+file.getUrl());
                try {
                    new File(filePath).delete();
                } catch (Exception e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                System.out.println("文件上传失败：" + i + "--  " + s);
            }

//			@Override
//			public void done(BmobException e) {
//				if(e==null){
//
//					System.out.println("bmob" + "文件上传成功：" + bmobFile.getFilename()
//							+ ",可访问的文件地址：" + bmobFile.getFileUrl(Register2.this));
//					// Log.i("bmob","文件上传成功："+fileName+",可访问的文件地址："+file.getUrl());
//					try {
//						new File(filePath).delete();
//					} catch (Exception e2) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					//bmobFile.getFileUrl()--返回的上传文件的完整地址
//					//toast("上传文件成功:" + bmobFile.getFileUrl());
//				}else{
//					System.out.println("文件上传失败：" + e.getMessage());
//					// Log.i("bmob","文件上传失败："+errormsg);
//					//toast("上传文件失败：" + e.getMessage());
//				}
//
//			}

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                bnp.incrementProgressBy(1);
                counter = value;
                bnp.setProgress(value);
                if (counter == 100) {
//						 bnp.setProgress(0);
//						 counter=0;
                }
                System.out.println("onProgress :" + value);
            }
        });


//		BTPFileResponse response = BmobProFile.getInstance(Register2.this)
//				.upload(filePath, new UploadListener() {
//					@Override
//					public void onSuccess(String fileName, String url,
//							BmobFile file) {
//						// TODO Auto-generated method stub
//						System.out.println("bmob" + "文件上传成功：" + fileName
//								+ ",可访问的文件地址：" + file.getUrl());
//						// Log.i("bmob","文件上传成功："+fileName+",可访问的文件地址："+file.getUrl());
//						try {
//							new File(filePath).delete();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//
//					@Override
//					public void onProgress(int progress) {
//						// TODO Auto-generated method stub
//
//						 bnp.incrementProgressBy(1);
//						 counter =progress;
//						 bnp.setProgress(progress);
//						 if (counter == 100) {
////						 bnp.setProgress(0);
////						 counter=0;
//						 }
//						System.out.println("onProgress :"+progress);
//						// Log.i("bmob","onProgress :"+progress);
//					}
//
//					@Override
//					public void onError(int statuscode, String errormsg) {
//						// TODO Auto-generated method stub
//						System.out.println("文件上传失败：" + errormsg);
//						// Log.i("bmob","文件上传失败："+errormsg);
//					}
//				});
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // File f=new File(Environment.getExternalStorageDirectory()
        // +"/"+"aaa"+"/"+"aaa.jpg");
        // try {
        // Uri u =
        // Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
        // f.getAbsolutePath(), null, null));
        // //u就是拍摄获得的原始图片的uri，剩下的你想干神马坏事请便……
        // } catch (FileNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        if (requestCode == IMAGE_CODE) {
            uploadImage();
        }


    }
}
