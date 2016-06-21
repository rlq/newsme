package com.he.func.frist;

import com.he.data.frist.weather.ChannelBean;
import com.he.data.frist.news.NewsBean;

import java.util.List;

public interface FristView {

    void addNews(List<NewsBean> newsList);


    interface FristHeadView {
        void setCurrentItem(int i);
        void setUserData(List<ChannelBean> beanList);
    }

    interface FristNewsDetailView{
        void showNewsDetail(String content);
    }
}


