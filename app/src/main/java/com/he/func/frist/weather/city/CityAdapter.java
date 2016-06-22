package com.he.func.frist.weather.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.he.data.frist.weather.CityBean;
import com.lq.ren.news.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**  城市 name + pinyin 首字母  以及滑动处理*/
public class CityAdapter extends BaseAdapter implements SectionIndexer,
		CityHeadListView.HeaderAdapter, OnScrollListener {
	private Context context;
	private ArrayList<CityBean> cityList;
	private LayoutInflater inflater = null;
	private List<Integer> mPositions;
	private List<String> mSections;

	public CityAdapter(Context context, ArrayList<CityBean> cityList) {
		this.context = context;
		this.cityList = cityList;
		inflater = LayoutInflater.from(context);
		initDateHead();
	}

	/** 获取头部head标签数据 */
	private void initDateHead() {
		mSections = new ArrayList<>();
		mPositions = new ArrayList<>();
		for (int i = 0; i < cityList.size(); i++) {
			if (i == 0) {
				mSections.add(String.valueOf(cityList.get(i).getPinyin()));
				mPositions.add(i);
				continue;
			}
			if (cityList.get(i).getPinyin() != cityList.get(i - 1).getPinyin()) {
				mSections.add(String.valueOf(cityList.get(i).getPinyin()));
				mPositions.add(i);
			}
		}
	}

	@Override
	public int getCount() {
		return cityList == null ? 0 : cityList.size();
	}

	@Override
	public CityBean getItem(int position) {
		if (cityList != null && cityList.size() != 0) {
			return cityList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.he_frist3_city_item, null);
			mHolder = new ViewHolder();
			mHolder.city_name = (TextView) convertView.findViewById(R.id.city_name);
			//header
			mHolder.layout_city_section = (LinearLayout) convertView.findViewById(R.id.layout_city_section);
			mHolder.city_item_section_text = (TextView) convertView.findViewById(R.id.city_item_section_text);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		CityBean city = getItem(position);
		mHolder.city_name.setText(city.getName());
		//头部的相关东西
		int section = getSectionForPosition(position);
		if (getPositionForSection(section) == position) {
			mHolder.layout_city_section.setVisibility(View.VISIBLE);
			mHolder.city_item_section_text.setText(mSections.get(section));
		} else {
			mHolder.layout_city_section.setVisibility(View.GONE);
		}
		return convertView;
	}

	@Override
	public int getHeaderState(int position) {
		// TODO Auto-generated method stub
		int realPosition = position;
		if (realPosition < 0 || position >= getCount()) {
			return HEADER_GONE;
		}
		int section = getSectionForPosition(realPosition);
		int nextSectionPosition = getPositionForSection(section + 1);
		if (nextSectionPosition != -1
				&& realPosition == nextSectionPosition - 1) {
			return HEADER_PUSHED_UP;
		}
		return HEADER_VISIBLE;
	}

	@Override
	public void configureHeader(View header, int position, int alpha) {
		int realPosition = position;
		int section = getSectionForPosition(realPosition);
		String title = (String) getSections()[section];
		((TextView) header.findViewById(R.id.city_item_section_text)).setText(title);
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return mSections.toArray();
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		if (sectionIndex < 0 || sectionIndex >= mPositions.size()) {
			return -1;
		}
		return mPositions.get(sectionIndex);
	}

	@Override
	public int getSectionForPosition(int position) {
		if (position < 0 || position >= getCount()) {
			return -1;
		}
		int index = Arrays.binarySearch(mPositions.toArray(), position);
		return index >= 0 ? index : -index - 2;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
		if (view instanceof CityHeadListView) {
			((CityHeadListView) view).configureHeaderView(firstVisibleItem);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
	}

	static class ViewHolder {
		TextView city_name;
		//header
		LinearLayout layout_city_section;
		TextView city_item_section_text;
	}

}
