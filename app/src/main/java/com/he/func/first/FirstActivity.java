package com.he.func.first;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.he.config.HeTask;
import com.he.data.first.weather.ChannelBean;
import com.he.func.first.channel.ChannelPresenter;
import com.he.func.first.fragment.FirstNewsFragment;
import com.he.func.first.fragment.FirstWeatherFragment;
import com.he.util.Utils;
import com.lq.ren.news.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.widget.TextView;

public class FirstActivity extends FragmentActivity implements FirstView.FristHeadView{

    /**ViewPager*/
    private ViewPager viewPager;
    private FristAdapter mAdapter;

    /** 自定义HorizontalScrollView headlist  */
    private FirstHeadScrollView headScrollView;
    private LinearLayout mRadiolayout;
    private LinearLayout mAddLayout;

    private List<ChannelBean> mChannelBeanList;
    private ChannelPresenter mChannelPresenter;
    private int headListCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_first);
        FirstPresenter.headView = this;
        mChannelPresenter = new ChannelPresenter(this);
        initHeadList();
        initViewpager();
    }

    private void initHeadList(){
        headScrollView =  (FirstHeadScrollView)findViewById(R.id.mColumnHorizontalScrollView);
        mRadiolayout = (LinearLayout) findViewById(R.id.mRadioGroup_content);
        mAddLayout = (LinearLayout) findViewById(R.id.ll_more_columns);
        mAddLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HeTask.getInstance().startChannelActivity(FirstActivity.this);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        initHeadData();
    }

    /** 屏幕宽度 */
    private int mScreenWidth = 0;
    /** 选中的Item*/
    private int selectedIndex = 0;

    private void initHeadData(){
        mChannelBeanList = new ArrayList<>();
        mChannelBeanList = mChannelPresenter.getMyChannel();
        headListCount = mChannelBeanList.size();
        mRadiolayout.removeAllViews();
        mScreenWidth = Utils.getScreenSizeWidth(this);
        headScrollView.setParam(this, mScreenWidth, mRadiolayout);
        setHeadList();

    }

    private void setHeadList(){
        for (int i = 0; i < headListCount; i++) {
            View itemView = View.inflate(this,R.layout.he_first_head, null);
            itemView.setId(i);
            TextView headItemtv = (TextView) itemView.findViewById(R.id.headItemtv);
            headItemtv.setText(mChannelBeanList.get(i).getName());
            if(selectedIndex == i){
                headItemtv.setTextColor(getResources().getColor(R.color.color_white));
                headItemtv.setSelected(true);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(v.getId());
                }
            });
            mRadiolayout.addView(itemView, i);
        }
    }

    private void initViewpager(){
        viewPager = (ViewPager)findViewById(R.id.frist_viewPager);
        setViewPagerList();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSelected(int pos){
        selectedIndex = pos;
        for (int i = 0; i < mRadiolayout.getChildCount(); i++) {
            View loacal = mRadiolayout.getChildAt(i);
            TextView tv= (TextView) loacal.findViewById(R.id.headItemtv);//如果setId(i) 则需要通过i找到
            if(pos !=  i){
                tv.setSelected(false);
                tv.setTextColor(getResources().getColor(R.color.color_hint));
            }else{
                tv.setSelected(true);
                viewPager.setCurrentItem(i);
                tv.setTextColor(getResources().getColor(R.color.color_white));
                if(!isChildVisible(tv)) {
                    int width = loacal.getMeasuredWidth();
                    int left = loacal.getLeft();
                    int toPos = left + width /2 ;//- mScreenWidth / 2;
                    headScrollView.smoothScrollTo(left, 0);
                }
            }
        }
    }

    public boolean isChildVisible(View child){
        if(child==null){
            return false;
        }
        Rect scrollBounds = new Rect();
        child.getHitRect(scrollBounds);
        scrollBounds.right += 10;
        return child.getLocalVisibleRect(scrollBounds);
    }

    private void setViewPagerList(){
        mAdapter = new FristAdapter(getSupportFragmentManager());
        mAdapter.addFragment(FirstNewsFragment.getInstance(0), "1");
        mAdapter.addFragment(FirstNewsFragment.getInstance(2), "2");
        mAdapter.addFragment(FirstWeatherFragment.getInstance(), "3");
        mAdapter.addFragment(FirstNewsFragment.getInstance(3), "4");
        mAdapter.addFragment(FirstNewsFragment.getInstance(0), "5");
        mAdapter.addFragment(FirstNewsFragment.getInstance(2), "6");
        mAdapter.addFragment(FirstNewsFragment.getInstance(0), "7");

        viewPager.setAdapter(mAdapter);
    }

    public void addFragnent(int index){
        mAdapter.addFragment(FirstNewsFragment.getInstance(new Random().nextInt(3)), ""+index);
    }

    public void removeFragment(int index){
        mAdapter.removeFragment(index);
    }


    @Override
    public void setCurrentItem(int i) {
        viewPager.setCurrentItem(i);
    }

    @Override
    public void setUserData(List<ChannelBean> beanList) {
        int count = 0;
        if(headListCount < beanList.size()){
            count = beanList.size() - headListCount;
            for (int i = 0; i < count; i++) {
                addFragnent(headListCount+i);
            }
        }else{
            count = headListCount - beanList.size();
            for (int i = 0; i < count; i++) {
                removeFragment(headListCount-1-i);
            }
        }
        mChannelBeanList = beanList;
        headListCount = beanList.size();
        mRadiolayout.removeAllViews();
        setHeadList();
        mAdapter.notifyDataSetChanged();
    }

    public class FristAdapter extends FragmentPagerAdapter{
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        public FristAdapter(FragmentManager manager){
            super(manager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            mFragmentTitles.add(title);
        }

        public void removeFragment(int index){
            fragmentList.remove(index);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
