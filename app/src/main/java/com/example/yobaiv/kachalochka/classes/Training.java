package com.example.yobaiv.kachalochka.classes;

import android.content.ContentValues;
import android.util.Log;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

/**
 * Created by YOBA IV on 21.12.2017.
 */

public class Training implements IEntity{
    private long id;
    private long date;
    private String title;
    private String comment;
    private ContentValues contentValues = new ContentValues();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Training(){}

    public Training(long id, long date, String title, String comment) {
        this.id = id;
        setDate(date);
        setTitle(title);
        setComment(comment);
    }

    public Training(long date, String title, String comment) {
        setDate(date);
        setTitle(title);
        setComment(comment);
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        if (contentValues.containsKey("date")){
            contentValues.remove("date");
        }
        contentValues.put("date", date);
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (contentValues.containsKey("title")){
            contentValues.remove("title");
        }
        contentValues.put("title", title);
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if (contentValues.containsKey("comment")){
            contentValues.remove("comment");
        }
        contentValues.put("comment", comment);
        this.comment = comment;
    }

    @Override
    public ContentValues getContentValues(){
        return contentValues;
    }

    @Override
    public String[] getIdAsWhereArgs(){
        String[] result = {String.valueOf(id)};
        return result;
    }
}
