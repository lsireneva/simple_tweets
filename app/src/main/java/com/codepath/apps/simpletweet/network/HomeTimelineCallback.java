package com.codepath.apps.simpletweet.network;

import com.codepath.apps.simpletweet.models.Tweet;

import java.util.ArrayList;

/**
 * Created by luba on 9/28/17.
 */

public interface HomeTimelineCallback {

    void onSuccess(ArrayList<Tweet> tweets);

    void onError(Error error);
}
