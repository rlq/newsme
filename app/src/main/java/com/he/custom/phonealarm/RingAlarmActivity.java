package com.he.custom.phonealarm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.he.custom.utils.AlarmOpUtils;
import com.lq.ren.news.R;

/**
 * Created by lqren on 16/7/26.
 */
public class RingAlarmActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.custom_ringalarm);
    }

    public void snooze(View view){
        //TODO snooze 10m
    }

    public void close(View view){
        //TODO canel alarm
        AlarmOpUtils.canelAlarmTime(this,"", 100000);
        finish();
    }
}
