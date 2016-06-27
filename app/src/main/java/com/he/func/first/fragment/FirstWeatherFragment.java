package com.he.func.first.fragment;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.he.data.first.news.NewsBean;
import com.he.data.first.weather.WeatherBean;
import com.he.config.HeTask;
import com.he.config.KeyConfig;
import com.he.func.first.FirstAdapter;
import com.he.func.first.FirstPresenter;
import com.he.func.first.FirstView;
import com.he.func.first.weather.WeatherPresenter;
import com.he.func.first.weather.wea.WeatherView;
import com.lq.ren.news.R;

import java.util.ArrayList;
import java.util.List;

public class FirstWeatherFragment extends Fragment implements WeatherView,FirstView,
            SwipeRefreshLayout.OnRefreshListener{

    private View view;
    /**weather*/
    private TextView mCur;
    private ImageView mImage;
    private TextView mMax;
    private TextView mLower;
    private TextView mWeather;
    /**本地新闻*/
    private RecyclerView mRecyclerView;
    FirstPresenter mPresenter;
    WeatherPresenter mF3Presenter;
    private FirstAdapter mAdapter;
    private int pageIndex = 0;
    private List<NewsBean> mData;
    private LinearLayoutManager mLayoutManager;
    /** 刷新*/
    private SwipeRefreshLayout mSwipeRefresh;
    private ScrollView mScrollView;
    private View mRefreshLayout;

    private static FirstWeatherFragment instance;
    public static FirstWeatherFragment getInstance(){
        if(instance == null){
            instance = new FirstWeatherFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.he_first3, null);
        view.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeTask.getInstance().startFrist3Weather(getContext());
            }
        });
        mScrollView = (ScrollView) view.findViewById(R.id.frist3_scrollview);
        mScrollView.setOnTouchListener(onTouchListener);
        mRefreshLayout = view.findViewById(R.id.frist3_layout);
        mPresenter = new FirstPresenter(getContext(), this);
        mF3Presenter = new WeatherPresenter(getContext());
        WeatherPresenter.mView = this;
        initSwipRefresh();
        onRefresh();
        initWeatherView();
        initRecycleView();
        mF3Presenter.loadLocalData();
        return view;
    }

    private void initSwipRefresh(){
        mSwipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.frist3_swipe_refresh);
        mSwipeRefresh.setColorSchemeColors(R.color.color_gray, R.color.color_yellow, R.color.color_black,R.color.color_blue);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    private void initWeatherView(){
        mCur = (TextView) view.findViewById(R.id.frist3_city);
        mImage = (ImageView) view.findViewById(R.id.frist3_weatherImage);
        mMax = (TextView) view.findViewById(R.id.frist3_max);
        mLower = (TextView) view.findViewById(R.id.frist3_min);
        mWeather = (TextView) view.findViewById(R.id.frist3_weather);
        view.findViewById(R.id.frist3_switch_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeTask.getInstance().startFrist3City(getContext());
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initRecycleView(){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.frist3_recycle);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirstAdapter(getContext(), mPresenter);
        mAdapter.setOnItemClickListener(new FirstAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //TODO open newsDeital
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                HeTask.getInstance().startFristNewsDetail(getContext(), mAdapter.getItem(position));
            }
        });
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    float mLastY;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(event.getAction() == MotionEvent.ACTION_UP) {
                mLastY = mScrollView.getScrollY();//赋值给mLastY
                if (mLastY == (mRefreshLayout.getHeight() - mScrollView.getHeight())) {
                    //TODO 滑动到了底部
                    mPresenter.loadNews(2, pageIndex + KeyConfig.PAZE_SIZE);
                }
            }
            return false;
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                mPresenter.loadNews(2, pageIndex + KeyConfig.PAZE_SIZE);
            }
        }
    };

    @Override
    public void setTodayWeather(WeatherBean bean) {
        Log.i(KeyConfig.TAG_NAME, "bean: "+ bean.getImageRes() + "  T "+ bean.getTemperature()
                +"..." +bean.getWeather()+"."+bean.getWind());
        mCur.setText("实时天气  20");
        mImage.setImageResource(bean.getImageRes());
        mMax.setText(bean.getTemperature());
        mLower.setText(bean.getWeather());
        mWeather.setText(bean.getWind());
    }

    @Override
    public void setWeekWeather(List<WeatherBean> bean) {

    }

    @Override
    public void addNews(List<NewsBean> newsList) {
        if(mData == null)
            mData = new ArrayList<>();
        mData.addAll(newsList);
        if(pageIndex == 0) {
            mAdapter.setData(mData);
            mAdapter.setmIsLoading(true);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setmIsLoading(false);
            mAdapter.notifyDataSetChanged();
        }
        mSwipeRefresh.setRefreshing(false);
        pageIndex += KeyConfig.PAZE_SIZE;
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(mData != null) {
            mData.clear();
        }
        mSwipeRefresh.setRefreshing(true);
        mPresenter.loadNews(2, pageIndex);
    }
}
