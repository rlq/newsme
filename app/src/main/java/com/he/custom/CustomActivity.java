package com.he.custom;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.he.custom.activity.step7.MapToFile0909String;
import com.he.custom.course.Draw4Shader;
import com.he.custom.course.Draw918;
import com.he.custom.view.step19.LoadingView;
import com.he.custom.view.step5.RoundImage0905;
import com.lq.ren.news.R;
import com.he.custom.course.Draw3Graph;
import com.he.custom.view.CombintionView;
import com.he.custom.view.ListDeleteAdapter;
import com.he.custom.view.ListDeleteView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lqren on 16/8/11.
 */
public class CustomActivity extends Activity {

    private List<String> mContentList = new ArrayList<String>();
    private ListDeleteView mDeleteView;
    private ListDeleteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.custom_customview);

//        float value = 89.500f;
//        int value2 = (int)value;
//        Log.e("HEHE", "" + value2 + " isTrue " + (value > value2));

        //  initListView();
//        Log.e("HEHE", "son");
//        Son son = new Son();


        /**DrawView */
        //initDrawView();

        /** gps*/
        //testGps();

        /**0905 RoundView  */
        //step5SetRoundDrawable();

        /**0907 shared */
        //startActivity(new Intent(CustomActivity.this, SharedActivity.class));

        /**0909 file and hashMap */
        //mapToFileReadString();

        /**0918 draw point */
        //setContentView(new Draw918(this));

        /**0919 calendar add */
        calendar0919();

        /**0919 LoadingView */
        //setContentView(new LoadingView(this));

    }

    private void initListView() {

        ((CombintionView) findViewById(R.id.combine)).setBackButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HEHE", "哈哈");
            }
        });

        for (int i = 0; i < 20; i++) {
            mContentList.add(i, "我是 " + i);
        }

        mDeleteView = (ListDeleteView) findViewById(R.id.delete_view);
        mDeleteView.setOnDeleteListener(new ListDeleteView.OnListDeleteListener() {
            @Override
            public void onDelete(int index) {
                Log.d("HEHE", "哈哈2 ");
                mContentList.remove(index);
                mAdapter.notifyDataSetChanged();
            }
        });
        Log.d("HEHE", "哈哈1 ");

        mAdapter = new ListDeleteAdapter(this, 0);
        mAdapter.setDate(mContentList);
        mDeleteView.setAdapter(mAdapter);
    }

    private void initDrawView() {
        //DrawView view = new DrawView(this);

        //Draw2Canvas view = new Draw2Canvas(this);

       // Draw3Graph view = new Draw3Graph(this);

        Draw4Shader view = new Draw4Shader(this);

        // Draw5Animator view = new Draw5Animator(this);

        // Draw5AnimatorFrame view = new Draw5AnimatorFrame(this);

        setContentView(view);
    }

    LocationManager mLocationManager;

    private void testGps() {
        Log.e("HEHE", "test gps");
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /*Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setCostAllowed(true);
        c.setPowerRequirement(Criteria.POWER_LOW);

        mLocationManager.getBestProvider(c, true);

        String provider = mLocationManager.getBestProvider(c, true);
        Location lc;
        if (provider == null && provider.equals("")) {

        } else {
            lc = mLocationManager.getLastKnownLocation(provider);
        }
        updateLocation(lc);*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("HEHE", "test gps false");
            return;
        }
        updateLocation(null);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 100, mListener);
    }


    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(CustomActivity.this, "onLocationChanged " + location.toString(), Toast.LENGTH_LONG).show();
            Log.e("HEHE", location.toString());
            updateLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Toast.makeText(CustomActivity.this, "onStatusChanged " + s, Toast.LENGTH_LONG).show();
            Log.e("HEHE", "onStatusChanged " + s);

        }

        @Override
        public void onProviderEnabled(String s) {
            Toast.makeText(CustomActivity.this, "onProviderEnabled " + s, Toast.LENGTH_LONG).show();
            Log.e("HEHE", "onProviderEnabled " + s);
            updateLocation(null);
        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(CustomActivity.this, "onProviderDisabled " + s, Toast.LENGTH_LONG).show();
            Log.e("HEHE", " onProviderDisabled "+ s);

        }
    };

    public void updateLocation(Location lc) {
        String last;
        TextView tv = (TextView) findViewById(R.id.gps);
        if (lc != null) {
            double lat = lc.getLatitude();
            double lng = lc.getLongitude();

            last = "维度：" + lat + "\n经度：" + lng;
        } else {
            last = "无法获取当前位置！";
        }
        tv.setText("您的当前位置：\n" + last);
    }

    /** 0905 Drawable */
    private void step5SetRoundDrawable() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qq);
        ImageView iv = (ImageView) findViewById(R.id.roundIv);
        iv.setImageDrawable(new RoundImage0905(bitmap));
    }

    /** 0909 mapToFile  call to MapToFile0909String and bean */
    int count = 0;
    private void mapToFileReadString() {
        MapToFile0909String.createFile(this);
        findViewById(R.id.addCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add 重复的key
                count++;
                MapToFile0909String.setValue("id_" + count, count);
                MapToFile0909String.readStringStream();
            }
        });

        findViewById(R.id.minusCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                MapToFile0909String.setValue("id_" + count, count );
                //MapToFile0909String.readHashMapStream();
                MapToFile0909String.readStringStream();
            }
        });

    }

    /**0919 Calendar add */
    private void calendar0919() {
        Calendar calendar = Calendar.getInstance();
        Log.e("HEHE", "calendar add before " + calendar);
        calendar.add(Calendar.MONTH, -2);
//        calendar.add(Calendar.DAY_OF_WEEK, -3);
//        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Log.e("HEHE", "calendar " + calendar);

        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
        Log.v("HEHE", "format " + format.format(calendar.getTime()));
    }


}


