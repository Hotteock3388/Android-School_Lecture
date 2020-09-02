package com.example.suhang03_adapterview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    ListView listView;
    Button btnAdd, btnModify, btnDelete;
    ArrayAdapter<String> adapter;

    // 리스트뷰에 표시할 문자열을 저장할 ArrayList 생성 및 초기 데이터 저장
    ArrayList<String> arrayList = getArrayListData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("2101홍길동");

        //region 참조변수에 객체 연결
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnModify = findViewById(R.id.btnModify);
        btnDelete = findViewById(R.id.btnDelete);
        //endregion

         //각 항목을 표시할 View, 데이터 가진 ArrayAdapter 객체 생성
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_single_choice,
                arrayList);

        // choiceMode를 CHOICE_MODE_SINGLE로 설정하면 목록에서 하나의 항목을 선택된 상태로 만들 수 있습니다.
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

        // ListView에 아이템 추가, 수정, 삭제 이벤트 리스너 연결
        btnAdd.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    // ListView에 표시될 초기 문자열을 ArrayList에 저장하여 반환하는 함수
    private ArrayList<String> getArrayListData(){
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=0; i<5; i++){
            arrayList.add("리스트 데이터 "+(i+1));
        }
        return arrayList;
    }

    // 추가, 수정, 삭제 버튼 이벤트 처리 코드
    @Override
    public void onClick(View v) {
        /* 현재 체크 된 항목을 반환한다. 선택 모드가 CHOICE_MODE_SINGLE로 설정된 경우에만 결과가 유효함. */
        final int checkedIndex = listView.getCheckedItemPosition();

        switch (v.getId()){
            case R.id.btnAdd:
                int count = adapter.getCount();               // 아이템 개수
                arrayList.add("리스트 데이터 "+(count+1));     // 아이템 추가
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
        // adapter객체에게 데이터가 변경되었음을 알려 ListView의 화면 갱신
        adapter.notifyDataSetChanged();
    }
}
