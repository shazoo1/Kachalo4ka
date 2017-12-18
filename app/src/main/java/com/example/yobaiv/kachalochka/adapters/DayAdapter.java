package com.example.yobaiv.kachalochka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.classes.Day;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YOBA IV on 20.10.2017.
 */

public class DayAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    List<Day> objects;

    public DayAdapter (Context context, List<Day> days){
        this.context = context;
        objects = days;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return objects.size();
    }

    @Override
    public Object getItem(int position){
        return objects.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }

        Day day = (Day)getItem(position);

        ((TextView) view.findViewById(R.id.tvTitle)).setText(day.getTitle());
        ((TextView) view.findViewById(R.id.tvSubtitle)).setText(day.getType());

        return view;
    }

}
