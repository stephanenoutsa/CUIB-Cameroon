package com.stephnoutsa.cuib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stephnoutsa.cuib.adapters.PaymentAdapter;
import com.stephnoutsa.cuib.models.Payment;
import com.stephnoutsa.cuib.utils.MyDBHandler;

import java.util.Collections;
import java.util.List;

public class Payments extends AppCompatActivity {

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    List<Payment> paymentList;
    ListView listView;
    ListAdapter listAdapter;
    Context context = this;
    ImageView noPaymentsIcon;
    TextView noPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        noPaymentsIcon = (ImageView) findViewById(R.id.noPaymentsIcon);
        noPayments = (TextView) findViewById(R.id.noPayments);
        noPayments.setTypeface(font, Typeface.BOLD);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // Get all payments from database
                paymentList = dbHandler.getAllPayments();

                // Remove placeholder payment
                paymentList.remove(0);

                // Display icon and text if there are no payments
                if (paymentList.isEmpty()) {
                    noPaymentsIcon.setVisibility(View.VISIBLE);
                    noPayments.setVisibility(View.VISIBLE);
                } else {
                    // Reverse the order of the payments
                    Collections.reverse(paymentList);

                    listAdapter = new PaymentAdapter(context, paymentList);

                    listView = (ListView) findViewById(R.id.paymentList);
                    listView.setAdapter(listAdapter);

                    // Action when a user clicks on a list item
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Payment payment = (Payment) adapterView.getItemAtPosition(i);
                            String pid = "" + payment.getId();
                            Intent intent = new Intent(context, SinglePayment.class);
                            intent.putExtra("id", pid);
                            startActivity(intent);
                        }
                    });

                    // Action when a user clicks and holds a list item
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Payment payment = (Payment) adapterView.getItemAtPosition(i);
                            final int pid = payment.getId();

                            new AlertDialog.Builder(context).
                                    setIcon(android.R.drawable.ic_menu_delete).
                                    setTitle(getString(R.string.delete_title)).
                                    setMessage(getString(R.string.delete_payment)).
                                    setPositiveButton(getString(R.string.delete_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dbHandler.deletePayment(pid);
                                            Intent i = new Intent(Payments.this, Payments.class);
                                            finish();
                                            overridePendingTransition(0, 0);
                                            startActivity(i);
                                            overridePendingTransition(0, 0);
                                        }
                                    }).setNegativeButton(getString(R.string.delete_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {

                                }
                            }).show();

                            return true;
                        }
                    });
                }
            }
        };

        Runnable r = new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }

}
