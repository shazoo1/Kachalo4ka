package com.example.yobaiv.kachalochka.classes;

import android.content.ContentValues;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

/**
 * Created by YOBA IV on 19.12.2017.
 */

public class Unit implements IEntity{
    private long id;
    private String name;
    private ContentValues contentValues = new ContentValues();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        if (contentValues.containsKey("id")){
            contentValues.remove("id");
        }
        contentValues.put("id", id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (contentValues.containsKey("name")){
            contentValues.remove("name");
        }
        contentValues.put("name", name);
    }

    public Unit(){

    }

    public Unit(String name){
        setName(name);
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
