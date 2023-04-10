package com.xiaobai.login;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ForgetActivity extends Activity {
	private EditText mName;
	private Button mGoForget;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forget);
		initView();
		initData();
		initListener();
	}
	private void initListener() {
		mGoForget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = mName.getText().toString();
				String passwd = sp.getString(name, "");
				if(TextUtils.isEmpty(passwd)) {
					new AlertDialog.Builder(ForgetActivity.this).setTitle("密码提示").setMessage("请确认输入的用户名已注册").show();
				}else {
					new AlertDialog.Builder(ForgetActivity.this).setTitle("密码提示").setMessage("密码为:"+passwd).setPositiveButton("确定",new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(ForgetActivity.this, MainActivity.class);
							startActivity(intent);
							finish();
						}
					}).show();
				}
			}
		});
	}
	private void initData() {
		sp = getSharedPreferences("sp_file", MODE_PRIVATE);
	}
	private void initView() {
		mName = (EditText) findViewById(R.id.forget_et_name);
		mGoForget = (Button) findViewById(R.id.forget_bn_forget);
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
