package com.he.func.frist.weather.wea;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.he.data.frist.weather.WeatherBean;
import com.he.config.HeTask;
import com.he.func.frist.weather.WeatherPresenter;
import com.lq.ren.newsme.R;

import java.util.Calendar;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class WeatherActivity extends SwipeBackActivity implements WeatherView {

    private TextView mTodayTV;
    private ImageView mTodayWeatherImage;
    private TextView mTodayTemperatureTV;
    private TextView mTodayWindTV;
    private TextView mTodayWeatherTV;
    private TextView mCityTV;
    private LinearLayout mWeatherContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_frist3_weather);
        mTodayTV = (TextView) findViewById(R.id.today);
        mTodayWeatherImage = (ImageView) findViewById(R.id.weatherImage);
        mTodayTemperatureTV = (TextView) findViewById(R.id.weatherTemp);
        mTodayWindTV = (TextView) findViewById(R.id.wind);
        mTodayWeatherTV = (TextView) findViewById(R.id.weather);
        mCityTV = (TextView)findViewById(R.id.city);
        mWeatherContentLayout = (LinearLayout) findViewById(R.id.weather_content);

        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        /** 设置可以滑动的区域，推荐用屏幕像素的一半来指定*/
        swipeBackLayout.setEdgeSize(10);//Utils.getScreenSizeWidth(this)/4
        /**设定滑动关闭的方向，SwipeBackLayout.EDGE_ALL表示向下、左、右滑动均可。EDGE_LEFT，EDGE_RIGHT，EDGE_BOTTOM*/
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM);

        WeatherPresenter mPresenter = new WeatherPresenter(this);
        final Context context = this;
        WeatherPresenter.mView = this;
        mPresenter.loadLocalData();

        findViewById(R.id.frist3_switch_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeTask.getInstance().startFrist3City(context);
            }
        });
        findViewById(R.id.frist3_back_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setTodayWeather(WeatherBean bean) {
        mCityTV.setText(WeatherPresenter.cityName);
        Calendar c = Calendar.getInstance();
        String month = "" + (c.get(Calendar.MONTH)+1) +"月";
        mTodayTV.setText(month + bean.getDate());
        mTodayTemperatureTV.setText(bean.getTemperature());
        mTodayWindTV.setText(bean.getWind());
        mTodayWeatherTV.setText(bean.getWeather());
        mTodayWeatherImage.setImageResource(bean.getImageRes());
    }

    @Override
    public void setWeekWeather(List<WeatherBean> bean) {
        mWeatherContentLayout.removeAllViews();
        for (WeatherBean weatherBean : bean) {
            View view = LayoutInflater.from(this).inflate(R.layout.he_frist3_item_weather, null, false);
            TextView dateTV = (TextView) view.findViewById(R.id.date);
            ImageView todayWeatherImage = (ImageView) view.findViewById(R.id.weatherImage);
            TextView todayTemperatureTV = (TextView) view.findViewById(R.id.weatherTemp);
            TextView todayWindTV = (TextView) view.findViewById(R.id.wind);
            TextView todayWeatherTV = (TextView) view.findViewById(R.id.weather);

            dateTV.setText(weatherBean.getWeek());
            todayTemperatureTV.setText(weatherBean.getTemperature());
            todayWindTV.setText(weatherBean.getWind());
            todayWeatherTV.setText(weatherBean.getWeather());
            todayWeatherImage.setImageResource(weatherBean.getImageRes());
            mWeatherContentLayout.addView(view);
        }
    }

}
