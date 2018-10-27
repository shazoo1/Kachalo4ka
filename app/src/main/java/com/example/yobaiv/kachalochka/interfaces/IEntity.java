package com.example.yobaiv.kachalochka.interfaces;

import android.content.ContentValues;

/**
 * Created by YOBA IV on 19.12.2017.
 */

public interface IEntity {
    public ContentValues getContentValues();
    public String[] getIdAsWhereArgs();

}
