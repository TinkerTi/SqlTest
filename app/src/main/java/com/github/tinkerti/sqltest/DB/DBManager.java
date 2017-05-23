package com.github.tinkerti.sqltest.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        String sql = "alter table " + DBHelper.TABLE_NAME_TRAIN_TIME + " add column run_at_weekend text";
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
        String renameTable = "alter table train_times rename to train_time";
        database.execSQL(renameTable);
    }

    public void dropTable() {
        String dropTableSql = "drop table train_time";
        getDbHelper().getWritableDatabase().execSQL(dropTableSql);
    }

    public void createMemberDetailsTable() {
        String sql = "create table if not exists MemberDetails (MemberId integer not null primary key, FirstName text, LastName text, DateOfBirth text, Street text," +
                "City text, State varchar,ZipCode text, Email text, DateOfJoining text)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createLocationTable() {
        String sql = "create table if not exists Location(LocationId integer,Street text,City text,State text)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createAttendanceTable() {
        String sql = "create table if not exists Attendance(LocationId integer,MeetingDate text,MemberAttended text, MemberId integer)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createFilmTable() {
        String sql = "create table if not exists Film (FilmId integer primary key,FilmName text,YearReleased integer,PlotSummary text,AvailableDVD text," +
                "Rating integer,CategoryId integer)";
        getDbHelper().getWritableDatabase().execSQL(sql);

        String addFieldSql = "alter table Film add column DVDPrice real";
        getDbHelper().getWritableDatabase().execSQL(addFieldSql);
    }

    public void createFilmCategoryTable() {
        String sql = "create table FilmCategory(CategoryId integer primary key,Category text)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createFavCategoryTable() {
        String sql = "create table if not exists FavCategory(CategoryId integer,MemberId integer)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void insertDataIntoCategoryTable() {
        String sql1 = "insert into FilmCategory(CategoryId,Category) values(1,'Thriller')";
        String sql2 = "insert into FilmCategory(CategoryId,Category) values(2,'Romance')";
        String sql3 = "insert into FilmCategory(CategoryId,Category) values(3,'Horror')";
        String sql4 = "insert into FilmCategory(CategoryId,Category) values(4,'War')";
        String sql5 = "insert into FilmCategory(CategoryId,Category) values(5,'Sci-fi')";
        getDbHelper().getWritableDatabase().execSQL(sql1);
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
                "'Townsville',\n" +
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
                "'Small State',\n" +
                "'34564',\n" +
                "'bob@gmail.com',\n" +
                "'2004-03-13'\n" +
                ")";
        String sql3 = "insert into MemberDetails (\n" +
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
                "3,\n" +
                "'Sandra',\n" +
                "'Jakes',\n" +
                "'1957-01-09',\n" +
                "'The Avenue',\n" +
                "'Windy Village',\n" +
                "'Golden State',\n" +
                "'65423',\n" +
                "'sandra@gmail.com',\n" +
                "'2004-03-13'\n" +
                ")";
        String sql4 = "insert into MemberDetails (\n" +
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
                "4,\n" +
                "'Steve',\n" +
                "'Gee',\n" +
                "'1967-10-05',\n" +
                "'The Road',\n" +
                "'Windy Village',\n" +
                "'Golden State',\n" +
                "'65424',\n" +
                "'steve@gmail.com',\n" +
                "'2004-03-13'\n" +
                ")";
        String sql5 = "insert into MemberDetails (\n" +
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
                "5,\n" +
                "'John',\n" +
                "'Johns',\n" +
                "'1952-01-09',\n" +
                "'New Lane',\n" +
                "'Big Apple City',\n" +
                "'New State',\n" +
                "'88776',\n" +
                "'jjj@gmail.com',\n" +
                "'2004-03-13'\n" +
                ")";
        String sql6 = "insert into MemberDetails (\n" +
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
                "6,\n" +
                "'Jenny',\n" +
                "'Johns',\n" +
                "'1953-01-09',\n" +
                "'New Lane',\n" +
                "'Big Apple City',\n" +
                "'New State',\n" +
                "'88776',\n" +
                "'jjjj@gmail.com',\n" +
                "'2004-03-13'\n" +
                ")";
        String sql7 = "insert into MemberDetails (\n" +
                "MemberId ,\n" +
                " FirstName , \n" +
                "LastName , \n" +
                "Email , \n" +
                "DateOfJoining \n" +
                ") values(" +
                "15,\n" +
                "'Catherine',\n" +
                "'Hawthorn',\n" +
                "'chawthorn@gmail.com',\n" +
                "'2005-08-25'\n" +
                ")";

        getDbHelper().getWritableDatabase().execSQL(sql1);
        getDbHelper().getWritableDatabase().execSQL(sql2);
        getDbHelper().getWritableDatabase().execSQL(sql3);
        getDbHelper().getWritableDatabase().execSQL(sql4);
        getDbHelper().getWritableDatabase().execSQL(sql5);
        getDbHelper().getWritableDatabase().execSQL(sql6);
        getDbHelper().getWritableDatabase().execSQL(sql7);
    }

    public void insertDataIntoLocationTable() {
        String sql1 = "insert into Location(" +
                "LocationId," +
                "Street," +
                "City," +
                "State) values(" +
                "1," +
                "'Main Street'," +
                "'Big Apple City'," +
                "'New State')";
        String sql2 = "insert into Location(" +
                "LocationId," +
                "Street," +
                "City," +
                "State) values(" +
                "2," +
                "'Winding Road'," +
                "'Windy Village'," +
                "'Golden State')";
        String sql3 = "insert into Location(" +
                "LocationId," +
                "Street," +
                "City," +
                "State) values(" +
                "3," +
                "'Tiny Terrace'," +
                "'Big City'," +
                "'Small State')";
        getDbHelper().getWritableDatabase().execSQL(sql1);
        getDbHelper().getWritableDatabase().execSQL(sql2);
        getDbHelper().getWritableDatabase().execSQL(sql3);
    }

    public void insertDataIntoFavCategoryTable() {
        String sql1 = "insert into FavCategory(CategoryId,MemberId) values(1,3)";
        String sql2 = "insert into FavCategory(CategoryId,MemberId) values(1,5)";
        String sql3 = "insert into FavCategory(CategoryId,MemberId) values(1,2)";
        String sql4 = "insert into FavCategory(CategoryId,MemberId) values(2,1)";
        String sql5 = "insert into FavCategory(CategoryId,MemberId) values(2,3)";
        String sql6 = "insert into FavCategory(CategoryId,MemberId) values(3,3)";
        String sql7 = "insert into FavCategory(CategoryId,MemberId) values(4,6)";
        String sql8 = "insert into FavCategory(CategoryId,MemberId) values(4,1)";
        String sql9 = "insert into FavCategory(CategoryId,MemberId) values(5,2)";
        String sql10 = "insert into FavCategory(CategoryId,MemberId) values(5,3)";
        String sql11 = "insert into FavCategory(CategoryId,MemberId) values(5,4)";
        getDbHelper().getWritableDatabase().execSQL(sql1);
        getDbHelper().getWritableDatabase().execSQL(sql2);
        getDbHelper().getWritableDatabase().execSQL(sql3);
        getDbHelper().getWritableDatabase().execSQL(sql4);
        getDbHelper().getWritableDatabase().execSQL(sql5);
        getDbHelper().getWritableDatabase().execSQL(sql6);
        getDbHelper().getWritableDatabase().execSQL(sql7);
        getDbHelper().getWritableDatabase().execSQL(sql8);
        getDbHelper().getWritableDatabase().execSQL(sql9);
        getDbHelper().getWritableDatabase().execSQL(sql10);
        getDbHelper().getWritableDatabase().execSQL(sql11);
    }

    public void insertDataIntoFilmTable() {
        String sql1 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "1," +
                "'The Dirty Half Dozen'," +
                "'1987'," +
                "'Six men go to war wearing unwashed uniforms. The horror!'," +
                "'N'," +
                "2," +
                "4)";
        String sql2 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "2," +
                "'On Golden Puddle'," +
                "'1967'," +
                "'A couple find love while wading through a puddle.'," +
                "'Y'," +
                "4," +
                "2)";
        String sql3 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "3," +
                "'The Lion, the Witch, and the Chest of Drawers'," +
                "'1977'," +
                "'A fun film for all those interested in zoo/magic/furniture drama.'," +
                "'N'," +
                "1," +
                "3)";
        String sql4 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "4," +
                "'Nightmare on Oak Street, Part 23'," +
                "'1997'," +
                "'The murderous Terry stalks Oak Street.'," +
                "'Y'," +
                "2," +
                "3)";
        String sql5 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "5," +
                "'The Wide-Brimmed Hat'," +
                "'2005'," +
                "'Fascinating life story of a wide-brimmed hat'," +
                "'N'," +
                "1," +
                "5)";
        String sql6 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "6," +
                "'Sense and Insensitivity'," +
                "'2001'," +
                "'She longs for a new life with Mr. Arcy; he longs for a small cottage in the\n" +
                "Hamptons.'," +
                "'Y'," +
                "3," +
                "6)";
        String sql7 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "7," +
                "'Planet of the Japes'," +
                "'1967'," +
                "'Earth has been destroyed, to be taken over by a species of comedians.'," +
                "'Y'," +
                "5," +
                "4)";
        String sql8 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "8," +
                "'The Maltese Poodle'," +
                "'1947'," +
                "'A mysterious bite mark, a guilty-looking poodle. First class thriller.'," +
                "'Y'," +
                "1," +
                "1)";
        String sql9 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "11," +
                "'15th Late Afternoon'," +
                "'1989'," +
                "'One of Shakespeare’’s lesser-known plays'," +
                "'N'," +
                "5," +
                "6)";
        String sql10 = "insert into Film(" +
                "FilmId," +
                "FilmName," +
                "YearReleased," +
                "PlotSummary," +
                "AvailableDVD," +
                "Rating," +
                "CategoryId) values(" +
                "12," +
                "'Soylent Yellow'," +
                "'1967'," +
                "'Detective Billy Brambles discovers that Soylent Yellow is made of soya bean.\n" +
                "Ewwww!'," +
                "'Y'," +
                "5," +
                "5)";

        getDbHelper().getWritableDatabase().execSQL(sql1);
        getDbHelper().getWritableDatabase().execSQL(sql2);
        getDbHelper().getWritableDatabase().execSQL(sql3);
        getDbHelper().getWritableDatabase().execSQL(sql4);
        getDbHelper().getWritableDatabase().execSQL(sql5);
        getDbHelper().getWritableDatabase().execSQL(sql6);
        getDbHelper().getWritableDatabase().execSQL(sql7);
        getDbHelper().getWritableDatabase().execSQL(sql8);
        getDbHelper().getWritableDatabase().execSQL(sql9);
        getDbHelper().getWritableDatabase().execSQL(sql10);
        String updateFilm = "update Film set DVDPrice=2.333 where CategoryId=2";
        getDbHelper().getWritableDatabase().execSQL(updateFilm);
        String updateFilm1 = "update Film set DVDPrice=4 where CategoryId=1";
        getDbHelper().getWritableDatabase().execSQL(updateFilm1);
        String updateFilm2 = "update Film set DVDPrice=5.3 where CategoryId=3";
        getDbHelper().getWritableDatabase().execSQL(updateFilm2);
        String updateFilm3 = "update Film set DVDPrice=10 where CategoryId=4";
        getDbHelper().getWritableDatabase().execSQL(updateFilm3);
        String updateFilm4 = "update Film set DVDPrice=23 where CategoryId=5";
        getDbHelper().getWritableDatabase().execSQL(updateFilm4);
        String updateFilm5 = "update Film set DVDPrice=203 where CategoryId=6";
        getDbHelper().getWritableDatabase().execSQL(updateFilm5);

    }

    public void insertDataIntoAttendanceTable() {
        String sql1 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(2," +
                "'2004-01-01'," +
                "'Y'," +
                "1)";
        String sql2 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(2," +
                "'2004-01-01'," +
                "'N'," +
                "2)";
        String sql3 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(2," +
                "'2004-01-01'," +
                "'Y'," +
                "3)";
        String sql4 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(2," +
                "'2004-01-01'," +
                "'N'," +
                "4)";
        String sql5 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(2," +
                "'2004-01-01'," +
                "'Y'," +
                "5)";
        String sql6 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(2," +
                "'2004-01-01'," +
                "'Y'," +
                "6)";
        String sql7 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(1," +
                "'2004-01-01'," +
                "'Y'," +
                "1)";
        String sql8 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(1," +
                "'2004-01-01'," +
                "'N'," +
                "2)";
        String sql9 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(1," +
                "'2004-01-01'," +
                "'Y'," +
                "3)";
        String sql10 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(1," +
                "'2004-01-01'," +
                "'Y'," +
                "4)";

        String sql11 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(1," +
                "'2004-01-01'," +
                "'N'," +
                "5)";
        String sql12 = "insert into Attendance" +
                "(LocationId," +
                "MeetingDate ," +
                "MemberAttended , " +
                "MemberId ) values" +
                "(1," +
                "'2004-01-01'," +
                "'N'," +
                "6)";
        getDbHelper().getWritableDatabase().execSQL(sql1);
        getDbHelper().getWritableDatabase().execSQL(sql2);
        getDbHelper().getWritableDatabase().execSQL(sql3);
        getDbHelper().getWritableDatabase().execSQL(sql4);
        getDbHelper().getWritableDatabase().execSQL(sql5);
        getDbHelper().getWritableDatabase().execSQL(sql6);
        getDbHelper().getWritableDatabase().execSQL(sql7);
        getDbHelper().getWritableDatabase().execSQL(sql8);
        getDbHelper().getWritableDatabase().execSQL(sql9);
        getDbHelper().getWritableDatabase().execSQL(sql10);
        getDbHelper().getWritableDatabase().execSQL(sql11);
        getDbHelper().getWritableDatabase().execSQL(sql12);

    }

    public void createAllTable() {
        createAttendanceTable();
        createFavCategoryTable();
        createFilmTable();
        createFilmCategoryTable();
        createMemberDetailsTable();
    }

    public void updateTable() {
        String sql = "update MemberDetails set Street='45Upper Road',\n" +
                "City='New Town',\n" +
                "State='New State',\n" +
                "ZipCode='99112'\n" +
                " where MemberId=2";
        getDbHelper().getWritableDatabase().execSQL(sql);

        String sql1 = "update MemberDetails set City='Mega State' where City='New Town' or City='TownSville'";
        getDbHelper().getWritableDatabase().execSQL(sql1);
    }

    /**
     * create table if not exists new_table (id integer, name text )
     * <p>
     * insert into new_table select * from old_table
     * <p>
     * drop table old_table
     * <p>
     * alter table new_table rename to old_table
     */
    public void alterMemberDetailsTable() {
        //这么做事不允许的，
        String sql = "alter table MemberDetails modify column Street text not null";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createTableAndInitData() {
        createLocationTable();
        createAttendanceTable();
        createFavCategoryTable();
        createFilmTable();
        createFilmCategoryTable();
        createMemberDetailsTable();


        insertDataIntoCategoryTable();
        insertIntoTableMemberDetails();
        insertDataIntoLocationTable();
        insertDataIntoFavCategoryTable();
        insertDataIntoFilmTable();
        insertDataIntoAttendanceTable();

        createMoreTable();
        insertDataToTable();

    }

    public void doExercise() {
        //Exercise:插入数据
        String sql = "insert into MemberDetails values(7,'Jhon','Jackson','1974-05-27','Long Lane','Orange Town','New State','88992','jackson@gmail.com','2005-11-25')";
        getDbHelper().getWritableDatabase().execSQL(sql);
        //删除数据
        String deleteSql = "delete from MemberDetails where MemberId = 2";
        String deleteSql1 = "delete from FavCategory where MemberId=2";

        //更新数据
        String updateSql = " update MemberDetails set City='Big City' where Street='Long Lane' and City='Orange Town'";
        String updateSql1 = "update Location set City ='Big City' where Street='Long Lane' and City='Orange Town'";

        getDbHelper().getWritableDatabase().execSQL(deleteSql);
        getDbHelper().getWritableDatabase().execSQL(deleteSql1);

        getDbHelper().getWritableDatabase().execSQL(updateSql);
        getDbHelper().getWritableDatabase().execSQL(updateSql1);
    }

    public void selectLike() {
//        String sql = "select FirstName,LastName,Street from MemberDetails where Street not like '%Road' and Street not like '%Street'";
//        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(sql, null);
//        while (cursor.moveToNext()) {
//            Log.e("selectLike", cursor.getString(2) + "...");
//        }
//
//        Log.e("分隔符", "--------------------");
//        String sql1 = "select FirstName,LastName,City from MemberDetails where City='Townsville' or City='Windy Village' or City='Dover' or City='Big City'";
//        Cursor cursor1 = getDbHelper().getWritableDatabase().rawQuery(sql1, null);
//        while (cursor1.moveToNext()) {
//            Log.e("more than one or", cursor1.getString(0) + " " + cursor1.getString(1) + " " + cursor1.getString(2));
//        }
//
//        //关键字as，对result set中的column赋予别名，如果想在别名中添加特殊字符，需要使用中括号；
//        Log.e("分隔符", "--------------------");
//        String sql2 = "select FirstName as [F**K],LastName as [**xx],City from MemberDetails where City in ('Townsville','Dover','Big City')";
//        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(sql2, null);
//        while (cursor2.moveToNext()) {
//            Log.e("more than one or", cursor2.getString(cursor2.getColumnIndex("F**K")) + " " + cursor2.getString(cursor2.getColumnIndex("**xx")) + " " + cursor2.getString(2));
//
//        }
//
//
//        Log.e("分隔符", "--------------------");
//        String sql3 = "select Rating from Film";
//        Cursor cursor3 = getDbHelper().getWritableDatabase().rawQuery(sql3, null);
//        while (cursor3.moveToNext()) {
//            Log.e("order by test", "" + cursor3.getInt(0));
//        }
//
//        //关键字order by，排序
//        Log.e("分隔符", "--------------------");
//        String sql4 = "select Rating from Film order by Rating desc";
//        Cursor cursor4 = getDbHelper().getWritableDatabase().rawQuery(sql4, null);
//        while (cursor4.moveToNext()) {
//            Log.e("order by test", "" + cursor4.getInt(0));
//        }
//
//        //concatenate columns,关键字||
//        Log.e("分隔符", "--------------------");
//        String sql5 = "select FirstName ||' '||LastName||' lives in '||City  as Dis from MemberDetails where City in ('Townsville','Dover','Big City')";
//        Cursor cursor5 = getDbHelper().getWritableDatabase().rawQuery(sql5, null);
//        while (cursor5.moveToNext()) {
//            Log.e("more than one or", cursor5.getString(cursor5.getColumnIndex("Dis")) + " ");
//        }
//
//        Log.e("分隔符", "--------------------");
//        String sql6 = "select FilmName,YearReleased,Rating from Film inner join FilmCategory on Film.CategoryId=FilmCategory.CategoryId where Category='Thriller'";
//        Cursor cursor6 = getDbHelper().getWritableDatabase().rawQuery(sql6, null);
//        while (cursor6.moveToNext()) {
//            Log.e("more than one or", "电影名：" + cursor6.getString(0) + " " + cursor6.getString(1) + " 评分：" + cursor6.getInt(2));
//        }
//
//        //复杂的inner join联合查表的例子；
//        Log.e("分隔符", "--------------------");
//        String sql7 = "select MemberDetails.FirstName,MemberDetails.LastName,Film.FilmName,Film.YearReleased,FilmCategory.Category " +
//                "from FavCategory " +
//                "inner join FilmCategory on FavCategory.CategoryId=FilmCategory.CategoryId " +
//                "inner join MemberDetails on FavCategory.MemberId=MemberDetails.MemberId " +
//                "inner join Film on FavCategory.CategoryId=Film.CategoryId " +
//                "order by MemberDetails.FirstName,MemberDetails.LastName,FilmCategory.Category";
//        Cursor cursor7 = getDbHelper().getWritableDatabase().rawQuery(sql7, null);
//        while (cursor7.moveToNext()) {
//            Log.e("more than one or", "成员名：" + cursor7.getString(0) + " " + cursor7.getString(1)
//                    + " 电影信息：" + cursor7.getString(2) + " " + cursor7.getString(3) + " " + cursor7.getString(4));
//
//        }
//
//
////        Log.e("分隔符","--------------------");
////        String sql8="select MemberDetails.FirstName,MemberDetails.LastName,MemberDetails.City,MemberDetails.State " +
////                "from MemberDetails " +
////                " where MemberDetails.City<>Location.City or MemberDetails.State<>Location.State";
////        Cursor cursor8=getDbHelper().getWritableDatabase().rawQuery(sql8,null);
////        while (cursor8.moveToNext()){
////            Log.e("more than one or","成员名："+cursor8.getString(0)+" "+cursor8.getString(1)
////                    +" 位置信息："+cursor8.getString(2)+" "+cursor8.getString(3));
////
////        }
//
//        Log.e("分隔符", "--------------------");
//        String sql8 = "select MemberDetails.FirstName,MemberDetails.LastName,MemberDetails.City,MemberDetails.State " +
//                "from MemberDetails " +
//                "inner join Location on MemberDetails.City<>Location.City and MemberDetails.State=Location.State ";
////        or MemberDetails.State<>Location.State
//        Cursor cursor8 = getDbHelper().getWritableDatabase().rawQuery(sql8, null);
//        while (cursor8.moveToNext()) {
//            Log.e("more than one or", "成员名：" + cursor8.getString(0) + " " + cursor8.getString(1)
//                    + " 位置信息：" + cursor8.getString(2) + " " + cursor8.getString(3));
//
//        }
//
//        //如果在插入数据的时候，某一个field没有指定特定的值，那么这个field的值实际上为null，即未知的数值；
//        Log.e("分隔符", "--------------------");
//        String sql9 = "select MemberDetails.FirstName,MemberDetails.LastName,MemberDetails.DateOfBirth " +
//                "from MemberDetails " +
//                "where DateOfBirth<=1991-11-25 or DateOfBirth>1991-11-25 or DateOfBirth is null";
//        Cursor cursor9 = getDbHelper().getWritableDatabase().rawQuery(sql9, null);
//        while (cursor9.moveToNext()) {
//            Log.e("more than one or", "成员名：" + cursor9.getString(0) + " " + cursor9.getString(1)
//                    + " 出生日期信息：" + cursor9.getString(2) + " ");
//
//        }
//
//        Log.e("分隔符", "--------------------");
//        String sql10 = "select Street,City,State from MemberDetails where FirstName='Bob' and LastName='Robson'";
//        Cursor cursor10 = getDbHelper().getWritableDatabase().rawQuery(sql10, null);
//        while (cursor10.moveToNext()) {
//            Log.e("more than one or", "地址：" + cursor10.getString(0) + " " + cursor10.getString(1)
//                    + " 州信息：" + cursor10.getString(2) + " ");
//
//        }
//
//        Log.e("分隔符", "--------------------");
//        String sql11 = "select FirstName,LastName from MemberDetails where LastName like 'J%'";
//        Cursor cursor11 = getDbHelper().getWritableDatabase().rawQuery(sql11, null);
//        while (cursor11.moveToNext()) {
//            Log.e("more than one or", "姓名：" + cursor11.getString(0) + " " + cursor11.getString(1));
//
//        }
//
//        Log.e("分隔符", "--------------------");
//        String sql12 = "select FirstName,LastName from MemberDetails where DateOfJoining <='2004-12-31' order by LastName,FirstName";
//        Cursor cursor12 = getDbHelper().getWritableDatabase().rawQuery(sql12, null);
//        while (cursor12.moveToNext()) {
//            Log.e("more than one or", "姓名：" + cursor12.getString(0) + " " + cursor12.getString(1));
//
//        }

        //第三章练习4
        Log.e("分隔符", "--------------------");
        String sql13 = "select MemberDetails.FirstName,MemberDetails.LastName " +
                "from Attendance " +
                "inner join Location on Attendance.LocationId=Location.LocationId " +
                "inner join MemberDetails on Attendance.MemberId=MemberDetails.MemberId " +
                "where Attendance.MemberAttended='Y' and Location.City='Windy Village' and Location.State='Golden State'";
        Cursor cursor13 = getDbHelper().getWritableDatabase().rawQuery(sql13, null);
        while (cursor13.moveToNext()) {
            Log.e("more than one or", "姓名：" + cursor13.getString(0) + " " + cursor13.getString(1));

        }

        //unique constraint
//        String createSql="create table if not exists testUnique(column1 text,column2 text,constraint MyConstraint unique(column1,column2))";
//        getDbHelper().getWritableDatabase().execSQL(createSql);
//        String insertSql="insert into testUnique values('1','2')";
//        String insertSql2="insert into testUnique values('1','3')";
//        getDbHelper().getWritableDatabase().execSQL(insertSql);
//        getDbHelper().getWritableDatabase().execSQL(insertSql2);

        //unique constraint ,qlite 不支持这种添加constraint的形式；
//        String createSql1="create table testAddUnique(column1 text,column2 text)";
//        getDbHelper().getWritableDatabase().execSQL(createSql1);
//        String addConstraint="alter table testAddUnique add constraint MyConstraint unique(column1,column2)";
//        getDbHelper().getWritableDatabase().execSQL(addConstraint);
//        String insertSql3="insert into testUnique values('1','2')";
//        String insertSql4="insert into testUnique values('1','3')";
//        getDbHelper().getWritableDatabase().execSQL(insertSql3);
//        getDbHelper().getWritableDatabase().execSQL(insertSql4);

        //check constraint
        String createSql2 = "create table if not exists testCheck(column1 integer not null check(column1>0),column2 text)";
        getDbHelper().getWritableDatabase().execSQL(createSql2);
        String insertSql5 = "insert into testCheck values(1,'1')";
//        String insertSql6="insert into testCheck values(-1,'2')";
        getDbHelper().getWritableDatabase().execSQL(insertSql5);
//        getDbHelper().getWritableDatabase().execSQL(insertSql6);
    }

    /**
     * 创建表的时候添加上if not exists，可以避免重复创建表；
     */
    public void addPrimaryKey() {
        String sql = "create table if not exists testPrimaryKey(column1 text,column2 text,constraint test_pk primary key(column1,column2))";
        getDbHelper().getWritableDatabase().execSQL(sql);
        String insertSql1 = "insert into testPrimaryKey values('1','4')";
        getDbHelper().getWritableDatabase().execSQL(insertSql1);
        String insertSql2 = "insert into testPrimaryKey values('1','5')";
        getDbHelper().getWritableDatabase().execSQL(insertSql2);
//        这个虽然添加了primary key，但是由于没有not null 的限制，所以可以插入null值；
        String insertSql3 = "insert into testPrimaryKey values('1',null)";
        getDbHelper().getWritableDatabase().execSQL(insertSql3);


        String createSql = "create table if not exists testPrimary(column1 text primary key,column2 text)";
        getDbHelper().getWritableDatabase().execSQL(createSql);
        //在主键的column中插入空值，也是可以的，所以在SQLite中primary key应该与not null结合使用；
        String insertSql4 = "insert into testPrimary values(1,'1')";
        getDbHelper().getWritableDatabase().execSQL(insertSql4);
    }

    /**
     * 添加外键需要在SQLiteDatabaseHelper中的onConfigure（）中配置打开foreign key的开关；
     */
    public void testForeignKey() {


        String sql = "create table if not exists testForeignKey(column1 text primary key,column2 text)";
        getDbHelper().getWritableDatabase().execSQL(sql);
        String insertSql1 = "insert into testForeignKey values('1','4')";
        getDbHelper().getWritableDatabase().execSQL(insertSql1);
        String insertSql2 = "insert into testForeignKey values('2','5')";
        getDbHelper().getWritableDatabase().execSQL(insertSql2);
//        这个虽然添加了primary key，但是由于没有not null 的限制，所以可以插入null值；
        String insertSql3 = "insert into testForeignKey values('3','6')";
        getDbHelper().getWritableDatabase().execSQL(insertSql3);


        String createSql = "create table if not exists testForeign" +
                "(column1 text,column2 text," +
                "foreign key (column1) references testForeignKey(column1))";
        getDbHelper().getWritableDatabase().execSQL(createSql);
        //在主键的column中插入空值，也是可以的，所以在SQLite中primary key应该与not null结合使用；
        String insertSql4 = "insert into testForeign values('3','1')";
        getDbHelper().getWritableDatabase().execSQL(insertSql4);
    }

    public void testIndex() {
        String createIndexSql = "create index MemberDetails_index on MemberDetails(FirstName,LastName)";
        getDbHelper().getWritableDatabase().execSQL(createIndexSql);
        String selectSql = "select FirstName,LastName from MemberDetails";
        Cursor cursor13 = getDbHelper().getWritableDatabase().rawQuery(selectSql, null);
        while (cursor13.moveToNext()) {
            Log.e("more than one or", "姓名：" + cursor13.getString(0) + " " + cursor13.getString(1));
        }
        String dropIndexSql = "drop index MemberDetails_index";
        getDbHelper().getWritableDatabase().execSQL(dropIndexSql);
        String selectSql1 = "select FirstName,LastName from MemberDetails";
        Cursor cursor14 = getDbHelper().getWritableDatabase().rawQuery(selectSql1, null);
        while (cursor14.moveToNext()) {
            Log.e("more than one or", "姓名：" + cursor14.getString(0) + " " + cursor14.getString(1));
        }
    }

    public void alterAttendanceTable() {
        String deleteSql = "delete from Attendance where MemberAttended='N'";
        getDbHelper().getWritableDatabase().execSQL(deleteSql);
        String createTempTable = "create table if not exists tempAttendance(LocationId integer,MeetingDate text,MemberId integer)";
        getDbHelper().getWritableDatabase().execSQL(createTempTable);
        //select多个column的时候不用加括号；
        String copySql = "insert into tempAttendance select LocationId,MeetingDate,MemberId from Attendance";
        String dropSql = "drop table Attendance";
        String renameSql = "alter table tempAttendance rename to Attendance";
        getDbHelper().getWritableDatabase().execSQL(copySql);
        getDbHelper().getWritableDatabase().execSQL(dropSql);
        getDbHelper().getWritableDatabase().execSQL(renameSql);
    }

    public void addFavCategoryForeignKey() {
        String createIndex = "create unique index MemberDetails_index on MemberDetails(MemberId)";
        getDbHelper().getWritableDatabase().execSQL(createIndex);
        String createSql = "create table if not exists tempFavCategory(CategoryId integer,MemberId integer," +
                "foreign key (CategoryId) references FilmCategory(CategoryId)," +
                "foreign key(MemberId) references MemberDetails(MemberId)) ";
        String copySql = "insert into tempFavCategory select * from FavCategory";
        String dropSql = "drop table FavCategory";
        String renameSql = "alter table tempFavCategory rename to FavCategory";
        getDbHelper().getWritableDatabase().execSQL(createSql);
        getDbHelper().getWritableDatabase().execSQL(copySql);
        getDbHelper().getWritableDatabase().execSQL(dropSql);
        getDbHelper().getWritableDatabase().execSQL(renameSql);

        String insertSql = "insert into FavCategory values(5,3)";
        getDbHelper().getWritableDatabase().execSQL(insertSql);
    }

    public void testOperators() {
        String sql = "select MemberId,MemberId+2*3 from MemberDetails";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Log.e("selectLike", cursor.getInt(0) + "..." + cursor.getInt(1));
        }
        Log.e("分隔符", "--------------------");

        String sql1 = "select MemberId-10,abs(MemberId-10) from MemberDetails";
        Cursor cursor1 = getDbHelper().getWritableDatabase().rawQuery(sql1, null);
        while (cursor1.moveToNext()) {
            Log.e("selectLike", cursor1.getInt(0) + "..." + cursor1.getInt(1));
        }
        Log.e("分隔符", "--------------------");


//        SQLite不支持这个函数；
        String sql2 = "select MemberId,ZipCode/MemberId from MemberDetails";
        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(sql2, null);
        while (cursor2.moveToNext()) {
            Log.e("selectLike", cursor2.getInt(0) + "..." + cursor2.getInt(1));
        }
        Log.e("分隔符", "--------------------");

        String updateFilm = "update Film set DVDPrice=2.333 where FilmId=2";
        getDbHelper().getWritableDatabase().execSQL(updateFilm);

        //不支持ceil（）
//        String sql2 = "select DVDPrice,ceiling(DVDPrice) from Film order by DVDPrice";
//        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(sql2, null);
//        while (cursor2.moveToNext()) {
//            Log.e("selectLike", cursor2.getDouble(0) + "..." + cursor2.getInt(1));
//        }
//        Log.e("分隔符", "--------------------");

    }

    /**
     * String insertSql2="insert into testNull values(2)";
     * String insertSql3="insert into testNull values(4)";
     * 这种语法是错误的，如果只指定了一个column的值，需要说明插入哪个column
     */

    public void testNulls() {
        String createSql = "create table if not exists testNull(column1 integer,column2 integer)";
        String insertSql1 = "insert into testNull values(1,3)";
        String insertSql2 = "insert into testNull(column1) values(2)";
        String insertSql3 = "insert into testNull(column2) values(4)";
        getDbHelper().getWritableDatabase().execSQL(createSql);
        getDbHelper().getWritableDatabase().execSQL(insertSql1);
        getDbHelper().getWritableDatabase().execSQL(insertSql2);
        getDbHelper().getWritableDatabase().execSQL(insertSql3);

        String sql2 = "select column1,column2,column1+column2 from testNull";
        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(sql2, null);
        while (cursor2.moveToNext()) {
            Log.e("selectLike", cursor2.getInt(0) + "..." + cursor2.getInt(1) + "...." + cursor2.getInt(2));
        }
        Log.e("分隔符", "--------------------");
    }

    public void testStringNulls() {
        String createSql = "create table if not exists testStringNull(column1 text,column2 text)";
        String insertSql1 = "insert into testStringNull values('1','3')";
        String insertSql2 = "insert into testStringNull(column1) values('2')";
        String insertSql3 = "insert into testStringNull(column2) values('4')";
        getDbHelper().getWritableDatabase().execSQL(createSql);
        getDbHelper().getWritableDatabase().execSQL(insertSql1);
        getDbHelper().getWritableDatabase().execSQL(insertSql2);
        getDbHelper().getWritableDatabase().execSQL(insertSql3);

        String sql2 = "select column1,column2,column1||column2 from testStringNull";
        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(sql2, null);
        while (cursor2.moveToNext()) {
            Log.e("selectLike", cursor2.getString(0) + "..." + cursor2.getString(1) + "...." + cursor2.getString(2));
        }
        Log.e("分隔符", "--------------------");
    }

    public void testGroupBy() {
        String selectSql = "select City from MemberDetails group by City";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(selectSql, null);
        while (cursor.moveToNext()) {
            Log.e("groupBy", "city_name " + cursor.getString(0));
        }
        Log.e("分隔符", "--------------------");
    }

    public void testCount() {
        String countSql = "select City,count(City) from MemberDetails";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(countSql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0) + ".." + cursor.getInt(1));
        }
        Log.e("分隔符", "--------------------");

        String countSql2 = "select count(Street),count(MemberId),count(City) from MemberDetails";
        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(countSql2, null);
        while (cursor2.moveToNext()) {
            Log.e("count", "numbers: " + cursor2.getInt(0) + "..." + cursor2.getInt(1) + "..." + cursor2.getInt(2));
        }
        Log.e("分隔符", "--------------------");


        String countSql3 = "select State,count(FirstName) from MemberDetails group by State";
        Cursor cursor3 = getDbHelper().getWritableDatabase().rawQuery(countSql3, null);
        while (cursor3.moveToNext()) {
            Log.e("count", "numbers: " + cursor3.getString(0) + "..." + cursor3.getInt(1));
        }
        Log.e("分隔符", "--------------------");


        String countSql4 = " select count(distinct City),count(City) from MemberDetails";
        Cursor cursor4 = getDbHelper().getWritableDatabase().rawQuery(countSql4, null);
        while (cursor4.moveToNext()) {
            Log.e("count", "numbers: " + cursor4.getInt(0) + "..." + cursor4.getInt(1));
        }
        Log.e("分隔符", "--------------------");

        String countSql5 = "select Category,count(FavCategory.MemberId) from FavCategory " +
                "inner join FilmCategory on FavCategory.CategoryId=FilmCategory.CategoryId " +
                "group by FavCategory.CategoryId " +
                "order by count(MemberId) desc";
        Cursor cursor5 = getDbHelper().getWritableDatabase().rawQuery(countSql5, null);
        while (cursor5.moveToNext()) {
            Log.e("count", "numbers: " + cursor5.getString(0) + "..." + cursor5.getInt(1));
        }
        Log.e("分隔符", "--------------------");

        String countSql6 = "select avg(MemberId) from MemberDetails";
        Cursor cursor6 = getDbHelper().getWritableDatabase().rawQuery(countSql6, null);
        while (cursor6.moveToNext()) {
            Log.e("count", "numbers: " + cursor6.getInt(0) + "..");
        }
        Log.e("分隔符", "--------------------");
    }

    public void testAvg() {
        String avgSql = "select FilmCategory.Category,avg(Film.DVDPrice) from Film " +
                "inner join FilmCategory on Film.CategoryId=FilmCategory.CategoryId " +
                "group by Film.CategoryId";

        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(avgSql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0) + "..." + cursor.getFloat(1));
        }
        Log.e("分隔符", "--------------------");
    }

    public void testMinAndMax() {
        String minSql = "select min(DateOfBirth),max(DateOfBirth) from MemberDetails";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(minSql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0) + "..." + cursor.getString(1));
        }
        Log.e("分隔符", "--------------------");

        String sql = "select State,max(DateOfBirth),min(DateOfBirth) from MemberDetails group by State";
        Cursor cursor1 = getDbHelper().getWritableDatabase().rawQuery(sql, null);
        while (cursor1.moveToNext()) {
            Log.e("count", "numbers: " + cursor1.getString(0) + "..." + cursor1.getString(1) + "..." + cursor1.getString(2));
        }
        Log.e("分隔符", "--------------------");
    }

    public void testHaving() {
        String sql = "select City,count(City) from MemberDetails group by City having count(City)>1";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0) + "..." + cursor.getInt(1));
        }
        Log.e("分隔符", "--------------------");

        String havingSql = "select Category,count(FavCategory.MemberId) from FavCategory " +
                "inner join FilmCategory on FavCategory.CategoryId=FilmCategory.CategoryId " +
                "group by FavCategory.CategoryId " +
                "having count(FavCategory.MemberId)>2";
        Cursor cursor1 = getDbHelper().getWritableDatabase().rawQuery(havingSql, null);
        while (cursor1.moveToNext()) {
            Log.e("count", "numbers: " + cursor1.getString(0) + "..." + cursor1.getInt(1));
        }
        Log.e("分隔符", "--------------------");

        String ratingSql = "select FilmName,max(Rating),min(Rating) from Film group by CategoryId";
        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(ratingSql, null);
        while (cursor2.moveToNext()) {
            Log.e("count", "numbers: " + cursor2.getString(0) + "..." + cursor2.getInt(1) + "..." + cursor2.getInt(2));
        }
        Log.e("分隔符", "--------------------");

//        这个是不管用的，因为SQLite没有Year()这个函数；
        String nonEquiJoin = "select FilmName,FirstName,LastName from MemberDetails \n" +
                "inner join Film on MemberDetails.DateOfBirth>=Film.YearReleased \n" +
                "where MemberDetails.State='Golden State' order by FirstName,LastName";
        Cursor cursor3 = getDbHelper().getWritableDatabase().rawQuery(nonEquiJoin, null);
        while (cursor3.moveToNext()) {
            Log.e("count", "numbers: " + cursor3.getString(0) + "..." + cursor3.getString(1) + "..." + cursor3.getString(2));
        }
        Log.e("分隔符", "--------------------");

        String multiJoin = "select FirstName,LastName,Category from FavCategory " +
                "inner join MemberDetails on FavCategory.MemberId=MemberDetails.MemberId " +
                "inner join FilmCategory on FavCategory.CategoryId=FilmCategory.CategoryId " +
                "order by FirstName,LastName";
        Cursor cursor4 = getDbHelper().getWritableDatabase().rawQuery(multiJoin, null);
        while (cursor4.moveToNext()) {
            Log.e("count", "numbers: " + cursor4.getString(0) + "..." + cursor4.getString(1) + "..." + cursor4.getString(2));
        }
        Log.e("分隔符", "--------------------");

        String multiOnCondition = "select FirstName,LastName,MemberDetails.City,MemberDetails.State from MemberDetails " +
                "inner join Location on MemberDetails.City=Location.City and MemberDetails.State=Location.State order by FirstName";
        Cursor cursor5 = getDbHelper().getWritableDatabase().rawQuery(multiOnCondition, null);
        while (cursor5.moveToNext()) {
            Log.e("count", "numbers: " + cursor5.getString(0) + "..." + cursor5.getString(1) + "..." + cursor5.getString(2) + "..." + cursor5.getString(3));
        }
        Log.e("分隔符", "--------------------");

        String crossJoin = "select Category,Street from FilmCategory cross join Location order by Category";
        Cursor cursor6 = getDbHelper().getWritableDatabase().rawQuery(crossJoin, null);
        while (cursor6.moveToNext()) {
            Log.e("count", "numbers: " + cursor6.getString(0) + "..." + cursor6.getString(1) + "...");
        }
        Log.e("分隔符", "--------------------");

        String crossJoin1 = "select Category,Street from FilmCategory ,Location order by Category";
        Cursor cursor7 = getDbHelper().getWritableDatabase().rawQuery(crossJoin1, null);
        while (cursor7.moveToNext()) {
            Log.e("count", "numbers: " + cursor7.getString(0) + "..." + cursor7.getString(1) + "...");
        }
        Log.e("分隔符", "--------------------");


        //selfJoin
        String selfJoin = "select t1.FirstName,t1.LastName,t2.FirstName,t2.LastName from MemberDetails as t1 " +
                "inner join MemberDetails as t2 " +
                "where t1.City=t2.City and t1.State=t2.State and t1.MemberId<t2.MemberId";
        Cursor cursor8 = getDbHelper().getWritableDatabase().rawQuery(selfJoin, null);
        while (cursor8.moveToNext()) {
            Log.e("count", "numbers: " + cursor8.getString(0) + " " + cursor8.getString(1) + "..." + cursor8.getString(2) + " " + cursor8.getString(3));
        }
        Log.e("分隔符", "--------------------");

        //selfJoin
        String selfJoin1 = "select t1.FirstName,t1.LastName,t2.FirstName,t2.LastName from MemberDetails as t1 " +
                "inner join MemberDetails as t2 " +
                "on t1.City=t2.City and t1.State=t2.State and t1.MemberId<t2.MemberId";
        Cursor cursor9 = getDbHelper().getWritableDatabase().rawQuery(selfJoin1, null);
        while (cursor9.moveToNext()) {
            Log.e("count", "numbers: " + cursor9.getString(0) + " " + cursor9.getString(1) + "..." + cursor9.getString(2) + " " + cursor9.getString(3));
        }
        Log.e("分隔符", "--------------------");
    }

    public void testOuterJoin() {
        String sql = "select Location.Street,MemberDetails.Street from Location " +
                "left outer join MemberDetails on Location.Street=MemberDetails.Street";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0) + "..." + cursor.getString(1) + "...");
        }
        Log.e("分隔符", "--------------------");

        String sql1 = "select Location.City,MemberDetails.City from Location " +
                "left outer join MemberDetails on Location.City=MemberDetails.City";
        Cursor cursor1 = getDbHelper().getWritableDatabase().rawQuery(sql1, null);
        while (cursor1.moveToNext()) {
            Log.e("count", "numbers: " + cursor1.getString(0) + "..." + cursor1.getString(1) + "...");
        }
        Log.e("分隔符", "--------------------");

        String sql2 = "select Location.City,MemberDetails.City from MemberDetails " +
                "left outer join Location on Location.City=MemberDetails.City";
        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(sql2, null);
        while (cursor2.moveToNext()) {
            Log.e("count", "numbers: " + cursor2.getString(0) + "..." + cursor2.getString(1) + "...");
        }
        Log.e("分隔符", "--------------------");

        String sql3 = "select FilmCategory.Category,Film.FilmName from FilmCategory " +
                "left outer join Film on FilmCategory.CategoryId=Film.CategoryId";
        Cursor cursor3 = getDbHelper().getWritableDatabase().rawQuery(sql3, null);
        while (cursor3.moveToNext()) {
            Log.e("count", "numbers: " + cursor3.getString(0) + "..." + cursor3.getString(1) + "...");
        }
        Log.e("分隔符", "--------------------");

        String sql4 = "select FilmCategory.Category,Film.FilmName from FilmCategory " +
                "left outer join Film on FilmCategory.CategoryId=Film.CategoryId where Film.AvailableDVD='Y'";
        Cursor cursor4 = getDbHelper().getWritableDatabase().rawQuery(sql4, null);
        while (cursor4.moveToNext()) {
            Log.e("count", "numbers: " + cursor4.getString(0) + "..." + cursor4.getString(1) + "...");
        }
        Log.e("分隔符", "--------------------");

        //SQLite 不支持right and full outer joins
