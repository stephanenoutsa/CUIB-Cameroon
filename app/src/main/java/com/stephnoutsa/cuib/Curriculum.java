package com.stephnoutsa.cuib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Curriculum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClickTimetable(View view) {
        Intent i = new Intent(this, Timetable.class);
        startActivity(i);
    }

    public void onClickCourses(View view) {
        Intent i = new Intent(this, Courses.class);
        startActivity(i);
    }

    public void onClickLecturers(View view) {
        Intent i = new Intent(this, Lecturers.class);
        startActivity(i);
    }

    public void onClickResults(View view) {

    }

}
