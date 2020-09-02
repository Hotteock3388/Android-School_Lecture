package com.example.ex03_sqlitemultipletable;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ReadDBActivity extends AppCompatActivity {

    ListView listViewCategory, listViewSoundData;
    ArrayList<String> arrayListCategory = new ArrayList<String>();
    ArrayList<HashMap<String, String>> arrayListSoundData = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_db);

        listViewCategory = findViewById(R.id.listview_category);
        listViewSoundData = findViewById(R.id.listview_sounddata);

        String[] keys = {"category_name", "button_name"};
        int[] ids = {android.R.id.text1, android.R.id.text2};

        //region DB Data 불러와 arrayList에 저장하기
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor_category = db.rawQuery("SELECT category_name FROM tb_category", null);
        Cursor cursor_soundData = db.rawQuery("SELECT category_name, button_name FROM tb_sound_data", null);

        while(cursor_category.moveToNext()){
            arrayListCategory.add(cursor_category.getString(0));
        }

        while (cursor_soundData.moveToNext()){
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("category_name", cursor_soundData.getString(0));
            hashMap.put("button_name", cursor_soundData.getString(1));
            arrayListSoundData.add(hashMap);
        }
        db.close();
        //endregion

        // 어댑터
        ArrayAdapter adapter_category = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListCategory);
        SimpleAdapter adapter_soundData = new SimpleAdapter(this, arrayListSoundData, android.R.layout.simple_list_item_2, keys, ids);

        listViewCategory.setAdapter(adapter_category);
        listViewSoundData.setAdapter(adapter_soundData);
    }
}
