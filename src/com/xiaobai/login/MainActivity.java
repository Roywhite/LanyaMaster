package com.xiaobai.login;

import java.util.Timer;
import java.util.TimerTask;

import com.xiaobai.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText mName;// 账号输入框
	private EditText mPasswd;// 密码输入框
	private CheckBox mRemember;// 记住密码选项
	private Button mLogin;// 登陆按钮
	private TextView mForget;// 忘记密码
	private TextView mZhuce;// 去注册
	private SharedPreferences sp;// 保存数据:键值对

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initListener();
	}

	private void initListener() {
		mLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = mName.getText().toString();
				String passwd = mPasswd.getText().toString();
				boolean Bname = TextUtils.isEmpty(name);
				boolean Bpasswd = TextUtils.isEmpty(passwd);
				if (Bname && !Bpasswd) {
					ToastUtil.showToast(MainActivity.this, "账号为空");
				} else if (Bpasswd && !Bname) {
					ToastUtil.showToast(MainActivity.this, "密码为空");
				} else if (Bname && Bpasswd) {
					ToastUtil.showToast(MainActivity.this, "账号和密码为空");
				} else {
					// 获取name下的密码
					String test_passwd = sp.getString(name, "");
					if (passwd.equals(test_passwd)) {
						if(sp.getBoolean("isRemember", false)==true) {
							sp.edit().putBoolean("isLogined", true).commit();
						}
						ToastUtil.showToast(MainActivity.this, "登陆成功");
						Intent intent = new Intent(MainActivity.this, MusicLineActivity.class);
						startActivity(intent);
						finish();
					} else {
						//当密码错误，清空勾选框时记住的密码，并且取消勾选，清空密码栏
						ToastUtil.showToast(MainActivity.this, "账号或密码错误");
						if (mRemember.isChecked() == true) {
							sp.edit().putBoolean("isRemember", false).commit();
							sp.edit().putString("name", "").commit();
							sp.edit().putString("passwd", "").commit();
							mRemember.setChecked(false);
							mPasswd.setText("");
						}
					}
				}
			}
		});

		mRemember.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				String name = mName.getText().toString();
				String passwd = mPasswd.getText().toString();
				boolean Bname = TextUtils.isEmpty(name);
				boolean Bpasswd = TextUtils.isEmpty(passwd);
				if (isChecked == true) {
					if (Bname || Bpasswd) {
						ToastUtil.showToast(MainActivity.this, "账号或密码为空，无法勾选");
						mRemember.setChecked(false);
					} else {
						sp.edit().putString("name", name).commit();
						sp.edit().putString("passwd", passwd).commit();
						sp.edit().putBoolean("isRemember", true).commit();
					}
				} else {
					sp.edit().putBoolean("isRemember", false).commit();
					sp.edit().putString("name", "").commit();
					sp.edit().putString("passwd", "").commit();
				}
			}
		});
		//设置功能：注册
		mZhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ZhuceActivity.class);

				startActivity(intent);
				finish();
			}
		});
		//设置功能：修改框中内容，自动取消勾选记住，并且忘记密码
		mName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (mRemember.isChecked() == true) {
					sp.edit().putBoolean("isRemember", false).commit();
					sp.edit().putString("name", "").commit();
					sp.edit().putString("passwd", "").commit();
					mRemember.setChecked(false);
				}
			}
		});
		
		//设置功能：修改框中内容，自动取消勾选记住，并且忘记密码
		mPasswd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (mRemember.isChecked() == true) {
					sp.edit().putBoolean("isRemember", false).commit();
					sp.edit().putString("name", "").commit();
					sp.edit().putString("passwd", "").commit();
					mRemember.setChecked(false);
				}
			}
		});
		mForget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ForgetActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private void initData() {
		sp = getSharedPreferences("sp_file", MODE_PRIVATE);
		boolean isRemember = sp.getBoolean("isRemember", false);
		boolean isLogined = sp.getBoolean("isLogined", false);
		if (isRemember == true) {
			String name = sp.getString("name", "");
			String passwd = sp.getString("passwd", "");
			mName.setText(name);
			mPasswd.setText(passwd);
			mRemember.setChecked(true);
		}
		if(isLogined==true) {
			Toast.makeText(this, "自动登陆成功！", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MainActivity.this,MusicPlayActivity.class);
			intent.putExtra("id", "1");
			startActivity(intent);
			finish();
		}
	}

	private void initView() {
		mName = (EditText) findViewById(R.id.main_et_name);
		mPasswd = (EditText) findViewById(R.id.main_et_passwd);
		mRemember = (CheckBox) findViewById(R.id.main_cb_remember);
		mLogin = (Button) findViewById(R.id.main_bn_login);
		mForget = (TextView) findViewById(R.id.main_tv_forgetpasswd);
		mZhuce = (TextView) findViewById(R.id.main_tv_zhuce);
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
