package com.hcmute.trietthao.yourtime.service.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;

public class AnimationUtils {

    public static void setFadeInAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    public static void setFadeInTime(View view,int time) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(time);
        view.startAnimation(anim);
    }

    public static void setFadeOutTime(View view,int time) {
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(time);
        view.startAnimation(anim);
    }

    public static void setFadeOutAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

}