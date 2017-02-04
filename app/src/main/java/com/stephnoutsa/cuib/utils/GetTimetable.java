package com.stephnoutsa.cuib.utils;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.stephnoutsa.cuib.R;
import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.models.Timetable;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by stephnoutsa on 1/29/17.
 */

public class GetTimetable {

    static MyDBHandler dbHandler;
    static String timetable = null;
    private static DownloadManager downloadManager;
    private static ProgressDialog progressDialog;

    public static void getTimetable(final Context c) {
        dbHandler = new MyDBHandler(c, null, null, 1);

        // Check if student is connected to a network
        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Display Progress dialog
            progressDialog = ProgressDialog.show(c, "", c.getString(R.string.load_msg), true);

            // Get student's details from database
            Student student = dbHandler.getStudent();
            final String department = student.getDepartment();
            String level = student.getLevel();

            /** Get corresponding timetable from server */
            RetrofitHandler retrofitHandler = new RetrofitHandler();

            CuibService cuibService = retrofitHandler.create();

            try {
                Call<Timetable> call = cuibService.getTimetable(URLEncoder.encode(department, "UTF-8"), level);
                call.clone().enqueue(new Callback<Timetable>() {
                    @Override
                    public void onResponse(Call<com.stephnoutsa.cuib.models.Timetable> call, Response<Timetable> response) {
                        // Remove progress dialog
                        progressDialog.dismiss();

                        int statusCode = response.code();
                        if (statusCode == 200) {
                            com.stephnoutsa.cuib.models.Timetable t = response.body();

                            String school = t.getSchool();
                            String dept = t.getDepartment();
                            String level = t.getLevel();
                            timetable = t.getUrl();

                            if (timetable != null) {
                                saveTtImage(c);

                                // Add timetable to local database
                                dbHandler.addTimetable(school, dept, level, timetable);
                            } else {
                                Toast.makeText(c, c.getString(R.string.no_timetable), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(c, c.getString(R.string.server_failure), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.stephnoutsa.cuib.models.Timetable> call, Throwable t) {
                        // Remove progress dialog
                        progressDialog.dismiss();

                        Toast.makeText(c, c.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(c, c.getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
    }

    // Save image to device
    private static void saveTtImage(final Context c) {
        try {
            /** Download the timetable if available*/
            downloadManager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(timetable);

            // Set the file name
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss_a", Locale.ENGLISH);
            String time = df.format(date.getTime());
            String filename = time + "_" + c.getString(R.string.tt_filename);

            // Set download settings
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(c.getString(R.string.tt_download_title)).
                    setDescription(c.getString(R.string.tt_download_desc)).
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).
                    //setVisibleInDownloadsUi(true).
                    //setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI).
                            setDestinationInExternalFilesDir(c, Environment.DIRECTORY_DOWNLOADS, filename);

            // Allow file to be scanned by MediaScanner
            request.allowScanningByMediaScanner();

            // Add download to queue
            downloadManager.enqueue(request);

            // Create broadcast receiver to perform action when download is complete
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast.makeText(c, c.getString(R.string.download_complete), Toast.LENGTH_SHORT).show();
                }
            };

            // Register the broadcast receiver
            c.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
