package com.he.func.frist.channel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.he.data.frist.weather.ChannelBean;
import com.lq.ren.news.R;

import java.util.List;

public class MoreAdapter extends BaseAdapter {
	private Context context;
	public List<ChannelBean> mChannelList;
	private TextView mItemText;
	/** 是否可见 */
	boolean isVisible = true;
	/** 要删除的position */
	public int remove_position = -1;

	public MoreAdapter(Context context, List<ChannelBean> channelList) {
		this.context = context;
		this.mChannelList = channelList;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
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
		mItemText = (TextView) view.findViewById(R.id.text_item);
		ChannelBean channel = getItem(position);

		mItemText.setText(channel.getName());
		if (!isVisible && (position == mChannelList.size()-1)){
			mItemText.setText("");
		}
		if(remove_position == position){
			mItemText.setText("");
		}
		return view;
	}
	
	public List<ChannelBean> getChannnelList() {
		return mChannelList;
	}
	
	public void addItem(ChannelBean channel) {
		mChannelList.add(channel);
		notifyDataSetChanged();
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	public void remove() {
		mChannelList.remove(remove_position);
		remove_position = -1;
		notifyDataSetChanged();
	}

}