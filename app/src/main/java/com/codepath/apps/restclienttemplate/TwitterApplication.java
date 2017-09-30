package com.codepath.apps.restclienttemplate;

import android.app.Application;
import android.content.Context;

/**
 * Created by luba on 9/28/17.
 */

public class TwitterApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterApplication.context = this;
    }

    public static TwitterClient getRestClient() {
        return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterApplication.context);
    }
}