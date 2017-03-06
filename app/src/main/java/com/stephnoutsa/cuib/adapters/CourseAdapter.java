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
import com.stephnoutsa.cuib.models.Course;

import java.util.List;

/**
 * Created by stephnoutsa on 11/27/16.
 */

public class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(Context context, List<Course> courses) {
        super(context, R.layout.custom_crs_row, courses);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_crs_row, parent, false);

        Course course = getItem(position);
        TextView crsCode = (TextView) customView.findViewById(R.id.crsCode);
        TextView crsTitle = (TextView) customView.findViewById(R.id.crsTitle);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Magnificent.ttf");

        crsCode.setTypeface(font, Typeface.BOLD);
        crsCode.setText(course.getCode());

        crsTitle.setTypeface(font);
        crsTitle.setText(course.getName());

        return customView;
    }
}
