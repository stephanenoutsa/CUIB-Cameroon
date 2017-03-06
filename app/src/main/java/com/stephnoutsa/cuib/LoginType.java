package com.stephnoutsa.cuib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LoginType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClickStudentLogin(View view) {
        Intent i = new Intent(this, StudentLogin.class);
        startActivity(i);
    }

    public void onClickUserLogin(View view) {
        Intent i = new Intent(this, SubscriberLogin.class);
        startActivity(i);
    }

}
