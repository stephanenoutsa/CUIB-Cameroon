package com.stephnoutsa.cuib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.stephnoutsa.cuib.models.Course;
import com.stephnoutsa.cuib.utils.MyDBHandler;

public class SingleCourse extends AppCompatActivity {

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    String cid = "";
    Course course;
    TextView crsCode, crsTitle, crsDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        cid = getIntent().getExtras().getString("id");
        int id = Integer.parseInt(cid);

        course = dbHandler.getCourse(id);
        String code = course.getCode();
        String title = course.getName();
        //String description = course.getDescription();

        crsCode = (TextView) findViewById(R.id.crsCode);
        crsCode.setTypeface(font);
        crsCode.setText(code);

        crsTitle = (TextView) findViewById(R.id.crsTitle);
        crsTitle.setTypeface(font);
        crsTitle.setText(title);

        crsDesc = (TextView) findViewById(R.id.crsDesc);
        crsDesc.setTypeface(font);
        //crsDesc.setText(description);
    }

}
