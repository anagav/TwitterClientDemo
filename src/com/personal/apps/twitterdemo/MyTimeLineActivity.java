package com.personal.apps.twitterdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.personal.apps.adapter.TwitterAdapter;
import com.personal.apps.listener.EndlessScrollListener;
import com.personal.apps.model.TwitterModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashishn on 1/27/14.
 */
public class MyTimeLineActivity extends Activity {

    TwitterAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timelineactivity);

        adapter
                = new TwitterAdapter(new ArrayList<TwitterModel>(), this);
        ListView tweetlist = (ListView) findViewById(R.id.listView);

        tweetlist.setAdapter(adapter);

        tweetlist.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(final int page, int totalItemsCount) {
                TwitterClientApp.getRestClient().getHomeTimeLine(page, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(Throwable throwable, JSONArray jsonArray) {
                        super.onFailure(throwable, jsonArray);
                        System.out.println("Error:" + jsonArray);
                        System.out.println("Error:" + throwable.toString());
                    }

                    @Override
                    public void onSuccess(JSONArray jsonArray) {
                        Toast.makeText(getBaseContext(),"loading more for page"+ page, Toast.LENGTH_SHORT).show();
                        super.onSuccess(jsonArray);
                        adapter.setdata(jsonArray.toString());
                    }
                });

            }
        });


        TwitterClientApp.getRestClient().getHomeTimeLine(1,new JsonHttpResponseHandler() {
            @Override
            public void onFailure(Throwable throwable, JSONArray jsonArray) {
                super.onFailure(throwable, jsonArray);
                System.out.println("Error:" + jsonArray);
                System.out.println("Error:" + throwable.toString());

            }

            @Override
            public void onFailure(Throwable throwable, JSONObject jsonObject) {
                super.onFailure(throwable, jsonObject);
                System.out.println("Error:" + jsonObject);
                System.out.println("Error:" + throwable.toString());
            }

            @Override
            public void onSuccess(JSONArray jsonArray) {
                super.onSuccess(jsonArray);
                adapter.setdata(jsonArray.toString());
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void loadCompose(MenuItem item) {
        Intent i = new Intent(MyTimeLineActivity.this, Compose.class);
        final int result = 2;
        startActivityForResult(i, result);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        TwitterClientApp.getRestClient().postTweet(data.getStringExtra("tweet"), new JsonHttpResponseHandler() {
            @Override
            public void onFailure(Throwable throwable, JSONArray jsonArray) {
                throwable.printStackTrace();
                System.out.println(jsonArray);
            }

            @Override
            public void onSuccess(JSONObject json) {
                Toast.makeText(getBaseContext(), "new tweet posted", Toast.LENGTH_SHORT).show();
                adapter.newTweet(json.toString());
            }
        });

    }

}