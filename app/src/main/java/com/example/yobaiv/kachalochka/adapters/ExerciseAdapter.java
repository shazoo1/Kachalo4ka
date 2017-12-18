package com.example.yobaiv.kachalochka.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.classes.Exercise;

import java.util.ArrayList;

/**
 * Created by YOBA IV on 25.10.2017.
 */

public class ExerciseAdapter extends BaseAdapter {

    private final String SELFTAG = "Exercise Adapter";

    Context context;
    LayoutInflater inflater;
    ArrayList<Exercise> objects;

    public ExerciseAdapter(Context context, ArrayList<Exercise> exercises){
        this.context = context;
        objects = exercises;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_item, parent, false);
        }
        Log.d(SELFTAG, "Trying to prepare exercises list.");
        Exercise ex = getEx(position);

        ((TextView) view.findViewById(R.id.tvTitle)).setText(ex.getName());
        ((TextView) view.findViewById(R.id.tvSubtitle)).setText(ex.getSetsCount()+" подходов");

        return view;
    }
    private Exercise getEx(int pos){
        return ((Exercise) objects.get(pos));
    }
}
