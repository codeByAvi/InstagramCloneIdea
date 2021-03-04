package com.skill.aboutmy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MediaList {

    @SerializedName("data")
    @Expose
    public List<MediaItem> data = null;
    @SerializedName("paging")
    @Expose
    public Paging paging;

    public class Paging {

        @SerializedName("cursors")
        @Expose
        public Cursors cursors;
        @SerializedName("next")
        @Expose
        public String next;
        @SerializedName("previous")
        @Expose
        public String previous;

    }

    public class Cursors {

        @SerializedName("before")
        @Expose
        public String before;
        @SerializedName("after")
        @Expose
        public String after;

    }
}
