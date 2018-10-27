package com.example.yobaiv.kachalochka.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yobaiv.kachalochka.adapters.NewExerciseDDLAdapter;
import com.example.yobaiv.kachalochka.classes.TrainingExercise;
import com.example.yobaiv.kachalochka.classes.helpers.DBHandler;
import com.example.yobaiv.kachalochka.constants.ACode;
import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.adapters.ExerciseAdapter;
import com.example.yobaiv.kachalochka.classes.Exercise;

import java.util.ArrayList;
import java.util.List;

import static com.example.yobaiv.kachalochka.activities.MainActivity.SQLTAG;

public class TrainingActivity extends AppCompatActivity {
    private final String SELFTAG = "TrainingActivity";

    FloatingActionButton fabAdd;
    private long trainingId = -1;
    private String trainingTitle = "";
    ExerciseAdapter exerciseAdapter;
    DBHandler handler = new DBHandler(this);
    List<Exercise> exercises = new ArrayList<>();
    List<TrainingExercise> trexes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_menu);

        trainingId = getIntent().getLongExtra("trainingid", -1);
        trainingTitle = handler.getTrainingTitle(trainingId);
        exerciseAdapter = new ExerciseAdapter(this, exercises, trainingId);

        refreshExercises();

        Toolbar toolbar = (Toolbar)(findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(trainingTitle);

        ListView lvExercises = (ListView) findViewById(R.id.lvMain);
        lvExercises.setAdapter(exerciseAdapter);
        registerForContextMenu(lvExercises);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.bringToFront();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newExercise();
                Log.d(SELFTAG, "Adding exercise");
            }
        });
        lvExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewExercise(parent.getAdapter().getItemId(position));
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info){
        switch (view.getId()){
            case R.id.lvMain : {
                menu.add(0, R.integer.context_menu_view, 0, R.string.list_context_menu_view);
                menu.add(0, R.integer.context_menu_edit, 0, R.string.list_context_menu_edit);
                menu.add(0, R.integer.context_menu_add, 0, R.string.list_context_menu_add);
                menu.add(0, R.integer.context_menu_remove, 0, R.string.list_context_menu_remove);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.integer.context_menu_add: {
                newExercise();
                break;
            }
            case R.integer.context_menu_view:{
                viewExercise(info.id);
                break;
            }
            case R.integer.context_menu_remove:{
                removeExercise(info.id);
                refreshExercises();
                break;
            }
            case R.integer.context_menu_edit:{
                editExercise(info.id);
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    private void newExercise(){
        LayoutInflater li = LayoutInflater.from(this);
        View exView = li.inflate(R.layout.new_exercise_dialog, null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setView(exView);

        NewExerciseDDLAdapter exerciseDDLAdapter = new NewExerciseDDLAdapter(this, R.layout.ddlist_item ,
                handler.getExercisesFromDatabase());
        final AutoCompleteTextView exerciseName = ((AutoCompleteTextView) exView.findViewById(R.id.acExerciseName));
        exerciseName.setAdapter(exerciseDDLAdapter);

        mBuilder
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String mName = exerciseName.getText().toString();
                        addExercise(mName);
                        refreshExercises();
                        setResult(RESULT_OK);
                        exerciseAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CANCELED);
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    private void addExercise(String exName){

        long exerciseId;

        List<Exercise> exercises = handler.getExercisesFromDatabase("name = '"+exName+"'");
        if (exercises.isEmpty()){
            exerciseId = handler.putIntoDB(new Exercise(exName), "exercise");
        }
        else{
            exerciseId = exercises.get(0).getId();
        }
        handler.putIntoDB(new TrainingExercise(trainingId, exerciseId), "trainingexercise");

        Log.d(SELFTAG + "." + SQLTAG, "Added exercise "+exName+", id="+exerciseId+" for list_item "+
            trainingId);
        exerciseAdapter.notifyDataSetChanged();
    }

    private void viewExercise(long exerciseId){
            Intent intent = new Intent(this, ExerciseActivity.class);
            intent.putExtra("exerciseid", exerciseId);
            intent.putExtra("trainingid", trainingId);
            startActivityForResult(intent, ACode.SETS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        switch (requestCode){
            case ACode.SETS : {
                if (resultCode == RESULT_OK){
                    exerciseAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
            }
        }
        return false;
    }

    private void refreshExercises(){
        exercises.clear();
        exercises.addAll(handler.getExercisesForTraining(trainingId));
        exerciseAdapter.notifyDataSetChanged();
    }

    private void removeExercise(long id){
        Log.d(SELFTAG, "Trying to remove trex ex: "+id+"; tr: "+trainingId);
        handler.removeExerciseFromTraining(trainingId, id);
    }

    private void editExercise(final long exerciseId){
        Log.d(SELFTAG, "Trying to edit trex ex: "+exerciseId+"; tr: "+trainingId);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.new_exercise_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final DBHandler handler = new DBHandler(this);
        final AutoCompleteTextView acExerciseName = (AutoCompleteTextView) dialogView.findViewById(R.id.acExerciseName);
        NewExerciseDDLAdapter exerciseDDLAdapter = new NewExerciseDDLAdapter(this, R.layout.ddlist_item ,
                handler.getExercisesFromDatabase());
        acExerciseName.setText(handler.getExerciseNameById(exerciseId));
        acExerciseName.setAdapter(exerciseDDLAdapter);

        builder
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (handler.checkIfExerciseIsNew(acExerciseName.getText().toString())) {
                            Log.d(SELFTAG, "Trying to alter exercise (new)");
                            long newExerciseId = handler.putIntoDB(new Exercise(((AutoCompleteTextView)
                                    findViewById(R.id.acExerciseName)).getText().toString()), "exercise");
                            long trexId = handler.getTrainingExerciseId(trainingId, exerciseId);
                            handler.alterRow(new TrainingExercise(trexId, trainingId, newExerciseId), "trainingexercise");
                        }
                        else {
                            Log.d(SELFTAG, "Trying to alter exercise (existing)");
                            long newExerciseId = handler.getExerciseIdByName(acExerciseName.getText().toString());
                            Log.d(SELFTAG, "Changing exercise " + exerciseId + " for " + newExerciseId);
                            long trexId = handler.getTrainingExerciseId(trainingId, exerciseId);
                            handler.alterRow(new TrainingExercise(trexId, trainingId, newExerciseId), "trainingexercise");
                        }
                        setResult(RESULT_OK);
                        showToast(R.string.edit_exercise_success);
                        refreshExercises();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CANCELED);
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showToast(int stringRes){
        Toast t = Toast.makeText(this, stringRes, Toast.LENGTH_SHORT);
        t.show();
    }
}
