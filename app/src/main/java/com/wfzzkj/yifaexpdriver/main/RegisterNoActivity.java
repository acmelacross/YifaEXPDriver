package com.wfzzkj.yifaexpdriver.main;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a.a.a.This;
import com.wfzzkj.yifaexpdriver.CheckPermissionsActivity;
import com.wfzzkj.yifaexpdriver.Config;
import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.modle.DriverForYifa;
import com.wfzzkj.yifaexpdriver.utils.StringUtil;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;
import com.wfzzkj.yifaexpdriver.view.NumberProgressBar;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisterNoActivity extends CheckPermissionsActivity implements View.OnClickListener {
    DriverForYifa user = new DriverForYifa();
    int counter = 0;
    ImageView imageView_id;
    private final int IMAGE_CODE = 10; // 这里的IMAGE_CODE是自己任意定义的
    NumberProgressBar bnp;// 进度条控件
    private String fileNamePic = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_no);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title)).setText("身份证验证");
        ( (ImageView)findViewById(R.id.imgvPoiTitleBack)) .setOnClickListener(this);
        bnp = (NumberProgressBar) findViewById(R.id.numberbarNo);
        imageView_id = (ImageView) findViewById(R.id.imageView_id);
        imageView_id.setOnClickListener(this);
        fileNamePic = Config.getInstance().phone+ StringUtil.getCurrentTime()+".jpg";
        findViewById(R.id.tvRegConfirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.imgvPoiTitleBack:// 身份证验证流程
                finish();
                break;
            case R.id.imageView_id:// 上传图片
                getImage();
                break;
            case R.id.tvRegConfirm:// 确认注册
                EditText etRegCheJiaHao = (EditText) findViewById(R.id.etRegCheJiaHao);

                boolean b = StringUtil.checkEditText(etRegCheJiaHao)
                       ;
                System.out.println(b+"b" + user.getImageVal() + "---" +user.getImageVal() );
                if (b&&user.getImageVal()!=null
                        &&!"".equals(user.getImageVal())) {
                }else{
                    ToastUtils.showShort(getApplicationContext(), "请将您的信息输入完整");
                    return;
                }

                user.setPhone(Config.getInstance().phone);
                user.setCarJia(etRegCheJiaHao.getText().toString());
                user.setPassword("123456");
                user.setUsername(Config.getInstance().phone);
                regDriver();
                break;
            default:
                break;
        }
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
//                Uri u = Uri.fromFile(f);
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, f.getAbsolutePath());
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, IMAGE_CODE);
            } catch (ActivityNotFoundException e) {
                // TODO Auto-generated catch block
                Toast.makeText(RegisterNoActivity.this, "没有找到储存目录", Toast.LENGTH_LONG)
                        .show();
            }
        } else {
            Toast.makeText(RegisterNoActivity.this, "没有储存卡", Toast.LENGTH_LONG).show();
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
            System.out.println("aaaaaa  return");
            return;
        }

        final BmobFile bmobFile = new BmobFile(new File(filePath));

        bmobFile.uploadblock(RegisterNoActivity.this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                user.setImageVal(bmobFile.getFileUrl(RegisterNoActivity.this));
                System.out.println("bmob" + "文件上传成功：" + bmobFile.getFilename()
                        + ",可访问的文件地址：" + bmobFile.getFileUrl(RegisterNoActivity.this));
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
}
