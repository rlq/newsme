package com.he.func.frist.fragment;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.bumptech.glide.Glide;
import com.he.data.frist.news.NewsBean;
import com.he.func.frist.FristPresenter;
import com.he.func.frist.FristView;
import com.lq.ren.newsme.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class FristNewsDetail extends AppCompatActivity implements FristView.FristNewsDetailView {

    private NewsBean mNews;
    private HtmlTextView mNewsContent;
    private FristPresenter mPresenter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_frist_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mNewsContent = (HtmlTextView) findViewById(R.id.htNewsContent);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mNews = (NewsBean) getIntent().getSerializableExtra("news");

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mNews.getTitle());

        Glide.with(this).load(mNews.getImgsrc()).placeholder(R.drawable.he_image_loading)
                .error(R.drawable.he_loadfail).crossFade().into((ImageView) findViewById(R.id.news_ivImage));

        mPresenter = new FristPresenter(getApplication(), this);
        mPresenter.loadNewsDetail(mNews.getDocid());
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNewsDetail(String content) {
        mNewsContent.setHtmlFromString(content, new HtmlTextView.LocalImageGetter());
        mProgressBar.setVisibility(View.GONE);
    }

}
