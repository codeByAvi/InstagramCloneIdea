package com.skill.aboutmy.utils;

/**
 * enum for type of the Media received
 */
public enum  MediaType {

    CAROUSEL_ALBUM ( "CAROUSEL_ALBUM"),
    VIDEO("VIDEO"),
    IMAGE("IMAGE");

    private String type;
    MediaType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
