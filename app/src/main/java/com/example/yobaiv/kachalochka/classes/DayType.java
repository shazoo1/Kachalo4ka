package com.example.yobaiv.kachalochka.classes;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

/**
 * Created by YOBA IV on 23.11.2017.
 */

public class DayType implements IEntity{
    private long id;
    private String name;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DayType(){}

    public DayType(long id, String name) {
        this.id = id;
        this.name = name;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
