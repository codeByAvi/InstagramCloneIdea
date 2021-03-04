package com.skill.aboutmy.utils;

import android.animation.ValueAnimator;
import android.widget.TextView;

public class AnimationUtils {

    /**
     * value animator that works towards resembling text change animation
     * @param textView
     * @param fromNumber
     * @param toNumber
     * @param duration
     */
    public static void startCountAnimation(TextView textView,int fromNumber,int toNumber,int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(fromNumber, toNumber);
        animator.setDuration(duration);
        animator.addUpdateListener(animation -> textView.setText(animation.getAnimatedValue().toString()));
        animator.start();
    }
}
