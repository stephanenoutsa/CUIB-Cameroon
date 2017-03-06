package com.stephnoutsa.cuib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.stephnoutsa.cuib.models.Student;
import com.stephnoutsa.cuib.utils.MyDBHandler;

public class StudentAccount extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
    Student student;
    TextView logoutLabel;
    TextView nameLabel, matriculeLabel, enrolledLabel, levelLabel, schoolLabel, deptLabel, emailLabel, phoneLabel;
    TextView nameField, matriculeField, enrolledField, levelField, schoolField, deptField, emailField, phoneField;
    String name, matricule, enrolled, level, school, department, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        logoutLabel = (TextView) findViewById(R.id.logoutLabel);
        logoutLabel.setTypeface(font);
        logoutLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ask student to confirm logout
                new AlertDialog.Builder(context).
                        setTitle(getString(R.string.logout_title)).
                        setMessage(getString(R.string.logout_message)).
                        setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete any user in database
                                dbHandler.deleteStudent();
                                dbHandler.deleteUser();
                                dbHandler.updateSubscribed("no");

                                // Return to Home activity
                                Intent i = new Intent(context, Home.class);
                                startActivity(i);
                            }
                        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        nameLabel = (TextView) findViewById(R.id.nameLabel);
        nameLabel.setTypeface(font);

        nameField = (TextView) findViewById(R.id.nameField);
        nameField.setTypeface(font);

        matriculeLabel = (TextView) findViewById(R.id.matriculeLabel);
        matriculeLabel.setTypeface(font);

        matriculeField = (TextView) findViewById(R.id.matriculeField);
        matriculeField.setTypeface(font);

        enrolledLabel = (TextView) findViewById(R.id.enrolledLabel);
        enrolledLabel.setTypeface(font);

        enrolledField = (TextView) findViewById(R.id.enrolledField);
        enrolledField.setTypeface(font);

        levelLabel = (TextView) findViewById(R.id.levelLabel);
        levelLabel.setTypeface(font);

        levelField = (TextView) findViewById(R.id.levelField);
        levelField.setTypeface(font);

        schoolLabel = (TextView) findViewById(R.id.schoolLabel);
        schoolLabel.setTypeface(font);

        schoolField = (TextView) findViewById(R.id.schoolField);
        schoolField.setTypeface(font);

        deptLabel = (TextView) findViewById(R.id.deptLabel);
        deptLabel.setTypeface(font);

        deptField = (TextView) findViewById(R.id.deptField);
        deptField.setTypeface(font);

        emailLabel = (TextView) findViewById(R.id.emailLabel);
        emailLabel.setTypeface(font);

        emailField = (TextView) findViewById(R.id.emailField);
        emailField.setTypeface(font);

        phoneLabel = (TextView) findViewById(R.id.phoneLabel);
        phoneLabel.setTypeface(font);

        phoneField = (TextView) findViewById(R.id.phoneField);
        phoneField.setTypeface(font);

        // Get student from Student table
        student = dbHandler.getStudent();

        name = student.getName();
        matricule = student.getMatricule();
        enrolled = student.getEnrolYr();
        level = student.getLevel();
        school = student.getSchool();
        department = student.getDepartment();
        email = student.getEmail();
        phone = student.getPhone();

        // Set text to be displayed on fields in StudentAccount activity
        if (!name.equals("null") && !matricule.equals("null") && !enrolled.equals("null") &&
                !level.equals("null") && !school.equals("null") && !department.equals("null") &&
                !email.equals("null") && !phone.equals("null")) {
            nameField.setText(name);
            matriculeField.setText(matricule);
            enrolledField.setText(enrolled);
            levelField.setText(level);
            schoolField.setText(school);
            deptField.setText(department);
            emailField.setText(email);
            phoneField.setText(phone);
        }
    }

}
