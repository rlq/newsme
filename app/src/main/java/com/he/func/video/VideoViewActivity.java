/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.he.func.video;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.he.config.KeyConfig;
import com.he.util.Utils;
import com.lq.ren.news.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.utils.StringUtils;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import master.flame.danmaku.danmaku.util.IOUtils;

/**  播放在线视频 */
public class VideoViewActivity extends Activity  {
    public static final String VIDEO_PATH="videopath";
    private VideoView mVideoView;
    private LinearLayout mLoadingLayout;
    private ImageView mLoadingImg;
    private ObjectAnimator mOjectAnimator;

    /** 当前进度 */
    private Long currentPosition = (long) 0;
    private String mVideoPath = "";
    /** setting */
    private boolean needResume;

    /** 弹幕 */
    private IDanmakuView mDanmakuView;
    private DanmakuContext mContext;
    private BaseDanmakuParser mParser;
    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {
        private Drawable mDrawable;
        @Override
        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
            if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
                // FIXME 这里只是简单启个线程来加载远程url图片，请使用你自己的异步线程池，最好加上你的缓存池
                new Thread() {

                    @Override
                    public void run() {
                        String url = "http://www.bilibili.com/favicon.ico";
                        InputStream inputStream = null;
                        Drawable drawable = mDrawable;
                        if(drawable == null) {
                            try {
                                URLConnection urlConnection = new URL(url).openConnection();
                                inputStream = urlConnection.getInputStream();
                                drawable = BitmapDrawable.createFromStream(inputStream, "bitmap");
                                mDrawable = drawable;
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                IOUtils.closeQuietly(inputStream);
                            }
                        }
                        if (drawable != null) {
                            drawable.setBounds(0, 0, 100, 100);
                            SpannableStringBuilder spannable = createSpannable(drawable);
                            danmaku.text = spannable;
                            if(mDanmakuView != null) {
                                mDanmakuView.invalidateDanmaku(danmaku, false);
                            }
                            return;
                        }
                    }
                }.start();
            }
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            // TODO 重要:清理含有ImageSpan的text中的一些占用内存的资源 例如drawable
        }
    };

    /**
     * 视频播放控制界面
     */
    CustomVideoController mediaController;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!LibsChecker.checkVitamioLibs(this)) return;
        setContentView(R.layout.he_video_play);
        //getDataFromIntent();
        mVideoPath = "http://125.39.142.86/data2/video09/2016/03/01/3871799-102-1615.mp4";
        initviews();
        initTanMuViews();
        initVideoSettings();
    }

    /**
     * 初始化弹幕相关
     */
    private void initTanMuViews() {
        mediaController = new CustomVideoController(this);
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        //初始化
        mDanmakuView = (IDanmakuView) findViewById(R.id.sv_danmaku);
        mContext = DanmakuContext.create();
        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
        if (mDanmakuView != null) {
            mParser = createParser(this.getResources().openRawResource(R.raw.comments));
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
//                    Log.d("DFM", "danmakuShown(): text=" + danmaku.text);
                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
                }
            });
            mDanmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {
                @Override
                public void onDanmakuClick(BaseDanmaku latest) {
                    Log.d("DFM", "onDanmakuClick text:" + latest.text);
                }
                @Override
                public void onDanmakuClick(IDanmakus danmakus) {
                    Log.d("DFM", "onDanmakuClick danmakus size:" + danmakus.size());
                }
            });
            mDanmakuView.showFPS(true);
