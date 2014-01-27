package com.personal.apps.twitterdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.codepath.apps.restclienttemplate.R;

/**
 * Created by ashishn on 1/27/14.
 */
public class Compose extends Activity {

    EditText composeBox;
    Button composeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose);
        composeBox = (EditText) findViewById(R.id.composeBox);
        composeButton = (Button) findViewById(R.id.composeButton);

        getIntent();
    }


    public void sendTweet(View view) {
        Intent i = new Intent();
        assert composeBox.getText() != null;
        i.putExtra("tweet", composeBox.getText().toString());

        setResult(RESULT_OK, i);
        finish();
    }


}

