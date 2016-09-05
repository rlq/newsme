package com.he.custom.alarm.provider;

import android.content.ContentResolver;
import android.database.Cursor;
//import android.icu.util.Calendar;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.Calendar;

/**
 * 首先需要的参数 一个闹钟单例 ID 需要时间 震动 铃声 状态 标签等
 */
public class AlarmInstance implements ClockContract.InstanceColums{
    public long mId;
    public int mYear;
    public int mMonth;
    public int mDay;
    public int mHour;
    public int mMinute;
    public String mLabel;
    public boolean mVibrate;
    public Uri mRingtone;
    public Long mAlarmId;
    public int mAlarmState;

    public AlarmInstance(Calendar calendar, Long alarmId){
        this(calendar);
        this.mAlarmId = alarmId;
    }

    public AlarmInstance(Calendar calender){
        mId = INVALID_ID;
        mLabel = "";
        mVibrate = false;
        mRingtone = null;
        mAlarmState = SILENT_STATE;
    }

    public AlarmInstance(Cursor c) {
        mId = c.getLong(ID_INDEX);
        mYear = c.getInt(YEAR_INDEX);
        mMonth = c.getInt(MONTH_INDEX);
        mDay = c.getInt(DAY_INDEX);
        mHour = c.getInt(HOUR_INDEX);
        mMinute = c.getInt(MINUTES_INDEX);
        mLabel = c.getString(LABEL_INDEX);
        mVibrate = c.getInt(VIBRATE_INDEX) == 1;
        if (c.isNull(RINGTONE_INDEX)) {
            // Should we be saving this with the current ringtone or leave it
            // null
            // so it changes when user changes default ringtone?
            mRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        } else {
            mRingtone = Uri.parse(c.getString(RINGTONE_INDEX));
        }

        if (!c.isNull(ALARM_ID_INDEX)) {
            mAlarmId = c.getLong(ALARM_ID_INDEX);
        }
        mAlarmState = c.getInt(ALARM_STATE_INDEX);
    }

    public static final int LOW_NOTIFICATION_HOUR_OFFSET = -2;

    /**
     * Offset from alarm time to show high priority notification
     */
    public static final int HIGH_NOTIFICATION_MINUTE_OFFSET = -30;

    /**
     * Offset from alarm time to stop showing missed notification.
     */
    private static final int MISSED_TIME_TO_LIVE_HOUR_OFFSET = 12;

    /**
     * Default timeout for alarms in minutes.
     */
    private static final String DEFAULT_ALARM_TIMEOUT_SETTING = "10";

    /**
     * AlarmInstances start with an invalid id when it hasn't been saved to the
     * database.
     */
    public static final long INVALID_ID = -1;

    private static final String[] QUERY_COLUMNS = { _ID, YEAR, MONTH, DAY, HOUR, MINUTES, LABEL, VIBRATE, RINGTONE,
            ALARM_ID, ALARM_STATE };

    /**
     * These save calls to cursor.getColumnIndexOrThrow() THEY MUST BE KEPT IN
     * SYNC WITH ABOVE QUERY COLUMNS
     */
    private static final int ID_INDEX = 0;
    private static final int YEAR_INDEX = 1;
    private static final int MONTH_INDEX = 2;
    private static final int DAY_INDEX = 3;
    private static final int HOUR_INDEX = 4;
    private static final int MINUTES_INDEX = 5;
    private static final int LABEL_INDEX = 6;
    private static final int VIBRATE_INDEX = 7;
    private static final int RINGTONE_INDEX = 8;
    private static final int ALARM_ID_INDEX = 9;
    private static final int ALARM_STATE_INDEX = 10;

    public static AlarmInstance getInstance(ContentResolver contentResolver, long instanceId){
       // Cursor cursor = contentResolver.query()
        AlarmInstance instance = null;

        return instance;
    }


}
