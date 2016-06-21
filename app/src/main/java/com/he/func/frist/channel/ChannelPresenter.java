package com.he.func.frist.channel;

import android.content.Context;
import android.database.SQLException;

import com.he.data.db.DBHelper;
import com.he.data.frist.weather.ChannelBean;

import java.util.List;

public class ChannelPresenter {
	private ChannelModel channelModel;

	public ChannelPresenter(Context context) throws SQLException {
		this.channelModel = new ChannelModel(DBHelper.getInstance(context));
	}

	/**
	 * 获取我的频道
	 */
	public List<ChannelBean> getMyChannel() {
		return channelModel.getUserChannel();
	}
	
	/**
	 * 获取其他的频道
	 */
	public List<ChannelBean> getOtherChannel() {
		return channelModel.getOtherChannel();
	}

	/**
	 * 保存频道到数据库
	 */
	public void resetChannelData(List<ChannelBean> myList,List<ChannelBean> otherList){
		channelModel.deleteAllChannel();
		channelModel.saveMyChannel(myList);
		channelModel.saveOtherChannel(otherList);
	}
}
