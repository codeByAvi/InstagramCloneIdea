package com.skill.aboutmy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MediaItem implements Serializable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("caption")
    @Expose
    public String caption;
    @SerializedName("media_url")
    @Expose
    public String mediaUrl;
    @SerializedName("media_type")
    @Expose
    public String mediaType;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("timestamp")
    @Expose
    public String timestamp;
    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;
}
