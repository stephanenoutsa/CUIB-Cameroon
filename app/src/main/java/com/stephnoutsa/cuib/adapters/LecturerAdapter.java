package com.stephnoutsa.cuib.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stephnoutsa.cuib.R;
import com.stephnoutsa.cuib.models.Lecturer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by stephnoutsa on 11/27/16.
 */

public class LecturerAdapter extends ArrayAdapter<Lecturer> {

    Context context;

    public LecturerAdapter(Context context, List<Lecturer> lecturers) {
        super(context, R.layout.custom_lect_row, lecturers);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_lect_row, parent, false);

        Lecturer l = getItem(position);
        CircleImageView lectAvatar = (CircleImageView) customView.findViewById(R.id.lectAvatar);
        TextView lectName = (TextView) customView.findViewById(R.id.lectName);
        TextView lectBio = (TextView) customView.findViewById(R.id.lectBio);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Magnificent.ttf");

        /*String avatar = l.getAvatar();
        String name = l.getName();
        String bio = l.getBio();*/

        Picasso.with(context).load(R.drawable.admissions).into(lectAvatar);

        lectName.setTypeface(font, Typeface.BOLD);
        //lectName.setText(name);
        lectName.setText(context.getString(R.string.lect_name_placeholder));

        lectBio.setTypeface(font);
        //lectBio.setText(bio);
        lectBio.setText(trimText(context.getString(R.string.lect_bio_placeholder)));

        return customView;
    }

    // Trim notification ticker text
    public static String trimText(String text) {
        if (text != null) {
            int requiredNum = 50;

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

        return "";
    }
}
