package com.example.yobaiv.kachalochka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.classes.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YOBA IV on 19.12.2017.
 */

public class NewExerciseDDLAdapter extends ArrayAdapter {

    private List<Exercise> objects;
    private List<Exercise> suggestions;
    private List<Exercise> all;

    public NewExerciseDDLAdapter(Context context, int resource, List<Exercise> exercises){
        super(context, resource, exercises);
        objects = exercises;
        all = clone(objects);
        suggestions = new ArrayList<>();
    }

    @Override
    public int getCount(){return objects.size();}

    @Override
    public Object getItem (int position){return objects.get(position);}

    @Override
    public long getItemId (int position){return objects.get(position).getId();}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ddlist_item, parent, false);
        }
        Exercise exercise = (Exercise) getItem(position);
        if(exercise != null){
            TextView tv = view.findViewById(R.id.text);
            if (tv != null){
                tv.setText(exercise.getName());
            }
        }
        return view;
    }

    Filter nameFilter = new Filter() {

        @Override
        public String convertResultToString (Object resultValue){
            return ((Exercise)resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null){
                suggestions.clear();
                for (Exercise exercise : all){
                    if (exercise.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(exercise);
                    }
                }
                FilterResults fres = new FilterResults();
                fres.values = suggestions;
                fres.count = suggestions.size();
                return fres;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Exercise> res = (ArrayList<Exercise>) results.values;

            if (results != null && results.count > 0){
                clear();
                for (Exercise exercise : res){
                    add(exercise);
                }
                notifyDataSetChanged();
            }

        }
    };

    public Filter getFilter(){return nameFilter;}

    private List<Exercise> clone(List<Exercise> objects) {
        List<Exercise> output = new ArrayList<>();
        for (Exercise object :objects) {
            output.add(object);
        }
        return output;
    }
}
