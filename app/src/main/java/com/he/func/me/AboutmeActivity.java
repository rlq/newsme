package com.he.func.me;


import android.os.Bundle;
import com.he.util.Utils;
import com.lq.ren.news.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AboutmeActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_about);
        SwipeBackLayout back = getSwipeBackLayout();
        back.setEdgeSize(Utils.getScreenSizeWidth(this));
        back.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
    }
}
