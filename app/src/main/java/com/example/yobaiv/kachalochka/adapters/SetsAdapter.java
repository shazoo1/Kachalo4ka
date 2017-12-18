package com.example.yobaiv.kachalochka.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.classes.Set;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by YOBA IV on 07.11.2017.
 */

public class SetsAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Set> objects = new ArrayList<>();

    public SetsAdapter(Context context, ArrayList<Set> sets){
        this.context = context;
        objects = sets;
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
        View view =  convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_item, parent, false);
        }
        Set set = (Set)getItem(position);

        String title = "Подход " + set.getNumber();
        String subtitle = set.getWeight() + " кг - " + set.getCount() + " повторений";

        ((TextView) view.findViewById(R.id.tvTitle)).setText(title);
        ((TextView) view.findViewById(R.id.tvSubtitle)).setText(subtitle);

        return view;
    }

    private void addSet(){

    }
}
