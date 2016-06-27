package com.he.func.first.channel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MoreGridView extends GridView {

	public MoreGridView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	/**onMeasure函数决定了组件显示的高度与宽度
	 * MeasureSpec.AT_MOST  子控件需要多大的控件就扩展到多大空间
	 * */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);//第一个参数决定布局空间的大小，第二个参数是布局模式
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	
}
