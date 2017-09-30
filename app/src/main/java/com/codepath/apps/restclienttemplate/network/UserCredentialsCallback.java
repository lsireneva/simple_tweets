package com.codepath.apps.restclienttemplate.network;

import com.codepath.apps.restclienttemplate.models.User;

/**
 * Created by luba on 9/27/17.
 */

public interface UserCredentialsCallback {

    void onSuccess(User user);

    void onError(Error error);
}