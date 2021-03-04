package com.skill.aboutmy.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.skill.aboutmy.R;

public class CircularImage extends AppCompatImageView {

    public CircularImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
            setOutlineProvider(ViewOutlineProvider.BACKGROUND);
            setClipToOutline(true);
            setBackground(context.getDrawable(R.drawable.circular_image_bg));
            setScaleType(ScaleType.CENTER_CROP);
    }

    public CircularImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
