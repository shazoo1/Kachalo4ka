package com.example.yobaiv.kachalochka.classes;

import android.support.annotation.NonNull;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by YOBA IV on 19.10.2017.
 */

public class Exercise implements IEntity{

    private String name;

    private long id;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
