package com.xiaobai.bean;

import java.util.ArrayList;
import java.util.List;

public class Music {
	private List<String> name = new ArrayList<String>();

	public List<String> getName() {
		name.add("过火--张信哲");
		name.add("纸短情长--烟把儿");
		name.add("振翅高飞--未知歌手");
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}
	
	
}
