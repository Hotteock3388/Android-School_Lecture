package com.example.ex04_listview04_simpleadapter01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;

    ArrayList<String> titleData = getArrayListData("Title", 50);
    ArrayList<String> contentsData = getArrayListData("Contents", 50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region 참조변수에 객체 연결
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
        //endregion

        // ListView에 표시할 데이터들을 하나로 담을 ArrayList 객체 생성
        ArrayList<HashMap<String, String>> listViewData = new ArrayList<HashMap<String, String>>();
        for(int i=0; i<contentsData.size(); i++){
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("title", titleData.get(i));
            hashMap.put("contents", contentsData.get(i));

            listViewData.add(hashMap);
        }
        String[] keys = {"title", "contents"};
//        android.R.layout.simple_list_item_2
        int[] ids = {android.R.id.text1, android.R.id.text2};

        // 각 항목을 표시할 View, 데이터 가진 SimpleAdapter 객체 생성
        // SimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
        SimpleAdapter adapter = new SimpleAdapter(this, listViewData, android.R.layout.simple_list_item_2, keys, ids);

        // ListView에 adapter 연결
        listView.setAdapter(adapter);

        // ListView의 아이템을 클릭했을 때의 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(titleData.get(position) + ": " + contentsData.get(position));
            }
        });

    }

    // ArrayList를 반환하는 함수
    private ArrayList<String> getArrayListData(String str, int length){
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=0; i<length; i++){
            arrayList.add(str + (i+1));
        }
        return arrayList;
    }
}
