package com.example.yobaiv.kachalochka.classes;

import android.content.ContentValues;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

/**
 * Created by YOBA IV on 19.10.2017.
 */

public class Set implements IEntity{

    private long id;
    private int count;
    private int number;
    private long unitId;
    private float value;
    private long trexid;
    private ContentValues contentValues = new ContentValues();

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
        if (contentValues.containsKey("unitid")){
            contentValues.remove("unitid");
        }
        contentValues.put("unitid", unitId);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        if (contentValues.containsKey("number")){
            contentValues.remove("number");
        }
        contentValues.put("number", number);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set(){}
    public Set(int count, float value){
        this.count = count;
        this.value = value;
    }
    public Set(int count, float value, int number, long unitId, long trexid){
        setCount(count);
        setValue(value);
        setNumber(number);
        setUnitId(unitId);
        setTrexId(trexid);
    }
    public Set(long id, int count, float value, int number, long unitId, long trexid){
        setCount(count);
        setValue(value);
        setNumber(number);
        setUnitId(unitId);
        this.id = id;
        setTrexId(trexid);
    }

    public void setTrexId(long trexId){
        if(contentValues.containsKey("trexid")){
            contentValues.remove("trexid");
        }
        contentValues.put("trexid",trexId);
        this.trexid = trexId;
    }

    public void setCount(int count){
        this.count = count;
        if (contentValues.containsKey("count")){
            contentValues.remove("count");
        }
        contentValues.put("count", count);
    }

    public void setValue(float value){
        this.value = value;
        if (contentValues.containsKey("value")){
            contentValues.remove("value");
        }
        contentValues.put("value", value);
    }

    public int getCount(){
        return count;
    }
    public float getValue(){
        return value;
    }
    public long getTrexid() {return trexid; }

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
