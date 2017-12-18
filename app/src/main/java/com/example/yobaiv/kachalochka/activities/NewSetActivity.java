package com.example.yobaiv.kachalochka.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.classes.DBhelper;

import static com.example.yobaiv.kachalochka.activities.MainActivity.SQLTAG;

public class NewSetActivity extends AppCompatActivity {
    private long deid = -1;
    private int number = 0;
    private final String SELFTAG = "NewSetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_field_input);
        Toolbar toolbar = (Toolbar)(findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        setTitle("Новый подход");
        Bundle b = getIntent().getExtras();
        deid = b.getLong("deid");
        number = b.getInt("number");
        Log.d(SELFTAG, "About to add new set number " +number+ " for exercise with ID "+ deid);

        EditText etFirst = (EditText) findViewById(R.id.etFirst);
        EditText etSecond = (EditText) findViewById(R.id.etSecond);

        etFirst.setHint("Вес");
        etSecond.setHint("Повторений");
        etFirst.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etSecond.setInputType(InputType.TYPE_CLASS_NUMBER);

        FloatingActionButton fabOk = (FloatingActionButton) findViewById(R.id.fabOk);
        fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strweight = ((EditText)findViewById(R.id.etFirst)).getText().toString();
                String strcount = ((EditText) findViewById(R.id.etSecond)).getText().toString();
                if(TextUtils.isEmpty(strweight)) {
                    ((EditText) findViewById(R.id.etFirst)).setError("Введите вес");
                } else {
                    if(TextUtils.isEmpty(strcount)){
                        ((EditText)findViewById(R.id.etSecond)).setError("Введите количество повторений");
                    }
                    else{
                        float weight = Float.parseFloat(strweight);
                        int count = Integer.parseInt(strcount);
                        addSet(weight, count);
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            }
        });


    }
    private void addSet(float weight, int count){
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("deid", deid);
        cv.put("count", count);
        cv.put("number", number);
        cv.put("weight", weight);

        if (db.insert("sets", "null", cv)!=-1){
            Log.d(SELFTAG+"."+SQLTAG, "Added set number "+ number + ". Weight: "+weight);
            setResult(1);
        }
    }
}
