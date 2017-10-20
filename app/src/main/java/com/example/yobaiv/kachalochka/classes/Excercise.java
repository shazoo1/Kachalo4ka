package com.example.yobaiv.kachalochka.classes;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by YOBA IV on 19.10.2017.
 */

public class Excercise {

    private String name;
    private List<Set> sets = new ArrayList<Set>();

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addSet(Set set){
        sets.add(set);
    }

    public void addNewSet(int repeats, float weight){
        sets.add(new Set(repeats, weight));
    }
}
