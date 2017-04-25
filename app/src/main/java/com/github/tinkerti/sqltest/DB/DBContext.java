package com.github.tinkerti.sqltest.DB;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by tiankui on 4/25/17.
 */

public class DBContext extends ContextWrapper{

    public DBContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        File file=new File(name);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        return file;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name),factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name).getAbsolutePath(),factory, errorHandler);
    }
}
