package com.codepath.apps.restclienttemplate.network;

import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;

/**
 * Created by luba on 9/28/17.
 */

public interface HomeTimelineCallback {

    void onSuccess(ArrayList<Tweet> tweets);

    void onError(Error error);
}
