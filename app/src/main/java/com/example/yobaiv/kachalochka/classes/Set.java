package com.example.yobaiv.kachalochka.classes;

import com.example.yobaiv.kachalochka.interfaces.IEntity;

/**
 * Created by YOBA IV on 19.10.2017.
 */

public class Set implements IEntity{

    private long exId;
    private int count;
    private int number;
    private float weight;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getExId() {
        return exId;
    }

    public void setExId(long exId) {
        this.exId = exId;
    }

    public Set(){}
    public Set(int count, float weight){
        this.count = count;
        this.weight = weight;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setWeight(float weight){
        this.weight = weight;
    }

    public int getCount(){
        return count;
    }
    public float getWeight(){
        return weight;
    }
}
