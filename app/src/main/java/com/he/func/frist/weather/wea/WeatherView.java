package com.he.func.frist.weather.wea;

import com.he.data.frist.weather.WeatherBean;

import java.util.List;

public interface WeatherView {

    void setTodayWeather(WeatherBean bean);

    void setWeekWeather(List<WeatherBean> bean);

}