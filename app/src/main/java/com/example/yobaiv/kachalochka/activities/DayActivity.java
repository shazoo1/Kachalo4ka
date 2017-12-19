package com.example.yobaiv.kachalochka.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.yobaiv.kachalochka.adapters.NewExerciseDDLAdapter;
import com.example.yobaiv.kachalochka.constants.ACode;
import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.adapters.ExerciseAdapter;
import com.example.yobaiv.kachalochka.classes.DBhelper;
import com.example.yobaiv.kachalochka.classes.Exercise;

import java.util.ArrayList;

import static com.example.yobaiv.kachalochka.activities.MainActivity.SQLTAG;

public class DayActivity extends AppCompatActivity {
    private final String SELFTAG = "DayActivity";

    FloatingActionButton fabAdd;
    private long dayid = -1;
    private String dayTitle = "";
    ExerciseAdapter exerciseAdapter;

    private ArrayList<Exercise> exercises = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_menu);

        dayid = getIntent().getLongExtra("list_item", -1);
        dayTitle = getIntent().getStringExtra("title");



        exerciseAdapter = new ExerciseAdapter(this, exercises);
        Toolbar toolbar = (Toolbar)(findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        Log.d(SELFTAG+".onCreate", dayTitle);
        setTitle(dayTitle);

        Log.d(SELFTAG, "Got dayId from intent.");
        getExercises(dayid);
        ListView lvEx = (ListView) findViewById(R.id.lvMain);
        lvEx.setAdapter(exerciseAdapter);
        Log.d(SELFTAG, "Adapter set");

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.bringToFront();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newExercise();
                Log.d(SELFTAG, "Adding exercise");
            }
        });
        lvEx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewExercise((Exercise)parent.getAdapter().getItem(position));
            }
        });

    }

    private void getExercises(long dayid){
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String table = "daysexercises as de inner join exercises as ex on de.exid = ex.id";
        String[] columns = {"ex.id as id", "ex.name as name", "de.dayid as dayid"};
        Cursor c = db.query(table, columns, "de.dayid = " + dayid, null, null, null, null);
        Log.d(SELFTAG+"."+SQLTAG, "----INNER JOIN with rawQuery----");
        helper.logCursor(c);
        Log.d(SELFTAG+"."+SQLTAG, "---- ----");

        if (c.moveToFirst()){
            exercises.clear();
            do
            {
                Exercise ex = new Exercise();
                for (String columnName : c.getColumnNames()){
                    switch (columnName){
                        case "id": ex.setId(Long.parseLong(c.getString(c.getColumnIndex("id"))));
                        case "name": ex.setName(c.getString(c.getColumnIndex("name")));
                    }
                }


                String[] setColumns ={"count",};
                Cursor setc = db.query("sets", setColumns, "deid = "+ex.getId(), null, null, null, null);
                ex.setSetsCount(setc.getCount());

                exercises.add(ex);
            } while (c.moveToNext());
            Log.d(SELFTAG, "exercises<exercise> consists of "+exercises.size()+" elements");
        }
        else{
            Log.d(SELFTAG, "No entries in EXERCISES");
        }
        exerciseAdapter.notifyDataSetChanged();
        db.close();
    }

    private void newExercise(){
        LayoutInflater li = LayoutInflater.from(this);
        View exView = li.inflate(R.layout.new_exercise_dialog, null);
        String mName = "";
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setView(exView);

        NewExerciseDDLAdapter exerciseDDLAdapter = new NewExerciseDDLAdapter(this, R.layout.ddlist_item ,getAllExercises());
        final AutoCompleteTextView exerciseName = ((AutoCompleteTextView) exView.findViewById(R.id.etName));
        exerciseName.setAdapter(exerciseDDLAdapter);

        mBuilder
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String mName = exerciseName.getText().toString();
                        addExercise(mName);
                        getExercises(dayid);
                        exerciseAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    private void addExercise(String exName){
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", exName);
        long id = db.insert("exercises", null, cv);

        cv.clear();
        cv.put("dayid", dayid);
        cv.put("exid", id);

        db.insert("daysexercises", null, cv);

        Log.d(SELFTAG + "." + SQLTAG, "Added exercise "+exName+", id="+id+" for list_item "+dayid);
        getExercises(dayid);
        exerciseAdapter.notifyDataSetChanged();
    }

    private void viewExercise(Exercise ex){
        if (ex != null){
            DBhelper helper = new DBhelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            String[] cols = {"id", "dayid", "exid"};

            Cursor c = db.query("daysexercises", cols, "exid = "+ex.getId()+" and dayid = " + dayid, null, null, null, null);

            Intent intent = new Intent(this, SetsActivity.class);
            if (c.moveToFirst()){
                intent.putExtra("deid", c.getLong(c.getColumnIndex("id")));
            }
            intent.putExtra("exname", ex.getName());
            startActivityForResult(intent, ACode.SETS);
        }
        else{
            Log.d(SELFTAG, "Chosen exercise is NULL.");
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        switch (requestCode){
            case ACode.SETS : {
                if (resultCode == RESULT_OK){
                    boolean toUpdate = intent.getBooleanExtra("refresh", true);
                    if (toUpdate) getExercises(dayid);
                }
            }
        }
    }

    private ArrayList<Exercise> getAllExercises(){
        ArrayList<Exercise> result = new ArrayList<>();
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] cols = {"id", "name"};
        Cursor c = db.query("exercises", cols, null, null, null, null, null);
        if (c.moveToFirst()){
            do{
                Exercise exercise = new Exercise();
                exercise.setId(c.getLong(c.getColumnIndex("id")));
                exercise.setName(c.getString(c.getColumnIndex("name")));
                result.add(exercise);
            } while (c.moveToNext());
        }
        return result;
    }
}
