package com.he.custom.course;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

import com.lq.ren.news.R;


/**
 * Created by lqren on 16/8/13.
 */
public class Draw4Shader extends View{

    private Bitmap mBitQQ = null;
    private int mBitQQWidth = 0;
    private int mBitQQHeight = 0;
    private Paint mPaint = null;
    //bitmap shader
    private Shader mBitmapShader = null;
    private Shader mLinearGradient = null;//线性渐变
    private Shader mComposeShader = null;//混合渐变
    private Shader mRadialGrandient = null;//唤醒渐变
    private Shader mSweepGrandient = null;//梯度
    private ShapeDrawable mShapeDrawableQQ = null;

    public Draw4Shader(Context context) {
        super(context);

        //装载资源  qq图片本来是矩形,先渲染出圆形
        mBitQQ = ((BitmapDrawable)getResources().getDrawable(R.drawable.qq)).getBitmap();
        mBitQQWidth = mBitQQ.getWidth();
        mBitQQHeight = mBitQQ.getHeight();
        //
        mBitmapShader = new BitmapShader(mBitQQ, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);

        mLinearGradient = new LinearGradient(0, 0, 100, 100, new int[]{
                Color.GREEN, Color.RED, Color.BLUE, Color.WHITE
        }, null, Shader.TileMode.REPEAT);

        //compose 混合渲染模式有很多种
        mComposeShader = new ComposeShader(mBitmapShader, mLinearGradient, PorterDuff.Mode.DARKEN);

        //RadialGradient 设置半径属性,
        mRadialGrandient = new RadialGradient(50, 200, 50, new int[]{
          Color.GREEN, Color.RED, Color.BLUE, Color.WHITE
        }, null, Shader.TileMode.REPEAT);


        mSweepGrandient = new SweepGradient(30, 30, new int[]{
           Color.GREEN, Color.RED, Color.BLUE, Color.WHITE
        }, null);

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        //将图片裁剪成椭圆形
        //并将shapeDrawable 创建为椭圆形
        mShapeDrawableQQ = new ShapeDrawable(new OvalShape());
        //set shader
        mShapeDrawableQQ.getPaint().setShader(mBitmapShader);
        //设置显示区域
        mShapeDrawableQQ.setBounds(0, 0, mBitQQWidth, mBitQQHeight);
        //draw
        mShapeDrawableQQ.draw(canvas);

        //linear
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(mBitQQWidth, 0 , 320, 156, mPaint);



        //circle
        mPaint.setShader(mRadialGrandient);
        canvas.drawCircle(50, 200, 50, mPaint);

        //sweep
        mPaint.setShader(mSweepGrandient);
        canvas.drawRect(150, 160, 300, 300, mPaint);

        //compose
        mPaint.setShader(mComposeShader);
        canvas.drawRect(0, 300, mBitQQHeight, 300 + mBitQQHeight, mPaint);
    }
}
