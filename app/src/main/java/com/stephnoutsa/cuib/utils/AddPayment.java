package com.stephnoutsa.cuib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.stephnoutsa.cuib.R;
import com.stephnoutsa.cuib.models.Payment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by stephnoutsa on 1/26/17.
 */

public class AddPayment {

    private static MyDBHandler dbHandler;

    public static void addPayment(final Context c, Payment payment) {
        // Check if student is connected to a network
        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /** Add payment to server */
            RetrofitHandler retrofitHandler = new RetrofitHandler();

            CuibService cuibService = retrofitHandler.create();

            try {
                Call<Payment> call = cuibService.addPayment(payment);
                call.clone().enqueue(new Callback<Payment>() {
                    @Override
                    public void onResponse(Call<Payment> call, Response<Payment> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {
                            dbHandler = new MyDBHandler(c, null, null, 1);

                            Payment p = response.body();

                            // Add payment to local database
                            dbHandler.addPayment(p.getDate(), p.getAmount(), p.getSchool());
                        } else {
                            Toast.makeText(c, c.getString(R.string.server_failure), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Payment> call, Throwable t) {
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

}
