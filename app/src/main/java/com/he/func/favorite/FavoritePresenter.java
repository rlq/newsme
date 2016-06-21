package com.he.func.favorite;


import android.content.Context;

import com.he.data.Friend;

import java.util.List;

public class FavoritePresenter {
    private FavoriteModel mFavoriteModel;
    private Context context;

    public FavoritePresenter(Context context){
        this.context = context;
        mFavoriteModel = new FavoriteModel();
    }

    public List<Friend> getChatUserData(){
        return mFavoriteModel.initChatUserData();
    }

    public String getUrlToBaike(int pos){
        return mFavoriteModel.getUrlToBaike(pos);
    }
}
