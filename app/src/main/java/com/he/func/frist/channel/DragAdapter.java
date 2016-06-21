package com.he.func.frist.channel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.he.data.frist.weather.ChannelBean;
import com.he.config.KeyConfig;
import com.lq.ren.newsme.R;

import java.util.List;


public class DragAdapter extends BaseAdapter {

	/** 是否显示底部的ITEM */
	private boolean mItemShow = false;
	private Context context;
	/** 控制的postion */
	private int mHoldPosition;
	private boolean mIsChanged = false;
	private boolean mIsListChanged = false;
	private boolean isVisible = true;
	/** 可以拖动的列表（即用户选择的频道列表） */
	public List<ChannelBean> mChannelList;
	private TextView item_text;
	public int remove_position = -1;

	public DragAdapter(Context context, List<ChannelBean> channelList) {
		this.context = context;
		this.mChannelList = channelList;
	}

	private boolean notNull(){
		return mChannelList != null && mChannelList.size() > 0;
	}

	@Override
	public int getCount() {
		return notNull() ? mChannelList.size():0;
	}

	@Override
	public ChannelBean getItem(int position) {
		return notNull()? mChannelList.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.he_channel_item, null);
		item_text = (TextView) view.findViewById(R.id.text_item);
		ChannelBean channel = getItem(position);
		item_text.setText(channel.getName());
		if (position == 0 || position == 1  || position == 2){
//			item_text.setTextColor(context.getResources().getColor(R.color.black));
			item_text.setEnabled(false);
		}
		if (mIsChanged && (position == mHoldPosition) && !mItemShow) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
			mIsChanged = false;
		}
		if (!isVisible && (position == mChannelList.size()- 1)) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
		}
		if(remove_position == position){
			item_text.setText("");
		}
		return view;
	}

	/** 添加频道列表 */
	public void addItem(ChannelBean channel) {
		mChannelList.add(channel);
		mIsListChanged = true;
		notifyDataSetChanged();
	}

	/** 拖动变更频道排序 */
	public void exchange(int dragPostion, int dropPostion) {
		mHoldPosition = dropPostion;
		ChannelBean dragItem = getItem(dragPostion);
		Log.d(KeyConfig.TAG_NAME, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
		if (dragPostion < dropPostion) {
			mChannelList.add(dropPostion + 1, dragItem);
			mChannelList.remove(dragPostion);
		} else {
			mChannelList.add(dropPostion, dragItem);
			mChannelList.remove(dragPostion + 1);
		}
		mIsChanged = true;
		mIsListChanged = true;
		notifyDataSetChanged();
	}
	
	public List<ChannelBean> getChannnelList() {
		return mChannelList;
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	public void remove() {
		mChannelList.remove(remove_position);
		remove_position = -1;
		mIsListChanged = true;
		notifyDataSetChanged();
	}
	
	public void setListDate(List<ChannelBean> list) {
		mChannelList = list;
	}

	/** 排序是否发生改变 */
	public boolean ismIsListChanged() {
		return mIsListChanged;
	}
	
	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		mItemShow = show;
	}
}