package com.example.yobaiv.kachalochka.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.solver.SolverVariable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.adapters.NewDayDDLAdapter;
import com.example.yobaiv.kachalochka.classes.DBhelper;
import com.example.yobaiv.kachalochka.classes.DayType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.yobaiv.kachalochka.activities.MainActivity.SQLTAG;

public class NewDayActivity extends AppCompatActivity {
    String SELFTAG = "NewDayActivity";
    int number;
    List<DayType> types = new ArrayList<>();
    ArrayAdapter<String> acadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        types = getTypes();
        List<String> typeNames = getTypesNames(types);

        setContentView(R.layout.two_field_bot_ac);
        AutoCompleteTextView acView = (AutoCompleteTextView)(findViewById(R.id.acSecond));

        acadapter = new NewDayDDLAdapter(this, R.layout.ddlist_item, types);

        acView.setAdapter(acadapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DBhelper db = new DBhelper(this);
        final Date date = new Date();

        Bundle b = getIntent().getExtras();
        if (b != null){
            number = (int) b.get("daynumber");
        }

        ((EditText) findViewById(R.id.etFirst)).setText("День " + number + " - " +
                new SimpleDateFormat("dd/MM/yyyy").format(date));
        ((AutoCompleteTextView) findViewById(R.id.acSecond)).setHint("Тип");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabOk);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ((TextView) findViewById(R.id.etFirst)).getText().toString();
                long typeid = getTypeId(((TextView) findViewById(R.id.acSecond)).getText().toString());
                DBhelper dbhelper = new DBhelper(getCurrentContext());
                SQLiteDatabase db = dbhelper.getWritableDatabase();

                if (!title.isEmpty() && typeid != -1) {


                    ContentValues cv = new ContentValues();
                    cv.put("title", title);
                    cv.put("typeid", typeid);
                    cv.put("date", date.getTime());
                    long id = db.insert("days", null, cv);

                    setResult(RESULT_OK);
                    Log.d(SELFTAG+"."+SQLTAG, "Successfully added list_item "+id);
                }
                else{
                    Toast t = new Toast(getCurrentContext());
                    t.setText("Unable to add.");
                    t.show();
                    Log.d(SELFTAG,"Title or type ID is wrong.");
                    setResult(RESULT_OK);
                }

                db.close();
                finish();
            }
        });
    }

    private Context getCurrentContext(){
        return this;
    }

    private long getTypeId(String typename){
        String[] columns = {"id", "name"};
        DBhelper dbhelper = new DBhelper(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Log.d(SELFTAG, "Trying to get type "+typename+" from DB.");

        Cursor c =  db.query("types", columns, "name = \""+typename+"\"", null, null, null, null);
        if (c.moveToFirst()){
            Log.d(SELFTAG, "Found type \""+typename+"\" with id = "+ c.getInt(c.getColumnIndex("id")));
            return c.getInt(c.getColumnIndex("id"));
        }
        ContentValues values = new ContentValues();
        values.put("name", typename);

        long newRowId = db.insert("types", "null", values);
        Log.d(SELFTAG, "Added new type with id = "+newRowId);
        db.close();
        return newRowId;
    }

    private List<DayType> getTypes(){
        List<DayType> types = new ArrayList<>();
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] cols = {"id", "name"};

        Cursor c = db.query("types", cols, null, null, null, null, "name");

        Log.d(SELFTAG, "---- Table types ----");
        helper.logCursor(c);
        Log.d(SELFTAG, "----  ----");


        if(c.moveToFirst()){

            do {
                long id = 0;
                String name = "";
                for (String col : c.getColumnNames()){
                    switch (col){
                        case "id": id = c.getLong(c.getColumnIndex(col));
                        case "name": name = c.getString(c.getColumnIndex(col));
                    }
                }
                types.add(new DayType(id, name));
            } while (c.moveToNext());
        }
        return types;
    }
    private List<String> getTypesNames(List<DayType> types){
        List<String> names = new ArrayList<>();
        for (DayType type : types){
            names.add(type.getName());
        }
        return names;
    }

}
