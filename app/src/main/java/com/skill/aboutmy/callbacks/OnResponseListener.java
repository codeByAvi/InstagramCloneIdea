package com.skill.aboutmy.callbacks;

import com.skill.aboutmy.models.AuthToken;
import com.skill.aboutmy.models.MediaItem;
import com.skill.aboutmy.models.MediaList;

/**
 * API response listener for Media api
 */
public interface OnResponseListener {

    void onMediaListReceived(MediaList mediaList);
    void onMediaByID(MediaItem mediaItem);
    void onError(String message);
    void onNetworkFail();
}
