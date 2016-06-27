package com.he.func.first.weather.city;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.he.data.first.weather.CityBean;
import com.he.func.first.weather.WeatherPresenter;
import com.lq.ren.news.R;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**切换城市  */
public class CityActivity extends SwipeBackActivity{

    private TextView mTitle;
    private CityHeadListView mListView;
    private ArrayList<CityBean> cityList;
    private CityAdapter mAdapter;
    private WeatherPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_first3_city);

        SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(10);//Utils.getScreenSizeWidth(this)/4
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        initView();
        initData();
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.backTV);
        mListView = (CityHeadListView)findViewById(R.id.cityListView);
        mPresenter = new WeatherPresenter(this);
        cityList = mPresenter.getCityList();
        findViewById(R.id.backBar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        mTitle.setText(getString(R.string.first3_curCity)+ WeatherPresenter.cityName);

        mAdapter = new CityAdapter(this, cityList);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(mAdapter);
        mListView.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.he_first3_city_item_section, mListView, false));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mTitle.setText(getString(R.string.first3_curCity)+ mPresenter.getCityList().get(position).getName());
                WeatherPresenter.cityName = mPresenter.getCityList().get(position).getName();
                mPresenter.loadWeatherData(WeatherPresenter.cityName);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        });
    }
}
