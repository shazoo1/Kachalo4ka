package com.example.yobaiv.kachalochka.classes;

import android.content.ContentValues;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

/**
 * Created by YOBA IV on 05.02.2018.
 */

public class TrainingExercise implements IEntity {

    private long id;
    private long trainingId;
    private long exerciseId;
    private ContentValues contentValues = new ContentValues();

    public TrainingExercise() {
    }

    public TrainingExercise(long id, long trainingId, long exerciseId) {
        this.id = id;
        setExerciseId(exerciseId);
        setTrainingId(trainingId);
    }


    public TrainingExercise(long trainingId, long exerciseId) {
        setExerciseId(exerciseId);
        setTrainingId(trainingId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        if (contentValues.containsKey("trainingid")){
            contentValues.remove("trainingid");
        }
        contentValues.put("trainingid", trainingId);
        this.trainingId = trainingId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        if (contentValues.containsKey("exrciseid")){
            contentValues.remove("exerciseid");
        }
        contentValues.put("exerciseid", exerciseId);
        this.exerciseId = exerciseId;
    }


    @Override
    public ContentValues getContentValues() {
        return contentValues;
    }

    @Override
    public String[] getIdAsWhereArgs(){
        String[] result = {String.valueOf(id)};
        return result;
    }
}
