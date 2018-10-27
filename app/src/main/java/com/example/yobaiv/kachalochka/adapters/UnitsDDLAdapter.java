package com.example.yobaiv.kachalochka.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.classes.Unit;

import java.io.FilterReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by YOBA IV on 02.02.2018.
 */

public class UnitsDDLAdapter extends ArrayAdapter {

    private final String SELFTAG = "UnitsDDLAdapter";

    private List<Unit> objects;
    private List<Unit> suggestions;
    private List<Unit> all;

    public UnitsDDLAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Unit> units) {
        super(context, resource, units);

        objects = units;
        all = clone(objects);
        suggestions = new ArrayList<>();
    }

    @Override
    public int getCount(){
        return objects.size();
    }

    @Override
    public Object getItem(int pos){
        return objects.get(pos);
    }

    @Override
    public long getItemId(int pos){
        return objects.get(pos).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ddlist_item, parent, false);
        }
        Unit unit = (Unit) getItem(position);
        if (unit != null){
            TextView tv = view.findViewById(R.id.text);
            if (tv != null){
                tv.setText(unit.getName());
            }
        }
        return view;
    }

    Filter filter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue){
            return ((Unit)resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            if (constraint != null){
                suggestions.clear();
                for (Unit unit : all){
                    if (unit.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(unit);
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
        protected void publishResults(CharSequence constraint, FilterResults results){
            ArrayList<Unit> units = (ArrayList<Unit>) results.values;
            if (results != null && results.count > 0){
                clear();
                for (Unit unit : units){
                    add(unit);
                }
                notifyDataSetChanged();
            }
        }
    };

    public Filter getFilter(){
        return filter;
    }

    private List<Unit> clone(List<Unit> objects) {
        List<Unit> res = new ArrayList<>();
        for (Unit unit : objects){
            res.add(unit);
        }
        return res;
    }
}
