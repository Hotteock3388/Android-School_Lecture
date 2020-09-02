package com.example.ex05_listview05_simpleadapter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;

    // 리스트뷰에 표시할 데이터
    int[] imgResIds = {R.drawable.kakao01, R.drawable.kakao02, R.drawable.kakao03, R.drawable.kakao04,
            R.drawable.kakao05, R.drawable.kakao06, R.drawable.kakao07, R.drawable.kakao08, R.drawable.kakao09};
    ArrayList<String> titleData = getArrayListData("Title ", imgResIds.length);
    ArrayList<String> contentsData = getArrayListData("Contents", imgResIds.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);

        // 리스트뷰의 전체 항목을 구성할 ArrayList 객체 생성
        ArrayList<HashMap<String, Object>> listViewData = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<imgResIds.length; i++){
            // 한 항목을 구성할 HashMap 객체를 생성하여 listViewData에 추가
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("img", imgResIds[i]);
            hashMap.put("title", titleData.get(i));
            hashMap.put("contents", contentsData.get(i));

            listViewData.add(hashMap);
        }

        // SimpleAdapter 객체 생성
        // SimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listViewData,
                R.layout.row,
                new String[]{"img", "title", "contents"},
                new int[]{R.id.imageViewRow, R.id.textViewRowTitle, R.id.textViewRowContents}
        );

        // 리스트뷰에 adapter 연결
        listView.setAdapter(adapter);

        // 리스트뷰의 아이템을 클릭하면 발생하는 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(titleData.get(position)+" : "+contentsData.get(position));
            }
        });
    }

    private ArrayList<String> getArrayListData(String str, int count) {
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0; i<count; i++){
            list.add(str + (i+1));
        }
        return list;
    }
}
