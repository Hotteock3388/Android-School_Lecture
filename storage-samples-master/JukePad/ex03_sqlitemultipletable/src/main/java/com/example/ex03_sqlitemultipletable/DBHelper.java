package com.example.ex03_sqlitemultipletable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    
    public DBHelper(Context context){
        super(context, "testdb2", null, DATABASE_VERSION);
    }

    // 앱이 설치된 후 SQLiteOpenHelper가 최초로 이용되는 순간 한 번 호출
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE tb_category (category_name text PRIMARY KEY NOT NULL)";
        db.execSQL(sql);
        sql="CREATE TABLE tb_sound_data (" +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "category_name text NOT NULL," +
                "button_name text NOT NULL)";
        db.execSQL(sql);
        Log.d("SQLiteTestTag", "DBHelper onCreate()");
    }

    // 데이터베이스 버전이 변경될 때마다 호출
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==DATABASE_VERSION){
            db.execSQL("drop table tb_category");
            db.execSQL("drop table tb_sound_data");
            onCreate(db);
        }
        Log.d("SQLiteTestTag", "DBHelper onUpgrade()");
    }
}
