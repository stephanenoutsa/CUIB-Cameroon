package com.stephnoutsa.cuib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stephnoutsa.cuib.adapters.CourseAdapter;
import com.stephnoutsa.cuib.models.Course;
import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.utils.CuibService;
import com.stephnoutsa.cuib.utils.MyDBHandler;
import com.stephnoutsa.cuib.utils.RetrofitHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Courses extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    List<Course> courseList = new ArrayList<>();
    ListView listView;
    ListAdapter listAdapter;
    ProgressBar progressBar;
    ImageView noCoursesIcon;
    TextView noCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        noCoursesIcon = (ImageView) findViewById(R.id.noCoursesIcon);
        noCourses = (TextView) findViewById(R.id.noCourses);
        noCourses.setTypeface(font, Typeface.BOLD);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Check if student is connected to a network
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get student's details from database
            Student student = dbHandler.getStudent();
            String department = student.getDepartment();
            String level = student.getLevel();

            /** Get courses from server */
            RetrofitHandler retrofitHandler = new RetrofitHandler();

            CuibService cuibService = retrofitHandler.create();

            Call<Course[]> call = cuibService.getCourses(department, level);
            call.clone().enqueue(new Callback<Course[]>() {
                @Override
                public void onResponse(Call<Course[]> call, Response<Course[]> response) {
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        Course[] c = response.body();
                        courseList = Arrays.asList(c);

                        // Remove progress bar
                        progressBar.setVisibility(View.GONE);

                        if (courseList.isEmpty()) {
                            noCoursesIcon.setVisibility(View.VISIBLE);
                            noCourses.setVisibility(View.VISIBLE);
                        } else {
                            for (Course course : courseList) {
                                String cd = course.getCode();
                                String nm = course.getName();
                                String dsc = course.getDescription();
                                String[] sch = course.getSchools();
                                String[] dpt = course.getDepartments();
                                String[] lvl = course.getLevels();

                                // Add course to local database
                                dbHandler.addCourse(cd, nm, dsc, sch, dpt, lvl);
                            }
                        }

                        listAdapter = new CourseAdapter(context, courseList);

                        listView = (ListView) findViewById(R.id.courseList);
                        listView.setAdapter(listAdapter);

                        // Action when user clicks a list item
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Course course = (Course) parent.getItemAtPosition(position);
                                String code = course.getCode();
                                Intent i = new Intent(context, SingleCourse.class);
                                i.putExtra("code", code);
                                startActivity(i);
                            }
                        });
                    } else {
                        Toast.makeText(context, getString(R.string.server_failure), Toast.LENGTH_SHORT).show();

                        // Display placeholders
                        noCoursesIcon.setVisibility(View.VISIBLE);
                        noCourses.setVisibility(View.VISIBLE);

                        // Remove progress bar
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Course[]> call, Throwable t) {
                    Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();

                    // Display placeholders
                    noCoursesIcon.setVisibility(View.VISIBLE);
                    noCourses.setVisibility(View.VISIBLE);

                    // Remove progress bar
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            Toast.makeText(context, getString(R.string.no_network), Toast.LENGTH_SHORT).show();

            // Display placeholders
            noCoursesIcon.setVisibility(View.VISIBLE);
            noCourses.setVisibility(View.VISIBLE);

            // Remove progress bar
            progressBar.setVisibility(View.GONE);
        }
    }

}
