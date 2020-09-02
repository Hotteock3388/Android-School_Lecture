package com.example.ex09_spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Spinner spinner;

    // Spinner에 표시할 초기 문자열 배열
    ArrayList<String> dataList = getArrayListData(20);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);

        // 어댑터 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataList);

        // 드롭다운뷰 지정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 스피너에 어댑터 연결
        spinner.setAdapter(adapter);

        // 스피너에서 항목이 선택되었을 때의 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(dataList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // ArrayList를 반환하는 함수
    private ArrayList<String> getArrayListData(int count){
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=1; i<=count; i++){
            arrayList.add("스피너 데이터 " + i);
        }
        return arrayList;
    }
}
