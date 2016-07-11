package com.he.func.video;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.he.config.KeyConfig;
import com.he.util.BitmapUtil;
import com.he.util.Utils;
import com.lq.ren.news.R;

import java.io.File;
import java.util.Random;

import io.vov.vitamio.widget.MediaController;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

public class CustomVideoController extends MediaController {
    private Context mContext;
    private View mVolumeBrightnessLayout;
    private ImageView mOperationBg;
    private ImageView mOperationPercent;
    private AudioManager mAudioManager;
    private int mMaxVolume;
    private int mVolume = -1;
    private float mBrightness = -1f;
    private GestureDetector mGestureDetector;

    VideoViewActivity activity;
    private ImageView mShot;
    private ImageView mPrevious;
    private ImageView mNext;
    private ImageView mScrennfit;

    private String[] strDialogs=new String[]{"100%","全屏","拉伸","裁剪"};
    private int[] imgs=new int[]{R.drawable.he_media_sreen_size_100, R.drawable.he_video_screen_fit,
            R.drawable.he_media_screen_size,R.drawable.he_media_sreen_size_crop};
    private int mCurrentPageSize=2;

    private TextView currenttime_tv;

    /**
     * 弹幕相关
     */
    private IDanmakuView mDanmakuView;
    private Switch mtanMuSwitch;
    private DanmakuContext mDanmakuContext;
    private BaseDanmakuParser mParser;

    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mVolumeBrightnessLayout.setVisibility(View.GONE);
            }
        }
    };

    public CustomVideoController(Context context) {
        super(context);
        this.mContext = context;
        activity=(VideoViewActivity)context;
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mGestureDetector = new GestureDetector(mContext, new VolumeBrightnesGestureListener());
    }

    @Override
    protected View makeControllerView() {
        return ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                inflate(R.layout.he_videocontroller, this);
        //getResources().getIdentifier("he_videocontroller", "layout", mContext.getPackageName() 代替R.layout.xx
    }

    @Override
    protected void initOtherView() {
        mtanMuSwitch= (Switch) mRoot.findViewById(R.id.switch_tanmu);
        mtanMuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mtanMuSwitch.setBackgroundColor(getResources().getColor(R.color.color_red));
                    //开启弹幕
                    mDanmakuView.prepare(mParser, mDanmakuContext);
                    mDanmakuView.show();
                }else{
                    mtanMuSwitch.setBackgroundColor(getResources().getColor(R.color.color_gray));
                    //关闭弹幕
                    mDanmakuView.hide();
                }
            }
        });
        mShot = (ImageView) mRoot.findViewById(R.id.mediacontroller_snapshot);
        mPrevious = (ImageView) mRoot.findViewById(R.id.mediacontroller_previous);
        mNext = (ImageView) mRoot.findViewById(R.id.mediacontroller_next);
        mScrennfit = (ImageView) mRoot.findViewById(R.id.mediacontroller_screen_fit);
        mShot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap currentFrame = activity.getCurrentFrame();
                //保存到本地
                String picturnPath= activity.getExternalCacheDir()+File.separator+ (new Random().nextInt(6))+".jpg";//RandomUtil.getRandomLetters(6)
                boolean saveSuccess = BitmapUtil.saveBitmap(currentFrame, new File(picturnPath));
                if(saveSuccess){
                    Utils.showTips(activity,"截屏保存到"+picturnPath);
                }else{
                    Utils.showTips(activity,"截屏失败");
                }
            }
        });
        mPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.reverseVideo();
            }
        });
        mNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.speedVideo();
            }
        });
        mScrennfit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPageSize++;
                if(mCurrentPageSize>3){
                    mCurrentPageSize=0;
                }
                Utils.showTips(activity,strDialogs[mCurrentPageSize]);
                mScrennfit.setBackground(getResources().getDrawable(imgs[mCurrentPageSize]));
                activity.setVideoPageSize(mCurrentPageSize);
            }
        });
        currenttime_tv=(TextView) mRoot.findViewById(R.id.currenttime_tv);

        mVolumeBrightnessLayout = mRoot.findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView) mRoot.findViewById(R.id.operation_bg);
        mOperationPercent = (ImageView) mRoot.findViewById(R.id.operation_percent);
        mRoot.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(KeyConfig.TAG_NAME, "onTouchEvent");
                if (mGestureDetector.onTouchEvent(event)) {
                    return true;
                }
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        endGesture();
                        break;
                }
                return false;
            }
        });
    }

    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;
        // 隐藏
        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);
    }

    public void setTanMuView(IDanmakuView tanMuView, DanmakuContext mDanmakuContext, BaseDanmakuParser mParser ) {
        this.mDanmakuView = tanMuView;
        this.mDanmakuContext=mDanmakuContext;
        this.mParser=mParser;
    }

    private class VolumeBrightnesGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            Display disp = ((Activity) mContext).getWindowManager().getDefaultDisplay();
            int windowWidth = disp.getWidth();
            int windowHeight = disp.getHeight();
            if (mOldX > windowWidth * 4.0 / 5) {
                onVolumeSlide((mOldY - y) / windowHeight);
                return true;
            } else if (mOldX < windowWidth / 5.0) {
                onBrightnessSlide((mOldY - y) / windowHeight);
                return true;
            }
            return false;
        }
    }

    /**
     * 声音高低
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            mOperationBg.setImageResource(R.drawable.he_video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = findViewById(R.id.operation_full).getLayoutParams().width * index / mMaxVolume;
        mOperationPercent.setLayoutParams(lp);
    }

    /**
     * 处理屏幕亮暗
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = ((Activity) mContext).getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            mOperationBg.setImageResource(R.drawable.he_video_brightness_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        WindowManager.LayoutParams lpa = ((Activity) getContext()).getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        ((Activity) mContext).getWindow().setAttributes(lpa);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = (int) (findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
        mOperationPercent.setLayoutParams(lp);
    }

}
