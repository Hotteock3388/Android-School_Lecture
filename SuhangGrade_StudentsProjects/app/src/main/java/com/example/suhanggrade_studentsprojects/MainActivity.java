package com.example.suhanggrade_studentsprojects;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textViewSelected;
    ListView listView;
    Button btnAdd, btnMotify, btnDelete;
    int selectedNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("2213은종엽");

        textViewSelected = findViewById(R.id.textViewSelected);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnMotify = findViewById(R.id.btnModify);
        btnDelete = findViewById(R.id.btnDelete);

        final ArrayList<String> dataList = new ArrayList<>();
        for (int i=1; i<=5; i++) {
            dataList.add("리스트 데이터 "+i);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_single_choice, dataList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textViewSelected.setText(dataList.get(position));
                selectedNum = position;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.add("리스트 에어터 "+(dataList.size()+1));
                adapter.notifyDataSetChanged();
            }
        });

        btnMotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedNum == -1) {
                    Toast.makeText(MainActivity.this, "항목을 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                final EditText editText = new EditText(getApplicationContext());
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("리스트 아이템 수정")
                        .setMessage("현재 데이터 : "+dataList.get(selectedNum))
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setCancelable(false)
                        .setView(editText)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataList.set(selectedNum, editText.getText().toString());
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedNum == -1) {
                    Toast.makeText(MainActivity.this, "항목을 선택해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                dataList.remove(selectedNum);
                adapter.notifyDataSetChanged();
                listView.setItemChecked(selectedNum, false);
                textViewSelected.setText("Selected Item Content");
                selectedNum = -1;
            }
        });
    }
}