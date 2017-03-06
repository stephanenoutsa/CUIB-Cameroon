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
import com.stephnoutsa.cuib.models.Payment;

import java.util.List;

/**
 * Created by stephnoutsa on 1/26/17.
 */

public class PaymentAdapter extends ArrayAdapter<Payment> {

    public PaymentAdapter(Context context, List<Payment> payments) {
        super(context, R.layout.custom_pay_row, payments);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_pay_row, parent, false);

        Payment payment = getItem(position);
        TextView payType = (TextView) customView.findViewById(R.id.payType);
        TextView payDate = (TextView) customView.findViewById(R.id.payDate);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Magnificent.ttf");

        payType.setTypeface(font, Typeface.BOLD);
        payType.setText(payment.getType());

        payDate.setTypeface(font);
        payDate.setText(payment.getDate());

        return customView;
    }
}
