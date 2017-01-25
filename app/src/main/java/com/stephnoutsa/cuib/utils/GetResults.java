package com.stephnoutsa.cuib.utils;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.stephnoutsa.cuib.R;
import com.stephnoutsa.cuib.models.Results;

import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by stephnoutsa on 1/18/17.
 */

public class GetResults {

    private static DownloadManager downloadManager;
    private static String results = "";

    public static void getResults(final Context c, final String year, final String semester, final String matricule) {
        // Check if student is connected to a network
        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /** Get corresponding results from server */
            RetrofitHandler retrofitHandler = new RetrofitHandler();

            CuibService cuibService = retrofitHandler.create();

            try {
                Call<Results> call = cuibService.getResults(URLEncoder.encode(year, "UTF-8"), URLEncoder.encode(semester, "UTF-8"), URLEncoder.encode(matricule, "UTF-8"));
                call.clone().enqueue(new Callback<Results>() {
                    @Override
                    public void onResponse(Call<com.stephnoutsa.cuib.models.Results> call, Response<Results> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {
                            com.stephnoutsa.cuib.models.Results r = response.body();

                            results = r.getUrl();

                            if (results != null) {
                                // Download the results
                                downloadResults(c, year, semester);
                            } else {
                                new AlertDialog.Builder(c).
                                        setTitle(c.getString(R.string.retry_title)).
                                        setMessage(c.getString(R.string.retry_msg)).
                                        setPositiveButton(c.getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                getResults(c, year, semester, matricule);
                                            }
                                        }).setNegativeButton(c.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {

                                    }
                                }).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<com.stephnoutsa.cuib.models.Results> call, Throwable t) {
                        Toast.makeText(c, c.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        getResults(c, year, semester, matricule);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(c, c.getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
    }

    // Save results to device
    private static void downloadResults(Context c, String year, String semester) {
        if (results != null) {
            try {
                /** Download the results if available*/
                downloadManager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(results);

                // Set the file name
                String filename = c.getString(R.string.rs_filename) + "_" + year + "_s" + semester + ".png" ;

                // Set download settings
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(c.getString(R.string.rs_download_title)).
                        setDescription(c.getString(R.string.rs_download_desc)).
                        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).
                        //setVisibleInDownloadsUi(true).
                        //setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI).
                                setDestinationInExternalFilesDir(c, Environment.DIRECTORY_DOWNLOADS, filename);

                // Allow file to be scanned by MediaScanner
                request.allowScanningByMediaScanner();

                // Add download to queue
                downloadManager.enqueue(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(c, c.getString(R.string.no_results), Toast.LENGTH_SHORT).show();
        }
    }

}
