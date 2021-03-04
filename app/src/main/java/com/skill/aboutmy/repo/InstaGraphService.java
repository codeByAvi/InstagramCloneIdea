package com.skill.aboutmy.repo;


import com.skill.aboutmy.models.AuthToken;
import com.skill.aboutmy.models.MediaItem;
import com.skill.aboutmy.models.MediaList;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface InstaGraphService {

    @GET("me/media?fields=id,caption,media_url,media_type,username,thumbnail_url,timestamp")
    Single<MediaList> getMyMediaList(@Query("access_token") String token);

    @GET("v1.0/{userId}/media?fields=id,caption,media_url,media_type,username,thumbnail_url,timestamp")
    Single<MediaList> getMyNextPageMediaList(@Path("userId") String userId,@Query("access_token") String token,@Query("limit") String limit,@Query("after") String afterKey);

    @GET("{id}?fields=id,caption,media_url,media_type,username,thumbnail_url,timestamp")
    Single<MediaItem> getMediaDetailsById(@Path("id") String mediaId, @Query("access_token") String token);
}
