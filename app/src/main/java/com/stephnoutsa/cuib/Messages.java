package com.stephnoutsa.cuib;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.stephnoutsa.cuib.models.Message;
import com.stephnoutsa.cuib.adapters.MessageAdapter;
import com.stephnoutsa.cuib.utils.MyDBHandler;

import java.util.Collections;
import java.util.List;

public class Messages extends AppCompatActivity {

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    List<Message> messageList;
    ListView listView;
    ListAdapter listAdapter;
    Context context = this;
    ImageView noMessagesIcon;
    TextView noMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Remove notification(s) from notification tray
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancelAll();

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Magnificent.ttf");

        noMessagesIcon = (ImageView) findViewById(R.id.noMessagesIcon);
        noMessages = (TextView) findViewById(R.id.noMessages);
        noMessages.setTypeface(font, Typeface.BOLD);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                // Get all messages from database
                messageList = dbHandler.getAllMessages();

                // Remove placeholder message
                messageList.remove(0);

                // Display icon and text if there are no messages
                if (messageList.isEmpty()) {
                    noMessagesIcon.setVisibility(View.VISIBLE);
                    noMessages.setVisibility(View.VISIBLE);
                } else {
                    // Reverse the order of the messages
                    Collections.reverse(messageList);

                    listAdapter = new MessageAdapter(context, messageList);

                    listView = (ListView) findViewById(R.id.messageList);
                    listView.setAdapter(listAdapter);

                    // Action when user presses a list item
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Message message = (Message) parent.getItemAtPosition(position);
                            String mid = "" + message.getId();
                            Intent i = new Intent(context, SingleMessage.class);
                            i.putExtra("id", mid);
                            startActivity(i);
                        }
                    });

                    // Action when user long presses a list item
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            Message message = (Message) parent.getItemAtPosition(position);
                            final int mid = message.getId();

                            new AlertDialog.Builder(context).
                                    setIcon(android.R.drawable.ic_menu_delete).
                                    setTitle(getString(R.string.delete_title)).
                                    setMessage(getString(R.string.delete_msg)).
                                    setPositiveButton(getString(R.string.delete_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dbHandler.deleteMessage(mid);
                                            Intent i = new Intent(Messages.this, Messages.class);
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
