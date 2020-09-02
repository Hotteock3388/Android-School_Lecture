package com.example.ex03_sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editTextTitle, editTextContents;
    Button btnAdd;

    DBHelper helper;    // 테이블 생성, 스키마 변경 등의 작업 처리
    SQLiteDatabase db;  // 데이터베이스에 데이터를 저장, 수정, 삭제하는 모든 SQL 질의문 처리

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContents = findViewById(R.id.editTextContents);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String contents = editTextContents.getText().toString();

                try{
                    helper = new DBHelper(getApplicationContext());
                    db = helper.getWritableDatabase();
                    String sql=String.format("INSERT INTO tb_test (title, contents) values ('%s', '%s')", title, contents);
                    db.execSQL(sql);    // insert, update, delete 문 실행, select문은 rawQuery()로 실행
                }catch (Exception e){
                    Log.d("DatabaseError",e.toString());
                }
                finally {
                    db.close();
                }

                Intent intent = new Intent(getApplicationContext(), ReadDBActivity.class);
                startActivity(intent);
            }
        });
    }
}
