package com.github.tinkerti.sqltest.DB;

import android.content.Context;

/**
 * Created by tiankui on 4/25/17.
 */

public class DBManager {
    private static final String DB_NAME = "db_test";
    private static final int DB_VERSION = 1;
    private Context context;

    private static class SingleTonHolder {
        private static DBManager sIns = new DBManager();
    }

    public DBManager() {
        new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    public static void init(Context context) {
        SingleTonHolder.sIns.context = context;
    }

    public static DBManager getInstance() {
        return SingleTonHolder.sIns;
    }
}
