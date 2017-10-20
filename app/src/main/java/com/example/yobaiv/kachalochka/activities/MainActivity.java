package com.example.yobaiv.kachalochka.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.adapters.DayAdapter;
import com.example.yobaiv.kachalochka.classes.Day;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Day> days = new ArrayList<Day>();
    DayAdapter dayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillDays();
        dayAdapter = new DayAdapter(this, days);

        ListView lvDays = (ListView) findViewById(R.id.lvDays);
        lvDays.setAdapter(dayAdapter);
    }

    private void fillDays(){
        days.add(new Day(1, "Руки"));
        days.add(new Day(2, "Ноги"));
    }
}
