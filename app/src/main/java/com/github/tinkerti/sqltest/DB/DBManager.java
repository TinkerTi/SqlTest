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
        String sql = "create table MemberDetails (MemberId integer , FirstName text, LastName text, DateOfBirth text, Street text," +
                "City text, State varchar,ZipCode text, Email text, DateOfJoining text)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createLocationTable() {
        String sql = "create table Location(LocationId integer,Street text,City text,State text)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createAttendanceTable() {
        String sql = "create table Attendance(LocationId integer,MeetingDate text,MemberAttended text, MemberId integer)";
        getDbHelper().getWritableDatabase().execSQL(sql);
    }

    public void createFilmTable() {
        String sql = "create table Film (FilmId integer,FilmName text,YearReleased integer,PlotSummary text,AvailableDVD text," +
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
        String sql = "create table FavCategory(CategoryId integer,MemberId integer)";
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
                "2," +
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
                "2," +
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
            Log.e("count", "numbers: " + cursor5.getString(0)+"..."+cursor5.getInt(1));
        }
        Log.e("分隔符", "--------------------");

        String countSql6 = "select avg(MemberId) from MemberDetails";
        Cursor cursor6 = getDbHelper().getWritableDatabase().rawQuery(countSql6, null);
        while (cursor6.moveToNext()) {
            Log.e("count", "numbers: " + cursor6.getInt(0) + ".." );
        }
        Log.e("分隔符", "--------------------");
    }

}
