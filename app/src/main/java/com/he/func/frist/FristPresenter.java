package com.he.func.frist;

import android.content.Context;
import android.util.Log;

import com.he.data.frist.FristNewsItem;
import com.he.data.frist.weather.ChannelBean;
import com.he.data.frist.news.NewsBean;
import com.he.data.frist.news.NewsDetailBean;
import com.he.data.frist.news.NewsJsonUtils;
import com.he.config.KeyConfig;
import com.he.base.HeCallbckListener;

import java.util.List;


public class FristPresenter  {

    public static final int NEWS_TYPE_ZERO = 0;
    public static final int NEWS_TYPE_ONE = 1;
    public static final int NEWS_TYPE_TWO = 2;
    public static final int NEWS_TYPE_THREE = 3;

    private FristModel fristModel;
    private Context context;
    private FristView fristView;
    private FristView.FristNewsDetailView newsDetailView;
    public static FristView.FristHeadView headView;
    public FristPresenter(Context context, FristView fristView){
        this.fristView = fristView;
        this.context = context;
        this.fristModel = new FristModel(context);

    }
    public FristPresenter(Context context, FristView.FristNewsDetailView newsDetailView){
        this.newsDetailView = newsDetailView;
        this.context = context;
        this.fristModel = new FristModel(context);
    }

    public FristPresenter(Context context){
        this.context = context;
        this.fristModel = new FristModel(context);
    }

    public void loadNews(final int type, int pageIndex){
        fristModel.loadNews(type, pageIndex, new HeCallbckListener() {
            @Override
            public void onSuccess(String result) {
                List<NewsBean> newsBeanList = NewsJsonUtils.getNewsBeans(result, fristModel.id);
                if(newsBeanList == null)
                    return;
                Log.i(KeyConfig.TAG_NAME, "size "+ newsBeanList.size());
                if(fristView != null)
                     fristView.addNews(newsBeanList);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    public void loadNewsDetail(final String docid){
        fristModel.loadNewsDetail(docid, new HeCallbckListener() {
            @Override
            public void onSuccess(String result) {
                NewsDetailBean newsDetailBean = NewsJsonUtils.getNewsDetailBeans(result, docid);
                newsDetailView.showNewsDetail(newsDetailBean.getBody());
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    public void reloadHeadList(List<ChannelBean> beanList){
        headView.setUserData(beanList);
    }

    public FristNewsItem getFavoriteAndComment(){
        return fristModel.getFavoriteAndComment();
    }
}
