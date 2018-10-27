package com.example.yobaiv.kachalochka.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yobaiv.kachalochka.adapters.TrainingListAdapter;
import com.example.yobaiv.kachalochka.classes.Training;
import com.example.yobaiv.kachalochka.classes.helpers.DBHandler;
import com.example.yobaiv.kachalochka.constants.ACode;
import com.example.yobaiv.kachalochka.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String SQLTAG = "SQL";
    public static final int REFRESH_PARENT = 333;
    public static final ACode ACODE = new ACode();


    private final String MAINTAG = "MAIN";
    List<Training> trainings = new ArrayList<>();
    TrainingListAdapter trainingListAdapter;
    FloatingActionButton fabAdd;
    private final DBHandler handler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //clearTables();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_menu);

        //viewDaysExercises();

        Toolbar toolbar = (Toolbar)(findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);

        trainings = handler.getTrainingsFromDatabase();
        Log.d(MAINTAG, "List trainings consists of " + trainings.size() + " elements.");
        trainingListAdapter = new TrainingListAdapter(this, trainings);

        ListView lvTrainings = (ListView) findViewById(R.id.lvMain);
        lvTrainings.setAdapter(trainingListAdapter);
        trainingListAdapter.notifyDataSetChanged();

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.bringToFront();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTraining();
                trainingListAdapter.notifyDataSetChanged();
            }
        });

        lvTrainings.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View  view, int pos, long id){
                viewTraining(parent.getAdapter().getItemId(pos));
            }
        });
        registerForContextMenu(lvTrainings);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo){
        switch (view.getId()){
            case R.id.lvMain : {
                menu.add(0, R.integer.context_menu_view, 0, R.string.list_context_menu_view);
                menu.add(0, R.integer.context_menu_add, 0, R.string.list_context_menu_add);
                menu.add(0, R.integer.context_menu_edit, 0, R.string.list_context_menu_edit);
                menu.add(0, R.integer.context_menu_remove, 0, R.string.list_context_menu_remove);
                menu.add(0, R.integer.context_menu_about, 0, R.string.list_context_menu_remove);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.integer.context_menu_add: {
                addNewTraining();
                break;
            }
            case R.integer.context_menu_view: {
                viewTraining(info.id);
                break;
            }
            case R.integer.context_menu_remove: {
                if(handler.removeTraining(info.id)){
                    Toast t = Toast.makeText(this, R.string.main_training_removed, Toast.LENGTH_SHORT);
                    t.show();
                }
                refreshTrainingsList();
                break;
            }
            case R.integer.context_menu_edit: {
                edirTraining(info.id);
                refreshTrainingsList();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d(MAINTAG, "NewTrainingActivity - OK");
        if (resultCode == RESULT_OK){
            refreshTrainingsList();
        }
    }

    private void addNewTraining(){
        Intent intent = new Intent(this, NewTrainingActivity.class);
        intent.putExtra("trainingnumber", trainings.size() + 1);
        intent.putExtra("requestCode", ACode.NEW_TRAINING);
        startActivityForResult(intent, ACode.NEW_TRAINING);
    }

    private void edirTraining(long id){
        Intent intent = new Intent(this, NewTrainingActivity.class);
        intent.putExtra("trainingid", id);
        intent.putExtra("requestCode", ACode.EDIT_TRAINING);
        startActivityForResult(intent, ACode.EDIT_TRAINING);
    }

    private void viewTraining(long trainingId){
        if (trainingId < 0){
            Log.e(MAINTAG, "Wrong training id");
            return;
        }
        Intent intent = new Intent(this, TrainingActivity.class);
        intent.putExtra("trainingid", trainingId);
        Log.d(MAINTAG, "viewTraining started with training " + trainingId);
        startActivityForResult(intent, ACode.TRAINING);
    }


    private void refreshTrainingsList(){
        trainings.clear();
        trainings.addAll(handler.getTrainingsFromDatabase());
        trainingListAdapter.notifyDataSetChanged();
    }
}
