package com.codepath.apps.restclienttemplate.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by luba on 9/28/17.
 */

public class DeserializerDate implements JsonDeserializer<Date> {

    // Tue Aug 28 21:16:23 +0000 2012
    private final DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.getDefault());

    public DeserializerDate() {
        df.setTimeZone(TimeZone.getTimeZone("Zulu"));
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return df.parse(json.getAsString());
        } catch (ParseException e) {
            // Do nothing by now
            return null;
        }
    }

}
