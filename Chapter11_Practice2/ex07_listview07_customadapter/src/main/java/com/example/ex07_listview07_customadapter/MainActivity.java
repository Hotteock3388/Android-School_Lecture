package com.example.ex07_listview07_customadapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;
    Button btnAdd;

    ArrayList<ItemVO> dataList = new ArrayList<ItemVO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);

        // dataList에 초기 데이터 추가
        dataList.add(new ItemVO("doc", "Document 1", "Sample Data"));
        dataList.add(new ItemVO("img", "Image 1", "Sample Data"));
        dataList.add(new ItemVO("file", "File 1", "Sample Data"));

        // 사용자 정의 Adapter 객체 생성
        final CustomAdapter adapter = new CustomAdapter(this, R.layout.row, dataList);

        // 리스트뷰에 어댑터 연결
        listView.setAdapter(adapter);

        // 리스트뷰의 아이템 클릭 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(((ItemVO)adapter.getItem(position)).getTitleStr());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View myView = inflater.inflate(R.layout.alertdialog_add_item, null);
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("리스트 아이템 추가")
                    .setIcon(R.mipmap.ic_launcher)
                    .setCancelable(false)
                    .setView(myView)
                    .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 임시 저장 변수
                            String type=null, title, contents;

                            // 라디오 버튼과 EditText의 입력 결과 가져와 리스트뷰에 아이템 추가하기
                            int checkedId = ((RadioGroup)myView.findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
                            switch(checkedId){
                                case R.id.rbDoc:
                                    type = "doc";
                                    Toast.makeText(MainActivity.this, "Document", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.rbImg:
                                    type = "img";
                                    Toast.makeText(MainActivity.this, "Image", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.rbFile:
                                    type = "file";
                                    Toast.makeText(MainActivity.this, "File", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                            title = ((EditText)myView.findViewById(R.id.editTextTitle)).getText().toString();
                            contents = ((EditText)myView.findViewById(R.id.editTextContents)).getText().toString();

                            dataList.add(new ItemVO(type, title, contents));
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();
            }
        });

    }
}
