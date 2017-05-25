package com.github.tinkerti.sqltest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.tinkerti.sqltest.DB.DBManager;
import com.github.tinkerti.sqltest.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createTable(View view) {
        DBManager.getInstance().createTable();
    }

    public void addField(View view) {
        DBManager.getInstance().addTableField();
    }

    public void dropField(View view) {
        DBManager.getInstance().dropTableField();
    }

    public void dropTable(View view) {
        DBManager.getInstance().dropTable();
    }

    public void createMemberDetailTable(View view) {
        DBManager.getInstance().createMemberDetailsTable();
    }

    public void createAttendanceTable(View view) {
        DBManager.getInstance().createAttendanceTable();
    }

    public void createFilmTable(View view) {
        DBManager.getInstance().createFilmTable();
    }

    public void createFilmCategoryTable(View view) {
        DBManager.getInstance().createFilmCategoryTable();
    }

    public void createFavCategoryTable(View view) {
        DBManager.getInstance().createFavCategoryTable();
    }

    public void insertDataIntoFilmCategoryTable(View view) {
        DBManager.getInstance().insertDataIntoCategoryTable();
    }

    public void createAllTable(View view) {
        DBManager.getInstance().createAllTable();
    }

    public void insertData(View view) {
        DBManager.getInstance().insertDataIntoCategoryTable();
        DBManager.getInstance().insertIntoTableMemberDetails();
    }

    public void updateTable(View view) {
        DBManager.getInstance().updateTable();
    }

    public void doExercise(View view) {
//        DBManager.getInstance().doExercise();
//        DBManager.getInstance().alterAttendanceTable();
//        DBManager.getInstance().addFavCategoryForeignKey();
//        DBManager.getInstance().testOperators();
//        DBManager.getInstance().testNulls();
//        DBManager.getInstance().testStringNulls();
//        DBManager.getInstance().testGroupBy();
//        DBManager.getInstance().testCount();
//        DBManager.getInstance().testAvg();
//        DBManager.getInstance().testMinAndMax();
//        DBManager.getInstance().testHaving();
//        DBManager.getInstance().testOuterJoin();
//        DBManager.getInstance().testUnion();
//        DBManager.getInstance().chapter7();
//        DBManager.getInstance().subQuery();
//        DBManager.getInstance().testExists();
//        DBManager.getInstance().testCorrelatedQuery();
//        DBManager.getInstance().testOtherSubquery();
//        DBManager.getInstance().testComplexOrder();
//        DBManager.getInstance().testEqualAndIn();
        DBManager.getInstance().testView();
    }

    public void initData(View view) {
        DBManager.getInstance().createTableAndInitData();
    }

    public void selectLike(View view) {
        DBManager.getInstance().selectLike();
    }

    public void addPrimaryKey(View view) {
        DBManager.getInstance().addPrimaryKey();
    }

    public void testForeignKey(View view) {
        DBManager.getInstance().testForeignKey();
//        DBManager.getInstance().testIndex();
    }

    @Override
    public void onClick(View v) {

    }
}

