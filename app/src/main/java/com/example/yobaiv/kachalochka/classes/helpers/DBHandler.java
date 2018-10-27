package com.example.yobaiv.kachalochka.classes.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yobaiv.kachalochka.classes.Exercise;
import com.example.yobaiv.kachalochka.classes.Set;
import com.example.yobaiv.kachalochka.classes.Training;
import com.example.yobaiv.kachalochka.classes.TrainingExercise;
import com.example.yobaiv.kachalochka.classes.Unit;
import com.example.yobaiv.kachalochka.classes.helpers.DBhelper;
import com.example.yobaiv.kachalochka.interfaces.IEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.yobaiv.kachalochka.activities.MainActivity.SQLTAG;

/**
 * Created by YOBA IV on 19.12.2017.
 */

public class DBHandler {
    private final String SELFTAG = "DBHandler";

    private final String[] unitColumns = {"id", "name"};
    private final String[] exerciseColumns = {"id", "name"};
    private final String[] trainingColumns = {"id", "date", "title", "comment"};
    private final String[] metaColumns = {"id","trainingid", "name", "value", "isnumeric"};
    private final String[] setColumns = {"id", "number", "value", "count", "unitid, trexid"};
    private final String[] trainingExerciseColumns = {"id", "trainingid", "exerciseid"};
    Context context;

    public DBHandler(Context context){
        this.context = context;
    }