//          mDanmakuView.prepare(mParser, mContext);
            mediaController.setTanMuView(mDanmakuView,mContext,mParser);
            mDanmakuView.enableDanmakuDrawingCache(true);
            ((View) mDanmakuView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaController.show();
                }
            });
        }
    }
    private BaseDanmakuParser createParser(InputStream stream) {
        if (stream == null) {
            return new BaseDanmakuParser() {
                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }
        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);
        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }
    private SpannableStringBuilder createSpannable(Drawable drawable) {
        String text = "bitmap";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        ImageSpan span = new ImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("图文混排");
        spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#8A2233B1")), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }


    private void getDataFromIntent() {
        Intent Intent = getIntent();
        if (Intent != null && Intent.getExtras().containsKey(VIDEO_PATH)) {
            mVideoPath = Intent.getExtras().getString(VIDEO_PATH);
        }
    }

    private void initviews() {
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mLoadingLayout=(LinearLayout) findViewById(R.id.loading_LinearLayout);
        mLoadingImg=(ImageView) findViewById(R.id.loading_image);
    }

    private void initVideoSettings() {
        mVideoView.requestFocus();
        mVideoView.setBufferSize(1024 * 1024);
        mVideoView.setVideoChroma(MediaPlayer.VIDEOCHROMA_RGB565);
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoPath(mVideoPath);
    }

    public void onResume() {
        super.onResume();
        preparePlayVideo();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    private void preparePlayVideo() {
        startLoadingAnimator();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // TODO Auto-generated method stub
                stopLoadingAnimator();

                if (currentPosition > 0) {
                    mVideoView.seekTo(currentPosition);
                } else {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
                startPlay();
            }
        });
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
                switch (arg1) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //开始缓存，暂停播放
                        Log.i(KeyConfig.TAG_NAME, "开始缓存");
                        startLoadingAnimator();
                        if (mVideoView.isPlaying()) {
                            stopPlay();
                            needResume = true;
                        }
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //缓存完成，继续播放
                        stopLoadingAnimator();
                        if (needResume) startPlay();
                        Log.i(KeyConfig.TAG_NAME, "缓存完成");
                        break;
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        //显示 下载速度
                        Log.i(KeyConfig.TAG_NAME,"download rate:" + arg2);
                        break;
                }
                return true;
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
//                LogUtils.i(LogUtils.LOG_TAG, "what=" + what);
                return false;
            }
        });
        mVideoView.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
            }
        });
        mVideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.i(KeyConfig.TAG_NAME, "percent" + percent);
            }
        });
    }

    @NonNull
    private void startLoadingAnimator() {
        if(mOjectAnimator==null){
            mOjectAnimator = ObjectAnimator.ofFloat(mLoadingImg, "rotation", 0f, 360f);
        }
        mLoadingLayout.setVisibility(View.VISIBLE);

        mOjectAnimator.setDuration(1000);
        mOjectAnimator.setRepeatCount(-1);
        mOjectAnimator.start();
    }

    private void stopLoadingAnimator() {
        mLoadingLayout.setVisibility(View.GONE);
        mOjectAnimator.cancel();
    }

    private void startPlay() {
        mVideoView.start();
    }

    private void stopPlay() {
        mVideoView.pause();
    }

    public void onPause() {
        super.onPause();
        currentPosition = mVideoView.getCurrentPosition();
        mVideoView.pause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(mVideoView!=null){
            mVideoView.stopPlayback();
            mVideoView = null;
        }
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    /**
     * 获取视频当前帧
     * @return
     */
    public Bitmap getCurrentFrame() {
        if(mVideoView!=null){
            MediaPlayer mediaPlayer = mVideoView.getmMediaPlayer();
            return  mediaPlayer.getCurrentFrame();
        }
        return null;
    }
    /**
     * 快退(每次都快进视频总时长的1%)
     */
    public void speedVideo() {
        if(mVideoView!=null){
            long duration = mVideoView.getDuration();
            long currentPosition = mVideoView.getCurrentPosition();
            long goalduration=currentPosition+duration/10;
            if(goalduration>=duration){
                mVideoView.seekTo(duration);
            }else{
                mVideoView.seekTo(goalduration);
            }
            Utils.showTips(this, StringUtils.generateTime(goalduration));
        }
    }

    /**
     * 快退(每次都快退视频总时长的1%)
     */
    public void reverseVideo() {
        if(mVideoView!=null){
            long duration = mVideoView.getDuration();
            long currentPosition = mVideoView.getCurrentPosition();
            long goalduration=currentPosition-duration/10;
            if(goalduration<=0){
                mVideoView.seekTo(0);
            }else{
                mVideoView.seekTo(goalduration);
            }
            Utils.showTips(this, StringUtils.generateTime(goalduration));
        }
    }
    /**
     * 设置屏幕的显示大小
     */
    public void setVideoPageSize(int currentPageSize) {
        if(mVideoView!=null){
            mVideoView.setVideoLayout(currentPageSize,0);
        }
    }
}
