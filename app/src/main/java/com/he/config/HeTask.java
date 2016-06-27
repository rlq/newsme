package com.he.config;

import com.he.data.first.news.NewsBean;
import com.he.func.favorite.FavoriteItemActivity;
import com.he.func.first.channel.ChannelActivity;
import com.he.func.first.fragment.FirstNewsDetail;
import com.he.func.first.weather.city.CityActivity;
import com.he.func.first.weather.wea.WeatherActivity;
import com.he.func.me.AboutmeActivity;
import com.he.func.me.MoreLoginActivity;
import com.he.func.video.VideoViewActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class HeTask {

	private static HeTask task;
	
	public static HeTask getInstance(){
		if(task == null){
			task = new HeTask();
		}
		return task;
	}

	/**
	 * 关心 打开详情
     */
	public void startFarvoriteBaike(Context context, String url){
		Intent intent = new Intent(context, FavoriteItemActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

	public void startVideoActicity(Context context){
		context.startActivity(new Intent(context, VideoViewActivity.class));
	}


	public void startAboutActicity(Context context){
		context.startActivity(new Intent(context, AboutmeActivity.class));
	}

	public void startMoreLogin(Context context){
		context.startActivity(new Intent(context, MoreLoginActivity.class));
	}

	public void startFristNewsDetail(Context context, NewsBean news){
		Intent intent = new Intent(context, FirstNewsDetail.class);
		intent.putExtra("news", news);
		ActivityCompat.startActivity((Activity) context, intent, null);
	}


	public void startFrist3Weather(Context context){
		context.startActivity(new Intent(context, WeatherActivity.class));
	}

	public void startFrist3City(Context context){
		Intent intent = new Intent(context, CityActivity.class);
		ActivityCompat.startActivity((Activity) context, intent, null);
	}

	public void startChannelActivity(Context context){
		Intent intent = new  Intent(context, ChannelActivity.class);
		context.startActivity(intent);
	}

	public final String sdCardRoot() {
		boolean sdExist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
		if (!sdExist) {
			Log.e(KeyConfig.TAG_NAME, "sd card not exist");
			return null;
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public String sdkRoot() {
	        return sdCardRoot() + "/Moo/";
	    }

}