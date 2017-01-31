package com.stephnoutsa.cuib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.stephnoutsa.cuib.models.Payment;
import com.stephnoutsa.cuib.utils.MyDBHandler;

public class SinglePayment extends AppCompatActivity {

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    String pid = "";
    Payment payment;
    TextView typeLabel, typeField, dateLabel, dateField, amountLabel, amountField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        pid = getIntent().getExtras().getString("id");
        int id = Integer.parseInt(pid);

        payment = dbHandler.getPayment(id);
        String type = payment.getType();
        String date = payment.getDate();
        String amt = payment.getAmount();
        String amount = amt + " " + getString(R.string.amount_suffix);

        typeLabel = (TextView) findViewById(R.id.typeLabel);
        typeLabel.setTypeface(font, Typeface.BOLD);

        typeField = (TextView) findViewById(R.id.typeField);
        typeField.setTypeface(font);
        typeField.setText(type);

        dateLabel = (TextView) findViewById(R.id.dateLabel);
        dateLabel.setTypeface(font, Typeface.BOLD);

        dateField = (TextView) findViewById(R.id.dateField);
        dateField.setTypeface(font);
        dateField.setText(date);

        amountLabel = (TextView) findViewById(R.id.amountLabel);
        amountLabel.setTypeface(font, Typeface.BOLD);

        amountField = (TextView) findViewById(R.id.amountField);
        amountField.setTypeface(font);
        amountField.setText(amount);
    }

}
