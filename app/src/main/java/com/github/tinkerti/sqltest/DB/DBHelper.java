package com.github.tinkerti.sqltest.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tiankui on 4/25/17.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final String TABLE_NAME_TRAIN_TIME = "train_time";
    public static final String CREATE_TABLE_TRAIN_TIME = "create table if not exists train_time(start_location text,destination text," +
            "departs text,arrives time)";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(new DBContext(context), name, factory, version);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        String enableForeign = "PRAGMA foreign_keys = ON";
        db.execSQL(enableForeign);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL(CREATE_TABLE_TRAIN_TIME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
