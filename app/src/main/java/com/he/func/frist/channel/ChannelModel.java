package com.he.func.frist.channel;

import android.database.SQLException;

import com.he.data.db.DBHelper;
import com.he.data.frist.weather.ChannelBean;
import com.he.config.KeyConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChannelModel {

	public static final String TABLE_CHANNEL = "channel";//数据表

	public static final String ID = "id";//
	public static final String NAME = "name";
	public static final String ORDERID = "orderId";
	public static final String SELECTED = "selected";

	public static List<ChannelBean> defaultMyChannels;
	public static List<ChannelBean> defaultOtherChannels;

	DBHelper helper;
	/** 判断数据库中是否存在用户数据 */
	private boolean isHasMyChannel = false;

	static {
		defaultMyChannels = new ArrayList<>();
		defaultMyChannels.add(new ChannelBean(1, "推荐", 1, 1));
		defaultMyChannels.add(new ChannelBean(2, "热点", 2, 1));
		defaultMyChannels.add(new ChannelBean(3, "北京", 3, 1));
		defaultMyChannels.add(new ChannelBean(4, "爆笑", 4, 1));
		defaultMyChannels.add(new ChannelBean(5, "头条", 5, 1));
		defaultMyChannels.add(new ChannelBean(6, "体育", 6, 1));
		defaultMyChannels.add(new ChannelBean(7, "军事", 7, 1));

		defaultOtherChannels = new ArrayList<>();
		defaultOtherChannels.add(new ChannelBean(8, "财经", 1, 0));
		defaultOtherChannels.add(new ChannelBean(9, "汽车", 2, 0));
		defaultOtherChannels.add(new ChannelBean(10, "房产", 3, 0));
		defaultOtherChannels.add(new ChannelBean(11, "时事", 4, 0));
		defaultOtherChannels.add(new ChannelBean(12, "情感", 5, 0));
		defaultOtherChannels.add(new ChannelBean(13, "设计", 6, 0));
		defaultOtherChannels.add(new ChannelBean(14, "旅游", 7, 0));
		defaultOtherChannels.add(new ChannelBean(15, "健康", 8, 0));
		defaultOtherChannels.add(new ChannelBean(16, "明星", 9, 0));
		defaultOtherChannels.add(new ChannelBean(17, "游戏", 10, 0));
		defaultOtherChannels.add(new ChannelBean(18, "数码", 11, 0));
		defaultOtherChannels.add(new ChannelBean(19, "娱乐", 12, 0));
		defaultOtherChannels.add(new ChannelBean(20, "股市", 13, 0));
		defaultOtherChannels.add(new ChannelBean(21, "国际", 14, 0));
		defaultOtherChannels.add(new ChannelBean(22, "音乐", 15, 0));
		defaultOtherChannels.add(new ChannelBean(23, " IT", 16, 0));
		defaultOtherChannels.add(new ChannelBean(24, "健身", 17, 0));
	}

	public ChannelModel(DBHelper dbHelper) throws SQLException {
		this.helper = dbHelper;
	}

	/**
	 * 初始化数据库内的频道数据
	 */
	public void initDefaultChannel(){
		deleteAllChannel();
		saveMyChannel(defaultMyChannels);
		saveOtherChannel(defaultOtherChannels);
	}

	public void deleteAllChannel() {
		helper.clearFeedTable();
	}

	/**
	 * 获取我的频道
	 * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
	 */
	public List<ChannelBean> getUserChannel() {
		Object myChannelList = helper.getListData(SELECTED + "= ?",new String[] { "1" });
		if (myChannelList != null && !((List) myChannelList).isEmpty()) {
			isHasMyChannel = true;
			List<Map<String, String>> maplist = (List) myChannelList;
			int count = maplist.size();
			List<ChannelBean> list = new ArrayList<ChannelBean>();
			for (int i = 0; i < count; i++) {
				ChannelBean navigate = new ChannelBean();
				navigate.setId(Integer.valueOf(maplist.get(i).get(ID)));
				navigate.setName(maplist.get(i).get(NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		initDefaultChannel();
		return defaultMyChannels;
	}
	
	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
	 */
	public List<ChannelBean> getOtherChannel() {
		Object otherChannelList = helper.getListData(SELECTED + "= ?" ,new String[] { "0" });
		List<ChannelBean> list = new ArrayList<ChannelBean>();
		if (otherChannelList != null && !((List) otherChannelList).isEmpty()){
			List<Map<String, String>> maplist = (List) otherChannelList;
			int count = maplist.size();
			for (int i = 0; i < count; i++) {
				ChannelBean navigate= new ChannelBean();
				navigate.setId(Integer.valueOf(maplist.get(i).get(ID)));
				navigate.setName(maplist.get(i).get(NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		if(isHasMyChannel){
			return list;
		}
		otherChannelList = defaultOtherChannels;
		return (List<ChannelBean>) otherChannelList;
	}
	
	/**
	 * 保存用户频道到数据库
	 * @param myList
	 */
	public void saveMyChannel(List<ChannelBean> myList) {
		for (int i = 0; i < myList.size(); i++) {
			ChannelBean channelItem =  myList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(1));
			helper.addData(channelItem);
		}
	}
	
	/**
	 * 保存其他频道到数据库
	 * @param otherList
	 */
	public void saveOtherChannel(List<ChannelBean> otherList) {
		for (int i = 0; i < otherList.size(); i++) {
			ChannelBean channelItem =  otherList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(0));
			helper.addData(channelItem);
		}
	}

}
