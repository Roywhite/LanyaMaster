package com.xiaobai.login;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.xiaobai.bean.Music;
import com.xiaobai.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayActivity extends Activity {

	private ImageButton mStart;
	private ImageButton mNext;
	private ImageButton mPrevious;
	private MediaPlayer mPlay;// 播放音乐的类
	private TextView mName;
	private Intent intent;
	private boolean isPlay;
	private String id;
	private String now_id;
	private SharedPreferences sp;// 保存数据:键值对

	public void start(View v) {
		if (mPlay.isPlaying() == false) {
			if (TextUtils.isEmpty(now_id) == true) {
				id = intent.getStringExtra("id");
			} else {
				id = now_id;
			}
			// 获取assets目录
			AssetManager am = this.getAssets();
			try {
				AssetFileDescriptor afd = null;
				if ("1".equals(id)) {
					// 得到文件
					afd = am.openFd("guohuo.mp3");
				} else if ("2".equals(id)) {
					// 得到文件
					afd = am.openFd("zhiduanqingchang.mp3");
				} else if ("3".equals(id)) {
					// 得到文件
					afd = am.openFd("butterfly.mp3");
				}
				// 装载资源
				mPlay.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
				// 正式加载资源
				mPlay.prepare();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 开始播放
			Music music = new Music();
			mName.setText(music.getName().get(Integer.parseInt(id) - 1));
			mPlay.start();
			mStart.setBackgroundResource(R.drawable.pause);
		} else {
			mPlay.pause();
			mStart.setBackgroundResource(R.drawable.play);
		}
	}

	public void previous(View v) {

		if (TextUtils.isEmpty(now_id) == true) {
			id = intent.getStringExtra("id");
		} else {
			id = now_id;
		}
		// 获取assets目录
		now_id = String.valueOf((Integer.parseInt(id) - 1));
		AssetManager am = this.getAssets();
		try {
			AssetFileDescriptor afd = null;
			if ("1".equals(now_id)) {
				mPlay.reset();
				// 得到文件
				afd = am.openFd("guohuo.mp3");
			} else if ("2".equals(now_id)) {
				mPlay.reset();
				// 得到文件
				afd = am.openFd("zhiduanqingchang.mp3");
			} else if ("3".equals(now_id)) {
				mPlay.reset();
				// 得到文件
				afd = am.openFd("butterfly.mp3");
			} else if ("0".equals(now_id)) {
				ToastUtil.showToast(MusicPlayActivity.this, "无上一首");
				afd = am.openFd("guohuo.mp3");
				now_id = "1";
			}
			// 装载资源
			mPlay.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
			// 正式加载资源
			mPlay.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 开始播放
		mPlay.start();
		Music music = new Music();
		mName.setText(music.getName().get(Integer.parseInt(now_id) - 1));
		mStart.setBackgroundResource(R.drawable.pause);
	}

	public void next(View v) {
		if (TextUtils.isEmpty(now_id) == true) {
			id = intent.getStringExtra("id");
		} else {
			id = now_id;
		}
		// 获取assets目录
		now_id = String.valueOf((Integer.parseInt(id) + 1));
		AssetManager am = this.getAssets();
		try {
			AssetFileDescriptor afd = null;
			if ("1".equals(now_id)) {
				// 得到文件
				mPlay.reset();
				afd = am.openFd("guohuo.mp3");
			} else if ("2".equals(now_id)) {
				// 得到文件
				mPlay.reset();
				afd = am.openFd("zhiduanqingchang.mp3");
			} else if ("3".equals(now_id)) {
				// 得到文件
				mPlay.reset();
				afd = am.openFd("butterfly.mp3");
			} else if ("4".equals(now_id)) {
				ToastUtil.showToast(MusicPlayActivity.this, "无下一首");
				afd = am.openFd("butterfly.mp3");
				now_id = "3";
			}
			// 装载资源
			mPlay.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
			// 正式加载资源
			mPlay.prepare();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 开始播放
		mPlay.start();
		Music music = new Music();
		mName.setText(music.getName().get(Integer.parseInt(now_id) - 1));
		mStart.setBackgroundResource(R.drawable.pause);

	}

	public void loginout(View v) {
		mPlay.release();
		sp.edit().putBoolean("isLogined", false).commit();
		Intent intent = new Intent(MusicPlayActivity.this, MainActivity.class);
		startActivity(intent);
		Toast.makeText(this, "退出登录成功", Toast.LENGTH_SHORT).show();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_music_play);
		mPlay = new MediaPlayer();
		intent = this.getIntent();
		initView();
		initData();
		initListener();
	}

	private void initListener() {
		mNext.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mNext.setBackgroundResource(R.drawable.next_right);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					mNext.setBackgroundResource(R.drawable.next);
				}
				return false;
			}

		});

		mPrevious.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mPrevious.setBackgroundResource(R.drawable.previous_left);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					mPrevious.setBackgroundResource(R.drawable.previous);
				}
				return false;
			}

		});

	}

	private void initData() {
		sp = getSharedPreferences("sp_file", MODE_PRIVATE);
		/*
		 * if(mPlay.isPlaying()==false) { mStart.setBackgroundResource(R.drawable.play);
		 * }
		 */
	}

	private void initView() {
		mStart = (ImageButton) findViewById(R.id.music_play_btn_start);
		mName = (TextView) findViewById(R.id.music_play_tv_name);
		mNext = (ImageButton) findViewById(R.id.music_play_ib_next);
		mPrevious = (ImageButton) findViewById(R.id.music_play_ib_previous);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
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
