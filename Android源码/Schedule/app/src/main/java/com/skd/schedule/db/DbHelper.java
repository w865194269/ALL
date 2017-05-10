package com.skd.schedule.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangxungong on 2017/4/14.
 */

public class DbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS teacher" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id VARCHAR, course VARCHAR,  credit VARCHAR, teachingType VARCHAR, courseType VARCHAR, classNum VARCHAR, classroom VARCHAR, number VARCHAR, time VARCHAR, address VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS teacherlist" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, jobnumber VARCHAR, name VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS course" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, courseid VARCHAR, name VARCHAR,  classNum VARCHAR, number VARCHAR, courseType VARCHAR, credit VARCHAR, classroom VARCHAR, weeks VARCHAR, section VARCHAR, address VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS courselist" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id VARCHAR, name VARCHAR)");

        db.execSQL("CREATE TABLE IF NOT EXISTS classlist" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id VARCHAR, name VARCHAR)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
