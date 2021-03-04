package com.skill.aboutmy.ui.main;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skill.aboutmy.R;
import com.skill.aboutmy.adapter.MediaListAdapter;
import com.skill.aboutmy.callbacks.OnMediaItemCallbackListener;
import com.skill.aboutmy.databinding.MainFragmentBinding;
import com.skill.aboutmy.models.MediaItem;
import com.skill.aboutmy.utils.AnimationUtils;
import com.skill.aboutmy.utils.Constants;
import com.skill.aboutmy.utils.MediaViewModelFactory;
import com.skill.aboutmy.utils.PageScrollListener;
import com.skill.aboutmy.viewmodels.MainViewModel;



public class MainFragment extends Fragment implements OnMediaItemCallbackListener, View.OnClickListener {

    private static final String TAG = "AboutMy.MainFragment";
    private MainViewModel mViewModel;
    private MainFragmentBinding mainFragmentBinding;
    MediaListAdapter mediaListAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainFragmentBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.main_fragment, container, false);
        mainFragmentBinding.setLifecycleOwner(getViewLifecycleOwner());
        return mainFragmentBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transition transition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.shared_image_transform);
        setSharedElementEnterTransition(transition);

        Transition transitionExit = TransitionInflater.from(requireContext())
                .inflateTransition(android.R.transition.no_transition);
        setExitTransition(transitionExit);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mainFragmentBinding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        mainFragmentBinding.recyclerView.setHasFixedSize(true);
        mainFragmentBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mediaListAdapter = new MediaListAdapter(this);
        mainFragmentBinding.recyclerView.setAdapter(mediaListAdapter);
        mainFragmentBinding.fabToScrollUp.hide(); //initial state
        mainFragmentBinding.fabToScrollUp.setOnClickListener(this);
        AnimationUtils.startCountAnimation(mainFragmentBinding.acountInfoLayout.tvForFollowersCount, 400, 520, 3000);
        AnimationUtils.startCountAnimation(mainFragmentBinding.acountInfoLayout.tvForPostsCount, 45, 78, 3000);
        AnimationUtils.startCountAnimation(mainFragmentBinding.acountInfoLayout.tvForFollowingCount, 245, 350, 3000);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
        mViewModel = new ViewModelProvider(getActivity(), new MediaViewModelFactory(getActivity().getApplication())).get(MainViewModel.class);

        //+ set binding data
        mainFragmentBinding.setMainViewModel(mViewModel);
        mainFragmentBinding.executePendingBindings();
        //- set binding data

        mediaListAdapter.addMediaItemList(mViewModel.getMediaItemList());
        mediaListAdapter.notifyDataSetChanged();
        subscribeObservers();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        mainFragmentBinding.recyclerView.addOnScrollListener(new PageScrollListener(staggeredGridLayoutManager) {
            @Override
            protected void loadNextPage() {
                mediaListAdapter.addLoadingFooter();
                mViewModel.getNextPageMediaListData();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && mainFragmentBinding.fabToScrollUp.isShown()) {
                    mainFragmentBinding.fabToScrollUp.hide();
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isOnFirstPage) {
                    mainFragmentBinding.fabToScrollUp.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public boolean isLastPage() {
                return mViewModel.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return mViewModel.isLoadingNextMediaPage();
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void subscribeObservers() {
        mViewModel.getMediaListData().observe(getViewLifecycleOwner(), mediaList -> {
            Log.d(TAG, "size = " + mediaList.data.size());
            mediaListAdapter.removeLoadingFooter();
            mediaListAdapter.addMediaItemList(mediaList.data);
        });
    }

    @Override
    public void onItemMenuClick(int position) {
        MediaItemBottomSheet mediaItemBottomSheet = MediaItemBottomSheet.newInstance(mediaListAdapter.getMediaItemList().get(position));
        mediaItemBottomSheet.show(getChildFragmentManager(), "mediaItemBottomSheet");
    }

    @Override
    public void onMediaClicked(int position, AppCompatImageView sharedImageView) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.MEDIA_ITEM_TRANSITION_KEY, MediaListAdapter.MEDIA_ITEM_TRANSITION_KEY + position);
        bundle.putSerializable(Constants.MEDIA_ITEM_ARG_KEY, mediaListAdapter.getMediaItemList().get(position));
        MediaDetailFragment mediaDetailFragment = MediaDetailFragment.newInstance(bundle);

        // launch MediaDetailFragment with current clicked item along with shared transition element
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(((ViewGroup) getView().getParent()).getId(), mediaDetailFragment)
                .addSharedElement(sharedImageView, MediaListAdapter.MEDIA_ITEM_TRANSITION_KEY + position)
                .hide(this)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabToScrollUp:
                mainFragmentBinding.fabToScrollUp.hide();
                mainFragmentBinding.recyclerView.scrollToPosition(0);
                break;
        }
    }
}