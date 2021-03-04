package com.skill.aboutmy.ui.main;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.skill.aboutmy.R;
import com.skill.aboutmy.databinding.MediaDetailLayoutBinding;
import com.skill.aboutmy.databinding.SplashFragmentBinding;
import com.skill.aboutmy.models.MediaItem;
import com.skill.aboutmy.utils.Constants;
import com.skill.aboutmy.utils.MediaViewModelFactory;
import com.skill.aboutmy.viewmodels.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MediaDetailFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AboutMy.SplashFragment";
    private static final String ARG_MEDIA_ITEM = "media_item";
    private static final String ARG_MEDIA_ITEM_TRANSITION = "media_item_transition";

    private MediaItem mediaItem;
    private MainViewModel mainViewModel;
    private MediaDetailLayoutBinding mediaDetailLayoutBinding;
    private SimpleDateFormat fromDateFormat,toDateFormat;


    public static MediaDetailFragment newInstance(Bundle bundle) {
        final MediaDetailFragment fragment = new MediaDetailFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ARG_MEDIA_ITEM, bundle.getSerializable(Constants.MEDIA_ITEM_ARG_KEY));
        args.putString(ARG_MEDIA_ITEM_TRANSITION,bundle.getString(Constants.MEDIA_ITEM_TRANSITION_KEY));
        fragment.setArguments(args);
        return fragment;
    }

    private MediaDetailFragment(){
        fromDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        toDateFormat =  new SimpleDateFormat("MMM dd, yyyy");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mediaDetailLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),R.layout.media_detail_layout, container, false);
        mediaDetailLayoutBinding.setLifecycleOwner(getViewLifecycleOwner());
        return mediaDetailLayoutBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Transition transition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.shared_image_transform);
        setSharedElementEnterTransition(transition);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorBlackBackground));
        mediaItem = (MediaItem) getArguments().getSerializable(ARG_MEDIA_ITEM);
        mediaDetailLayoutBinding.imgForMediaImage.setTransitionName(getArguments().getString(ARG_MEDIA_ITEM_TRANSITION));
        //+ set binding data
        mediaDetailLayoutBinding.setFromDateFormat(fromDateFormat);
        mediaDetailLayoutBinding.setToDateFormat(toDateFormat);
        mediaDetailLayoutBinding.setMediaItem(mediaItem);
        mediaDetailLayoutBinding.executePendingBindings();
        //- set binding data
        mediaDetailLayoutBinding.actionMediaMenu.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mainViewModel = new ViewModelProvider(getActivity(),new MediaViewModelFactory(getActivity().getApplication())).get(MainViewModel.class);

        subscribeObservers();
    }

    void subscribeObservers(){
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_media_menu:
                showPopupMenu(v);
                break;
        }
    }

    /**
     * Disply popup menu
     * @param anchorView anchor view to display popup
     */
    public void showPopupMenu(View anchorView) {
        PopupMenu popup = new PopupMenu(getContext(), anchorView);
        popup.getMenuInflater().inflate(R.menu.media_detail_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> true);
        popup.show();
    }
}