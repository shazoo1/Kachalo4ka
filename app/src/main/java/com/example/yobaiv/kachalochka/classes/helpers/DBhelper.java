package com.example.yobaiv.kachalochka.classes.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.yobaiv.kachalochka.activities.MainActivity.SQLTAG;

/**
 * Created by YOBA IV on 24.10.2017.
 */

public class DBhelper extends SQLiteOpenHelper{

    private final String SELFTAG = "DBHelper";
    public DBhelper (Context context){
        super (context, "kach", null, 10);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table exercise ("
                +"id integer primary key autoincrement,"
                +"name text not null)");
        db.execSQL("create table unit (" +
                "id integer primary key autoincrement," +
                "name text not null)");
        db.execSQL("create table training (" +
                "id integer primary key autoincrement," +
                "date integer not null," +
                "title text not null," +
                "comment text)");
        db.execSQL("create table meta (" +
                "id integer primary key autoincrement, " +
                "name text not null," +
                "trainingid integer not null," +
                "value text not null," +
                "isnumeric integer not null default 0," +
                "foreign key (trainingid) references training(id) on delete cascade)");
        db.execSQL("create table trainingexercise (" +
                "id integer primary key autoincrement," +
                "exerciseid integer not null," +
                "trainingid integer not null," +
                "foreign key (exerciseid) references exercise(id) on delete cascade," +
                "foreign key (trainingid) references training(id) on delete cascade)");
        db.execSQL("create table settable (" +
                "id integer primary key autoincrement," +
                "trexid integer not null," +
                "number integer not null," +
                "value real not null," +
                "count integer not null," +
                "unitid integer," +
                "foreign key (unitid) references unit(id) on delete set null," +
                "foreign key (trexid) references trainingexercise(id) on delete cascade)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d(SELFTAG, "Trying to upgrade database");
        Log.d(SELFTAG, "Durrent version is: " + oldVersion);
        if (oldVersion < 2){
            db.execSQL("alter table exercises add column dayid integer default -1 references days(id)");
        }
        if (oldVersion < 3){
            db.execSQL("alter table days add column date datetime not null default \"2017-01-01 00:00:00\"");
        }
        if (oldVersion < 4){
            db.execSQL("create table days_backup(id integer, title text, typeid integer)");
            db.execSQL("insert into days_backup select id, title, typeid from days");
            db.execSQL("drop table days");
            db.execSQL("create table days ("
                    +"id integer primary key autoincrement,"
                    +"title text,"
                    +"typeid integer not null,"
                    +"foreign key (typeid) references types(id)"+")");
            db.execSQL("insert into days select id, title, typeid from days_backup");
            db.execSQL("alter table days add column date integer not null default 0;");
        }
        if (oldVersion < 5){
            db.execSQL("drop table days");
            db.execSQL("drop table exercises");
            db.execSQL("drop table types");
            db.execSQL("drop table sets");
            db.execSQL("drop table daysexercises");
            onCreate(db);
        }
        if (oldVersion<6){
            db.execSQL("drop table days");
            db.execSQL("drop table exercises");
            db.execSQL("drop table types");
            db.execSQL("drop table sets");
            db.execSQL("drop table daysexercises");
            onCreate(db);
        }
        if (oldVersion<7){
            db.execSQL("drop table day");
            db.execSQL("drop table exercise");
            db.execSQL("drop table type");
            db.execSQL("drop table settable");
            db.execSQL("drop table daysexercises");
            onCreate(db);
        }
        if (oldVersion<9){
            db.execSQL("drop table trainingexercise");
            db.execSQL("drop table training");
            db.execSQL("drop table meta");
            db.execSQL("drop table exercise");
            db.execSQL("drop table settable");
            db.execSQL("drop table unit");
            onCreate(db);
        }
        if (oldVersion<10){
            db.execSQL("drop table meta");
            db.execSQL("create table meta (" +
                    "id integer primary key autoincrement, " +
                    "name text not null," +
                    "trainingid integer not null," +
                    "value text not null," +
                    "isnumeric integer not null default 0," +
                    "foreign key (trainingid) references training(id) on delete cascade)");
        }
        Log.d(SELFTAG, "Current DB version is "+newVersion);
    }

    public void logCursor(Cursor c){
        if (c != null){
            if (c.moveToFirst()){
                do{
                    String str = "";
                    for (String cn: c.getColumnNames())
                    {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn))+";");
                    }
                    Log.d(SELFTAG+"."+SQLTAG, str);
                }
                while (c.moveToNext());
            }
        }
        else Log.d(SELFTAG+"."+SQLTAG, "Cursor is null");
    }
}
