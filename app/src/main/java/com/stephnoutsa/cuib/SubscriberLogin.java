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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stephnoutsa.cuib.models.User;
import com.stephnoutsa.cuib.utils.CuibService;
import com.stephnoutsa.cuib.utils.MyDBHandler;
import com.stephnoutsa.cuib.utils.RetrofitHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriberLogin extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
    User user, newUser;
    TextView wrongCredentials, emailLabel, passwordLabel, forgotPassword;
    EditText emailField, passwordField;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        wrongCredentials = (TextView) findViewById(R.id.wrongCredentials);
        wrongCredentials.setTypeface(font);
        wrongCredentials.setVisibility(View.GONE);

        emailLabel = (TextView) findViewById(R.id.emailLabel);
        emailLabel.setTypeface(font);

        emailField = (EditText) findViewById(R.id.emailField);

        passwordLabel = (TextView) findViewById(R.id.passwordLabel);
        passwordLabel.setTypeface(font);

        passwordField = (EditText) findViewById(R.id.passwordField);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void onClickLogin(View view) {
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        if (noEmptyField(email, password)) {
            // Check is student is connected
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                user = new User(email, "null", "null", "null", password);

                /** Check for user in remote database */
                // Trailing slash is needed
                RetrofitHandler retrofitHandler = new RetrofitHandler();

                CuibService cuibService = retrofitHandler.create();

                Call<User> check = cuibService.loginCheck(user);
                check.clone().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        int statusCode = response.code();

                        if (statusCode == 200) {
                            newUser = response.body();

                            String eml = newUser.getEmail();
                            String phn = newUser.getPhone();
                            String db = newUser.getPhone();
                            String gdr = newUser.getPhone();
                            String pw = newUser.getPassword();

                            if (eml.equals("null") && phn.equals("null") && db.equals("null") && gdr.equals("null")
                                    && pw.equals("null")) {
                                wrongCredentials.setVisibility(View.VISIBLE);
                            } else {
                                // Remove wrongCredentials view if present
                                wrongCredentials.setVisibility(View.GONE);

                                // Add student to app database
                                dbHandler.addUser(newUser);

                                // Update subscribed status
                                dbHandler.updateSubscribed("yes");

                                // Return to Home activity
                                Intent i = new Intent(context, Home.class);
                                startActivity(i);
                            }
                        } else {
                            Toast.makeText(context, getString(R.string.server_failure), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }



    // Check for empty EditText fields
    public boolean noEmptyField(String email, String password) {
        boolean ok = true;

        if (email.isEmpty()) {
            emailField.setError(getString(R.string.empty_field));
            ok = false;
        }

        if (password.isEmpty()) {
            passwordField.setError(getString(R.string.empty_field));
            ok = false;
        }

        return ok;
    }

}
