package com.stephnoutsa.cuib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.stephnoutsa.cuib.R;
import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.models.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by stephnoutsa on 11/23/16.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    Token token;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Check if student logged in to app
        Student student = dbHandler.getStudent();

        String name = student.getName();
        String matricule = student.getMatricule();
        String enrolled = student.getEnrolYr();
        String level = student.getLevel();
        String school = student.getSchool();
        String department = student.getDepartment();
        String email = student.getEmail();
        String phone = student.getPhone();
        String password = student.getPassword();

        if (name.equals("null") && matricule.equals("null") && enrolled.equals("null") &&
                level.equals("null") && school.equals("null") && department.equals("null") &&
                email.equals("null") && phone.equals("null") && password.equals("null")) {
            token = new Token(refreshedToken, "null", "null", "null");

            // Save the token in the database
            dbHandler.updateToken(refreshedToken, "null", "null", "null");
        } else {
            token = new Token(refreshedToken, school, department, level);

            // Save the token in the database
            dbHandler.updateToken(refreshedToken, school, department, level);
        }

        // Send registration to remote server
        sendRegistrationToServer(context, token);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    public void sendRegistrationToServer(final Context c, Token token) {
        // Check if user is connected to a network
        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /** Save token in server */
            RetrofitHandler retrofitHandler = new RetrofitHandler();

            CuibService cuibService = retrofitHandler.create();

            Call<Token> call = cuibService.addToken(token);
            call.clone().enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        Toast.makeText(c, c.getResources().getString(R.string.token_saved), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(c, c.getResources().getString(R.string.server_failure), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(c, c.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(c, c.getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
    }
}
