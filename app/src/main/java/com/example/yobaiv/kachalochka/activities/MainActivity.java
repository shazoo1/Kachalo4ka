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
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yobaiv.kachalochka.constants.ACode;
import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.adapters.DayAdapter;
import com.example.yobaiv.kachalochka.classes.Day;
import com.example.yobaiv.kachalochka.classes.DBhelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String SQLTAG = "SQL";
    public static final int REFRESH_PARENT = 333;
    public static final ACode ACODE = new ACode();

    private final String MAINTAG = "MAIN";
    ArrayList<Day> days = new ArrayList<>();
    DayAdapter dayAdapter;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        clearTables();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_menu);

        viewDaysExercises();

        Toolbar toolbar = (Toolbar)(findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);

        dayAdapter = new DayAdapter(this, days);

        getDays();
        ListView lvDays = (ListView) findViewById(R.id.lvMain);
        lvDays.setAdapter(dayAdapter);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.bringToFront();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDay();
            }
        });

        lvDays.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View  view, int pos, long id){
                viewDay((Day) parent.getAdapter().getItem(pos));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d(MAINTAG, "NewDayActivity - OK");
        if (resultCode == RESULT_OK){
            getDays();
        }
        dayAdapter.notifyDataSetChanged();
    }

    private void addNewDay(){
        Intent intent = new Intent(this, NewDayActivity.class);
        intent.putExtra("daynumber", days.size() + 1);
        startActivityForResult(intent, ACode.NEW_DAY);
    }

    private void viewDay(Day day){
        if (day == null) Log.d(MAINTAG, "viewDay: list_item is null.");

        Intent intent = new Intent(this, DayActivity.class);
        intent.putExtra("list_item", day.getId());
        intent.putExtra("title", day.getTitle());
        Log.d(MAINTAG, "viewDay started with day " + day.getId());
        startActivityForResult(intent, ACode.DAY);
    }

    private void getDays(){

        days.clear();

        DBhelper dbhelper = new DBhelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String[] dayscolumns = {"id", "titles", "typeid"};
        String[] typescolumns = {"id","name"};
        Cursor c = db.query("days", null, null, null, null, null, null);

        Log.d(MAINTAG + "." + SQLTAG, "----Table Days----");
        dbhelper.logCursor(c);
        c.close();
        Log.d(MAINTAG + "." + SQLTAG, "---- ----");

        c = db.query("types", null, null, null, null, null, null);
        Log.d(MAINTAG + "." + SQLTAG, "----Table Types----");
        dbhelper.logCursor(c);
        c.close();
        Log.d(MAINTAG + "." + SQLTAG, "---- ----");

        Log.d(MAINTAG + "." + SQLTAG, "---- INNER JOIN with rawQuery ----");
        String table = "days as days inner join types as types on days.typeid = types.id";
        String [] columns = {"days.id as id", "days.title as title", "types.name as type", "days.date as date"};
        c = db.query(table, columns, null, null, null, null, "date");
        dbhelper.logCursor(c);
        Log.d(MAINTAG + "." + SQLTAG, "---- ----");

        if (c.moveToFirst()){
            do{
                int id = -1;
                String title = "";
                String type = "";
                String str = "";
                long date = 0;
                for (String colname : c.getColumnNames()){
                    switch (colname){
                        case "id" : id = c.getInt(c.getColumnIndex(colname));
                        case "title" : title = c.getString(c.getColumnIndex(colname));
                        case "type" : type = c.getString(c.getColumnIndex(colname));
                        case "date" : date = c.getLong(c.getColumnIndex(colname));
                    }
                }
                days.add(new Day(id, title, type, date));
                Log.d(MAINTAG+"."+SQLTAG, "Added list_item "+ id + " of type " + type);
            }
            while(c.moveToNext());
        }
        Log.d(MAINTAG, "Days are loaded. ArrayList days consists of "+ days.size() +" elements.");
        dayAdapter.notifyDataSetChanged();
        db.close();
    }

    private void clearTables(){
        DBhelper dbhelper = new DBhelper(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        db.execSQL("delete from days");
        db.execSQL("delete from types");
        db.execSQL("delete from exercises");
        db.execSQL("delete from sets");
        db.execSQL("delete from daysexercises");
        db.execSQL("delete from sets");
        db.close();
    }

    private void viewDaysExercises(){
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] cols = {"dayid", "exid"};
        Log.d(SQLTAG, "---- Table daysexercises ----");
        Cursor c = db.query("daysexercises", cols, null, null, null, null, null);
        Log.d(SQLTAG, "---- ----");
        helper.logCursor(c);
        db.close();
    }
}
