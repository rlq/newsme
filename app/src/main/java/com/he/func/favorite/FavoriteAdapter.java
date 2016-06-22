package com.he.func.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.he.config.HeTask;
import com.he.data.Friend;
import com.lq.ren.news.R;

import java.util.List;

public  class FavoriteAdapter extends BaseAdapter implements View.OnClickListener{

    private final Context context;
    FavoritePresenter mPresenter;
    private List<Friend> friList ;

    public FavoriteAdapter(Context context) {
        this.context = context;
        mPresenter = new FavoritePresenter(context);
        friList = mPresenter.getChatUserData();
    }

    @Override
    public String getItem(int position) {
        return friList.get(position).getUserId();
    }

    @Override
    public int getCount() {
        return friList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FravoriteViewHolder mViewHolder = new FravoriteViewHolder();
        if(convertView == null){
            convertView = LayoutInflater.from(this.context)
                    .inflate(R.layout.he_favorite_item, parent, false);
            mViewHolder.view = convertView.findViewById(R.id.favort_item);
            mViewHolder.name = (TextView) convertView.findViewById(R.id.favort_name);
            mViewHolder.icon = (ImageView) convertView.findViewById(R.id.favort_image);
            mViewHolder.num = (TextView) convertView.findViewById(R.id.favort_num);
            mViewHolder.favortBtn = (Button)convertView.findViewById(R.id.favort_button);
            mViewHolder.view.setId(position);
            mViewHolder.favortBtn.setTag(position);

            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (FravoriteViewHolder) convertView.getTag();
        }
        mViewHolder.view.setOnClickListener(this);
        mViewHolder.favortBtn.setOnClickListener(this);

        Friend fri = friList.get(position);
        mViewHolder.name.setText(fri.getNickname());
        mViewHolder.num.setText(fri.getChatContent()+ context.getString(R.string.fareds));
        mViewHolder.favortBtn.setText(fri.isSelected()?context.getString(R.string.fared):
                context.getString(R.string.far));
        Glide.with(context).load(fri.getPortrait()).into(mViewHolder.icon);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.favort_button:
                friList.get((int)v.getTag()).setSelected(!friList.get((int)v.getTag()).isSelected()) ;
                notifyDataSetChanged();
                break;
            default:
                //TODO open name baike
                String url = mPresenter.getUrlToBaike(v.getId());//(int)v.getTag()
                HeTask.getInstance().startFarvoriteBaike(context,url);
                break;
        }
    }

    class FravoriteViewHolder {
        private View view;
        public TextView name;
        public ImageView icon;
        private TextView num;
        private Button favortBtn;
    }
}


