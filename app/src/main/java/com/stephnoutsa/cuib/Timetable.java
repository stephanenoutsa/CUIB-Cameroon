package com.stephnoutsa.cuib;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
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
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.utils.CuibService;
import com.stephnoutsa.cuib.utils.MyDBHandler;
import com.stephnoutsa.cuib.utils.RetrofitHandler;

import java.net.URLEncoder;
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
    TextView noTimetable, downloadText;
    ImageView noTimetableIcon, ttImage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        noTimetable = (TextView) findViewById(R.id.noTimetable);
        noTimetable.setTypeface(font, Typeface.BOLD);
        noTimetableIcon = (ImageView) findViewById(R.id.noTimetableIcon);
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
            final String department = student.getDepartment();
            String level = student.getLevel();

            /** Get corresponding timetable from server */
            RetrofitHandler retrofitHandler = new RetrofitHandler();

            CuibService cuibService = retrofitHandler.create();

            try {
                Call<com.stephnoutsa.cuib.models.Timetable> call = cuibService.getTimetable(URLEncoder.encode(department, "UTF-8"), level);
                call.clone().enqueue(new Callback<com.stephnoutsa.cuib.models.Timetable>() {
                    @Override
                    public void onResponse(Call<com.stephnoutsa.cuib.models.Timetable> call, Response<com.stephnoutsa.cuib.models.Timetable> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {
                            com.stephnoutsa.cuib.models.Timetable t = response.body();

                            String school = t.getSchool();
                            String dept = t.getDepartment();
                            String level = t.getLevel();
                            timetable = t.getUrl();

                            if (timetable != null) {
                                // Display the timetable image
                                Target target = new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        downloadText.setText(R.string.download_text);

                                        ttImage.setImageBitmap(bitmap);

                                        ttImage.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                onClickTtImage();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {
                                        noTimetableIcon.setVisibility(View.VISIBLE);
                                        noTimetable.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                };
                                Picasso.with(context).
                                        load(timetable).
                                        into(target);
                            } else {
                                // Display placeholders
                                noTimetableIcon.setVisibility(View.VISIBLE);
                                noTimetable.setVisibility(View.VISIBLE);
                            }

                            // Remove progress bar
                            progressBar.setVisibility(View.GONE);

                            // Add timetable to local database
                            dbHandler.addTimetable(school, dept, level, timetable);
                        }
                    }

                    @Override
                    public void onFailure(Call<com.stephnoutsa.cuib.models.Timetable> call, Throwable t) {
                        Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, getString(R.string.no_network), Toast.LENGTH_SHORT).show();

            // Display placeholders
            noTimetableIcon.setVisibility(View.VISIBLE);
            noTimetable.setVisibility(View.VISIBLE);

            // Remove progress bar
            progressBar.setVisibility(View.GONE);
        }
    }

    // Save image to device
    public void onClickTtImage() {
        if (timetable != null) {
            try {
                /** Download the timetable if available*/
                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(timetable);

                // Set the file name
                Date date = new Date();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss_a", Locale.ENGLISH);
                String time = df.format(date.getTime());
                String filename = time + "_" + getString(R.string.tt_filename);

                // Set download settings
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(getString(R.string.tt_download_title)).
                        setDescription(getString(R.string.tt_download_desc)).
                        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).
                        //setVisibleInDownloadsUi(true).
                        //setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI).
                        setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, filename);

                // Allow file to be scanned by MediaScanner
                request.allowScanningByMediaScanner();

                // Add download to queue
                downloadManager.enqueue(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, getString(R.string.no_timetable), Toast.LENGTH_SHORT).show();
        }
    }

}
