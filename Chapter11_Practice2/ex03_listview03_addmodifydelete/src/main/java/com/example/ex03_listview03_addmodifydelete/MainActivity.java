package com.example.ex03_listview03_addmodifydelete;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;
    Button btnAdd, btnModify, btnDelete;

    ArrayList<String> arrayList = getArrayListData();   // 리스트뷰에 표시할 ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region 참조변수에 객체 연결
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnModify = findViewById(R.id.btnModify);
        btnDelete = findViewById(R.id.btnDelete);
        //endregion

         //각 항목을 표시할 View, 데이터 가진 ArrayAdapter 객체 생성
         /*
         simple_list_item_1 : 문자열 데이터 하나
         simple_list_item_single_choice : 문자열 데이터 하나 + 라디오 버튼
         simple_list_item_multiple_choice : 문자열 데이터 하나 + 체크박스 버튼
         */
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_single_choice,
                arrayList);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // ListView에 ArrayAdapter 연결
        listView.setAdapter(adapter);

        // ListView의 Item을 클릭하면 발생하는 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // 항목을 클릭하면 호출되는 메소드
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position : 사용자가 클릭한 항목의 index 번호
                textView.setText(arrayList.get(position));
            }
        });

        // ListView에 항목 추가
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int checkedIndex = listView.getCheckedItemPosition();   // 체크된 아이템 인덱스 저장

                switch (v.getId()){
                    case R.id.btnAdd:
                        int count = adapter.getCount();               // 아이템 개수
                        arrayList.add("리스트 데이터"+(count+1));     // 아이템 추가
                        break;

                    case R.id.btnModify:
                        final EditText editText = new EditText(getApplicationContext());
                        new AlertDialog.Builder(MainActivity.this)
                            .setTitle("리스트 아이템 수정")
                            .setMessage("현재 데이터 : " + arrayList.get(checkedIndex))
                            .setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setView(editText)
                            .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    arrayList.set(checkedIndex, editText.getText().toString());
                                }
                            })
                            .setNegativeButton("취소", null)
                            .show();
                        break;

                    case R.id.btnDelete:
                        arrayList.remove(checkedIndex);         // 선택 아이템 삭제
                        listView.clearChoices();                // listView 선택 초기화
                        break;
                }
                adapter.notifyDataSetChanged();         // listView 갱신
            }
        };

        btnAdd.setOnClickListener(listener);
        btnModify.setOnClickListener(listener);
        btnDelete.setOnClickListener(listener);
    }

    // ArrayList를 반환하는 함수
    private ArrayList<String> getArrayListData(){
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=0; i<5; i++){
            arrayList.add("리스트 데이터 "+(i+1));
        }
        return arrayList;
    }
}
