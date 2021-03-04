package com.skill.aboutmy.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.skill.aboutmy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class which handles all the bindings between data passed to UI layouts and views.
 *
 *  This Factory acts as the one stop location for all binding adapters between custom binding attributes and Views
 */
public class BindingAdapterFactory {

    @BindingAdapter({"loadThumbnail", "context"})
    public static void loadMediaThumbnail(ImageView view, String imageUrl, Context context) {
        Glide.with(view.getContext()).load(imageUrl).placeholder(new ColorDrawable(Color.GRAY))
                .into(view);
    }

    @BindingAdapter({"loadAdaptiveSizeMediaThumbnail", "context"})
    public static void loadAdaptiveSizeMediaThumbnail(ImageView imageView,String imageUrl,Context context){
        Glide.with(context.getApplicationContext())
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable com.bumptech.glide.request.transition.Transition<? super Bitmap> transition) {

                /*
                        requestLayout()
                        Call this when something has changed which has
                        invalidated the layout of this view.
                */
                        imageView.requestLayout();
                        imageView.getLayoutParams().height = bitmap.getHeight();
                        imageView.getLayoutParams().width = bitmap.getWidth();


                        // Set the scale type for ImageView image scaling
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @BindingAdapter({"loadRoundThumbnail"})
    public static void loadRoundThumbnail(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).placeholder(new ColorDrawable(Color.GRAY)).into(view);
    }

    @BindingAdapter({"fromDateFormat", "toDateFormat"})
    public static void setDateText(TextView view, SimpleDateFormat fromDateFormat, SimpleDateFormat toDateFormat) {
        try {
            Date fromDate = fromDateFormat.parse((String) view.getText());
//            String toDate = toDateFormat.format(fromDate);
            view.setText(toDateFormat.format(fromDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"mediaType"})
    public static void setMediaType(ImageView view, String mediaType) {
        view.setVisibility(mediaType .contentEquals( MediaType.IMAGE.getType()) ? View.GONE : View.VISIBLE);
        if (view.getVisibility() == View.GONE) return;
        if (mediaType .contentEquals( MediaType.VIDEO.getType())) {
            view.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
        } else if (mediaType .contentEquals( MediaType.CAROUSEL_ALBUM.getType())) {
            view.setImageResource(R.drawable.ic_baseline_collections_24);
        }
    }
}