package com.he.func.me;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.he.config.HeTask;
import com.he.util.Utils;
import com.lq.ren.newsme.R;

public class AccountActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_account);
        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeTask.getInstance().startAboutActicity(AccountActivity.this);
            }
        });

        findViewById(R.id.login_more).setOnClickListener(this);
        findViewById(R.id.login1).setOnClickListener(this);
        findViewById(R.id.login2).setOnClickListener(this);
        findViewById(R.id.login3).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_more:
                //TODO open moreLogin
                HeTask.getInstance().startMoreLogin(this);
                break;
            case R.id.login1:
                Utils.showTips(this,getString(R.string.log_qq));
                break;
            case R.id.login2:
                Utils.showTips(this,getString(R.string.log_tecent));
                break;
            case R.id.login3:
                Utils.showTips(this,getString(R.string.log_sina));
                break;
        }
    }
}
