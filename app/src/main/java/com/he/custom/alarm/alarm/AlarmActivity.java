package com.he.custom.alarm.alarm;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.WindowManager;

import com.he.custom.alarm.provider.AlarmInstance;
import com.he.custom.alarm.utils.WindowUtils;

/**
 * Created by lqren on 16/7/16.
 */
public class AlarmActivity extends Activity {

    private AlarmInstance mAlarmInstance;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //setContentView(R.layout.alarm_alert2);
        // every class needs call this method
        WindowUtils.clipToScreenShape(getWindow());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Uri uri = getIntent().getData();

        //mAlarmInstance = AlarmInstance.
        
    }
}
