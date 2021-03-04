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


public interface InstaApiService {

    @POST("oauth/access_token")
    @FormUrlEncoded
    Single<AuthToken> authorize(@FieldMap Map<String,String> params);
}
