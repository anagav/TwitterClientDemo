package com.personal.apps.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by ashishn on 1/26/14.
 */
public class TwitterModel {
    public String text;
    public User user;

    public static ArrayList<TwitterModel> gettweets(String s) {
        Gson gson = new GsonBuilder().create();
        ArrayList<TwitterModel> tweets = gson.fromJson(s, new TypeToken<ArrayList<TwitterModel>>() {
        }.getType());
        System.out.println("tweets=" + tweets);
        return tweets;


    }

    public static TwitterModel getSingleTweet(String s) {
        Gson gson = new GsonBuilder().create();
        TwitterModel tweet = gson.fromJson(s, TwitterModel.class);
        return tweet;
    }


}
