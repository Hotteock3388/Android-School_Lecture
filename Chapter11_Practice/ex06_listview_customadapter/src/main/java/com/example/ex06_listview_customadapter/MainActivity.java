package com.example.ex06_listview_customadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;

    // 리스트뷰에 표시할 데이터
    ArrayList<String> dataList = getArrayListData(50);   // 리스트뷰에 표시할 ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);

        // ArrayAdapter(Context context, int resource, List<T> objects)
        CustomAdapter adapter = new CustomAdapter();

        // ListView에 ArrayAdapter 연결
        listView.setAdapter(adapter);

    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        // 화면에 출력될 항목 개수만큼 호출
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 재사용 가능한 View가 없다면 View 생성
            if(convertView == null){
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.row, null);
            }

            // 반환할 View 구성
            final TextView textViewRow = convertView.findViewById(R.id.textViewRow);
            textViewRow.setText(dataList.get(position));
            Button btn1 = convertView.findViewById(R.id.button1);
            Button btn2 = convertView.findViewById(R.id.button2);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()){
                        case R.id.button1:
                            textView.setText(v.getTag().toString() + " BUTTON1 눌림");
                            break;
                        case R.id.button2:
                            textView.setText(v.getTag().toString() + " BUTTON2 눌림");
                            break;
                    }
                }
            };

            btn1.setOnClickListener(listener);
            btn2.setOnClickListener(listener);

            // 버튼에 인덱스 값을 저장한다.
            btn1.setTag(textViewRow.getText().toString());
            btn2.setTag(textViewRow.getText().toString());

            return convertView;
        }
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
