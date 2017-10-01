package com.codepath.apps.simpletweet.network;

import com.codepath.apps.simpletweet.models.Tweet;

/**
 * Created by luba on 9/29/17.
 */

public interface NewPostTweetCallback {

    void onSuccess(Tweet tweet);

    void onError(Error error);
    
}