//        String sql5="select Location.City,MemberDetails.City from Location " +
//                "right outer join MemberDetails on Location.City=MemberDetails.City";
//        Cursor cursor5 = getDbHelper().getWritableDatabase().rawQuery(sql5, null);
//        while (cursor5.moveToNext()) {
//            Log.e("count", "numbers: " + cursor5.getString(0) + "..." + cursor5.getString(1) + "...");
//        }
//        Log.e("分隔符", "--------------------");
    }

    /**
     * 测试union
     */
    public void testUnion() {
        String sql = "select FilmId,FilmName from Film " +
                "union select MemberId,FirstName from MemberDetails";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0) + "..." + cursor.getString(1) + "...");
        }
        Log.e("分隔符", "--------------------");

        String sql1 = "select FilmId from Film " +
                "union select MemberId from MemberDetails";
        Cursor cursor1 = getDbHelper().getWritableDatabase().rawQuery(sql1, null);
        while (cursor1.moveToNext()) {
            Log.e("count", "numbers: " + cursor1.getString(0) + "...");
        }
        Log.e("分隔符", "--------------------");

        String sql2 = "select FilmId from Film " +
                "union all select MemberId from MemberDetails";
        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(sql2, null);
        while (cursor2.moveToNext()) {
            Log.e("count", "numbers: " + cursor2.getString(0) + "...");
        }
        Log.e("分隔符", "--------------------");

        String sql3 = "select FilmId from Film " +
                "union all select MemberId from MemberDetails " +
                "order by MemberId";
        Cursor cursor3 = getDbHelper().getWritableDatabase().rawQuery(sql3, null);
        while (cursor3.moveToNext()) {
            Log.e("count", "numbers: " + cursor3.getString(0) + "...");
        }
        Log.e("分隔符", "--------------------");
    }


    public void chapter7() {
        String sql = "select md1.FirstName,md1.LastName,md2.FirstName,md2.LastName,FilmCategory.Category from FavCategory " +
                "as fav1 inner join FavCategory as fav2 on fav1.CategoryId=fav2.CategoryId and fav1.MemberId<fav2.MemberId " +
                "inner join MemberDetails as md1 on fav1.MemberId=md1.MemberId\n" +
                " inner join FilmCategory on fav1.CategoryId=FilmCategory.CategoryId " +
                "inner join MemberDetails as md2 on fav2.MemberId=md2.MemberId ";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0) + " " + cursor.getString(1) + "..."
                    + cursor.getString(2) + " " + cursor.getString(3) +
                    "..." + cursor.getString(4));
        }
        Log.e("分隔符", "--------------------");

        String sql1 = "select FilmCategory.Category from FilmCategory " +
                "where FilmCategory.CategoryId not in (select FavCategory.CategoryId from FavCategory)"; //注意subquery记得加上小括号；
        Cursor cursor1 = getDbHelper().getWritableDatabase().rawQuery(sql1, null);
        while (cursor1.moveToNext()) {
            Log.e("count", "numbers: " + cursor1.getString(0));
        }
        Log.e("分隔符", "--------------------");
    }

    public void subQuery() {
        String sql = "select MemberId from MemberDetails where MemberId=(select max(FilmId) from Film where FilmId in (select LocationId from Location))";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0));
        }
        Log.e("分隔符", "--------------------");

        //subquery 作为结果集中的一部分
        String subquery = "select Category,(select max(FilmId) from Film where Film.CategoryId=FilmCategory.CategoryId),CategoryId from FilmCategory";
        Cursor cursor1 = getDbHelper().getWritableDatabase().rawQuery(subquery, null);
        while (cursor1.moveToNext()) {
            Log.e("count", "numbers: " + cursor1.getString(0) + "..." + cursor1.getString(1) + "..." + cursor1.getString(2));
        }
        Log.e("分隔符", "--------------------");
    }

    public void testExists() {
        String sql = "select City from Location where exists (select * from MemberDetails where MemberId>2)";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0));
        }
        Log.e("分隔符", "--------------------");

        //失败的sql语句
        String tryItOutSql = "select FilmCategory.Category from FavCategory " +
                "inner join FilmCategory on FavCategory.CategoryId=FilmCategory.CategoryId " +
