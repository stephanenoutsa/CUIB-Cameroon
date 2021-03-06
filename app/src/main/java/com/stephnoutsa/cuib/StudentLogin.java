package com.stephnoutsa.cuib;

import android.app.ProgressDialog;
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

import com.google.common.hash.Hashing;
import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.models.Token;
import com.stephnoutsa.cuib.utils.CuibService;
import com.stephnoutsa.cuib.utils.MyDBHandler;
import com.stephnoutsa.cuib.utils.MyFirebaseIDService;
import com.stephnoutsa.cuib.utils.RetrofitHandler;

import java.nio.charset.Charset;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentLogin extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
    ProgressDialog progressDialog;
    Student student, newStudent;
    TextView wrongCredentials, matriculeLabel, passwordLabel, forgotPassword;
    EditText matriculeField, passwordField;
    String matricule, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        wrongCredentials = (TextView) findViewById(R.id.wrongCredentials);
        wrongCredentials.setTypeface(font);
        wrongCredentials.setVisibility(View.GONE);

        matriculeLabel = (TextView) findViewById(R.id.matriculeLabel);
        matriculeLabel.setTypeface(font);

        matriculeField = (EditText) findViewById(R.id.matriculeField);

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
        matricule = matriculeField.getText().toString();
        password = passwordField.getText().toString();

        if (noEmptyField(matricule, password)) {
            // Check is student is connected
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // Display Progress dialog
                progressDialog = ProgressDialog.show(context, "", getString(R.string.load_msg), true);

                // Hash password
                String pwd = Hashing.sha256().hashString(password, Charset.forName("UTF-8")).toString();

                student = new Student("null", matricule, "null", "null", "null", "null", "null", "null", pwd);

                /** Check for student in remote database */
                RetrofitHandler retrofitHandler = new RetrofitHandler();

                CuibService cuibService = retrofitHandler.create();

                Call<Student> check = cuibService.studentExists(student);
                check.clone().enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        // Remove progress dialog
                        progressDialog.dismiss();

                        int statusCode = response.code();

                        if (statusCode == 200) {
                            newStudent = response.body();

                            String nm = newStudent.getName();
                            String mtr = newStudent.getMatricule();
                            String enr = newStudent.getEnrolYr();
                            String lvl = newStudent.getLevel();
                            String sch = newStudent.getSchool();
                            String dpt = newStudent.getDepartment();
                            String eml = newStudent.getEmail();
                            String phn = newStudent.getPhone();
                            String pw = newStudent.getPassword();

                            if (nm.equals("null") && mtr.equals("null") && enr.equals("null") &&
                                    lvl.equals("null") && sch.equals("null") && dpt.equals("null") &&
                                    eml.equals("null") && phn.equals("null") && pw.equals("null")) {
                                wrongCredentials.setVisibility(View.VISIBLE);
                            } else {
                                // Remove wrongCredentials view if present
                                wrongCredentials.setVisibility(View.GONE);

                                // Add student to app database
                                dbHandler.addStudent(newStudent);

                                // Update subscribed status
                                dbHandler.updateSubscribed("yes");

                                // Get token from database, update it and send to remote server
                                Token t = dbHandler.getToken();
                                String val = t.getValue();
                                dbHandler.updateToken(val, sch, dpt, lvl);
                                Token token = new Token(val, sch, dpt, lvl);

                                if (!val.equals("null")) {
                                    MyFirebaseIDService firebaseIDService = new MyFirebaseIDService();
                                    firebaseIDService.sendRegistrationToServer(context, token);
                                }

                                // Return to Home activity
                                Intent i = new Intent(context, Home.class);
                                startActivity(i);
                            }
                        } else {
                            Toast.makeText(context, getString(R.string.server_failure), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        // Remove progress dialog
                        progressDialog.dismiss();

                        Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Check for empty EditText fields
    public boolean noEmptyField(String matricule, String password) {
        boolean ok = true;

        if (matricule.isEmpty()) {
            matriculeField.setError(getString(R.string.empty_field));
            ok = false;
        }

        if (password.isEmpty()) {
            passwordField.setError(getString(R.string.empty_field));
            ok = false;
        }

        return ok;
    }

}
