package com.stephnoutsa.cuib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stephnoutsa.cuib.utils.MyDBHandler;

public class JoinUs extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_us);
    }

    public void onClickLogin(View view) {
        finish();
        Intent i = new Intent(context, LoginType.class);
        startActivity(i);
    }

    public void onClickSignup(View view) {
        Intent i = new Intent(context, SignUp.class);
        startActivity(i);
    }

    public void onClickSkip(View view) {
        new AlertDialog.Builder(context).
                setTitle(getString(R.string.skip_title)).
                setMessage(getString(R.string.skip_message)).
                setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.updateSubscribed("no");
                        Intent i = new Intent(context, Home.class);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(context).
                setTitle(getString(R.string.skip_title)).
                setMessage(getString(R.string.skip_message)).
                setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.updateSubscribed("no");
                        Intent i = new Intent(context, Home.class);
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
