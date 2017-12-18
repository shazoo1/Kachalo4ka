package com.example.yobaiv.kachalochka.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.yobaiv.kachalochka.constants.ACode;
import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.adapters.SetsAdapter;
import com.example.yobaiv.kachalochka.classes.DBhelper;
import com.example.yobaiv.kachalochka.classes.Set;

import java.util.ArrayList;

public class SetsActivity extends AppCompatActivity {
    private final String SELFTAG = "SetsActivity";
    private ArrayList<Set> sets = new ArrayList<>();
    private long deid = -1;
    private String exname = "";
    SetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        deid = b.getLong("deid");
        exname = b.getString("exname");

        Log.d(SELFTAG, "Started with DE with ID "+ deid +" - "+exname);

        setContentView(R.layout.list_menu);
        Toolbar toolbar = (Toolbar)(findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        setTitle(exname);
        adapter = new SetsAdapter(this, sets);

        ListView lv = (ListView)findViewById(R.id.lvMain);
        lv.setAdapter(adapter);

        getSets(deid);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSet();
            }
        });
    }

    private void getSets(long deid){
        sets.clear();
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {
                "deid", "number", "weight", "count",
        };

        Cursor c = db.query("sets", columns, "deid = " + deid, null, null, null, "deid, number");
        helper.logCursor(c);

        if (c.moveToFirst()){
            do {
                Set set = new Set();
                for (String col: c.getColumnNames()){
                    switch (col){
                        case "deid": set.setExId(c.getInt(c.getColumnIndex("deid")));
                        case "number": set.setNumber(c.getInt(c.getColumnIndex("number")));
                        case "weight": set.setWeight(c.getFloat(c.getColumnIndex("weight")));
                        case "count": set.setCount(c.getInt(c.getColumnIndex("count")));
                    }
                }
                sets.add(set);
            } while (c.moveToNext());
            Log.d(SELFTAG, "Sets count is " + sets.size());
        } else {
            Log.d(SELFTAG, "No sets for DE " + deid);
        }
        c.close();
    }

    private void addSet(){
        Intent intent = new Intent(this, NewSetActivity.class);
        intent.putExtra("deid", deid);
        intent.putExtra("number", sets.size()+1);
        startActivityForResult(intent, ACode.NEW_SET);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case ACode.NEW_SET : {
                Intent intent = new Intent();
                if (resultCode == RESULT_OK){
                    intent.putExtra("refresh", true);
                    setResult(RESULT_OK, intent);
                    getSets(deid);
                    adapter.notifyDataSetChanged();
                }
                else{
                    intent.putExtra("refresh", false);
                    setResult(RESULT_CANCELED, intent);
                }

            }
        }
    }
}
