package com.codepath.apps.simpletweet.models;

import com.codepath.apps.simpletweet.MyDatabase;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by luba on 9/27/17.
 */
@Parcel(analyze={User.class})
@Table(database = MyDatabase.class, name = "User_Table")
public class User extends BaseModel {

    //list the attributes
    @SerializedName("name")
    @Column(name = "name")
    public String name;

    @SerializedName("id")
    @Column(name = "userId")
    @PrimaryKey
    public long userId;

    @SerializedName("screen_name")
    @Column(name = "screen_name")
    public String screenName;

    @SerializedName("profile_image_url")
    @Column(name = "profile_image_url")
    public String profileImageUrl;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreennameToShow() {
        return "@" + screenName;
    }


    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    // Record Finders
    public static User byUserId(Long userId) {
        return new Select().from(User.class).where(User_Table.userId.eq(userId)).querySingle();
    }

    public static List<User> recentItems() {
        return new Select().from(User.class).orderBy(User_Table.userId, false).limit(300).queryList();
    }

}
