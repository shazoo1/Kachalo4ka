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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.adapters.UnitsDDLAdapter;
import com.example.yobaiv.kachalochka.classes.Set;
import com.example.yobaiv.kachalochka.classes.Unit;
import com.example.yobaiv.kachalochka.classes.helpers.DBHandler;
import com.example.yobaiv.kachalochka.classes.helpers.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.yobaiv.kachalochka.activities.MainActivity.SQLTAG;

public class NewSetActivity extends AppCompatActivity {
    private long trainingExerciseId;
    private int number = 0;
    private final String SELFTAG = "NewSetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_field_bot_ac);
        Toolbar toolbar = (Toolbar)(findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.new_set_title);

        Bundle b = getIntent().getExtras();
        trainingExerciseId = b.getLong("trainingexerciseid");
        number = b.getInt("number");

        DBHandler handler = new DBHandler(this);
        UnitsDDLAdapter unitsAdapter = new UnitsDDLAdapter(this, R.layout.ddlist_item, handler.getUnitsFromDatabase());

        Log.d(SELFTAG, "About to add new set number " +number+ " for trex with ID "+ trainingExerciseId);

        EditText etFirst = (EditText) findViewById(R.id.etFirst);
        EditText etSecond = (EditText) findViewById(R.id.etSecond);
        AutoCompleteTextView acUnit = (AutoCompleteTextView) findViewById(R.id.acThird);
        acUnit.setAdapter(unitsAdapter);

        etFirst.setHint(R.string.new_set_weight_etc);
        etSecond.setHint(R.string.new_set_repeats);
        acUnit.setHint(R.string.new_set_unit);
        etFirst.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etSecond.setInputType(InputType.TYPE_CLASS_NUMBER);

        FloatingActionButton fabOk = (FloatingActionButton) findViewById(R.id.fabOk);
        fabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strweight = ((EditText)findViewById(R.id.etFirst)).getText().toString();
                String strcount = ((EditText) findViewById(R.id.etSecond)).getText().toString();
                String unitName = (((AutoCompleteTextView) findViewById(R.id.acThird)).getText()).toString();
                if(TextUtils.isEmpty(strweight)) {
                    ((EditText) findViewById(R.id.etFirst)).setError(getText(R.string.new_set_error_weight_empty));
                } else {
                    if(TextUtils.isEmpty(strcount)){
                        ((EditText)findViewById(R.id.etSecond)).setError(getText(R.string.new_set_error_count_empty));
                    }
                    else{
                        if(TextUtils.isEmpty(unitName)){
                            ((AutoCompleteTextView)findViewById(R.id.acThird)).setError(getText(R.string.new_set_error_unit_empty));
                        }
                        else {
                            float weight = Float.parseFloat(strweight);
                            int count = Integer.parseInt(strcount);
                            long unitId = addUnit(unitName);
                            addSet(weight, count, unitId);
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }
            }
        });
    }

    private long addUnit(String unitName){
        DBHandler handler = new DBHandler(this);
        long id = -1;
        List<Unit> units;
        if ((units = handler.getUnitsFromDatabase("name = '" + unitName + "'")).isEmpty()){
            return handler.putIntoDB(new Unit(unitName), "unit");
        } else {
            return units.get(0).getId();
        }
    }

    private long addSet(float weight, int count, long unitId){
        DBHandler handler = new DBHandler(this);
        return handler.putIntoDB(new Set(count, weight, number, unitId, trainingExerciseId), "settable");
    }
}
