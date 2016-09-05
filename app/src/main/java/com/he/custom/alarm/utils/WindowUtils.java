package com.he.custom.alarm.utils;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowInsets;

/**
 * Created by lqren on 16/7/16.
 */
public class WindowUtils {

    /**
     * Clip given window to round
     * This method should be called before window is show
     */
    public static boolean clipToScreenShape(final Window window){
        if(window == null || window.getDecorView() == null){
            return  false;
        }

        final Drawable original = window.getDecorView().getBackground();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setOnApplyWindowInsetsListener(
                new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Drawable background = getBackgroundDrawable(original, windowInsets.isRound());
                window.setBackgroundDrawable(background);
                if(windowInsets.isRound()){
                    clipToROund(view);
                }

                return windowInsets;
            }
        });
        window.getDecorView().requestApplyInsets();
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static void clipToROund(@NonNull View view){
        view.setClipToOutline(true);
        view.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(-1, -1, view.getWidth() + 1, view.getHeight() + 1);
            }
        });
    }

    @NonNull
    static Drawable getBackgroundDrawable(Drawable origial, boolean isRound){
        if(origial == null){
            origial = new ColorDrawable(Color.BLACK);
        }
        if(isRound){
            return new InfectCircleDrawable(origial);
        }else {
            return origial;
        }
    }

    // this class extends CircleDrawable extends ClipPathDrawable extends Drawable
    public static class InfectCircleDrawable extends Drawable {

       public InfectCircleDrawable(Drawable origial){

        }

        @Override
        public void draw(Canvas canvas) {

        }

        @Override
        public void setAlpha(int i) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }
}


