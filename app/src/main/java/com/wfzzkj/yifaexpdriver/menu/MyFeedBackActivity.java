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
import com.wfzzkj.yifaexpdriver.utils.ToastUtils;


public class MyFeedBackActivity extends Activity  implements OnClickListener{
	  private EditText edit_txt_message = null;
	  private Button messageSubmit = null;
	  protected void onCreate(Bundle paramBundle)
	  {
	    super.onCreate(paramBundle);
	    setContentView(R.layout.activity_my_feedback);
	    ((TextView)findViewById(R.id.title)).setText("意见反馈");
	    this.messageSubmit = ((Button)findViewById(R.id.btn_message_submit));
	    this.messageSubmit.setOnClickListener(this);
	    this.edit_txt_message = ((EditText)findViewById(R.id.edit_txt_message));
	    findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
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
//		      MessageFBModle localMessageFBModle = new MessageFBModle();
//		      localMessageFBModle.setMessage(str);
//		      localMessageFBModle.setUserInfo(UserInfoModle.getInstance().toString());
//		      localMessageFBModle.save(this, new SaveListener()
//		      {
//		        public void onFailure(int paramAnonymousInt, String paramAnonymousString)
//		        {
//		        	 MyToast("�ύʧ�ܣ����Ժ�����");
//		          System.out.println(paramAnonymousString + "arg0" + paramAnonymousInt);
//		        }
//
//		        public void onSuccess()
//		        {
//		          //System.out.println("!!!!!!!!!");
//		        	 MyToast("�ύ�ɹ�");
//				      finish();
//		        }
//		      });
		     
		      return;
		    }
			ToastUtils.showLong(getApplicationContext(), "请输入有意义的内容");
		    break;
	    }
	    

	  }
	
	

}
