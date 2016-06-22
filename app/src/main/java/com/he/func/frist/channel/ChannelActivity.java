package com.he.func.frist.channel;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.he.data.frist.weather.ChannelBean;
import com.he.func.frist.FristPresenter;
import com.lq.ren.news.R;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 频道管理
 */
public class ChannelActivity extends SwipeBackActivity implements OnItemClickListener {

	/** 我的频道 */
	private DragGrid mMyGridView;
	ArrayList<ChannelBean> mMyChannelList = new ArrayList<>();
	DragAdapter mMyAdapter;

	/** 更多频道 */
	private MoreGridView mOtherGridView;
	MoreAdapter mOtherAdapter;
	ArrayList<ChannelBean> mOtherChannelList = new ArrayList<ChannelBean>();

	/** 是否在移动，动画结束后 数据更新*/
	boolean mIsMove = false;
	ChannelPresenter mChannelPresenter;
	FristPresenter mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.he_channel);
		mChannelPresenter = new ChannelPresenter(this);
		mPresenter = new FristPresenter(this);
		initView();
		initData();
	}
	
	private void initData() {
	    mMyChannelList = (ArrayList<ChannelBean>) mChannelPresenter.getMyChannel();
		mMyAdapter = new DragAdapter(this, mMyChannelList);
		mMyGridView.setAdapter(mMyAdapter);
		//more
		mOtherChannelList = ((ArrayList<ChannelBean>) mChannelPresenter.getOtherChannel());
	    mOtherAdapter = new MoreAdapter(this, mOtherChannelList);
	    mOtherGridView.setAdapter(mOtherAdapter);

	    mOtherGridView.setOnItemClickListener(this);
	    mMyGridView.setOnItemClickListener(this);
	}
	
	private void initView() {
		mMyGridView = (DragGrid) findViewById(R.id.userGridView);
		mOtherGridView = (MoreGridView) findViewById(R.id.otherGridView);

		SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeSize(10);
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
		findViewById(R.id.channel_title_bar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		TextView channelTV = (TextView)findViewById(R.id.backTV);
		channelTV.setText(R.string.frist_channels);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, final View view, final int position,long id) {
		//如果点击的时候，之前动画还没结束，那么就让点击事件无效
		if(mIsMove){
			return;
		}
		switch (parent.getId()) {
		case R.id.userGridView:
			//position为 0，1, 2 的不可以进行任何操作
			if (position != 0 && position != 1 && position != 2) {
				final ImageView moveImageView = getView(view);
				if (moveImageView != null) {
					TextView newTextView = (TextView) view.findViewById(R.id.text_item);
					final int[] startLocation = new int[2];
					newTextView.getLocationInWindow(startLocation);
					final ChannelBean channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
					mOtherAdapter.setVisible(false);
					//添加到最后一个
					mOtherAdapter.addItem(channel);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							try {
								int[] endLocation = new int[2];
								//获取终点的坐标
								mOtherGridView.getChildAt(mOtherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
								MoveAnim(moveImageView, startLocation , endLocation, channel, mMyGridView);
								mMyAdapter.setRemove(position);
							} catch (Exception localException) {
							}
						}
					}, 50L);
				}
			}
			break;
		case R.id.otherGridView:
			final ImageView moveImageView = getView(view);
			if (moveImageView != null){
				TextView newTextView = (TextView) view.findViewById(R.id.text_item);
				final int[] startLocation = new int[2];
				newTextView.getLocationInWindow(startLocation);
				final ChannelBean channel = ((MoreAdapter) parent.getAdapter()).getItem(position);
				mMyAdapter.setVisible(false);
				//添加到最后一个
				mMyAdapter.addItem(channel);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						try {
							int[] endLocation = new int[2];
							//获取终点的坐标
							mMyGridView.getChildAt(mMyGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
							MoveAnim(moveImageView, startLocation , endLocation, channel, mOtherGridView);
							mOtherAdapter.setRemove(position);
						} catch (Exception localException) {
						}
					}
				}, 50L);
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 点击ITEM移动动画
	 * @param moveView
	 * @param startLocation
	 * @param endLocation
	 * @param moveChannel
	 * @param clickGridView
	 */
	private void MoveAnim(View moveView, int[] startLocation,int[] endLocation,
						  final ChannelBean moveChannel, final GridView clickGridView) {
		int[] initLocation = new int[2];
		//获取传递过来的VIEW的坐标
		moveView.getLocationInWindow(initLocation);
		//得到要移动的VIEW,并放入对应的容器中
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
		//创建移动动画
		TranslateAnimation moveAnimation = new TranslateAnimation(
				startLocation[0], endLocation[0], startLocation[1],
				endLocation[1]);
		moveAnimation.setDuration(300L);//动画时间
		//动画配置
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		moveAnimationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				mIsMove = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);
				// instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
				if (clickGridView instanceof DragGrid) {
					mOtherAdapter.setVisible(true);
					mOtherAdapter.notifyDataSetChanged();
					mMyAdapter.remove();
				}else{
					mMyAdapter.setVisible(true);
					mMyAdapter.notifyDataSetChanged();
					mOtherAdapter.remove();
				}
				mIsMove = false;
			}
		});
	}
	
	/** 获取移动的VIEW，放入对应ViewGroup布局容器 */
	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		return view;
	}
	
	/** 创建移动的ITEM对应的ViewGroup布局容器*/
	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		return moveLinearLayout;
	}
	
	/** 获取点击的Item的对应View*/
	private ImageView getView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(cache);
		return iv;
	}
	
	/** 退出时候保存选择后数据库的设置  */
	private void saveChannel() {
		mChannelPresenter.resetChannelData(mMyAdapter.getChannnelList(),mOtherAdapter.getChannnelList());
	}
	
	@Override
	public void onBackPressed() {
		saveChannel();
		if(mMyAdapter.ismIsListChanged()) {
			mPresenter.reloadHeadList(mMyChannelList);//在此修改database
		}
		finish();
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	}
}
