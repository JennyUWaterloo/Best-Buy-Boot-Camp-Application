package com.example.dx205.bestbuy;

import android.view.View;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dx205 on 2015-05-04.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, String[] categories) {
        super(context, R.layout.custom_row, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row, parent, false);

        String singleCategory = getItem(position);
        TextView myText = (TextView) customView.findViewById(R.id.myText);

        myText.setText(singleCategory);
        return customView;
    }
}
