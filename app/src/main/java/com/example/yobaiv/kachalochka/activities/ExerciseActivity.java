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

import com.example.yobaiv.kachalochka.classes.helpers.DBHandler;
import com.example.yobaiv.kachalochka.constants.ACode;
import com.example.yobaiv.kachalochka.R;
import com.example.yobaiv.kachalochka.adapters.SetsAdapter;
import com.example.yobaiv.kachalochka.classes.Set;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {
    private static final int MENU_VIEW = 1;
    private static final int MENU_ADD = 2;
    private static final int MENU_REMOVE = 3;
    private final String SELFTAG = "ExerciseActivity";
    private ArrayList<Set> sets = new ArrayList<>();
    private String exname = "";
    SetsAdapter adapter;
    DBHandler handler;

    private long trainingExerciseId;
    private long exerciseId;
    private long trainingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        exerciseId = b.getLong("exerciseid");
        trainingId = b.getLong("trainingid");


        handler = new DBHandler(this);
        trainingExerciseId = handler.getTrainingExerciseId(trainingId, exerciseId);

        exname = handler.getExercisesFromDatabase("id = "+exerciseId).get(0).getName();

        Log.d(SELFTAG, "Started for exercise "+ exerciseId +" - "+exname);

        setContentView(R.layout.list_menu);
        Toolbar toolbar = (Toolbar)(findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(exname);
        adapter = new SetsAdapter(this, sets);
        refreshSets();

        ListView lv = (ListView)findViewById(R.id.lvMain);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSet();
            }
        });
    }

    private void refreshSets(){
        sets.clear();
        DBHandler handler = new DBHandler(this);
        sets.addAll(handler.getSetsFromDatabase(trainingId, exerciseId));
        Log.d(SELFTAG, "Got "+sets.size()+" sets");
        adapter.notifyDataSetChanged();
    }

    private void addSet(){
        Intent intent = new Intent(this, NewSetActivity.class);
        intent.putExtra("exerciseid", this.exerciseId);
        intent.putExtra("trainingexerciseid", trainingExerciseId);
        intent.putExtra("number", sets.size()+1);
        startActivityForResult(intent, ACode.NEW_SET);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info){
        switch (view.getId()){
            case R.id.lvMain : {
                menu.add(0, MENU_ADD, 0, R.string.list_context_menu_add);
                menu.add(0, MENU_REMOVE, 0, R.string.list_context_menu_remove);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case ACode.NEW_SET : {
                if (resultCode == RESULT_OK){
                    refreshSets();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home : {
                finish();
                break;
            }

        }
        return false;

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case MENU_ADD: {
                addSet();
                break;
            }
            case MENU_REMOVE: {
                if (handler.removeSet(info.id)) {
                    Toast t = Toast.makeText(this, R.string.exercise_set_removed, Toast.LENGTH_SHORT);
                    t.show();
                }
                refreshSets();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }
}
