package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by luba on 9/27/17.
 */
@Parcel
public class Tweet {

    @SerializedName("text")
    String text;
    @SerializedName("id")
    Long uid;

    @SerializedName("user")
    public User user;

    @SerializedName("created_at")
    Date createdAt;

    public Tweet() {
        super();
    }


    public Long getTweetId() {
        return uid;
    }

    public void setTweetId(Long tweetId) {
        this.uid = tweetId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFormattedCreatedAtDate() {
        if (getCreatedAt() != null) {
            return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault()).format(getCreatedAt());
        }
        return null;
    }

    public String getRelativeTimeAgo() {
        long dateMillis = getCreatedAt().getTime();
        String new_relativeDate=null;
        String relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        if (relativeDate.contains("minutes ago")) {
            new_relativeDate = relativeDate.replace("minutes ago", "m");
        } else if (relativeDate.contains("hours ago")){
            new_relativeDate = relativeDate.replace("hours ago", "h");
        } else if (relativeDate.contains("hour ago")){
            new_relativeDate = relativeDate.replace("hour ago", "h");
        } else if (relativeDate.contains("seconds ago")){
            new_relativeDate = relativeDate.replace("seconds ago", "s");
        }

        return new_relativeDate;
    }


    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }





    //deserialize the Json
    /*public static Tweet fromJson(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();

        //exrtact the values from Json
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        //tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        //tweet.user = User

        return tweet;
    }*/

}
