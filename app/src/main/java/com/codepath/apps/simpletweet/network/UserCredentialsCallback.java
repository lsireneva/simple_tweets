package com.codepath.apps.simpletweet.network;

import com.codepath.apps.simpletweet.models.User;

/**
 * Created by luba on 9/27/17.
 */

public interface UserCredentialsCallback {

    void onSuccess(User user);

    void onError(Error error);
}