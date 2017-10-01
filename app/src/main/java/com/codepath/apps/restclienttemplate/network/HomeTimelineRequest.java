package com.codepath.apps.restclienttemplate.network;

/**
 * Created by luba on 9/30/17.
 */

public class HomeTimelineRequest {

    private int count = 40;
    private Long sinceId;
    private Long maxId;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getSinceId() {
        return sinceId;
    }

    public void setSinceId(Long sinceId) {
        this.sinceId = sinceId;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }




}
