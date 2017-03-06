package com.stephnoutsa.cuib.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stephnoutsa.cuib.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by stephnoutsa on 10/8/16.
 */
public class HomeAdapter extends ArrayAdapter<Integer> {

    Context context;
    long call = 0;

    public HomeAdapter(Context context, List<Integer> list) {
        super(context, R.layout.home_row, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.home_row, parent, false);

        // Increase call value each time getView() is called
        call++;

        CircleImageView icon = (CircleImageView) customView.findViewById(R.id.icon);
        TextView label = (TextView) customView.findViewById(R.id.label);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Magnificent.ttf");
        label.setTypeface(font, Typeface.BOLD);

        int item = getItem(position);

        switch (item) {
            case 0:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cuib));
                label.setText(R.string.about_label);
                break;

            case 1:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.faculty));
                label.setText(R.string.academics_label);
                break;

            case 2:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.admissions));
                label.setText(R.string.admission_label);
                break;

            case 3:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.community));
                label.setText(R.string.enp_label);
                break;

            case 4:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.events));
                label.setText(R.string.campus_label);
                break;

            case 5:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.sports));
                label.setText(R.string.sports_label);
                break;

            case 6:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.partners));
                label.setText(R.string.partners_label);
                break;

            case 7:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.donate));
                label.setText(R.string.donate_label);
                break;

            case 8:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.contact));
                label.setText(R.string.contact_label);
                break;
        }

        return customView;
    }

}
