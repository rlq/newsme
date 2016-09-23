package com.he.custom.phonealarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

//import com.google.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;
import com.he.custom.utils.AlarmOpUtils;
import com.lq.ren.news.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static Calendar calendar;
    private AlarmManager mAlarmManager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_activity_main);
        initView();
//        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        findViewById(R.id.addAlarm).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void setTime(View v) {
        Log.v("HEHE", "finish");
       finish();
        // startActivityForResult(new Intent(this, TimeSetActivity.class),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            int minute = data.getIntExtra("Minute", 0);
            int hour = data.getIntExtra("Hour", 7);
            Log.v("HEHE", "hour "+ hour + "  minute "+ minute);
            alarmItem0.setText(hour+":"+minute);
            startAlarm(hour, minute, data);
        }
    }

    private void startAlarm(int hour, int minute, Intent data){
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.i("HEHE", "calendar "+ calendar);
        Calendar curCalendar= Calendar.getInstance();
        if(curCalendar.after(calendar)){
            Log.d("HEHE", "call after");
            calendar.set(Calendar.DAY_OF_YEAR,
                    calendar.get(Calendar.DAY_OF_YEAR) + 1);
        }
        AlarmOpUtils.setAlarmTime(this, calendar);
    }

    TextView alarmItem0;
    private void initView(){
        alarmItem0 = (TextView)findViewById(R.id.timeText);
        CheckBox box = (CheckBox)findViewById(R.id.timeCheck);
        // box on setAlarm; off canel

    }

    private void addAlarm() {
        //当前设备上的系统上时间选择器
        /** Calendar calendar = Calendar.getInstance();
         // 弹出设置时间的窗口
         new TImePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
        @Override public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Intent intent = new Intent(this, AlarmActivity.class);
        //create PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity
        (MainActivity.this,0,intent,0);

        Calendar setCalendar = Calendar.getInstance();
        setCalendar.set(Calendar.HOUR_OF_DAY, 7);
        setCalendar.set(Calendar.MINUTE, 0);
        // 设置AlarmManager将在Calendar对应的时间启动指定组件
        //AlarmManager.RTC_WAKEUP设定服务在系统休眠时同样会执行
        mAlarmManager.set(AlarmManager.RTC_WAKEUP,
        setCalendar.getTimeInMillis(), pendingIntent);
        Log.d("HEHE", "alarm add successfully");
        }
        },7, 0,true).show();
         */
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.mobvoi.phonealarm/http/host/path")
        );
       // AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        Log.d("HEHE", "stop");
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       /* Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.mobvoi.phonealarm/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("HEHE", "onDestroy");
        startActivityForResult(new Intent(this, TimeSetActivity.class),0);
    }
}
