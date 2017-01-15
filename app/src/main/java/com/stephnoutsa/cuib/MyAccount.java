package com.stephnoutsa.cuib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.stephnoutsa.cuib.models.Token;
import com.stephnoutsa.cuib.models.User;
import com.stephnoutsa.cuib.utils.CuibService;
import com.stephnoutsa.cuib.utils.MyDBHandler;
import com.stephnoutsa.cuib.utils.MyFirebaseIDService;
import com.stephnoutsa.cuib.utils.RetrofitHandler;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccount extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
    User newUser;
    TextView forSubscribed, emailLabel, phoneLabel, dobLabel, genderLabel, passwordLabel, confirmLabel;
    EditText emailField, phoneField, dobField, passwordField, confirmField;
    String email, phone, dob, gender, password, cPassword, role, subscribed, eml;
    RadioButton male, female, other;
    Button updateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        forSubscribed = (TextView) findViewById(R.id.forSubscribed);
        forSubscribed.setTypeface(font, Typeface.ITALIC);
        forSubscribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, LoginType.class);
                startActivity(i);
            }
        });

        emailLabel = (TextView) findViewById(R.id.emailLabel);
        emailLabel.setTypeface(font);

        emailField = (EditText) findViewById(R.id.emailField);

        phoneLabel = (TextView) findViewById(R.id.phoneLabel);
        phoneLabel.setTypeface(font);

        phoneField = (EditText) findViewById(R.id.phoneField);

        dobLabel = (TextView) findViewById(R.id.dobLabel);
        dobLabel.setTypeface(font);

        dobField = (EditText) findViewById(R.id.dobField);

        genderLabel = (TextView) findViewById(R.id.genderLabel);
        genderLabel.setTypeface(font);

        male = (RadioButton) findViewById(R.id.maleGender);
        female = (RadioButton) findViewById(R.id.femaleGender);
        other = (RadioButton) findViewById(R.id.otherGender);

        passwordLabel = (TextView) findViewById(R.id.passwordLabel);
        passwordLabel.setTypeface(font);

        passwordField = (EditText) findViewById(R.id.passwordField);

        confirmLabel = (TextView) findViewById(R.id.confirmLabel);
        confirmLabel.setTypeface(font);

        confirmField = (EditText) findViewById(R.id.confirmField);
        confirmField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                String pw = passwordField.getText().toString();

                // Password validation
                if (input.isEmpty()) {
                    confirmField.setError(null);
                } else if (!input.equals(pw) && !pw.isEmpty()) {
                    confirmField.setError(getString(R.string.confirm_invalid));
                }
                if (input.equals(pw)) {
                    confirmField.setError(null);
                }
            }
        });

        updateAccount = (Button) findViewById(R.id.updateAccount);

        subscribed = dbHandler.getSubscribed();
        if (subscribed.equals("yes")) {
            User u = dbHandler.getUser();
            eml = u.getEmail();
            String phn = u.getPhone();
            String db = u.getDob();
            String gdr = u.getGender();
            String pwd = u.getPassword();
            if(!eml.equals("null") && !phn.equals("null") && !db.equals("null") && !gdr.equals("null")
                    && !pwd.equals("null")) {
                emailField.setText(eml);
                phoneField.setText(phn);
                dobField.setText(db);
                gender = gdr;
                switch (gdr) {
                    case "male":
                        male.setChecked(true);
                        break;

                    case "female":
                        female.setChecked(true);
                        break;

                    case "other":
                        other.setChecked(true);
                        break;
                }
                passwordField.setText(pwd);
                passwordField.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        confirmLabel.setVisibility(View.VISIBLE);
                        confirmField.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                confirmField.setText(pwd);
            }
        } else {
            confirmLabel.setVisibility(View.VISIBLE);
            confirmField.setVisibility(View.VISIBLE);
            updateAccount.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            updateAccount.setText(getString(R.string.signup));
        }
    }

    // Check radio button clicks
    public void radioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.maleGender:
                if (checked)
                    gender = "male";
                break;
            case R.id.femaleGender:
                if (checked)
                    gender = "female";
                break;
            case R.id.otherGender:
                if (checked)
                    gender = "other";
                break;
        }
    }

    // Create or update user info
    public void onClickUpdate(View view) {
        email = emailField.getText().toString();
        phone = phoneField.getText().toString();
        dob = dobField.getText().toString();
        password = passwordField.getText().toString();
        cPassword = confirmField.getText().toString();
        role = getString(R.string.user_role);

        if (validate(email, phone, dob, gender, password, cPassword)) {
            // Check if user is connected
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                User user = new User(email, phone, dob, gender, password, role);

                subscribed = dbHandler.getSubscribed();
                if (subscribed.equals("yes")) { // Update user
                    /** Add or update user in remote server */
                    RetrofitHandler retrofitHandler = new RetrofitHandler();

                    CuibService cuibService = retrofitHandler.create();

                    Call<User> check = cuibService.updateUser(eml, user);
                    check.clone().enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            int statusCode = response.code();
                            if (statusCode == 200) {
                                User u = response.body();

                                newUser = response.body();

                                // Update user in app database
                                dbHandler.updateUser(eml, u);

                                // Notify that user has been updated
                                Toast.makeText(context, getString(R.string.user_updated), Toast.LENGTH_SHORT).show();

                                // Get token from database and send to remote server
                                Token token = dbHandler.getToken();
                                String val = token.getValue();

                                if (!val.equals("null")) {
                                    MyFirebaseIDService firebaseIDService = new MyFirebaseIDService();
                                    firebaseIDService.sendRegistrationToServer(context, token);
                                }

                                // Return to Home activity
                                Intent i = new Intent(context, Home.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(context, getString(R.string.check_failed), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    /** Add user to remote server */
                    RetrofitHandler retrofitHandler = new RetrofitHandler();

                    CuibService cuibService = retrofitHandler.create();

                    Call<User> check = cuibService.addUser(user);
                    check.clone().enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            int statusCode = response.code();
                            if (statusCode == 200) {
                                User u = response.body();

                                String uemail = u.getEmail();
                                String uphone = u.getPhone();
                                String udob = u.getDob();
                                String ugender = u.getGender();
                                String upassword = u.getPassword();
                                String urole = u.getRole();

                                if (uemail.equals("null") && uphone.equals("null") && udob.equals("null")
                                        && ugender.equals("null") && upassword.equals("null") && urole.equals("null")) {
                                    Toast.makeText(context, getString(R.string.user_exists), Toast.LENGTH_LONG).show();
                                } else {
                                    // Update subscribed status
                                    dbHandler.updateSubscribed("yes");

                                    // Add user to local database
                                    dbHandler.addUser(u);

                                    // Notify user that the account has been added
                                    Toast.makeText(context, getString(R.string.user_added), Toast.LENGTH_SHORT).show();

                                    // Get token from database and send to remote server
                                    Token token = dbHandler.getToken();
                                    String val = token.getValue();

                                    if (!val.equals("null")) {
                                        MyFirebaseIDService firebaseIDService = new MyFirebaseIDService();
                                        firebaseIDService.sendRegistrationToServer(context, token);
                                    }

                                    // Return to Home activity
                                    Intent i = new Intent(context, Home.class);
                                    startActivity(i);
                                }
                            } else {
                                Toast.makeText(context, getString(R.string.check_failed), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(context, getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Validate the form
    public boolean validate(String email, String phone, String dob, String gender, String password, String cPassword) {
        boolean valid = true;
        boolean pass = true;

        // Email validation
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError(getString(R.string.email_invalid));
            valid = false;
        } else {
            emailField.setError(null);
        }

        // Phone number validation
        if (phone.isEmpty() || phone.length() < 9) {
            phoneField.setError(getString(R.string.phone_invalid));
            valid = false;
        } else {
            phoneField.setError(null);
        }

        // DOB validation
        boolean a = dob.matches("\\d\\d/\\d\\d/\\d\\d");
        boolean b = dob.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d");
        boolean c = dob.matches("\\d\\d\\.\\d\\d\\.\\d\\d");
        boolean d = dob.matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d");
        boolean e = dob.matches("\\d\\d-\\d\\d-\\d\\d");
        boolean f = dob.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d");
        if (a || b || c || d || e || f) {
            dobField.setError(null);

            String day, month, year;
            String[] date;
            boolean ok;

            if (a || b) {
                date = dob.split("/");
            } else if (c || d) {
                date = dob.split("\\.");
            } else {
                date = dob.split("-");
            }

            day = date[0];
            month = date[1];
            year = date[2];

            ok = dobIsValid(day, month, year);

            if (ok) {
                dobField.setError(null);
            } else {
                dobField.setError(getString(R.string.dob_invalid));
            }
        } else {
            dobField.setError(getString(R.string.dob_invalid));
            valid = false;
        }

        // Gender validation
        if (gender == null) {
            male.setError(getString(R.string.gender_invalid));
            valid = false;
        } else {
            male.setError(null);
        }

        // Password validation
        if (password.isEmpty() || password.length() < 8) {
            passwordField.setError(getString(R.string.password_invalid));
            valid = false;
            pass = false;
        }
        if (cPassword.isEmpty()) {
            confirmField.setError(getString(R.string.password_invalid));
            valid = false;
        } else if (pass && !cPassword.equals(password)) {
            confirmField.setError(getString(R.string.confirm_invalid));
            valid = false;
        }

        return valid;
    }

    public boolean dobIsValid(String day, String month, String year) {
        boolean valid = true;

        int length = year.length();
        int yr = Integer.parseInt(year);
        int mth = Integer.parseInt(month);
        int dy = Integer.parseInt(day);

        Calendar calendar = Calendar.getInstance();
        int calYear = calendar.get(Calendar.YEAR);

        // Year validation
        switch (length) {
            case 2:
                if (yr >= (calYear % 100)) {
                    valid = false;
                }
                break;

            case 4:
                if (yr < 1930 || yr >= calYear) {
                    valid = false;
                }
                break;
        }

        // Month validation
        if (mth < 0 || mth > 12) {
            valid = false;
        }

        // Day validation
        if (dy < 0 || dy > 31) {
            valid = false;
        } else {
            if ((mth == 2 || mth == 4 || mth == 6 || mth == 9 || mth == 11) && (dy > 30)) {
                valid = false;
            }
        }

        return valid;
    }

}
