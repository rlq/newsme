package com.he.func;


import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.he.func.favorite.FavoriteActivity;
import com.he.func.frist.FristActivity;
import com.he.func.me.AccountActivity;
import com.he.func.video.VideoActivity;
import com.lq.ren.newsme.R;

import java.util.ArrayList;


public class MainActivity extends ActivityGroup implements View.OnClickListener,
		ViewPager.OnPageChangeListener{

	private ViewPager mViewPager;

	private View main1;
	private View main2;
	private View main3;
	private View main4;
	private ImageView image1;
	private ImageView image2;
	private ImageView image3;
	private ImageView image4;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private ArrayList<View> pageViews;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.he_main);
		mViewPager = (ViewPager)findViewById(R.id.mian_viewPager);
		initSelectView();
		initViewPager();


mViewPager.setAdapter(new PagerAdapter() {
	@Override
	public int getCount() {
		return pageViews.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void destroyItem(View view, int id, Object arg2) {
		((ViewPager) view).removeView(pageViews.get(id));
	}

	// 获取每一个item的id
	@Override
	public Object instantiateItem(View view, int id) {
		((ViewPager) view).addView(pageViews.get(id));
		return pageViews.get(id);
	}
});
mViewPager.addOnPageChangeListener(this);
		mViewPager.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});

	}

	private void initSelectView(){
		main1 = findViewById(R.id.main1);
		main2 = findViewById(R.id.main2);
		main3 = findViewById(R.id.main3);
		main4 = findViewById(R.id.main4);
		image1 = (ImageView) findViewById(R.id.main_Image1);
		image2 = (ImageView) findViewById(R.id.main_Image2);
		image3 = (ImageView) findViewById(R.id.main_Image3);
		image4 = (ImageView) findViewById(R.id.main_Image4);
		text1 = (TextView) findViewById(R.id.main_text1);
		text2 = (TextView) findViewById(R.id.main_text2);
		text3 = (TextView) findViewById(R.id.main_text3);
		text4 = (TextView) findViewById(R.id.main_text4);
		main1.setOnClickListener(this);
		main2.setOnClickListener(this);
		main3.setOnClickListener(this);
		main4.setOnClickListener(this);
		image4.setOnClickListener(this);
		image1.setBackgroundResource(R.drawable.he_frist_pressed);
		text1.setTextColor(getResources().getColor(R.color.color_red));
	}

	private void initViewPager(){
		pageViews = new ArrayList<>();

		LayoutInflater layoutInflater = LayoutInflater.from(this);

//		View view1 = layoutInflater.inflate(R.layout.he_frist, null);
//		View view2 = layoutInflater.inflate(R.layout.he_video, null);
//		View view3 = layoutInflater.inflate(R.layout.he_favorite, null);
//		View view4 = layoutInflater.inflate(R.layout.he_account, null);

		View view1 = getLocalActivityManager().startActivity("frist",
				new Intent(this, FristActivity.class)).getDecorView();
		View view2 = getLocalActivityManager().startActivity("two",
				new Intent(this, VideoActivity.class)).getDecorView();
		View view3 = getLocalActivityManager().startActivity("three",
				new Intent(this, FavoriteActivity.class)).getDecorView();
		View view4 = getLocalActivityManager().startActivity("four",
				new Intent(this, AccountActivity.class)).getDecorView();
		pageViews.add(view1);
		pageViews.add(view2);
		pageViews.add(view3);
		pageViews.add(view4);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main1:
				mViewPager.setCurrentItem(0);
				break;
			case R.id.main2:
				mViewPager.setCurrentItem(1);
				break;
			case R.id.main3:
				mViewPager.setCurrentItem(2);
				break;
			case R.id.main4:
			case R.id.main_Image4:
				mViewPager.setCurrentItem(3);
				break;
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int i) {
		unSelected();
		switch (i) {
			case 0:
				image1.setBackgroundResource(R.drawable.he_frist_pressed);
				setColor(text1, true);
				break;
			case 1:
				image2.setBackgroundResource(R.drawable.he_setting_pressed);
				setColor(text2, true);
				break;
			case 2:
				image3.setBackgroundResource(R.drawable.he_favorite_pressed);
				setColor(text3, true);
				break;
			case 3:
				image4.setBackgroundResource(R.drawable.he_account_pressed);
				setColor(text4, true);
				break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	private void unSelected() {
		image1.setBackgroundResource(R.drawable.he_frist);
		image2.setBackgroundResource(R.drawable.he_setting_normal);
		image3.setBackgroundResource(R.drawable.he_favorite_normal);
		image4.setBackgroundResource(R.drawable.he_account_normal);
		setColor(text1, false);
		setColor(text2, false);
		setColor(text3, false);
		setColor(text4, false);
	}

	private void setColor(TextView text, Boolean isSelected){
		text.setTextColor(isSelected?getResources().getColor(R.color.color_red):
			getResources().getColor(R.color.chat_text_bg));
	}

	
}
