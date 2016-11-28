package com.stephnoutsa.cuib;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.stephnoutsa.cuib.models.Department;
import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.utils.CuibService;
import com.stephnoutsa.cuib.utils.MyDBHandler;
import com.stephnoutsa.cuib.utils.RetrofitHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Timetable extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    String timetable = null;
    DownloadManager downloadManager;
    TextView downloadText;
    ImageView ttImage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        downloadText = (TextView) findViewById(R.id.downloadText);
        downloadText.setTypeface(font, Typeface.ITALIC);
        ttImage = (ImageView) findViewById(R.id.ttImage);
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

            /** Get corresponding timetable from server */
            RetrofitHandler retrofitHandler = new RetrofitHandler();

            CuibService cuibService = retrofitHandler.create();

            Call<Department> call = cuibService.getDepartment(department, level);
            call.clone().enqueue(new Callback<Department>() {
                @Override
                public void onResponse(Call<Department> call, Response<Department> response) {
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        Department d = response.body();

                        String name = d.getName();
                        String school = d.getSchool();
                        String level = d.getLevel();
                        timetable = d.getTimetable();

                        // Display the timetable image
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                downloadText.setText(R.string.download_text);

                                ttImage.setImageBitmap(bitmap);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Toast.makeText(context, getString(R.string.load_failed), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        };
                        Picasso.with(context).
                                load(timetable).
                                into(target);

                        // Remove progress bar
                        progressBar.setVisibility(View.GONE);

                        // Add department to local database
                        dbHandler.addDept(name, school, level, timetable);
                    } else {
                        Toast.makeText(context, getString(R.string.server_failure), Toast.LENGTH_SHORT).show();

                        // Remove progress bar
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Department> call, Throwable t) {
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

    public void onClickTtImage(View view) {
        if (timetable != null) {
            /** Download the timetable if available*/
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(timetable);

            // Set the file name
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss_a", Locale.ENGLISH);
            String time = df.format(date.getTime());
            String filename = getString(R.string.tt_filename) + time;

            // Set download settings
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(getString(R.string.tt_download_title)).
                    setDescription(getString(R.string.tt_download_desc)).
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).
                    //setVisibleInDownloadsUi(true).
                    //setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI).
                            setDestinationUri(Uri.parse(Environment.DIRECTORY_DOWNLOADS + "/" + filename));

            // Add download to queue
            downloadManager.enqueue(request);
        } else {
            Toast.makeText(this, getString(R.string.no_timetable), Toast.LENGTH_SHORT).show();
        }
    }

}
