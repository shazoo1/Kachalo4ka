package com.example.yobaiv.kachalochka.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by YOBA IV on 19.10.2017.
 */

public class Day {
    private long id;
    private String type;
    String title;
    private List<Exercise> exercises = new ArrayList<Exercise>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date date = new Date();

    public Day(long id, String title, String type, long date){
        this.id = id;
        this.title = title;
        this.type = type;
        this.date = new Date(date);
        dateFormat.format(this.date);
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
