package com.example.yobaiv.kachalochka.classes;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

/**
 * Created by YOBA IV on 19.12.2017.
 */

public class Unit implements IEntity{
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Unit(){

    }
}
