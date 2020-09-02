package com.example.ex03_sqlitemultipletable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editTextCategory, editTextButtonName;
    Button btnAddCategory, btnAddSoundData, btnGoReadActivity, btnDropTable;

    DBHelper helper;    // 테이블 생성, 스키마 변경 등의 작업 처리
    SQLiteDatabase db;  // 데이터베이스에 데이터를 저장, 수정, 삭제하는 모든 SQL 질의문 처리

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCategory = findViewById(R.id.editText_categoryName);
        editTextButtonName = findViewById(R.id.editText_soundData);
        btnAddCategory = findViewById(R.id.btnAddCategory);
        btnAddSoundData = findViewById(R.id.btnAddSoundData);
        btnGoReadActivity = findViewById(R.id.btnReadActivity);
        btnDropTable = findViewById(R.id.btnDropTable);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_name = editTextCategory.getText().toString();

                try{
                    helper = new DBHelper(getApplicationContext());
                    db = helper.getWritableDatabase();

                    String sql=String.format("INSERT INTO tb_category (category_name) VALUES ('%s')", category_name);
                    db.execSQL(sql);

                }catch (Exception e){
                    Log.d("DatabaseError",e.toString());
                }
                finally {
                    db.close();
                }
            }
        });

        btnAddSoundData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_name = editTextCategory.getText().toString();
                String button_name = editTextButtonName.getText().toString();

                try{
                    helper = new DBHelper(getApplicationContext());
                    db = helper.getWritableDatabase();

                    String sql=String.format("INSERT INTO tb_sound_data (category_name, button_name) VALUES ('%s', '%s')", category_name, button_name);
                    db.execSQL(sql);
                }catch (Exception e){
                    Log.d("DatabaseError",e.toString());
                }
                finally {
                    db.close();
                }
            }
        });

        btnGoReadActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadDBActivity.class);
                startActivity(intent);
            }
        });
    }
}
