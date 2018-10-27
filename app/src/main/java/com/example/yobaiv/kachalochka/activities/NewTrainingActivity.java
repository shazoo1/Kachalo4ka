package com.example.yobaiv.kachalochka.activities;

import android.os.Bundle;
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
import com.example.yobaiv.kachalochka.classes.Training;
import com.example.yobaiv.kachalochka.classes.helpers.DBHandler;
import com.example.yobaiv.kachalochka.constants.ACode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.yobaiv.kachalochka.activities.MainActivity.SQLTAG;

public class NewTrainingActivity extends AppCompatActivity {
    String SELFTAG = "NewTrainingActivity";
    int number;
    DBHandler handler = new DBHandler(this);
    int CODE;
    long trainingid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.two_field_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Date date = new Date();

        Bundle b = getIntent().getExtras();
        CODE = b.getInt("requestCode");

        switch (CODE){
            case ACode.NEW_TRAINING : {
                Log.d(SELFTAG, "NEW TRAINING");
                number = (int) b.get("trainingnumber");
                ((EditText) findViewById(R.id.etFirst)).setText("День " + number + " - " +
                        new SimpleDateFormat("dd/MM/yyyy").format(date));
                ((EditText) findViewById(R.id.etSecond)).setHint("Комментарий");
                break;
            }
            case ACode.EDIT_TRAINING:{
                Log.d(SELFTAG, "EDIT TRAINING");
                trainingid = b.getLong("trainingid");
                Training training = handler.getTrainingById(b.getLong("trainingid"));
                ((EditText) findViewById(R.id.etFirst)).setText(training.getTitle());
                ((EditText) findViewById(R.id.etSecond)).setText(training.getComment());
                break;
            }
        };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabOk);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ((TextView) findViewById(R.id.etFirst)).getText().toString();
                String comment = ((TextView) findViewById(R.id.etSecond)).getText().toString();

                if (!title.isEmpty()) {
                    switch (CODE) {
                        case ACode.EDIT_TRAINING:{
                            Training training = handler.getTrainingById(NewTrainingActivity.this.trainingid);
                            training.setTitle(title);
                            training.setComment(comment);
                            training.setId(trainingid);
                            handler.alterRow(training, "training");
                            setResult(RESULT_OK);
                            break;
                        }
                        case ACode.NEW_TRAINING:{
                            long date = Calendar.getInstance().getTime().getTime();
                            long trainingId = handler.putIntoDB(new Training(date, title, comment), "training");
                            setResult(RESULT_OK);
                            Log.d(SELFTAG+"."+SQLTAG, "Successfully added training "+title+" with id "+trainingId);
                        }
                    }
                }
                else{
                    Toast t = Toast.makeText(getApplicationContext(), R.string.unable_to_add, Toast.LENGTH_SHORT);
                    t.show();
                    Log.d(SELFTAG,"Title or type ID is wrong.");
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
    }
}
