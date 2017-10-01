package com.codepath.apps.simpletweet.models;

import android.text.format.DateUtils;

import com.codepath.apps.simpletweet.MyDatabase;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by luba on 9/27/17.
 */
@Parcel(analyze={Tweet.class})
@Table(database = MyDatabase.class, name = "Tweet_Table")
public class Tweet extends BaseModel{

    @SerializedName("text")
    @Column(name = "text")
    String text;

    @SerializedName("id")
    @Column(name = "tweetId")
    @PrimaryKey
    Long tweetId;

    @SerializedName("user")
    @Column
    @ForeignKey(saveForeignKeyModel = false)
    public User user;


    @SerializedName("created_at")
    @Column(name = "created_at")
    Date createdAt;

    public Tweet() {
        super();
    }


    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
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


    // Record Finders
    public static Tweet byTweetId(Long tweetId) {
        return new Select().from(Tweet.class).where(Tweet_Table.tweetId.eq(tweetId)).querySingle();
    }

    public static List<Tweet> recentTweets() {
        return new Select().from(Tweet.class).orderBy(Tweet_Table.tweetId, false).limit(300).queryList();
    }



}
