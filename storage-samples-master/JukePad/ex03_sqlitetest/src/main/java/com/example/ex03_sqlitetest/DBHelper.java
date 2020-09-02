package com.example.ex03_sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    
    public DBHelper(Context context){
        super(context, "testdb", null, DATABASE_VERSION);
    }

    // 앱이 설치된 후 SQLiteOpenHelper가 최초로 이용되는 순간 한 번 호출
    @Override
    public void onCreate(SQLiteDatabase db) {
        String memoSQL="create table tb_test (" +
                "_id integer primary key autoincrement," +
                "title text," +
                "contents text)";
        db.execSQL(memoSQL);
        Log.d("SQLiteTest", "DBHelper onCreate()");
    }

    // 데이터베이스 버전이 변경될 때마다 호출
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==DATABASE_VERSION){
            db.execSQL("drop table tb_test");
            onCreate(db);
        }
        Log.d("SQLiteTest", "DBHelper onUpgrade()");
    }
}
