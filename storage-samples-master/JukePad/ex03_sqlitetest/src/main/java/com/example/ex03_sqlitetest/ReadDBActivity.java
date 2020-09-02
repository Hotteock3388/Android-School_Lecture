package com.example.ex03_sqlitetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ReadDBActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_d_b);

        listView = findViewById(R.id.listview);
        String[] keys = {"title", "contents"};
        int[] ids = {android.R.id.text1, android.R.id.text2};

        // DB Data 불러오기
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select title, contents from tb_test", null);

        while (cursor.moveToNext()){
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("title", cursor.getString(0));
            hashMap.put("contents", cursor.getString(1));
            arrayList.add(hashMap);
//            Log.d("SQLiteData", cursor.getString(0) + ", " + cursor.getString(1));
        }
        db.close();

        // SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2, keys, ids);

        listView.setAdapter(adapter);

    }
}
