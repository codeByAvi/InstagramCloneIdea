package com.skill.aboutmy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthToken {
    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("user_id")
    @Expose
    public Integer userId;


    public String getAccessToken() {
        return accessToken;
    }

    public Integer getUserId() {
        return userId;
    }
}