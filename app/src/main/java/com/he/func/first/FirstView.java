package com.he.func.first;

import com.he.data.first.weather.ChannelBean;
import com.he.data.first.news.NewsBean;

import java.util.List;

public interface FirstView {

    void addNews(List<NewsBean> newsList);


    interface FristHeadView {
        void setCurrentItem(int i);
        void setUserData(List<ChannelBean> beanList);
    }

    interface FristNewsDetailView{
        void showNewsDetail(String content);
    }
}


