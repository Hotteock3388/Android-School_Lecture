package com.example.ex04_listview04_simpleadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // 리스트뷰 항목에 세팅할 데이터
    int[] imgResArray = {R.drawable.h_iu01, R.drawable.h_iu02, R.drawable.h_iu03, R.drawable.h_iu04, R.drawable.h_iu05,
            R.drawable.h_iu06, R.drawable.h_iu07, R.drawable.h_iu08, R.drawable.h_iu09, R.drawable.h_iu010};
    ArrayList<String> titleData = getArrayListData("제목", imgResArray.length);
    ArrayList<String> contentsData = getArrayListData("내용\n이미지", imgResArray.length);

    // 뷰의 참조변수
    ListView listView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);

        // ListView에 표시할 데이터를 가진 ArrayList 생성
        ArrayList<HashMap<String, Object>> listViewData = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<imgResArray.length; i++){
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("img", imgResArray[i]);
            hashMap.put("title", titleData.get(i));
            hashMap.put("contents", contentsData.get(i));

            listViewData.add(hashMap);
        }

        // HashMap 객체의 키값들
        String[] keys = {"img", "title", "contents"};
        // 데이터를 세팅할 뷰의 id값들
        int[] ids = {R.id.imageView, R.id.textViewTitle, R.id.textViewContents};

        // SimpleAdapter 생성
        // SimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
        SimpleAdapter adapter = new SimpleAdapter(this, listViewData, R.layout.row, keys, ids);

        // ListView에 Adapter 연결
        listView.setAdapter(adapter);

        // 리스트뷰의 항목을 클릭하면 반응하는 이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(titleData.get(position));
            }
        });
    }

    // ArrayList를 반환하는 함수
    private ArrayList<String> getArrayListData(String str, int count){
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=0; i<count; i++){
            arrayList.add(str + (i+1));
        }
        return arrayList;
    }
}
