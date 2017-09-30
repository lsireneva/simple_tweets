package com.codepath.apps.restclienttemplate.models;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

/**
 * Created by luba on 9/27/17.
 */
@Parcel(analyze={User.class})
public class User extends BaseModel {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getScreenName() {
        return "@"+screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    //list the attributes
    @SerializedName("name")
    public String name;
    @SerializedName("id")
    public long uid;
    @SerializedName("screen_name")
    public String screenName;
    @SerializedName("profile_image_url")
    public String profileImageUrl;


    //deserialize the Json
    /*public static User fromJson (JSONObject json) throws JSONException{
        User user = new User();

        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");


        return user;
    }*/


}
