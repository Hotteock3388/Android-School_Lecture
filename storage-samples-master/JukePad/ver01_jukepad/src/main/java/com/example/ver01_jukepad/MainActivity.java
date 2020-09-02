package com.example.ver01_jukepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnAdd, btnCheckFever;
    ArrayList<String> arrayList;
    CategoryCustomAdapter adapter;

    DBHelper dbHelper;    // 테이블 생성, 스키마 변경 등의 작업 처리
    SQLiteDatabase db;  // 데이터베이스에 데이터를 저장, 수정, 삭제하는 모든 SQL 질의문 처리

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        btnAdd = findViewById(R.id.btnAddCategory);
        btnCheckFever = findViewById(R.id.btnCheckFever);
        arrayList = new ArrayList<String>();

        //Log.d("JukePadTag", "onCreate() 시작");

        //region 초기에 ListView에 category를 표시하기 위해 Database에서 category 데이터를 가져와서 dataList_category에 저장
        try {
            dbHelper = new DBHelper(getApplicationContext());
            db = dbHelper.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT category_name FROM tb_category", null);

            while (cursor.moveToNext()){
                arrayList.add(cursor.getString(0));
            }
        }catch (Exception e){
            Log.d("JukePadTag", e.toString());
        }finally {
            db.close();
        }
        //endregion

        adapter = new CategoryCustomAdapter(this, R.layout.listview_item_category, arrayList);
        listView.setAdapter(adapter);

        //region ADD 버튼 클릭 이벤트 처리(카테고리 추가)
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("New Category")
                    .setMessage("새롭게 추가할 카테고리 이름을 입력하세요.")
                    .setCancelable(false)
                    .setView(editText)
                    .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ListView에 표시
                            arrayList.add(editText.getText().toString());
                            adapter.notifyDataSetChanged();

                            // DB에 Insert
                            try {
                                dbHelper = new DBHelper(getApplicationContext());
                                db = dbHelper.getWritableDatabase();

                                String sql = String.format("INSERT INTO tb_category (category_name) values ('%s')", editText.getText().toString());
                                db.execSQL(sql);
                            }catch (Exception e){
                                Log.d("DatabaseError",e.toString());
                            }finally {
                                db.close();
                            }
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();
            }
        });
        //endregion

        //region 발열 체크 버튼 클릭 이벤트 처리
        btnCheckFever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckFeverActivity.class);
                startActivity(intent);
            }
        });
        //endregion

        //ListView를 Context 메뉴로 등록
        registerForContextMenu(listView);
    }

    // Context 메뉴로 등록한 View(여기서는 ListView)가 처음 클릭되어 만들어질 때 호출되는 메소드
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //res폴더의 menu플더안에 xml로 MenuItem추가하기.
        //menu_category.xml 파일을 java 객체로 인플레이트(inflate)해서 menu객체에 추가
        getMenuInflater().inflate(R.menu.menu_category, menu);
    }

    // Context 메뉴로 등록한 View(여기서는 ListView)가 클릭되었을 때 자동으로 호출되는 메소드
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //AdapterContextMenuInfo : AdapterView가 onCreateContextMenu()를 실행할 때, 추가적인 menu 정보를 관리하는 클래스
        //ContextMenu로 등록된 AdapterView(여기서는 Listview)의 선택된 항목에 대한 정보를 관리하는 클래스
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        final int position= info.position; //AdapterView안에서 ContextMenu를 보여주는 항목의 위치

        //선택된 ContextMenu 아이템의 아이디로 구별하여 원하는 작업 수행
        switch( item.getItemId() ){
            case R.id.modify_category:
                // DB Update 작업
                final EditText editText = new EditText(MainActivity.this);
                editText.setText(arrayList.get(position));
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Modify Category")
                        .setMessage("수정할 카테고리 이름을 입력하세요.")
                        .setCancelable(false)
                        .setView(editText)
                        .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DB에 Insert
                                try {
                                    dbHelper = new DBHelper(getApplicationContext());
                                    db = dbHelper.getWritableDatabase();

                                    String sql = String.format(
                                            "UPDATE tb_category SET category_name='%s' WHERE category_name='%s'",
                                            editText.getText().toString(),
                                            arrayList.get(position));
                                    db.execSQL(sql);
                                }catch (Exception e){
                                    Log.d("DatabaseError",e.toString());
                                }finally {
                                    db.close();
                                }
                                // ListView에 표시
                                arrayList.set(position, editText.getText().toString());
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                Toast.makeText(this, arrayList.get(position)+" category modified", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_category:
                // DB Delete 작업
                Toast.makeText(this, arrayList.get(position)+" category deleted.", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Category")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DB에 Insert
                                try {
                                    dbHelper = new DBHelper(getApplicationContext());
                                    db = dbHelper.getWritableDatabase();

                                    String sql = String.format("DELETE FROM tb_category WHERE category_name='%s'", arrayList.get(position));
                                    db.execSQL(sql);
                                }catch (Exception e){
                                    Log.d("DatabaseError",e.toString());
                                }finally {
                                    db.close();
                                }
                                // ListView에 표시
                                arrayList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                break;
        }
        return true;
    }
}
