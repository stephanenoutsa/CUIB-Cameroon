package com.stephnoutsa.cuib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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

        Picasso.with(this).load(R.drawable.admissions).into(lectAvatar);

        lectName.setTypeface(font, Typeface.BOLD);
        lectName.setText(getString(R.string.lect_name_placeholder));

        lectBio.setTypeface(font);
        lectBio.setText(R.string.lect_bio_placeholder);

        school.setTypeface(font);
    }

}
