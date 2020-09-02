package com.example.ex06_listview06_customadapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ListView listView;
    Button btnAdd;

    // 리스트뷰에 표시할 데이터를 저정할 List
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);

        // 리스트뷰에 표시할 데이터 저장
        dataList = getArrayListData(5);

        // 사용자 정의 Adapter 객체 생성
        final CustomAdapter adapter = new CustomAdapter(this, R.layout.row, dataList);

        // 리스트뷰에 어댑터 연결
        listView.setAdapter(adapter);

        // 리스트뷰에 새로운 아이템 추가
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "추가할 문자열을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                adapter.addItem(editText.getText().toString());
                editText.setText("");

                // editText의 포커스를 없애고, 키보드 숨기기
                if(editText.hasFocus()){
                    editText.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }
        });
    }

    // ArrayList를 반환하는 함수
    private ArrayList<String> getArrayListData(int count){
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=0; i<count; i++){
            arrayList.add("리스트 데이터 "+(i+1));
        }
        return arrayList;
    }
}
