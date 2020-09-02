package com.example.ex04_listview03_addmodifydelete2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
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
                android.R.layout.simple_list_item_multiple_choice,
                arrayList);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
                final SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();   // 체크된 아이템 인덱스 저장
                int count = adapter.getCount();               // 아이템 개수

                switch (v.getId()){
                    case R.id.btnAdd:
                        arrayList.add("리스트 데이터"+(count+1));     // 아이템 추가
                        break;

                    case R.id.btnModify:
                        final EditText editText = new EditText(getApplicationContext());
                        for(int i=0; i<count; i++){
                            if(checkedItemPositions.size() > 1){
                                Log.d("checkedItemPositions", "하나만 선택하세요.");
//                                listView.clearChoices();    // 모든 체크박스 해제
                            }else{
                                final int index = i;
                                new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("리스트 아이템 수정")
                                    .setMessage("현재 데이터 : " + arrayList.get(i))
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setCancelable(false)
                                    .setView(editText)
                                    .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            arrayList.set(index, editText.getText().toString());
                                        }
                                    })
                                    .setNegativeButton("취소", null)
                                    .show();
                            }
                        }

                        break;

                    case R.id.btnDelete:
                        for(int i=count-1; i>=0; i--){          // remove() 함수를 호출하면 아이템이 삭제되면서 삭제 위치의 뒤에 있던 아이템들의 position이 앞으로 이동하게 되고 아이템의 갯수가 줄어듭니다. 그러면 당연히 의도하지 않은 아이템이 삭제되거나, for 루프가 줄어든 아이템 갯수보다 많이 실행되는 문제가 발생
                            if(checkedItemPositions.get(i))
                                arrayList.remove(i);
                        }

                        break;
                }
                listView.clearChoices();                // listView 선택 상태 초기화
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
