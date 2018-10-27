package com.example.yobaiv.kachalochka.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.classes.Training;
import com.example.yobaiv.kachalochka.classes.helpers.DBHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YOBA IV on 20.10.2017.
 */

public class TrainingListAdapter extends BaseAdapter {
    private final String SELFTAG = "TrainingListAdapter";

    Context context;
    LayoutInflater lInflater;
    List<Training> objects;

    public TrainingListAdapter(Context context, List<Training> trainings){
        this.context = context;
        objects = trainings;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d(SELFTAG, "Got "+trainings.size()+" for main list.");

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
        return objects.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        DBHandler handler = new DBHandler(context);
        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }

        Training training = (Training)getItem(position);

        ((TextView) view.findViewById(R.id.tvTitle)).setText(training.getTitle());
        ((TextView) view.findViewById(R.id.tvSubtitle)).setText(training.getComment());

        return view;
    }

}
