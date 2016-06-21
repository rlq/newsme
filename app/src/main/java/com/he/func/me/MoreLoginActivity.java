package com.he.func.me;


import android.os.Bundle;
import android.view.View;

import com.he.util.Utils;
import com.lq.ren.newsme.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class MoreLoginActivity extends SwipeBackActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_account_more_login);
        SwipeBackLayout back = getSwipeBackLayout();
        back.setEdgeSize(Utils.getScreenSizeWidth(this));
        back.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);

        findViewById(R.id.btn_qzone).setOnClickListener(this);
        findViewById(R.id.btn_sina).setOnClickListener(this);
        findViewById(R.id.btn_tencent).setOnClickListener(this);
        findViewById(R.id.btn_renren).setOnClickListener(this);
        findViewById(R.id.btn_kaixin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_qzone:
                Utils.showTips(this,getString(R.string.log_qq));
                break;
            case R.id.btn_sina:
                Utils.showTips(this,getString(R.string.log_sina));
                break;
            case R.id.btn_tencent:
                Utils.showTips(this,getString(R.string.log_tecent));
                break;
            case R.id.btn_renren:
                Utils.showTips(this,getString(R.string.log_ren));
                break;
            case R.id.btn_kaixin:
                Utils.showTips(this,getString(R.string.log_kaixin));
                break;

        }
    }
}
