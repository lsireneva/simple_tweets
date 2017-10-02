package com.codepath.apps.simpletweet.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by luba on 09/30/17.
 */

@Parcel
public class Size {

    @SerializedName("w")
    @Expose
    int width;

    @SerializedName("h")
    @Expose
    int height;

    @SerializedName("resize")
    @Expose
    String resize;

    public Size() {

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getResize() {
        return resize;
    }

    public void setResize(String resize) {
        this.resize = resize;
    }
}
