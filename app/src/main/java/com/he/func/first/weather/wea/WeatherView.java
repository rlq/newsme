package com.he.func.first.weather.wea;

import com.he.data.first.weather.WeatherBean;

import java.util.List;

public interface WeatherView {

    void setTodayWeather(WeatherBean bean);

    void setWeekWeather(List<WeatherBean> bean);

}