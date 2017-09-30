package com.codepath.apps.restclienttemplate.network;

import com.codepath.apps.restclienttemplate.models.Tweet;

/**
 * Created by luba on 9/29/17.
 */

public interface NewPostTweetCallback {

    void onSuccess(Tweet tweet);

    void onError(Error error);
    
}
