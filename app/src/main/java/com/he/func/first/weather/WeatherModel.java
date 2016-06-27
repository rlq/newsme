package com.he.func.first.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import com.he.data.first.weather.CityBean;
import com.he.data.first.weather.WeatherJsonUtils;
import com.he.config.KeyConfig;
import com.he.base.HeCallbckListener;
import com.he.util.OkHttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.os.Build;

public class WeatherModel {

    private Context context;

    public WeatherModel(Context context){
        this.context = context;
    }

    public void loadWeatherData(String city, final HeCallbckListener listener){
        try {
            String url = KeyConfig.WEATHER + URLEncoder.encode(city,"UTF-8");
            OkHttpUtil.OkHttpCallBack<String> callBack = new OkHttpUtil.OkHttpCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    listener.onSuccess(response);
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    listener.onFailure("load weather data failure.");
                }
            };
            OkHttpUtil.doGetRequest(url, callBack);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void loadLocalData(final HeCallbckListener listener){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
             != PackageManager.PERMISSION_GRANTED
            && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
                listener.onFailure("location failure");
                return;
            }

            Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location == null){
                listener.onFailure("location failure");
                return;
            }
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String url = getLocationURL(latitude, longitude);
            OkHttpUtil.doGetRequest(url, new OkHttpUtil.OkHttpCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    String city = WeatherJsonUtils.getCity(response);
                    listener.onSuccess(city);
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure("location failure");
                }
            });
        }
        else
            listener.onSuccess("北京");
    }

    private String getLocationURL(double latitude, double longitude) {
        StringBuffer sb = new StringBuffer(KeyConfig.INTERFACE_LOCATION);
        sb.append("?output=json").append("&referer=32D45CBEEC107315C553AD1131915D366EEF79B4");
        sb.append("&location=").append(latitude).append(",").append(longitude);
        return sb.toString();
    }

    public ArrayList<CityBean> getCityList(){
       return WeatherJsonUtils.getCityList();
    }

}