//                   "inner join Film on FavCategory.CategoryId=Film.CategoryId " +
                "group by FavCategory.CategoryId having count(FavCategory.MemberId)>=3 ";
//                   "where exists (select * from Film where Film.CategoryId=FavCategory.CategoryId and Film.Rating>3)";
        Cursor cursor1 = getDbHelper().getWritableDatabase().rawQuery(tryItOutSql, null);
        while (cursor1.moveToNext()) {
            Log.e("count", "numbers: " + cursor1.getString(0));
        }
        Log.e("分隔符", "--------------------");

        String sql2 = "select Category from FilmCategory where exists " +
                "(select * from film where FilmCategory.CategoryId=Film.CategoryId and Rating>3 " +
                "and (select count(CategoryId) from FavCategory where FavCategory.CategoryId=FilmCategory.CategoryId)>=3)";
        Cursor cursor2 = getDbHelper().getWritableDatabase().rawQuery(sql2, null);
        while (cursor2.moveToNext()) {
            Log.e("count", "numbers: " + cursor2.getString(0));
        }
        Log.e("分隔符", "--------------------");
    }

    public void testCorrelatedQuery() {
        String sql = "select FilmCategory.Category,Film.Rating,Film.DVDPrice,Film.FilmName from FilmCategory inner join Film on FilmCategory.CategoryId=Film.CategoryId " +
                "where Film.DVDPrice=(select min(Film.DVDPrice) from Film " +
                "where Film.CategoryId=FilmCategory.CategoryId and Rating=" +
                "(select max(Film.Rating) from Film where Film.CategoryId=FilmCategory.CategoryId group by Film.CategoryId) group by Film.CategoryId) group by Film.CategoryId";
        Cursor cursor = getDbHelper().getWritableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Log.e("count", "numbers: " + cursor.getString(0) + "..." + cursor.getInt(1) + "...." + cursor.getInt(2) + "......" + cursor.getString(3) + ".....");
        }
        Log.e("分隔符", "--------------------");
    }

    public void testOtherSubquery() {
        String sql = "insert into FavCategory select 9,MemberDetails.MemberId from MemberDetails " +
                "inner join FavCategory as fc1 on MemberDetails.MemberId=fc1.MemberId " +
                "where fc1.CategoryId=(select CategoryId from FilmCategory where FilmCategory.Category='Thriller') " +
                "and not exists (select * from FavCategory as fc2 where fc2.MemberId=fc1.MemberId and fc2.CategoryId=9)";
        getDbHelper().getWritableDatabase().execSQL(sql);

        String updateSubquery = "update Film set DVDPrice=(select max(DVDPrice) from Film) " +
                "where Film.Rating>3 " +
                "and (select count(fc1.MemberId) from FavCategory as fc1 where fc1.CategoryId=Film.CategoryId group by fc1.CategoryId)>=3";
        getDbHelper().getWritableDatabase().execSQL(updateSubquery);
    }

    /**
     * 在添加foreign key的时候有问题，少了关键字foreign key
     * 无意间使用了关键字order作为table name，然后就一直报错。。。
     * 下次需要注意这个问题；
     */
    public void createMoreTable() {
        String sql = "create table if not exists SalePerson(SalePersonId integer not null primary key," +
                "FirstName varchar(50) not null," +
                "LastName varchar(50) not null)";
        getDbHelper().getWritableDatabase().execSQL(sql);
//        String createOrders = "create table if not exists Order(OrderId integer not null primary key," +
//                "MemberId integer," +
//                "SalePersonId integer," +
//                "OrderDate text," +
//                "foreign key(SalePersonId) references SalePerson(SalePersonId)," +
//                "foreign key (MemberId) references MemberDetails(MemberId))";
//        getDbHelper().getWritableDatabase().execSQL(createOrders);

        String createOrder="create table if not exists Orders(OrderId integer not null primary key,MemberId integer,SalePersonId integer,OrderDate text," +
                "foreign key(SalePersonId) references SalePerson(SalePersonId)," +
                "foreign key(MemberId) references MemberDetails(MemberId))";
        getDbHelper().getWritableDatabase().execSQL(createOrder);

        String createOrderItems = "create table if not exists OrderItems(OrderId integer not null," +
                "FilmId integer not null," +
                "constraint Film_fk  foreign key(FilmId) references Film(FilmId)," +
                "constraint Order_fk foreign key(OrderId) references Orders(OrderId)," +
                "constraint OrderItem_pk primary key(OrderId,FilmId))";

        getDbHelper().getWritableDatabase().execSQL(createOrderItems);
    }

    public void insertDataToTable() {
        String insertSql1 = "insert into SalePerson values(1,'Sandra','Hugson')";
        String insertSql2 = "insert into SalePerson values(2,'Frasier','Crane')";
        String insertSql3 = "insert into SalePerson values(3,'Daphne','Moon')";
        getDbHelper().getWritableDatabase().execSQL(insertSql1);
        getDbHelper().getWritableDatabase().execSQL(insertSql2);
        getDbHelper().getWritableDatabase().execSQL(insertSql3);

        String insertOrderSql1 = "insert into Orders values(10,1,1,'2006-07-30')";
        String insertOrderSql2 = "insert into Orders values(11,4,1,'2006-07-30')";
        String insertOrderSql3 = "insert into Orders values(12,6,1,'2006-07-30')";
        String insertOrderSql4 = "insert into Orders values(13,4,1,'2006-07-30')";
        String insertOrderSql5 = "insert into Orders values(1,6,2,'2006-07-30')";
        String insertOrderSql6 = "insert into Orders values(2,4,2,'2006-07-30')";
        String insertOrderSql7 = "insert into Orders values(26,5,3,'2006-07-30')";
        String insertOrderSql8 = "insert into Orders values(27,5,3,'2006-07-30')";
        String insertOrderSql9 = "insert into Orders values(29,6,3,'2006-07-30')";

        getDbHelper().getWritableDatabase().execSQL(insertOrderSql1);
        getDbHelper().getWritableDatabase().execSQL(insertOrderSql2);
        getDbHelper().getWritableDatabase().execSQL(insertOrderSql3);
        getDbHelper().getWritableDatabase().execSQL(insertOrderSql4);
        getDbHelper().getWritableDatabase().execSQL(insertOrderSql5);
        getDbHelper().getWritableDatabase().execSQL(insertOrderSql6);
        getDbHelper().getWritableDatabase().execSQL(insertOrderSql7);
        getDbHelper().getWritableDatabase().execSQL(insertOrderSql8);
        getDbHelper().getWritableDatabase().execSQL(insertOrderSql9);

        String insertOrderItem1 = "insert into OrderItems values(10,4)";
        String insertOrderItem2 = "insert into OrderItems values(10,3)";
        String insertOrderItem3 = "insert into OrderItems values(11,1)";
        String insertOrderItem4 = "insert into OrderItems values(11,2)";
        String insertOrderItem5 = "insert into OrderItems values(12,7)";
        String insertOrderItem6 = "insert into OrderItems values(12,2)";
        String insertOrderItem7 = "insert into OrderItems values(13,5)";
        String insertOrderItem8 = "insert into OrderItems values(1,5)";
        String insertOrderItem9 = "insert into OrderItems values(2,6)";
        String insertOrderItem10 = "insert into OrderItems values(26,6)";
        String insertOrderItem11 = "insert into OrderItems values(27,8)";
        String insertOrderItem12 = "insert into OrderItems values(26,4)";
        String insertOrderItem13 = "insert into OrderItems values(29,4)";

        getDbHelper().getWritableDatabase().execSQL(insertOrderItem1);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem2);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem3);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem4);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem5);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem6);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem7);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem8);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem9);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem10);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem11);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem12);
        getDbHelper().getWritableDatabase().execSQL(insertOrderItem13);


    }


}
