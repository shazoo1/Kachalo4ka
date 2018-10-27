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
import com.example.yobaiv.kachalochka.classes.Set;
import com.example.yobaiv.kachalochka.classes.Training;
import com.example.yobaiv.kachalochka.classes.helpers.DBHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YOBA IV on 25.10.2017.
 */

public class ExerciseAdapter extends BaseAdapter {

    private final String SELFTAG = "Exercise Adapter";

    Context context;
    LayoutInflater inflater;
    List<Exercise> exercises = new ArrayList<>();
    private DBHandler handler;
    long trainingid;

    public ExerciseAdapter(Context context, List<Exercise> exerises, long trainingId){
        this.context = context;
        handler = new DBHandler(context);
        this.trainingid = trainingId;
        this.exercises = exerises;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return exercises.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_item, parent, false);
        }
        Log.d(SELFTAG, "Trying to prepare exercises list.");
        Exercise ex = (Exercise)getItem(position);

        Log.d(SELFTAG, ex.getId() + " " + ex.getName());
        ((TextView) view.findViewById(R.id.tvTitle)).setText(ex.getName());
        ((TextView) view.findViewById(R.id.tvSubtitle)).setText((handler.getSetsFromDatabase(trainingid, ex.getId())).size()
                +" подходов");

        return view;
    }
}
