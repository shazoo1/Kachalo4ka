package com.example.yobaiv.kachalochka.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.classes.Day;
import com.example.yobaiv.kachalochka.classes.DayType;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by YOBA IV on 23.11.2017.
 */

public class NewDayDDLAdapter extends ArrayAdapter{

    private List<DayType> objects;
    private List<DayType> suggestions;
    private List<DayType> all;

    public NewDayDDLAdapter(Context context, int resource, List<DayType> types){
        super (context, resource, types);
        Context context1 = context;
        objects = types;
        all = clone(objects);
        suggestions = new ArrayList<>();
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
        return (objects.get(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ddlist_item, parent, false);
        }
        DayType type = (DayType)getItem(position);
        if (type != null) {
            TextView tv = (TextView) view.findViewById(R.id.text);
            if (tv != null){
                tv.setText(type.getName());
            }
        }
        return view;
    }

    private List<DayType> clone(List<DayType> list){
        List<DayType> res = new ArrayList<>();
        for (DayType o : list){
            res.add(o);
        }
        return res;
    }

    public Filter getFilter(){
        return nameFilter;
}

    Filter nameFilter =  new Filter() {

        @Override
        public String convertResultToString (Object resultValue){
            return ((DayType)resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null){
                suggestions.clear();
                for (DayType type : all){
                    if (type.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(type);
                    }
                }
                FilterResults fres = new FilterResults();
                fres.values = suggestions;
                fres.count = suggestions.size();
                return fres;
            } else{
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<DayType> flist = (ArrayList<DayType>) results.values;
            if (results != null && results.count > 0){
                clear();
                for (DayType dt : flist){
                    add(dt);
                }
                notifyDataSetChanged();
            }
        }
    };
}
