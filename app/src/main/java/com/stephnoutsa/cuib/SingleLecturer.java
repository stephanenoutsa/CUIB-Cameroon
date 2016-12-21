package com.stephnoutsa.cuib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleLecturer extends AppCompatActivity {

    String name = "", avatar = "", bio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_lecturer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        name = getIntent().getExtras().getString("name");
        avatar = getIntent().getExtras().getString("avatar");
        bio = getIntent().getExtras().getString("bio");

        CircleImageView lectAvatar = (CircleImageView) findViewById(R.id.lectAvatar);
        TextView lectName = (TextView) findViewById(R.id.lectName);
        TextView lectBio = (TextView) findViewById(R.id.lectBio);
        TextView school = (TextView) findViewById(R.id.school);

        Picasso.with(this).load(avatar).into(lectAvatar);

        lectName.setTypeface(font, Typeface.BOLD);
        lectName.setText(name);

        lectBio.setTypeface(font);
        lectBio.setText(bio);

        school.setTypeface(font);
    }

}
