package com.example.yobaiv.kachalochka.classes;

import android.content.ContentValues;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

/**
 * Created by YOBA IV on 14.02.2018.
 */

public class Meta implements IEntity {

    private long id;
    private String name;
    private long trainingId;
    private String value;
    private boolean isNumeric;
    private ContentValues contentValues;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (contentValues.containsKey("name")){
            contentValues.remove("name");
        }
        contentValues.put("name", name);
        this.name = name;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (contentValues.containsKey("value")){
            contentValues.remove("value");
        }
        contentValues.put("value", value);
        this.value = value;
    }

    public boolean isNumeric() {
        return isNumeric;
    }

    public void setNumeric(boolean numeric) {
        if (contentValues.containsKey("isnumeric")){
            contentValues.remove("isnumeric");
        }
        contentValues.put("isnumeric", numeric);
        isNumeric = numeric;
    }

    public Meta(){
        contentValues = new ContentValues();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Meta(long id, String name, long trainingId, String value, boolean isNumeric){
        setId(id);
        setName(name);
        setTrainingId(trainingId);
        setValue(value);
        setNumeric(isNumeric);
    }

    public Meta(String name, long trainingId, String value, boolean isNumeric){
        setName(name);
        setTrainingId(trainingId);
        setValue(value);
        setNumeric(isNumeric);
    }


    @Override
    public ContentValues getContentValues() {
        return contentValues;
    }

    @Override
    public String[] getIdAsWhereArgs() {
        String[] result = {String.valueOf(id)};
        return result;
    }
}
