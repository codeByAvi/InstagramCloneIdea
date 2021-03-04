package com.skill.aboutmy.ui.splash;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skill.aboutmy.R;
import com.skill.aboutmy.databinding.SplashFragmentBinding;
import com.skill.aboutmy.ui.main.MainActivity;
import com.skill.aboutmy.ui.main.MainFragment;
import com.skill.aboutmy.utils.AnimationUtils;
import com.skill.aboutmy.utils.MediaViewModelFactory;
import com.skill.aboutmy.viewmodels.MainViewModel;

import java.util.ArrayList;

public class SplashFragment extends Fragment {

    private static final String TAG = "AboutMy.SplashFragment";
    private MainViewModel mainViewModel;
    SplashFragmentBinding splashFragmentBinding;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    private SplashFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        splashFragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),R.layout.splash_fragment, container, false);
        return splashFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animator animation = AnimatorInflater.loadAnimator(getActivity(), R.animator.scale_in_out_repeater);
                animation.setTarget(splashFragmentBinding.tvForSplashLoadingText);
                animation.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        mainViewModel = new ViewModelProvider(getActivity(),new MediaViewModelFactory(getActivity().getApplication())).get(MainViewModel.class);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // Fragment locked in portrait screen orientation
        mainViewModel.getInitialMediaListData(); //fetch initial media list api
        subscribeObservers();
    }

    void subscribeObservers(){

        mainViewModel.getMediaListData().observe(getViewLifecycleOwner(), mediaList -> {
            Log.d(TAG, "initial list received");
            MainFragment mainFragment = MainFragment.newInstance();
            mainFragment.setRetainInstance(true);

            //launching fragment with force delay to demonstrate splash animation
            new Handler().postDelayed(() -> getParentFragmentManager().beginTransaction()
                    .addSharedElement(splashFragmentBinding.tvForAppName,ViewCompat.getTransitionName(splashFragmentBinding.tvForAppName))
                    .replace(((ViewGroup)getView().getParent()).getId(),mainFragment )
                    .commitNow(),3000);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainViewModel.getMediaListData().removeObservers(getViewLifecycleOwner());
    }
}