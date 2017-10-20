package com.example.yobaiv.kachalochka.classes;

/**
 * Created by YOBA IV on 19.10.2017.
 */

public class Set {
    private int repeats;
    private float weight;

    public Set(int repeats, float weight){
        this.repeats = repeats;
        this.weight = weight;
    }

    public void setRepeats(int repeats){
        this.repeats = repeats;
    }

    public void setWeight(float weight){
        this.weight = weight;
    }

    public int getRepeats(){
        return  repeats;
    }
    public float getWeight(){
        return weight;
    }
}
