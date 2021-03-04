package com.skill.aboutmy.callbacks;

import com.skill.aboutmy.models.AuthToken;

/**
 * callback interface for Authorization api
 */
public interface AuthResponseListener {

    void onAuthorized(AuthToken authToken);
    void onError(String message);
}
