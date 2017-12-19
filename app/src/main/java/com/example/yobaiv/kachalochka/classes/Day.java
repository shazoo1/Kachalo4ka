package com.example.yobaiv.kachalochka.classes;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by YOBA IV on 19.10.2017.
 */

public class Day implements IEntity{
    private long id;

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    private long typeId;
    private String title;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date date = new Date();

    public Day(long id, String title, String type, long date){
        this.id = id;
        this.title = title;
        this.date = new Date(date);
        dateFormat.format(this.date);
    }

    public Day(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle(){
        return title;
    }

    private void generateTitle(int number){
        title = "День " + number + " - " + dateFormat.format(date);
    }
    public void setTitle(String title){
        this.title = title;
    }
}
