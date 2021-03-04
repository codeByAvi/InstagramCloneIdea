package com.skill.aboutmy.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.skill.aboutmy.callbacks.OnResponseListener;
import com.skill.aboutmy.models.MediaItem;
import com.skill.aboutmy.models.MediaList;
import com.skill.aboutmy.repo.CloudRepo;
import com.skill.aboutmy.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel implements OnResponseListener {

    private static final String TAG = "AboutMy.MainViewModel";
    private CloudRepo cloudRepo;
    private SingleLiveEvent<MediaList> mediaListData;
    private SingleLiveEvent<MediaItem> mediaItemData;
    private List<MediaItem> mediaItemList;
    private boolean isLastPage, isLoadingNextMediaPage;
    private String userName = "";
    private String nextPageKey = "";


    public MainViewModel(@NonNull Application application) {
        super(application);
        cloudRepo = CloudRepo.getInstance(application);
        mediaListData = new SingleLiveEvent<>();
        mediaItemData = new SingleLiveEvent<>();
        this.mediaItemList = new ArrayList<>();
    }

    public void getInitialMediaListData() {
        Log.d(TAG,"getInitialMediaListData");

        if (mediaListData == null) {
            mediaListData = new SingleLiveEvent<>();
        }
        cloudRepo.fetchMediaData(this);
    }

    public void getNextPageMediaListData() {
        Log.d(TAG,"getNextPageMediaListData");

        if (mediaListData == null) {
            mediaListData = new SingleLiveEvent<>();
        }
        isLoadingNextMediaPage = true;
        cloudRepo.fetchNextPageMediaData(nextPageKey,this);
    }

    public MutableLiveData<MediaList> getMediaListData() {
        return mediaListData;
    }

    public LiveData<MediaItem> getMediaItem(String mediaId) {
        if (mediaItemData == null) {
            mediaItemData = new SingleLiveEvent<>();
            cloudRepo.fetchMediaDetailById(mediaId, this);
        }
        return mediaItemData;
    }

    public String getUserName() {
        return userName;
    }

    public List<MediaItem> getMediaItemList() {
        Log.d(TAG,"getMediaItemList = "+mediaItemList.size());
        return mediaItemList;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public boolean isLoadingNextMediaPage() {
        return isLoadingNextMediaPage;
    }


    @Override
    public void onMediaListReceived(MediaList mediaList) {
        Log.d(TAG,"onMediaListReceived "+mediaList.data.size());
        userName = mediaList.data.isEmpty()? userName : mediaList.data.get(0).username;
        mediaItemList.addAll(mediaList.data);
        isLastPage = mediaList.paging.next == null;
        nextPageKey = mediaList.paging.cursors.after != null ? mediaList.paging.cursors.after : "";//for pagenation
        isLoadingNextMediaPage = false;
        mediaListData.setValue(mediaList);
    }

    @Override
    public void onMediaByID(MediaItem mediaItem) {
        mediaItemData.setValue(mediaItem);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onNetworkFail() {
        Log.d(TAG, "network fail or no internet");
    }
}