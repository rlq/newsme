package com.he.func.favorite;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.lq.ren.newsme.R;

public class FavoriteActivity extends Activity {

    private ListView mListView;
    private FavoriteAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.he_favorite);
        mListView = (ListView)findViewById(R.id.favort_listView);
        initListView();
    }

    private void initListView(){
        mAdapter = new FavoriteAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
