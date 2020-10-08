package com.codepath.apps.restclienttemplate.models;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet
{
    public String body;
    public String createdAt;
    public Integer favoriteCount;
    public Integer replyCount;
    public Integer retweetCount;
    public User user;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException
    {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.favoriteCount = jsonObject.getInt("favorite_count");
        tweet.retweetCount = jsonObject.getInt("retweet_count");
//        tweet.replyCount = jsonObject.getInt("reply_count");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++)
        {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return tweets;

    }


}
