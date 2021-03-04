package com.skill.aboutmy.repo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.skill.aboutmy.MyApplication;
import com.skill.aboutmy.callbacks.AuthResponseListener;
import com.skill.aboutmy.callbacks.OnResponseListener;
import com.skill.aboutmy.models.AuthToken;
import com.skill.aboutmy.models.MediaItem;
import com.skill.aboutmy.models.MediaList;
import com.skill.aboutmy.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CloudRepo {


    public Context context;
    private static CloudRepo cloudRepo;
    private MutableLiveData<MediaItem> mediaItemLiveData = new MutableLiveData<>();
    private MutableLiveData<AuthToken> authTokenLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isMediaListApiFailure = new MutableLiveData<>();
    private MutableLiveData<Boolean> isMediaItemApiFailure = new MutableLiveData<>();
//    private AuthToken authToken;
    private InstaApiService instaApiService;
    private InstaGraphService instaGraphService;


    private CloudRepo(Context context) {
        this.context = context;
        instaApiService = MyApplication.getContext().getRetrofitApiInstance().create(InstaApiService.class);
        instaGraphService = MyApplication.getContext().getRetrofitGraphInstance().create(InstaGraphService.class);
    }

    public static CloudRepo getInstance(Context context) {
        synchronized (context) {
            if (cloudRepo == null)
                cloudRepo = new CloudRepo(context);
            return cloudRepo;
        }
    }


//    public void authorize(String authCode, AuthResponseListener authResponseListener) {
//        Map<String,String> postParams = new HashMap<>();
//        postParams.put("client_id", Constants.CLIENT_ID);
//        postParams.put("client_secret", Constants.CLIENT_SECRET);
//        postParams.put("grant_type", Constants.GRANT_TYPE);
//        postParams.put("redirect_uri", Constants.REDIRECT_URI);
//        postParams.put("code",authCode);
//
//        instaApiService.authorize(postParams).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(result-> authResponseListener.onAuthorized(result),
//                        error -> authResponseListener.onError(error.getMessage()));
//    }
    /**
     * fetches media from api category wise
     * @param
     * @return
     */
    public void fetchMediaData(OnResponseListener onResponseListener) {
        if (!isNetWorkConnected()) {
            onResponseListener.onNetworkFail();
            return;
        }
        instaGraphService.getMyMediaList(Constants.AUTH_TOKEN).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(result -> onResponseListener.onMediaListReceived(result),
                        error -> onResponseListener.onError(error.getMessage()));
    }

    public void fetchNextPageMediaData(String nextPageKey,OnResponseListener onResponseListener) {
        if (!isNetWorkConnected()) {
            onResponseListener.onNetworkFail();
            return;
        }
        instaGraphService.getMyNextPageMediaList(Constants.MY_INSTA_USER_ID,Constants.AUTH_TOKEN,"25",nextPageKey).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(result -> onResponseListener.onMediaListReceived(result),
                        error -> onResponseListener.onError(error.getMessage()));
    }

    public void fetchMediaDetailById(String mediaId,OnResponseListener onResponseListener) {
        if (!isNetWorkConnected()) {
            onResponseListener.onNetworkFail();
            return;
        }
        instaGraphService.getMediaDetailsById(mediaId,Constants.AUTH_TOKEN).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(result -> onResponseListener.onMediaByID(result),
                        error -> onResponseListener.onError(error.getMessage()));
    }



    public MutableLiveData<Boolean> getIsMediaListApiFailure() {
        return isMediaListApiFailure;
    }

    public MutableLiveData<Boolean> getIsMediaItemApiFailure() {
        return isMediaItemApiFailure;
    }

    boolean isNetWorkConnected() {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
