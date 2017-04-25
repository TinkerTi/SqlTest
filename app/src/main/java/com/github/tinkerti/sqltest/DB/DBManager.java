package com.github.tinkerti.sqltest.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tiankui on 4/25/17.
 */

public class DBManager {
    private static final String DB_NAME = "db_test";
    private static final int DB_VERSION = 1;
    private Context context;
    private DBHelper dbHelper;

    private static class SingleTonHolder {
        private static DBManager sIns = new DBManager();
    }

    public DBManager() {

    }

    public static void init(Context context) {
        SingleTonHolder.sIns.context = context;
    }

    public static DBManager getInstance() {
        return SingleTonHolder.sIns;
    }


    public DBHelper getDbHelper() {
        return new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    public void addTableField() {
        String sql = "alter table " + DBHelper.TABLE_NAME_TRAIN_TIME + " add run_at_weekend text";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    /**
     * SQLiteDatabase 执行这个语句会报错，原因是：SQLite不支持"alter table table_name drop column"
     * 作为替代方案，需要创建一个新表，将旧表中的数据选择性地复制到新表中，然后删除旧表就可以了；
     */
    public void deleteTableField() {
//        String sql = "alter table " + DBHelper.TABLE_NAME_TRAIN_TIME + " drop column run_at_weekend";
//        getDbHelper().getWritableDatabase().execSQL(sql);
        SQLiteDatabase database=getDbHelper().getWritableDatabase();
        String createSql="create table train_times (start_location text,destination text,departs text,arrives time)";
        database.execSQL(createSql);
        String insertSql="insert or replace into train_times select start_location,destination,departs,arrives from train_time";
        database.execSQL(insertSql);
        String dropTableSql="drop table train_time";
        database.execSQL(dropTableSql);

    }
}
