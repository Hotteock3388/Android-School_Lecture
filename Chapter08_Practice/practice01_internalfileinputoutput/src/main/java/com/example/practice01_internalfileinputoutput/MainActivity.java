package com.example.practice01_internalfileinputoutput;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    //region 참조변수 선언
    Button btnRead, btnWrite;
    EditText editText;
    TextView textView;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region 참조변수 객체 연결
        btnRead = findViewById(R.id.btnRead);
        btnWrite = findViewById(R.id.btnWrite);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        //endregion

        //region 내장 메모리 파일 입출력 처리1 (FileOutputStream, FileInputStream)
        // 파일에서 바이트 단위로 읽는 기반 스트림
        View.OnClickListener listener1 = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnWrite:
                        FileOutputStream fos = null;
                        try {
                            // 매개변수로 전달된 파일이 경로에 없으면 새로 생성한다.
//                            fos = new FileOutputStream(getFileStreamPath("file.txt"), true);
                            fos = openFileOutput("file.txt", Context.MODE_PRIVATE);   // openFileOutput() : 내부 저장소와 연결된 FileOutputStream 추출

                            String str = editText.getText().toString();
                            fos.write(str.getBytes());

                            Toast.makeText(MainActivity.this, "file.txt 생성됨.", Toast.LENGTH_SHORT).show();
                            editText.setText(null);         // EditText의 문자열 지우기
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                fos.close();            // 열린 스트림은 finally 블록에서 닫음.
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case R.id.btnRead:
                        FileInputStream fis = null;
                        try {
//                            fis = new FileInputStream(getFileStreamPath("file.txt"));
                            fis = openFileInput("file.txt");  // 내부 저장소와 연결된 FileInputStream 추출

//                            textView.setText(String.valueOf((char)fis.read()));   // 한문자만 읽어서 출력하기
                            byte[] txt = new byte[30];
                            fis.read(txt);                      // 30 byte만 저장됨.
                            String str = new String(txt);
                            textView.setText(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                fis.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        };
        //endregion

        //region 내장 메모리 파일 입출력 처리2 (OutputStreamWriter, InputStreamReader)
        // 파일에서 바이트 단위로 읽은 자료를 문자로 변환해 주는 보조 스트림
        View.OnClickListener listener2 = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnWrite:
                        try(OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("file.txt", Context.MODE_PRIVATE))) {
                            outputStreamWriter.write(editText.getText().toString());
                            Toast.makeText(MainActivity.this, "file.txt 생성됨.", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            editText.setText(null);
                        }
                        break;

                    case R.id.btnRead:
                        try(InputStreamReader isr = new InputStreamReader(openFileInput("file.txt"))) {
                            int i;
                            String str = "";
                            while((i = isr.read()) != -1){      // read() 메서드로 파일을 읽는 경우 파일의 끝에 도달하면 -1을 반환한다.
                                str += (char)i;
                            }
                            textView.setText(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        //endregion

        //region 내장 메모리 파일 입출력 처리3 (BufferedWriter, BufferedReader)
        // 문자를 읽을 때 배열을 제공하여 한꺼번에 읽고 쓸 수 있는 보조 스트림(8,192 바이트 배열 멤버 변수)
        View.OnClickListener listener3 = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnWrite:
                        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getFileStreamPath("file.txt")))) {
                            bufferedWriter.write(editText.getText().toString());
                            Toast.makeText(MainActivity.this, "file.txt 생성됨.", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            editText.setText(null);
                        }
                        break;

                    case R.id.btnRead:
                        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(getFileStreamPath("file.txt")))) {
                            int i;
                            String str = "";
                            while((i = bufferedReader.read()) != -1){      // read() 메서드로 파일을 읽는 경우 파일의 끝에 도달하면 -1을 반환한다.
                                str += (char)i;
                            }
                            textView.setText(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        //endregion

        btnWrite.setOnClickListener(listener2);
        btnRead.setOnClickListener(listener2);
    }
}
