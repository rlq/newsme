package com.he.func.frist;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class FristHeadScrollView extends HorizontalScrollView {
	/** 传入整体布局  */
	private View mContent;
	/** 屏幕宽度 */
	private int mScreenWitdh = 0;
	/** 父类的活动activity */
	private Activity activity;
	
	public FristHeadScrollView(Context context) {
		super(context);
	}

	public FristHeadScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FristHeadScrollView(Context context, AttributeSet attrs,
							   int defStyle) {
		super(context, attrs, defStyle);
	}
	/** 
	 * 在拖动的时候执行
	 * */
	@Override
	protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		// TODO Auto-generated method stub
		super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
	}
	public void setParam(Activity activity, int mScreenWitdh,View paramView1){
		this.activity = activity;
		this.mScreenWitdh = mScreenWitdh;
		mContent = paramView1;
	}
}
