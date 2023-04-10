package com.xiaobai.login;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.xiaobai.adpter.MyAdapter;
import com.xiaobai.bean.Music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MusicLineActivity extends Activity {
	private ListView mMusicList;
	private List<String> music;
	private boolean isPlay;
	private int id=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_music_line);
		initView();
		initData();
		initListener();
		
	}
	private void initListener() {
		mMusicList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MusicLineActivity.this,MusicPlayActivity.class);
				MyAdapter adapter = new MyAdapter(music, MusicLineActivity.this);
				if("过火--张信哲".equals(adapter.getItem(position).toString())) {
					id=1;
				}else if("纸短情长--烟把儿".equals(adapter.getItem(position).toString())) {
					id=2;
				}else if("振翅高飞--未知歌手".equals(adapter.getItem(position).toString())) {
					id=3;
				}
				
				intent.putExtra("id", String.valueOf(id));
				startActivity(intent);
				finish();
			}
		});
	}
	private void initData() {
		Music list_music = new Music();
		music = list_music.getName();
		mMusicList.setAdapter(new MyAdapter(music, this));
	}
	private void initView() {
		mMusicList = (ListView) findViewById(R.id.music_lv_music_list);
		
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
