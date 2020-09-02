package com.example.ex04_splashactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        arrayList = getArrayListData();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(adapter);
    }

    // ArrayList를 반환하는 함수
    private ArrayList<String> getArrayListData(){
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=0; i<5; i++){
            arrayList.add("리스트 데이터 "+(i+1));
        }
        return arrayList;
    }
}