    //region Database readers
    public List<Training> getTrainingsFromDatabase(String condition){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Training> result = new ArrayList<>();
        Cursor c = db.query("training", trainingColumns, condition, null, null, null, null);
        helper.logCursor(c);
        if(c.moveToFirst()){
            do{
                Training training = new Training();
                training.setId(c.getLong(c.getColumnIndex("id")));
                training.setDate(c.getLong(c.getColumnIndex("date")));
                training.setComment(c.getString(c.getColumnIndex("comment")));
                training.setTitle(c.getString(c.getColumnIndex("title")));
                result.add(training);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        helper.close();
        return result;
    }

    public List<Unit> getUnitsFromDatabase(String condition){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Unit> result = new ArrayList<>();
        Cursor c = db.query("unit", unitColumns, condition, null, null, null, null);
        helper.logCursor(c);
        if(c.moveToFirst()){
            do{
                Unit unit = new Unit();
                unit.setId(c.getLong(c.getColumnIndex("id")));
                unit.setName(c.getString(c.getColumnIndex("name")));
                result.add(unit);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        helper.close();
        return result;
    }

    public List<Exercise> getExercisesFromDatabase(String condition){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Exercise> result = new ArrayList<>();
        Cursor c = db.query("exercise", exerciseColumns, condition, null, null, null, null);
        helper.logCursor(c);
        if(c.moveToFirst()){
            do{
                Exercise exercise = new Exercise();
                exercise.setId(c.getLong(c.getColumnIndex("id")));
                exercise.setName(c.getString(c.getColumnIndex("name")));
                result.add(exercise);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        helper.close();
        return result;
    }

    public String getExerciseNameById(long exerciseId){
        Log.d(SELFTAG, "Trying to get exercise "+exerciseId);
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(exerciseId)};
        Cursor c = db.query("exercise", exerciseColumns, "id=?", selectionArgs, null, null, null);
        helper.logCursor(c);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("name"));
    }

    public List<TrainingExercise> getTrainingExercisesFromDatabase(String condition){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<TrainingExercise> result = new ArrayList<>();
        Cursor c = db.query("trainingexercise", trainingExerciseColumns, condition, null, null, null, null);
        helper.logCursor(c);
        if(c.moveToFirst()){
            do{
                TrainingExercise trainingExercise = new TrainingExercise();
                trainingExercise.setId(c.getLong(c.getColumnIndex("id")));
                trainingExercise.setTrainingId(c.getLong(c.getColumnIndex("trainingid")));
                trainingExercise.setExerciseId(c.getLong(c.getColumnIndex("exerciseid")));
                result.add(trainingExercise);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        helper.close();
        return result;
    }

    public List<Set> getSetsFromDatabase(String condition){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Set> result = new ArrayList<>();
        Cursor c = db.query("settable", setColumns, condition, null, null, null, null);
        helper.logCursor(c);
        if (c.moveToFirst()){
            do{
                Set set = new Set();
                set.setId(c.getLong(c.getColumnIndex("id")));
                set.setNumber(c.getInt(c.getColumnIndex("number")));
                set.setValue(c.getFloat(c.getColumnIndex("value")));
                set.setCount(c.getInt(c.getColumnIndex("count")));
                set.setUnitId(c.getLong(c.getColumnIndex("unitid")));
                result.add(set);
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        helper.close();
        return result;
    }

    public List<TrainingExercise> getTrainingExercisesFromDatabase(long trainingId, long exerciseId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<TrainingExercise> result = new ArrayList<>();
        Cursor c = db.query("trainingexercise", trainingExerciseColumns, "trainingid = "+trainingId+" and exerciseid = "+exerciseId, null, null, null, null);
        helper.logCursor(c);
        if(c.moveToFirst()){
            do{
                TrainingExercise trainingExercise = new TrainingExercise();
                trainingExercise.setId(c.getLong(c.getColumnIndex("id")));
                trainingExercise.setTrainingId(c.getLong(c.getColumnIndex("trainingid")));
                trainingExercise.setExerciseId(c.getLong(c.getColumnIndex("exerciseid")));
                result.add(trainingExercise);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        helper.close();
        return result;
    }

    public List<TrainingExercise> getTrainingExercisesFromDatabase(long trainingId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<TrainingExercise> result = new ArrayList<>();
        Cursor c = db.query("trainingexercise", trainingExerciseColumns, "trainingid = "+trainingId, null, null, null, null);
        helper.logCursor(c);
        if(c.moveToFirst()){
            do{
                TrainingExercise trainingExercise = new TrainingExercise();
                trainingExercise.setId(c.getLong(c.getColumnIndex("id")));
                trainingExercise.setTrainingId(c.getLong(c.getColumnIndex("trainingid")));
                trainingExercise.setExerciseId(c.getLong(c.getColumnIndex("exerciseid")));
                result.add(trainingExercise);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        helper.close();
        return result;
    }

    public List<Exercise> getExercisesForTraining(long trainingId){
        DBhelper helper = new DBhelper(context);
        List<Exercise> result = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from trainingexercise " +
                "inner join exercise on exercise.id = trainingexercise.exerciseid " +
                "and (trainingid = " + trainingId + ")", null);
        if(c.moveToFirst()){
            do {
                Exercise ex = new Exercise();
                ex.setId(c.getLong(c.getColumnIndex("exerciseid")));
                ex.setName(c.getString(c.getColumnIndex("name")));
                result.add(ex);
            } while (c.moveToNext());
        }
        helper.close();
        return result;
    }

    public String getTrainingTitle(long trainingId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select title as title from training where id = "+trainingId, null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex("title"));
    }

    public List<Training> getTrainingsFromDatabase(){
        return getTrainingsFromDatabase(null);
    }

    public List<Unit> getUnitsFromDatabase(){
        return getUnitsFromDatabase(null);
    }

    public List<Exercise> getExercisesFromDatabase(){
        return getExercisesFromDatabase(null);
    }

    public List<TrainingExercise> getTrainingExercisesFromDatabase(){
        return getTrainingExercisesFromDatabase(null);
    }

    public List<Set> getSetsFromDatabase(){
        return getSetsFromDatabase(null);
    }

    public String[] getTrainingExerciseIdsForTrainingId(long trainingId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] result = {};
        Cursor c = db.query("trainingexercise", trainingExerciseColumns, "trainingid = "+trainingId, null, null, null, null);
        helper.logCursor(c);
        if(c.moveToFirst()){
            int i = 0;
            do{
                result[i] = c.getString(c.getColumnIndex("exerciseid"));
                i++;
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        helper.close();
        return result;
    }

    public List<Set> getSetsFromDatabase(long trainingId, long exerciseId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Set> result = new ArrayList<>();
        Cursor c = db.rawQuery("select trainingexercise.trainingid as trainingid, " +
                "trainingexercise.exerciseid as exerciseid, " +
                "settable.id as id, " +
                "settable.unitid as unitid, " +
                "settable.number as number, " +
                "settable.value as value, " +
                "settable.count as count, " +
                "unit.name as unitname " +
                "from trainingexercise " +
                "inner join settable on trainingexercise.id = settable.trexid " +
                "inner join unit on settable.unitid = unit.id and " +
                "(trainingid = " + trainingId + " and exerciseid = " + exerciseId +") " +
                "order by number", null);
        helper.logCursor(c);
        if (c.moveToFirst()){
            do{
                Set set = new Set();
                set.setId(c.getLong(c.getColumnIndex("id")));
                set.setUnitId(c.getLong(c.getColumnIndex("unitid")));
                set.setNumber(c.getInt(c.getColumnIndex("number")));
                set.setValue(c.getFloat(c.getColumnIndex("value")));
                set.setCount(c.getInt(c.getColumnIndex("count")));
                result.add(set);
            } while (c.moveToNext());
        }
        helper.close();
        return result;
    }

    public List<Set> getSetsByTrexId(long trexId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from settable where trexid = "+trexId, null);
        List<Set> result = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                Set set = new Set();
                set.setId(c.getLong(c.getColumnIndex("id")));
                set.setCount(c.getInt(c.getColumnIndex("count")));
                set.setUnitId(c.getLong(c.getColumnIndex("unitid")));
                set.setValue(c.getFloat(c.getColumnIndex("value")));
                set.setNumber(c.getInt(c.getColumnIndex("number")));
                set.setTrexId(c.getLong(c.getColumnIndex("trexid")));
                result.add(set);
            } while (c.moveToNext());
        }
        return result;
    }

    public Unit getUnitById(double id){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from unit where id = "+id, null);
        Unit unit = new Unit();
        if (c.moveToFirst()){
            unit.setId(c.getLong(c.getColumnIndex("id")));
            unit.setName(c.getString(c.getColumnIndex("name")));
        }
        return unit;
    }

    public Training getTrainingById(long id){
        DBhelper dBhelper = new DBhelper(context);
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from training where id = "+id, null);
        Training result = new Training();
        if (c.moveToFirst()){
            result.setComment(c.getString(c.getColumnIndex("comment")));
            result.setId(c.getLong(c.getColumnIndex("id")));
            result.setDate(c.getLong(c.getColumnIndex("date")));
            result.setTitle(c.getString(c.getColumnIndex("title")));
        }
        return result;
    }

    public long getTrainingExerciseId (long trainingId, long exerciseId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select id from trainingexercise where trainingid = "+trainingId+ " and exerciseid = "
                + exerciseId, null);
        if (c.moveToFirst()){
            return c.getLong(c.getColumnIndex("id"));
        }
        else {return  -1;}
    }

    public long getExerciseIdByName(String name){
        DBhelper helper = new DBhelper(context);
        String[] whereArgs = {name};
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("exercise", exerciseColumns, "name=?", whereArgs, null, null, null);
        c.moveToFirst();
        return c.getLong(c.getColumnIndex("id"));
    }

    //endregion

    public long putIntoDB(IEntity entity, String table){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Log.d(SELFTAG, "Trying to add entity:");
        logContentValues(entity.getContentValues());
        long id = db.insert(table, null, entity.getContentValues());
        helper.close();
        db.close();
        return id;
    }

    //region Removers

    public boolean removeTraining(long trainingId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {String.valueOf(trainingId)};
        return db.delete("training", "id=?", whereArgs) > 0;
    }

    public void removeExerciseFromTraining(long trainingId, long exerciseId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from trainingexercise where trainingid = "+trainingId +
                " and exerciseid = "+exerciseId);
    }

    public boolean removeSet(long setId){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {String.valueOf(setId)};
        return db.delete("settable", "id=?", whereArgs) > 0;
    }

    //endregion

    //region Checkers

    public boolean checkIfExerciseIsNew(String exerciseName){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] selecctionArgs = {exerciseName};
        Cursor c = db.query("exercise", exerciseColumns, "name=?", selecctionArgs, null, null, null);
        return !c.moveToFirst();
    }

    //endregion

    public void alterRow(IEntity entity, String table){
        DBhelper dBhelper = new DBhelper(context);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        db.update(table, entity.getContentValues(), "id=?", entity.getIdAsWhereArgs());
    }

    private String wrapStringIntoQuotes(String input){
        String output = "'";
        output += input;
        output += "'";
        return output;
    }

    private void logContentValues(ContentValues contentValues){
        for (String key : contentValues.keySet()){
            Log.d(SELFTAG, key + " = " + contentValues.get(key));
        }
    }

}
