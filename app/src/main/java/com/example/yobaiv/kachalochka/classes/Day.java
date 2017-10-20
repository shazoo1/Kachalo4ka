package com.example.yobaiv.kachalochka.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by YOBA IV on 19.10.2017.
 */

public class Day {
    private int number;
    private Date date;
    private String type;
    private List<Excercise> excercises = new ArrayList<Excercise>();

    public Day(int number, String type){
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
        return "День " + number;
    }

}
