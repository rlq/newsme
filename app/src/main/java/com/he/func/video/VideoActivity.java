package com.he.func.video;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.he.config.HeTask;
import com.lq.ren.news.R;

public class VideoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_video);
        findViewById(R.id.playVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeTask.getInstance().startVideoActicity(VideoActivity.this);
            }
        });
    }
}
