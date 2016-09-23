package com.he.custom.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.he.custom.phonealarm.MainActivity;

import java.util.Calendar;

/**
 * Created by lqren on 16/7/26.
 */
public class Timesetting extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("HEHE", "time set onReceive");

        Calendar curCalendar= Calendar.getInstance();
        Calendar alarmCalendar = MainActivity.calendar;
        Log.i("HEHE", "cur "+ curCalendar + " \n alarm "+ alarmCalendar);
        if(curCalendar.after(alarmCalendar)){
            Log.d("HEHE", "after");
            alarmCalendar.set(Calendar.DAY_OF_YEAR,
                    curCalendar.get(Calendar.DAY_OF_YEAR) + 1);
        }else{
            Log.d("HEHE", "before");

            alarmCalendar.set(java.util.Calendar.DAY_OF_YEAR,
                    curCalendar.get(java.util.Calendar.DAY_OF_YEAR));
        }
        Log.i("HEHE", " new alarm "+ alarmCalendar);
        AlarmOpUtils.setAlarmTime(context, alarmCalendar);
    }
}
