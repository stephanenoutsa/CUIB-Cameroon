package com.stephnoutsa.cuib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stephnoutsa.cuib.models.MoMoResponse;
import com.stephnoutsa.cuib.utils.CuibService;
import com.stephnoutsa.cuib.utils.GetResults;
import com.stephnoutsa.cuib.utils.MyDBHandler;
import com.stephnoutsa.cuib.utils.RetrofitHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transaction extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    ProgressBar progressBar;
    EditText phoneField;
    String year = "";
    String semester = "";
    String matricule = "";
    String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        // Retrieve necessary information to access results
        year = getIntent().getExtras().getString("year");
        semester = getIntent().getExtras().getString("semester");
        matricule = dbHandler.getStudent().getMatricule();

        phoneField = (EditText) findViewById(R.id.phoneField);
    }

    // Do MoMo transaction
    public void onClickPay(View view) {
        phone = phoneField.getText().toString();

        if (phoneIsValid(phone)) {
            // Check if student is connected to a network
            final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // Make progressBar visible
                progressBar.setVisibility(View.VISIBLE);

                /** Try debiting student's MoMo account **/
                RetrofitHandler retrofitHandler = new RetrofitHandler(getString(R.string.momo_url));

                CuibService cuibService = retrofitHandler.create();

                try {
                    Call<MoMoResponse> call = cuibService.momoPayment(
                            "2",
                            "PAIE",
                            getString(R.string.momo_amount),
                            phone,
                            "",
                            getString(R.string.momo_email)
                    );
                    /*String url = call.request().url().toString();
                    Toast.makeText(context, "URL: " + url, Toast.LENGTH_LONG).show();*/
                    call.clone().enqueue(new Callback<MoMoResponse>() {
                        @Override
                        public void onResponse(Call<MoMoResponse> call, Response<MoMoResponse> response) {
                            // Remove progressBar
                            progressBar.setVisibility(View.GONE);

                            int statusCode = response.code();
                            if (statusCode == 200) {
                                try {
                                    MoMoResponse r = response.body();
                                    String code = r.getStatusCode();
                                    String desc = r.getStatusDesc();

                                    switch(code) {
                                        case "01":
                                            Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
                                            // Fetch the results
                                            GetResults.getResults(context, year, semester, matricule);
                                            break;

                                        case "100":
                                            Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
                                            break;

                                        case "null":
                                            Toast.makeText(context, getResources().getString(R.string.trans_failed), Toast.LENGTH_SHORT).show();
                                            break;
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, getString(R.string.trans_failed), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, getString(R.string.server_failure), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MoMoResponse> call, Throwable t) {
                            // Remove progressBar
                            progressBar.setVisibility(View.GONE);

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

    // Validate phone number
    public boolean phoneIsValid(String phone) {
        boolean valid = true;

        if (phone.isEmpty()) {
            phoneField.setError(getString(R.string.empty_field));
            valid = false;
        } else if (phone.length() != 9) {
            phoneField.setError(getString(R.string.phone_invalid));
            valid = false;
        } else if (!(phone.substring(0, 2).equals("67")) && !(phone.substring(0, 2).equals("65"))) {
            phoneField.setError(getString(R.string.invalid_mtn_number));
            valid = false;
        }

        return valid;
    }

}
