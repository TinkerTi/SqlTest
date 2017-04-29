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


    public void createTable() {
        getDbHelper().getWritableDatabase().execSQL(DBHelper.CREATE_TABLE_TRAIN_TIME);
    }

    public void addTableField() {
        String sql = "alter table " + DBHelper.TABLE_NAME_TRAIN_TIME + " add run_at_weekend text";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    /**
     * SQLiteDatabase 执行这个语句会报错，原因是：SQLite不支持"alter table table_name drop column"
     * 作为替代方案，需要创建一个新表，将旧表中的数据选择性地复制到新表中，然后删除旧表就可以了；
     */
    public void dropTableField() {
//        String sql = "alter table " + DBHelper.TABLE_NAME_TRAIN_TIME + " drop column run_at_weekend";
//        getDbHelper().getWritableDatabase().execSQL(sql);
        SQLiteDatabase database = getDbHelper().getWritableDatabase();
        String createSql = "create table train_times (start_location text,destination text,departs text,arrives time)";
        database.execSQL(createSql);
        String insertSql = "insert or replace into train_times select start_location,destination,departs,arrives from train_time";
        database.execSQL(insertSql);
        String dropTableSql = "drop table train_time";
        database.execSQL(dropTableSql);
    }

    public void dropTable() {
        String dropTableSql = "drop table train_times";
        getDbHelper().getWritableDatabase().execSQL(dropTableSql);
    }

    public void createMemberDetailsTable() {
        String sql = "create table MemberDetails (MemberId integer, FirstName text, LastName text, DateOfBirth text, Street text," +
                "City text, State varchar,ZipCode text, Email text, DateOfJoining text)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createAttendanceTable() {
        String sql = "create table Attendance(MeetingDate text,Location text,MemberAttended text, MemberId integer)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createFilmTable() {
        String sql = "create table Film (FilmId integer,FilmName text,YearReleased integer,PlotSummary text,AvailableDVD text," +
                "Rating integer,CategoryId integer)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createFilmCategoryTable() {
        String sql = "create table FilmCategory(CategoryId integer,Category text)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createFavCategoryTable() {
        String sql = "create table FavCategory(CategoryId integer,MemberId integer)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void insertDataIntoCategoryTable() {
        String sql1 = "insert into FilmCategory(CategoryId,Category) values(1,'Thriller')";
        String sql2 = "insert into FilmCategory(CategoryId,Category) values(2,'Romance')";
        String sql3 = "insert into FilmCategory(CategoryId,Category) values(3,'Horror')";
        String sql4 = "insert into FilmCategory(CategoryId,Category) values(4,'War')";
        String sql5 = "insert into FilmCategory(CategoryId,Category) values(5,'Sci-fi')";
        getDbHelper().getWritableDatabase().execSQL(sql2);
        getDbHelper().getWritableDatabase().execSQL(sql3);
        getDbHelper().getWritableDatabase().execSQL(sql4);
        getDbHelper().getWritableDatabase().execSQL(sql5);

    }

    /**
     * 插入数据memberDetail；
     */

    public void insertIntoTableMemberDetails() {
        String sql1 = "insert into MemberDetails (\n" +
                "MemberId ,\n" +
                " FirstName , \n" +
                "LastName , \n" +
                "DateOfBirth ,\n" +
                " Street ,\n" +
                "City , \n" +
                "State ,\n" +
                "ZipCode ,\n " +
                "Email , \n" +
                "DateOfJoining \n" +
                ") values(" +
                "1,\n" +
                "'Katie',\n" +
                "'Smith',\n" +
                "'1997-01-09',\n" +
                "'Main Road',\n" +
                "'TownSville',\n" +
                "'StateSide',\n" +
                "'123456',\n" +
                "'xx@gmail.com',\n" +
                "'2004-03-04'\n" +
                ")";

        String sql2 = "insert into MemberDetails (\n" +
                "MemberId ,\n" +
                " FirstName , \n" +
                "LastName , \n" +
                "DateOfBirth ,\n" +
                " Street ,\n" +
                "City , \n" +
                "State ,\n" +
                "ZipCode ,\n " +
                "Email , \n" +
                "DateOfJoining \n" +
                ") values(" +
                "2,\n" +
                "'Bob',\n" +
                "'Robson',\n" +
                "'1987-01-09',\n" +
                "'Little Street',\n" +
                "'Big City',\n" +
                "'Small Street',\n" +
                "'34564',\n" +
                "'bob@gmail.com',\n" +
                "'2004-03-13'\n" +
                ")";
        getDbHelper().getWritableDatabase().execSQL(sql1);
        getDbHelper().getWritableDatabase().execSQL(sql2);
    }

    public void createAllTable(){
        createAttendanceTable();
        createFavCategoryTable();
        createFilmTable();
        createFilmCategoryTable();
        createMemberDetailsTable();
    }
}
