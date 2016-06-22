package com.he.func.favorite;


import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.he.util.Utils;
import com.lq.ren.news.R;


import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class FavoriteItemActivity extends SwipeBackActivity {

    private WebView webView;
    private WebSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_favorite_web);
        SwipeBackLayout layout = getSwipeBackLayout();
        layout.setEdgeSize(Utils.getScreenSizeWidth(this)/2);
        layout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        webView = (WebView)findViewById(R.id.favort_web);
        initWebView(getIntent().getStringExtra("url"));
    }

    private void initWebView(String url){
        settings = webView.getSettings();
        settings.setSaveFormData(false);
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setDomStorageEnabled(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                       //接受证书
                handler.proceed();
                //handleMessage(Message msg); 其他处理
            }
        });
        webView.loadUrl(url);//"http://www.baidu.com"
    }
}
