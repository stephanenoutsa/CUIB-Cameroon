package com.stephnoutsa.cuib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stephnoutsa.cuib.adapters.LecturerAdapter;
import com.stephnoutsa.cuib.models.Lecturer;
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

public class Lecturers extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    List<Lecturer> lecturers = new ArrayList<>();
    ListView listView;
    ListAdapter listAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Lecturer a = new Lecturer();
        lecturers.add(a);
        Lecturer b = new Lecturer();
        lecturers.add(b);

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

            /** Get lecturers from server */
            RetrofitHandler retrofitHandler = new RetrofitHandler();

            CuibService cuibService = retrofitHandler.create();

            Call<Lecturer[]> call = cuibService.getLecturers(department, level);
            call.clone().enqueue(new Callback<Lecturer[]>() {
                @Override
                public void onResponse(Call<Lecturer[]> call, Response<Lecturer[]> response) {
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        Lecturer[] ls = response.body();
                        //lecturers = Arrays.asList(ls);

                        // Remove progress bar
                        progressBar.setVisibility(View.GONE);

                        listAdapter = new LecturerAdapter(context, lecturers);

                        listView = (ListView) findViewById(R.id.lecturerList);
                        listView.setAdapter(listAdapter);

                        // Action when user clicks a list item
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Lecturer lecturer = (Lecturer) parent.getItemAtPosition(position);
                                String name = lecturer.getName();
                                String avatar = lecturer.getAvatar();
                                String bio = lecturer.getBio();
                                Intent i = new Intent(context, SingleLecturer.class);
                                i.putExtra("name", name);
                                i.putExtra("avatar", avatar);
                                i.putExtra("bio", bio);
                                startActivity(i);
                            }
                        });
                    } else {
                        Toast.makeText(context, getString(R.string.server_failure), Toast.LENGTH_SHORT).show();

                        // Remove progress bar
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Lecturer[]> call, Throwable t) {
                    Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();

                    // Remove progress bar
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            Toast.makeText(context, getString(R.string.no_network), Toast.LENGTH_SHORT).show();

            // Remove progress bar
            progressBar.setVisibility(View.GONE);
        }
    }

}
