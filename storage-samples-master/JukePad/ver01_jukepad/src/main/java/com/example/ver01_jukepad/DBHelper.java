package com.example.ver01_jukepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, "jukepad_database", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE tb_category (category_name text PRIMARY KEY NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE tb_sound_button (" +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "category_name text NOT NULL, "+
                "button_name text NOT NULL, "+
                "audio_id text, "+
                "audio_filename text)";
        db.execSQL(sql);

        Log.d("JukePadTag", "데이터베이스가 생성되었습니다.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==DATABASE_VERSION){
            db.execSQL("drop table tb_category");
            db.execSQL("drop table tb_sound_button");
            onCreate(db);
        }
        Log.d("JukePadTag", "데이터베이스가 업데이트 되었습니다.");
    }
}
