package com.stephnoutsa.cuib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.stephnoutsa.cuib.models.Message;
import com.stephnoutsa.cuib.utils.MyDBHandler;

public class SingleMessage extends AppCompatActivity {

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    String mid = "";
    Message message;
    TextView msgTitle, msgSender, msgTime, msgBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        mid = getIntent().getExtras().getString("id");
        int id = Integer.parseInt(mid);

        message = dbHandler.getMessage(id);
        String sender = message.getSender();
        String time = message.getTime();
        String title = message.getTitle();
        String body = message.getBody();

        msgTitle = (TextView) findViewById(R.id.msgTitle);
        msgTitle.setTypeface(font, Typeface.BOLD);
        msgTitle.setText(title);

        msgSender = (TextView) findViewById(R.id.msgSender);
        msgSender.setTypeface(font, Typeface.ITALIC);
        msgSender.setText(sender);

        msgTime = (TextView) findViewById(R.id.msgTime);
        msgTime.setTypeface(font);
        msgTime.setText(time);

        msgBody = (TextView) findViewById(R.id.msgBody);
        msgBody.setTypeface(font);
        msgBody.setText(body);

    }

}
