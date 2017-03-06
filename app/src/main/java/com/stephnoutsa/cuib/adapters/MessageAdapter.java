package com.stephnoutsa.cuib.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stephnoutsa.cuib.R;
import com.stephnoutsa.cuib.models.Message;

import java.util.List;

/**
 * Created by stephnoutsa on 11/24/16.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(Context context, List<Message> messages) {
        super(context, R.layout.custom_msg_row, messages);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_msg_row, parent, false);

        Message message = getItem(position);
        TextView msgTitle = (TextView) customView.findViewById(R.id.msgTitle);
        TextView msgTime = (TextView) customView.findViewById(R.id.msgTime);
        TextView msgBody = (TextView) customView.findViewById(R.id.msgBody);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Magnificent.ttf");

        msgTitle.setTypeface(font, Typeface.BOLD);
        msgTitle.setText(trimText("title", message.getTitle()));

        msgTime.setTypeface(font);
        msgTime.setText(message.getTime());

        msgBody.setTypeface(font);
        msgBody.setText(trimText("body", message.getBody()));

        return customView;
    }

    // Trim notification ticker text
    public static String trimText(String type, String text) {
        int requiredNum;
        if (type.equals("title")) {
            requiredNum = 15;
        } else {
            requiredNum = 50;
        }

        String trimmed = "";
        int currentNum = text.length();

        if(requiredNum >= currentNum) {
            trimmed = text;
        }
        else {
            for(int i = 0; i < requiredNum; i++) {
                trimmed += text.charAt(i);
            }
            trimmed += "...";
        }

        return trimmed;
    }
}
