package com.example.manageattendence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Helper extends SQLiteOpenHelper {
    Context context;
    public Helper(Context context){
        super(context,"attendence_manager",null,1);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String s1="CREATE TABLE ATTENDENCE_DETAIL(SUBJECT_NAME VARCHAR(30) PRIMARY KEY,TOTAL_PRESENT INT NOT NULL,TOTAL_CLASSES INT NOT NULL)";//create table in databse for storing subject name and attendence as subject_name primary key
        String s2="CREATE TABLE GOAL(ATTENDENCE_GOAL INT NOT NULL)";//create a table for taking the goal from the user
        ContentValues values=new ContentValues();
        values.put("ATTENDENCE_GOAL",75);
        String s4="CREATE TABLE TIME_TABLE(SUBJECT_NAME VARCHAR(30) NOT NULL,DAY_NAME VARCHAR(15) NOT NULL,TIMING VARCHAR(30) NOT NULL,LOCATION VARCHAR(30))";
        String s5="CREATE TABLE ATTENDENCE_STATUS(DATE VARCHAR(20) NOT NULL,TIME VARCHAR(20) NOT NULL,SUBJECT_NAME VARCHAR(30) NOT NULL,STATUS VARCHAR(20) NOT NULL,FOREIGN KEY(SUBJECT_NAME) REFERENCES ATTENDENCE_DETAIL(SUBJECT_NAME))";
        String s6="CREATE TABLE SWITCH(ISON INT)";
                db.execSQL(s2);
                db.execSQL(s1);
                db.insert("GOAL",null,values);
                db.execSQL(s4);
                db.execSQL(s5);
                db.execSQL(s6);
                db.execSQL("INSERT INTO SWITCH VALUES(1)");
        Toast.makeText(context, "Database created!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
