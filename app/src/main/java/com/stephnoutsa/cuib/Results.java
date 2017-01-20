package com.stephnoutsa.cuib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.stephnoutsa.cuib.utils.MyDBHandler;

public class Results extends AppCompatActivity {

    Context context = this;
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    TextView selectResults, yearLabel, semesterLabel;
    EditText yearField;
    Spinner semSpinner;
    int pos = 0;

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
        final String semester = semesters[pos];

        if (yearIsValid(yr)) {
            // Make year url-friendly
            final String year = convertYear(yr);

            // Launch Transaction activity
            Intent i = new Intent(this, Transaction.class);
            i.putExtra("year", year);
            i.putExtra("semester", semester);
            startActivity(i);
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

}
