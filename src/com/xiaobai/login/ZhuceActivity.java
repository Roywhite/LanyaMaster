package com.xiaobai.login;

import java.util.Timer;
import java.util.TimerTask;

import com.xiaobai.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ZhuceActivity extends Activity {
	private EditText mName;
	private EditText mPasswd;
	private EditText mAgainPasswd;
	private Button mZhuce;
	private SharedPreferences sp;// 保存数据:键值对
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhuce);
		initView();
		initListener();
	}
	
	private void initListener() {
		mZhuce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = mName.getText().toString();
				String password = mPasswd.getText().toString();
				String againPasswd = mAgainPasswd.getText().toString();
				boolean empty_name = TextUtils.isEmpty(name);
				boolean empty_passwd = TextUtils.isEmpty(password);
				boolean empty_againPasswd = TextUtils.isEmpty(againPasswd);
				if(empty_name&&!empty_againPasswd&&!empty_passwd) {
					ToastUtil.showToast(ZhuceActivity.this, "用户名为空");
				}else if (!empty_name&&!empty_againPasswd&&empty_passwd) {
					ToastUtil.showToast(ZhuceActivity.this, "密码为空");
				}else if (!empty_name&&empty_againPasswd&&!empty_passwd) {
					ToastUtil.showToast(ZhuceActivity.this, "请再次输入密码");
				}else if(!empty_name&& !againPasswd.equals(password)) {
					ToastUtil.showToast(ZhuceActivity.this, "两次输入的密码不相同");
				}else if(!empty_name&&!empty_againPasswd&&!empty_passwd){
					String test_empty = sp.getString(name, "");
					if(TextUtils.isEmpty(test_empty)) {
						ToastUtil.showToast(ZhuceActivity.this, "注册成功");
						sp.edit().putString(mName.getText().toString(), mPasswd.getText().toString()).commit();
						Intent intent = new Intent(ZhuceActivity.this,MainActivity.class);
						startActivity(intent);
						finish();
					}else {
						ToastUtil.showToast(ZhuceActivity.this, "已经有重名用户，请重新输入");
						mName.setText("");
						mPasswd.setText("");
						mAgainPasswd.setText("");
					}
					
				}else if(empty_name&&empty_againPasswd&&empty_passwd) {
					ToastUtil.showToast(ZhuceActivity.this, "全部不能为空");
				}else if(!empty_name&&empty_againPasswd&&empty_passwd) {
					ToastUtil.showToast(ZhuceActivity.this, "两次密码不能为空");
				}else if(empty_name&&!empty_againPasswd&&empty_passwd) {
					ToastUtil.showToast(ZhuceActivity.this, "用户名和再次输入框不能为空");
				}else if(empty_name&&empty_againPasswd&&!empty_passwd) {
					ToastUtil.showToast(ZhuceActivity.this, "用户名和密码框不能为空");
				}
			}
		});
	}

	private void initView() {
		mName = (EditText) findViewById(R.id.zhuce_et_name);
		mPasswd = (EditText) findViewById(R.id.zhuce_et_passwd);
		mAgainPasswd = (EditText) findViewById(R.id.zhuce_et_againPasswd);
		mZhuce = (Button) findViewById(R.id.zhuce_bn_zhuce);
		sp = getSharedPreferences("sp_file", MODE_PRIVATE);
	}
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //调用双击退出函数  
	       }  
	    return false;  
	}  
	/** 
	 * 双击退出函数 
	 */  
	private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true; // 准备退出  
	        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();  
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // 取消退出  
	            }  
	        }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
	  
	    } else {  
	        finish();  
	        System.exit(0);  
	    }  
	}  
}
