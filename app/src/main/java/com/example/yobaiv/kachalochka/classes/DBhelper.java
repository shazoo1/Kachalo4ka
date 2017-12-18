package com.example.yobaiv.kachalochka.classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static com.example.yobaiv.kachalochka.activities.MainActivity.SQLTAG;

/**
 * Created by YOBA IV on 24.10.2017.
 */

public class DBhelper extends SQLiteOpenHelper{

    private final String SELFTAG = "DBHelper";
    public DBhelper (Context context){
        super (context, "kach", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table types ("
                +"id integer primary key autoincrement,"
                +"name text not null)");
        db.execSQL("create table exercises ("
                +"id integer primary key autoincrement,"
                +"name text not null)");
        db.execSQL("create table sets ("
                +"deid integer not null,"
                +"number integer not null,"
                +"weight real not null,"
                +"count integer not null,"
                +"foreign key (deid) references daysexercises(id),"
                +"primary key (deid, number)"+")");
        db.execSQL("create table days ("
                +"id integer primary key autoincrement,"
                +"title text not null,"
                +"date integer not null,"
                +"typeid integer not null,"
                +"foreign key (typeid) references types(id))");
        db.execSQL("create table daysexercises ("
                +"id integer primary key autoincrement,"
                +"dayid integer not null,"
                +"exid integer not null,"
                +"foreign key (dayid) references days(id),"
                +"foreign key (exid) references exercises(id))");
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
    private void logNewVersion(int newVersion){
        Log.d(SELFTAG+"."+SQLTAG,"Successfully upgraded to version "+newVersion);
    }
}