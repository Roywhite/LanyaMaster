package com.xiaobai.adpter;

import java.util.List;

import com.xiaobai.login.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

	private List<String> music;
	private Context context;//上下文信息
	
	public MyAdapter(List<String> music,Context context) {
		this.music = music;
		this.context = context;
	}
	
	//获得数量
	@Override
	public int getCount() {
		return music.size();
	}

	
	@Override
	public Object getItem(int position) {
		return music.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;//缓存区
		
		if(convertView==null) {
			//视图为空
			convertView = View.inflate(context, R.layout.music_list, null);//将xml文件转换为对象,null为默认
			holder = new ViewHolder();
			holder.mMusic_name = (TextView) convertView.findViewById(R.id.music_tv_name);
			convertView.setTag(holder);//做标记
		}else {
			//视图不为空，从缓存中取视图
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.mMusic_name.setText(music.get(position));
		return convertView;
	}
	//ListView的优化--缓存
	static class ViewHolder{
		public TextView mMusic_name;
	}
}
