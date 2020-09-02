package com.example.ex01_listview01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;

    String[] dataArr = getStringArrayData();            // 리스트뷰에 표시할 문자열 배열
    ArrayList<String> arrayList = getArrayListData();   // 리스트뷰에 표시할 ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);

         //각 항목을 표시할 View, 데이터 가진 ArrayAdapter 객체 생성
         /*
         simple_list_item_1 : 문자열 데이터 하나
         simple_list_item_single_choice : 문자열 데이터 하나 + 라디오 버튼
         simple_list_item_multiple_choice : 문자열 데이터 하나 + 체크박스 버튼
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                dataArr);

        // ListView에 ArrayAdapter 연결
        listView.setAdapter(adapter);

        // 체크박스 선택모드 세팅
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.getCheckedItemPositions(); // 선택된 아이템의 정보를 SparseBooleanArrays 객체로 받기

        // ListView의 Item을 클릭하면 발생하는 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // 항목을 클릭하면 호출되는 메소드
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position : 사용자가 클릭한 항목의 index 번호
                textView.setText(dataArr[position]);
            }
        });

    }

    // 스트링 배열을 반환하는 함수
    private String[] getStringArrayData() {
        String[] stringArr = new String[100];
        for(int i=0; i<stringArr.length; i++){
            stringArr[i] = "배열 데이터" + (i+1);
        }
        return stringArr;
    }

    // ArrayList를 반환하는 함수
    private ArrayList<String> getArrayListData(){
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=0; i<100; i++){
            arrayList.add("리스트 데이터 "+(i+1));
        }
        return arrayList;
    }
}
