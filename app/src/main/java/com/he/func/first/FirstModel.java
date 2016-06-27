package com.he.func.first;

import android.content.Context;

import com.he.config.KeyConfig;
import com.he.base.HeCallbckListener;
import com.he.data.first.FirstNewsItem;
import com.he.util.OkHttpUtil;

import java.util.Random;

public class FirstModel {

    private Context context;
    public String id ;
    public FirstModel(Context context){
        this.context = context;
    }

    public void loadNews(int type, int pageIndex,final HeCallbckListener listener){

        OkHttpUtil.doGetRequest(getUrl(type,pageIndex), new OkHttpUtil.OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news failure");
            }
        });
    }

    public void loadNewsDetail(String docid, final HeCallbckListener listener){

        OkHttpUtil.doGetRequest(getDetailUrl(docid), new OkHttpUtil.OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news failure");
            }
        });
    }

    private String getUrl(int type, int pageIndex){
        StringBuffer sb = new StringBuffer();
        switch (type){
            case FirstPresenter.NEWS_TYPE_ZERO:
                sb.append(KeyConfig.TOP_URL).append(KeyConfig.TOP_ID);
                id = KeyConfig.TOP_ID;
                break;
            case FirstPresenter.NEWS_TYPE_ONE:
                sb.append(KeyConfig.COMMON_URL).append(KeyConfig.NBA_ID);
                id = KeyConfig.NBA_ID;
                break;
            case FirstPresenter.NEWS_TYPE_TWO:
                sb.append(KeyConfig.COMMON_URL).append(KeyConfig.CAR_ID);
                id = KeyConfig.CAR_ID;
                break;
            case FirstPresenter.NEWS_TYPE_THREE:
                sb.append(KeyConfig.COMMON_URL).append(KeyConfig.JOKE_ID);
                id = KeyConfig.JOKE_ID;
                break;
        }
        sb.append("/").append(pageIndex ).append(KeyConfig.END_URL);
        return sb.toString();
    }


    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(KeyConfig.NEW_DETAIL);
        sb.append(docId).append(KeyConfig.END_DETAIL_URL);
        return sb.toString();
    }

    public FirstNewsItem getFavoriteAndComment(){
        FirstNewsItem item = new FirstNewsItem();
        item.setFavortNums("" + new Random().nextInt(9999));
        item.setCommentNums("" + new Random().nextInt(9999));
        return item;
    }


}
