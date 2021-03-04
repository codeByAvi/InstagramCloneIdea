package com.skill.aboutmy.ui.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skill.aboutmy.R;
import com.skill.aboutmy.databinding.LayoutBottomMediaItemSheetBinding;
import com.skill.aboutmy.models.MediaItem;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     MediaItemBottomSheet.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class MediaItemBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_MEDIA_ITEM = "media_item";
    private MediaItem mediaItem;

    private LayoutBottomMediaItemSheetBinding layoutBottomMediaItemSheetBinding;
    public static MediaItemBottomSheet newInstance(MediaItem mediaItem) {
        final MediaItemBottomSheet fragment = new MediaItemBottomSheet();
        final Bundle args = new Bundle();
        args.putSerializable(ARG_MEDIA_ITEM, mediaItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        layoutBottomMediaItemSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.layout_bottom_media_item_sheet,container,false);
        return  layoutBottomMediaItemSheetBinding.getRoot();
    }

//    @SuppressLint("RestrictedApi")
//    @Override
//    public void setupDialog(@NonNull Dialog dialog, int style) {
//        super.setupDialog(dialog, style);
//        layoutBottomMediaItemSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.layout_bottom_media_item_sheet,container,false);
//
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mediaItem = (MediaItem) getArguments().getSerializable(ARG_MEDIA_ITEM);
        layoutBottomMediaItemSheetBinding.setMediaItem(mediaItem);
        layoutBottomMediaItemSheetBinding.executePendingBindings();
        layoutBottomMediaItemSheetBinding.tvForMediaCaption.setMovementMethod(new ScrollingMovementMethod());
        layoutBottomMediaItemSheetBinding.actionMenuEdit.setOnClickListener(v->{
            Toast.makeText(getContext(),getString(R.string.edit_toast),Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) layoutBottomMediaItemSheetBinding.getRoot().getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View view, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss();
                    }
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View view, float v) {

                }
            });
        }
    }
}