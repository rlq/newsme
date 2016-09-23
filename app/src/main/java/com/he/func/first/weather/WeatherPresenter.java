package com.he.func.first.weather;

import android.content.Context;

import com.he.data.first.weather.CityBean;
import com.he.data.first.weather.WeatherBean;
import com.he.data.first.weather.WeatherJsonUtils;
import com.he.base.HeCallbackListener;
import com.he.func.first.weather.wea.WeatherView;
import com.he.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class WeatherPresenter {

    private WeatherModel mModel;
    private Context context;
    public static WeatherView mView;
    public static String cityName = "北京";
    public WeatherPresenter(Context context){
        this.context = context;
        this.mModel = new WeatherModel(context);
    }

   public void loadWeatherData(final String city){
       cityName = city;
       mModel.loadWeatherData(city,new HeCallbackListener() {
           @Override
           public void onSuccess(String result) {
               //result 是一个list 需要解析
               List<WeatherBean> lists = WeatherJsonUtils.getWeatherInfo(result);
               lists.get(0).setCity(city);
               mView.setTodayWeather(lists.remove(0));
               mView.setWeekWeather(lists);
           }

           @Override
           public void onFailure(String error) {
               Utils.showTips(context, "天气数据加载错误");
           }

       });

   }

    public void loadLocalData(){
        if(!Utils.isNetworkAvailable(context)) {
            Utils.showTips(context, "无网络连接");
            return;
        }
        mModel.loadLocalData(new HeCallbackListener() {
            @Override
            public void onSuccess(String result) {
                loadWeatherData(result);
            }

            @Override
            public void onFailure(String error) {
                Utils.showTips(context, "获取位置失败");
            }
        });
    }

    public ArrayList<CityBean> getCityList(){
       return mModel.getCityList();
    }


}
