package com.he.func.first;

import android.content.Context;
import android.util.Log;

import com.he.data.first.FirstNewsItem;
import com.he.data.first.weather.ChannelBean;
import com.he.data.first.news.NewsBean;
import com.he.data.first.news.NewsDetailBean;
import com.he.data.first.news.NewsJsonUtils;
import com.he.config.KeyConfig;
import com.he.base.HeCallbackListener;

import java.util.List;


public class FirstPresenter {

    public static final int NEWS_TYPE_ZERO = 0;
    public static final int NEWS_TYPE_ONE = 1;
    public static final int NEWS_TYPE_TWO = 2;
    public static final int NEWS_TYPE_THREE = 3;

    private FirstModel fristModel;
    private Context context;
    private FirstView fristView;
    private FirstView.FristNewsDetailView newsDetailView;
    public static FirstView.FristHeadView headView;
    public FirstPresenter(Context context, FirstView fristView){
        this.fristView = fristView;
        this.context = context;
        this.fristModel = new FirstModel(context);

    }
    public FirstPresenter(Context context, FirstView.FristNewsDetailView newsDetailView){
        this.newsDetailView = newsDetailView;
        this.context = context;
        this.fristModel = new FirstModel(context);
    }

    public FirstPresenter(Context context){
        this.context = context;
        this.fristModel = new FirstModel(context);
    }

    public void loadNews(final int type, int pageIndex){
        fristModel.loadNews(type, pageIndex, new HeCallbackListener() {
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
        fristModel.loadNewsDetail(docid, new HeCallbackListener() {
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

    public FirstNewsItem getFavoriteAndComment(){
        return fristModel.getFavoriteAndComment();
    }
}
