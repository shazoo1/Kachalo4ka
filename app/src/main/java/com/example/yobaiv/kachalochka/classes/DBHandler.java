package com.example.yobaiv.kachalochka.classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by YOBA IV on 19.12.2017.
 */

public class DBHandler {

    private final String[] unitColumns = {"id", "name"};
    private final String[] exerciseColumns = {"id", "name"};
    private final String[] typeColumns = {"id", "name"};
    private final String[] trainingColumns = {"id", "dayid", "exerciseid"};
    private final String[] dayColumns = {"id", "typeid", "date", "title"};
    private final String[] metaColumns = {"dayid", "name", "value", "isnumeric"};
    private final String[] setColumns = {"trainingid", "number", "value", "count", "unitid"};
    Context context;

    public DBHandler(Context context){
        this.context = context;
    }

    public List<IEntity> getObjectsFromTable(String tableName, String condition){
        DBhelper helper = new DBhelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c;
        List<IEntity> result = new ArrayList<>();

        switch (tableName){
            case "unit" : {
                c = db.query(tableName, unitColumns, condition, null, null, null, null);
                if(c.moveToFirst()){
                    do{
                        Unit unit = new Unit();
                        unit.setId(c.getLong(c.getColumnIndex("id")));
                        unit.setName(c.getString(c.getColumnIndex("name")));
                        result.add(unit);
                    } while (c.moveToNext());
                }
                return result;
            }
            case "exercise" : {
                c = db.query(tableName, exerciseColumns, condition, null, null, null, null);
                if(c.moveToFirst()){
                    do{
                        Exercise exercise = new Exercise();
                        exercise.setId(c.getLong(c.getColumnIndex("id")));
                        exercise.setName(c.getString(c.getColumnIndex("name")));
                        result.add(exercise);
                    } while (c.moveToNext());
                }
                return result;
            }
            case "type" : {
                c = db.query(tableName, typeColumns, condition, null, null, null, null);
                if(c.moveToFirst()){
                    do{
                        DayType dayType = new DayType();
                        dayType.setId(c.getLong(c.getColumnIndex("id")));
                        dayType.setName(c.getString(c.getColumnIndex("name")));
                        result.add(dayType);
                    } while (c.moveToNext());
                }
                return result;
            }
            case "day" : {
                c = db.query(tableName, dayColumns, condition, null, null, null, null);
                if(c.moveToFirst()){
                    do{
                        Day day = new Day();
                        day.setId(c.getLong(c.getColumnIndex("id")));
                        day.setDate(new Date(c.getLong(c.getColumnIndex("date"))));
                        day.setTypeId(c.getLong(c.getColumnIndex("typeid")));
                        day.setTitle(c.getString(c.getColumnIndex("title")));
                        result.add(day);
                    } while (c.moveToNext());
                }
                return result;
            }
        }
    }


}
