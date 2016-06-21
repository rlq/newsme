package com.he.data.frist.weather;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lq.ren.newsme.R;

import java.util.ArrayList;
import java.util.List;

public class WeatherJsonUtils {

    /**
     * 从定位的json字串中获取城市
     */
    public static String getCity(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        JsonElement status = jsonObj.get("status");
        if(status != null && "OK".equals(status.getAsString())) {
            JsonObject result = jsonObj.getAsJsonObject("result");
            if(result != null) {
                JsonObject addressComponent = result.getAsJsonObject("addressComponent");
                if(addressComponent != null) {
                    JsonElement cityElement = addressComponent.get("city");
                    if(cityElement != null) {
                        return cityElement.getAsString().replace("市", "");
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取天气信息
     */
    public static List<WeatherBean> getWeatherInfo(String json) {
        List<WeatherBean> list = new ArrayList<WeatherBean>();
        if (TextUtils.isEmpty(json)) {
            return list;
        }
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        String status = jsonObj.get("status").getAsString();
        if("1000".equals(status)) {
            JsonArray jsonArray = jsonObj.getAsJsonObject("data").getAsJsonArray("forecast");
            for (int i = 0; i < jsonArray.size(); i++) {
                WeatherBean weatherBean = getWeatherBean(jsonArray.get(i).getAsJsonObject());
                list.add(weatherBean);
            }
        }
        return list;
    }

    /**json转为天气数据*/
    private static WeatherBean getWeatherBean(JsonObject jsonObject) {
        String temperature = jsonObject.get("high").getAsString() + " " + jsonObject.get("low").getAsString();
        String weather = jsonObject.get("type").getAsString();
        String wind = jsonObject.get("fengxiang").getAsString();
        String date = jsonObject.get("date").getAsString();

        WeatherBean weatherBean = new WeatherBean();
        weatherBean.setDate(date);
        weatherBean.setTemperature(temperature);
        weatherBean.setWeather(weather);
        weatherBean.setWeek(date.substring(date.length()-3));
        weatherBean.setWind(wind);
        weatherBean.setImageRes(getWeatherImage(weather));
        return weatherBean;
    }

    public static int getWeatherImage(String weather) {
        if (weather.equals("多云") || weather.equals("多云转阴") || weather.equals("多云转晴")) {
            return R.drawable.he_weather_yun;
        } else if (weather.equals("中雨") || weather.equals("中到大雨")) {
            return R.drawable.he_weather_moderaterain;
        } else if (weather.equals("雷阵雨")) {
            return R.drawable.he_weather_thunderstorms;
        } else if (weather.equals("阵雨") || weather.equals("阵雨转多云")) {
            return R.drawable.he_weather_shower;
        } else if (weather.equals("暴雪")) {
            return R.drawable.he_weather_blizzard;
        } else if (weather.equals("暴雨")) {
            return R.drawable.he_weather_rainstorm;
        } else if (weather.equals("大暴雨")) {
            return R.drawable.he_weather_heavyrain;
        } else if (weather.equals("大雪")) {
            return R.drawable.he_weather_snow;
        } else if (weather.equals("大雨") || weather.equals("大雨转中雨")) {
            return R.drawable.he_weather_rain;
        } else if (weather.equals("雷阵雨冰雹")) {
            return R.drawable.he_weather_thunderstormshail;
        } else if (weather.equals("晴")) {
            return R.drawable.he_weather_sun;
        } else if (weather.equals("沙尘暴")) {
            return R.drawable.he_weather_sandstorm;
        } else if (weather.equals("特大暴雨")) {
            return R.drawable.he_weather_morerainstorm;
        } else if (weather.equals("雾") || weather.equals("雾霾")) {
            return R.drawable.he_weather_fog;
        } else if (weather.equals("小雪")) {
            return R.drawable.he_weather_smallsnow;
        } else if (weather.equals("小雨")) {
            return R.drawable.he_weather_smallrain;
        } else if (weather.equals("阴")) {
            return R.drawable.he_weather_cloudy;
        } else if (weather.equals("雨夹雪")) {
            return R.drawable.he_weather_sleet;
        } else if (weather.equals("阵雪")) {
            return R.drawable.he_weather_snowshower;
        } else if (weather.equals("中雪")) {
            return R.drawable.he_weather_modsnow;
        } else {
            return R.drawable.he_weather_yun;
        }
    }


    public static ArrayList<CityBean> getCityList(){
        ArrayList<CityBean> cityList =new ArrayList<>();
        CityBean city1 = new CityBean(1, "澳门", 'A');
        CityBean city2 = new CityBean(2, "北京", 'B');
        CityBean city3 = new CityBean(3, "成都", 'C');
        CityBean city4 = new CityBean(4, "长沙", 'C');
        CityBean city5 = new CityBean(5, "大连", 'D');
        CityBean city6 = new CityBean(6, "哈尔滨", 'H');
        CityBean city7 = new CityBean(7, "杭州", 'H');
        CityBean city8 = new CityBean(8, "南昌", 'N');
        CityBean city9 = new CityBean(9, "南京", 'N');
        CityBean city10 = new CityBean(10, "汕头", 'S');
        CityBean city11 = new CityBean(11, "三亚", 'S');
        CityBean city12 = new CityBean(12, "沈阳", 'S');
        CityBean city13 = new CityBean(13, "郑州", 'Z');
        cityList.add(city1);
        cityList.add(city2);
        cityList.add(city3);
        cityList.add(city4);
        cityList.add(city5);
        cityList.add(city6);
        cityList.add(city7);
        cityList.add(city8);
        cityList.add(city9);
        cityList.add(city10);
        cityList.add(city11);
        cityList.add(city12);
        cityList.add(city13);
        return cityList;
    }

}
