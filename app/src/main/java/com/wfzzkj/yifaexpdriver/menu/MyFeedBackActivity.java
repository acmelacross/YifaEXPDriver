package com.wfzzkj.yifaexpdriver.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wfzzkj.yifaexpdriver.R;
import com.wfzzkj.yifaexpdriver.modle.Constact;
import com.wfzzkj.yifaexpdriver.modle.DriverForYifa;
import com.wfzzkj.yifaexpdriver.modle.MessageFBModle;
import com.wfzzkj.yifaexpdriver.modle.NoticeMsg;
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;


public class MyFeedBackActivity extends Activity  implements OnClickListener{
	  private EditText edit_txt_message = null;
	  private Button messageSubmit = null;
	  public static DriverForYifa driver;
	  protected void onCreate(Bundle paramBundle)
	  {
	    super.onCreate(paramBundle);
	    setContentView(R.layout.activity_my_feedback);
		  initView();
	  }
	  private void initView(){
		  findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View arg0) {
				  // TODO Auto-generated method stub
				  finish();
			  }
		  });
		  this.messageSubmit = ((Button)findViewById(R.id.btn_message_submit));
		  this.messageSubmit.setOnClickListener(this);
		  this.edit_txt_message = ((EditText)findViewById(R.id.edit_txt_message));
		  if (!getIntent().getBooleanExtra("flag",false)){//发送通知 而不是意见反馈
			  ((TextView)findViewById(R.id.title)).setText("意见反馈");


		  }else{
			  ((TextView)findViewById(R.id.title)).setText("发送通知");
			  edit_txt_message.setText("货物已经在路上了,请注意查收");
			  messageSubmit.setText("发送通知");
		  }

	  }

	
	@Override
	  public void onClick(View paramView)
	  {
	    switch (paramView.getId())
	    {
	    default:
	    case R.id.btn_message_submit:
		    String str = this.edit_txt_message.getText().toString().trim();
		    if ((str != null) && (str.length() > 0))
		    {
				if (!getIntent().getBooleanExtra("flag",false)){
		      MessageFBModle localMessageFBModle = new MessageFBModle();
		      localMessageFBModle.setMessage(str);
		      localMessageFBModle.setUserInfo(BmobUser.getCurrentUser(getApplicationContext()).toString());
		      localMessageFBModle.save(this, new SaveListener()
		      {
		        public void onFailure(int paramAnonymousInt, String paramAnonymousString)
		        {
		          System.out.println(paramAnonymousString + "arg0" + paramAnonymousInt);
		        }

		        public void onSuccess()
		        {
					ToastUtils.showLong(getApplicationContext(),"感谢反馈");
				      finish();
		        }
		      });
				}else{//发送通知 而不是意见反馈
					NoticeMsg localMessageFBModle = new NoticeMsg();
					localMessageFBModle.setContent(str);
					localMessageFBModle.setDriverFrom(BmobUser.getCurrentUser(getApplicationContext(),DriverForYifa.class));
					localMessageFBModle.setDriverTo(driver);
					localMessageFBModle.setNoticeType(Constact.NOTICE_TYPE_DRIVER_TO_DRIVER);
					localMessageFBModle.save(this, new SaveListener() {
						public void onFailure(int paramAnonymousInt, String paramAnonymousString) {
							System.out.println(paramAnonymousString + "arg0" + paramAnonymousInt);
						}

						public void onSuccess() {
							ToastUtils.showLong(getApplicationContext(), "发送成功");
							finish();
						}

					});
				}

		     
		    }else{
				ToastUtils.showLong(getApplicationContext(), "请输入有意义的内容");
			}

		    break;
	    }
	    

	  }
	
	

}
