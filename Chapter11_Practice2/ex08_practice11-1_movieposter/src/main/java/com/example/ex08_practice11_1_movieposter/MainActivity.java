package com.example.ex08_practice11_1_movieposter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    int[] posterId = {R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04, R.drawable.mov05,
            R.drawable.mov06, R.drawable.mov07, R.drawable.mov08, R.drawable.mov09, R.drawable.mov10,
            R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04, R.drawable.mov05,
            R.drawable.mov06, R.drawable.mov07, R.drawable.mov08, R.drawable.mov09, R.drawable.mov10,
            R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04, R.drawable.mov05,
            R.drawable.mov06, R.drawable.mov07, R.drawable.mov08, R.drawable.mov09, R.drawable.mov10,
            R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04, R.drawable.mov05,
            R.drawable.mov06, R.drawable.mov07, R.drawable.mov08, R.drawable.mov09, R.drawable.mov10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 그리드뷰에 출력할 이미지 ID 배열
        gridView = findViewById(R.id.gridView);
        MyGridAdapter adapter = new MyGridAdapter(this, posterId);

        gridView.setAdapter(adapter);
    }
}
