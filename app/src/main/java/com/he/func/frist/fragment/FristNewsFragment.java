package com.he.func.frist.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.he.func.frist.FristAdapter;
import com.he.data.frist.news.NewsBean;
import com.he.config.HeTask;
import com.he.config.KeyConfig;
import com.he.func.frist.FristPresenter;
import com.he.func.frist.FristView;
import com.lq.ren.newsme.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FristNewsFragment extends Fragment implements FristView, SwipeRefreshLayout.OnRefreshListener{

    View view;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private FristAdapter mAdapter;
    private FristPresenter mPresenter;
    private int pageIndex = 0;
    private List<NewsBean> mData;
    private LinearLayoutManager mLayoutManager;
    private int type;

    private static FristNewsFragment instance;
    public static FristNewsFragment getInstance(int type){
        instance = new FristNewsFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.he_frist_list, null);
        mPresenter = new FristPresenter(getContext(),this);
        initSwipRefresh();
        initRecycleView();
        onRefresh();
        return view;
    }

    private void initSwipRefresh(){
        mSwipeRefresh = (SwipeRefreshLayout)view.findViewById(
                R.id.swipe_refresh_widget);
        mSwipeRefresh.setColorSchemeColors(R.color.color_gray,
                R.color.color_yellow, R.color.color_black,R.color.color_blue);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    private void initRecycleView(){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FristAdapter(getContext(), mPresenter);
        mAdapter.setOnItemClickListener(new FristAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //TODO open NewsDetial
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
               HeTask.getInstance().startFristNewsDetail(getContext(), mAdapter.getItem(position));
            }
        });

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                mPresenter.loadNews(new Random().nextInt(3), pageIndex + KeyConfig.PAZE_SIZE);
            }
        }
    };

    @Override
    public void addNews(List<NewsBean> newsList) {
        if(mData == null)
            mData = new ArrayList<>();
        mData.addAll(newsList);
        if(pageIndex == 0) {
            mAdapter.setData(mData);
            mAdapter.setmIsLoading(true);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            //如果没有更多数据了,则隐藏Loading布局
               mAdapter.setmIsLoading(false);
                mAdapter.notifyDataSetChanged();

        }
        mSwipeRefresh.setRefreshing(false);
        pageIndex += KeyConfig.PAZE_SIZE;
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(mData != null) {
            mData.clear();
            mSwipeRefresh.setRefreshing(true);
        }
        mSwipeRefresh.setRefreshing(true);
        mPresenter.loadNews(type, pageIndex);
    }
}
