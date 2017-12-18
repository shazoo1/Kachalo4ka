package com.example.yobaiv.kachalochka.classes;

/**
 * Created by YOBA IV on 23.11.2017.
 */

public class DayType {
    private long id;
    private String name;

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
