package com.he.func.frist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.he.data.frist.FristNewsItem;
import com.he.data.frist.news.NewsBean;
import com.lq.ren.newsme.R;

import java.util.List;


public class FristAdapter extends RecyclerView.Adapter {
    private static final int ITEM_TYPE = 0;
    private static final int LOAD_TYPE = 1;
    private Boolean mIsLoading = true;
    private Context context;
    private FristPresenter mPresenter;
    private FristViewHodler mViewHodler;
    private OnItemClickListener onItemClickListener;
    private List<NewsBean> bean ;

    public FristAdapter(Context context , FristPresenter presenter) {
        this.context = context;
        this.mPresenter = presenter;
    }

    public void setData(List<NewsBean> listBean){
        this.bean = listBean;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(!mIsLoading){
            return ITEM_TYPE;
        }
        if (position + 1 == getItemCount()) {
            return LOAD_TYPE;
        } else {
            return ITEM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        int begin = mIsLoading ?1:0;
        if(bean == null) {
            return begin;
        }
        return bean.size() + begin;
    }

    public void setmIsLoading(boolean mIsLoading){ this.mIsLoading = mIsLoading;}

    public Boolean getmIsLoading(){ return this.mIsLoading;}

    public NewsBean getItem(int position) {
        return  bean.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = null;
        if(viewType == LOAD_TYPE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.he_loading, parent, false);
            LoadViewHolder load = new LoadViewHolder(view);
            return load;
        }
        else  {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.he_frist1_news, parent, false);
            mViewHodler = new FristViewHodler(view);
            return mViewHodler;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof FristViewHodler) {
            mViewHodler = (FristViewHodler) holder;
            if (bean == null)
                return;
            /**NewsList*/
            NewsBean news = bean.get(position);

            Glide.with(context).load(news.getImgsrc()).placeholder(R.drawable.he_image_loading)
                    .error(R.drawable.he_loadfail).crossFade().into(mViewHodler.imageView);
            mViewHodler.titleText.setText(news.getTitle());
            mViewHodler.contentText.setText(news.getDigest());
            /**NewsItem*/
            FristNewsItem item = mPresenter.getFavoriteAndComment();
            mViewHodler.favortNum.setText(item.getFavortNums());
            mViewHodler.commentNum.setText(item.getCommentNums());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private RecyclerView.ViewHolder getItemView(RecyclerView.ViewHolder holder){
        return holder;
    }


    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }


    public class LoadViewHolder extends RecyclerView.ViewHolder{

        public LoadViewHolder(View itemView){
            super(itemView);
        }
    }

    public class FristViewHodler extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleText;
        TextView contentText;
        TextView favortNum;
        TextView commentNum;
        public FristViewHodler(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.ivNews);
            titleText = (TextView) itemView.findViewById(R.id.tvTitle);
            contentText = (TextView) itemView.findViewById(R.id.tvDesc);
            favortNum = (TextView)itemView.findViewById(R.id.favort);
            commentNum = (TextView)itemView.findViewById(R.id.comment);

            itemView.findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v,getLayoutPosition());
                }
            });
        }
    }
}
