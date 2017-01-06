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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class Results extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    DownloadManager downloadManager;
    TextView selectResults, yearLabel, semesterLabel;
    EditText yearField;
    Spinner semSpinner;
    int pos = 0;
    String results = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        selectResults = (TextView) findViewById(R.id.selectResults);
        selectResults.setTypeface(font);

        yearLabel = (TextView) findViewById(R.id.yearLabel);
        yearLabel.setTypeface(font);

        yearField = (EditText) findViewById(R.id.yearField);

        semesterLabel = (TextView) findViewById(R.id.semesterLabel);

        semSpinner = (Spinner) findViewById(R.id.semSpinner);
        semSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.semester_options, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        semSpinner.setAdapter(adapter);
    }

    public void onClickGet(View view) {
        String yr = yearField.getText().toString().trim();

        String[] semesters = getResources().getStringArray(R.array.semester_options);
        String semester = semesters[pos];

        String matricule = dbHandler.getStudent().getMatricule();

        if (yearIsValid(yr)) {
            // Make year url-friendly
            String year = convertYear(yr);

            // Check if student is connected to a network
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                /** Get corresponding results from server */
                RetrofitHandler retrofitHandler = new RetrofitHandler();

                CuibService cuibService = retrofitHandler.create();

                try {
                    Call<com.stephnoutsa.cuib.models.Results> call = cuibService.getResults(URLEncoder.encode(year, "UTF-8"), URLEncoder.encode(semester, "UTF-8"), URLEncoder.encode(matricule, "UTF-8"));
                    call.clone().enqueue(new Callback<com.stephnoutsa.cuib.models.Results>() {
                        @Override
                        public void onResponse(Call<com.stephnoutsa.cuib.models.Results> call, Response<com.stephnoutsa.cuib.models.Results> response) {
                            int statusCode = response.code();
                            if (statusCode == 200) {
                                com.stephnoutsa.cuib.models.Results r = response.body();

                                results = r.getUrl();

                                if (results != null) {
                                    // Download the results
                                    downloadResults();
                                } else {
                                    Toast.makeText(context, getString(R.string.not_found), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<com.stephnoutsa.cuib.models.Results> call, Throwable t) {
                            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context, getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Check that the year entered is valid
    public boolean yearIsValid(String year) {
        boolean ok = true;

        if (year.isEmpty()) {
            yearField.setError(getString(R.string.empty_field));
            ok = false;
        } else if (!year.matches("\\d\\d\\d\\d/\\d\\d\\d\\d")) {
            yearField.setError(getString(R.string.invalid_year));
            ok = false;
        }

        return ok;
    }

    // Convert year to url-friendly string
    public String convertYear(String yr) {
        String[] yrs = yr.split("/");
        String year = "";

        for (int i = 0; i < yrs.length; i++) {
            year += yrs[i];

            if (i != (yrs.length - 1)) {
                year += "-";
            }
        }

        return year;
    }

    // Save results to device
    public void downloadResults() {
        if (results != null) {
            try {
                /** Download the results if available*/
                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(results);

                // Set the file name
                Date date = new Date();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss_a", Locale.ENGLISH);
                String time = df.format(date.getTime());
                String filename = time + "_" + getString(R.string.rs_filename);

                // Set download settings
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(getString(R.string.rs_download_title)).
                        setDescription(getString(R.string.rs_download_desc)).
                        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).
                        //setVisibleInDownloadsUi(true).
                        //setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI).
                                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

                // Add download to queue
                downloadManager.enqueue(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
        }
    }

}
