package com.example.sdcardtest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button sdWrite, sdRead, mkdirBtn, rmdirBtn, showListBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sdWrite = findViewById(R.id.btnWrite);
        sdRead = findViewById(R.id.btnRead);
        textView = findViewById(R.id.textView);

        // 해당 애플리케이션이 외부 파일에 액세스할 수 있도록 코딩
        // SDK ver < 23 이면 해당 메소드는 필요가 없음.
        // requestPermissions를 사용할 때 여러 종류의 퍼미션을 요청하려면 배열로 처리
        // 아래는 오류를 야기할 수 있는 코드
        // requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MODE_PRIVATE);
        // requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permissions, MODE_PRIVATE);

        sdWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    FileOutputStream outFs = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/sd_text2.txt");
                    String str = textView.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        sdRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream inFs = new FileInputStream("/sdcard/sd_text.txt");
                    byte[] txt = new byte[inFs.available()];
                    inFs.read(txt);
                    String str = new String(txt);
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                    inFs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}