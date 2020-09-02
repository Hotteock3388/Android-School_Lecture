package com.example.ex02_listview02_customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;

    // 리스트뷰에 표시할 문자열 배열
    String[] dataArr = getStringArrayData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);

        // 각 항목을 표시할 View, 데이터 가진 ArrayAdapter 객체 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.row,
                R.id.textView,
                dataArr);

        // ListView에 ArrayAdapter 연결
        listView.setAdapter(adapter);

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
            stringArr[i] = "데이터" + (i+1);
        }
        return stringArr;
    }
}
