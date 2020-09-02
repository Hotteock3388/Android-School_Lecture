package com.example.practice02_practice8_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //region 참조변수 선언
    DatePicker datePicker;
    EditText editText;
    Button btnWrite;
    String fileName;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setTitle("간단 일기장");

        //region 참조변수 객체 연결
        datePicker = findViewById(R.id.datePicker);
        editText = findViewById(R.id.editText);
        btnWrite = findViewById(R.id.btnWrite);
        //endregion

        //region Calendar 객체 생성(초기 상태 날짜 표시를 위한 년, 월, 일 세팅)
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //endregion

        readAndShowDiary(year, month, day);     // 앱이 처음 실행되었을 때, 일기가 존재하면 열기기

       // datePicker를 초기화(이벤트 처리 리스너 지정)
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                fileName = String.format("%d_%d_%d", year, monthOfYear+1, dayOfMonth);  // '연_월_일.txt' 파일 이름 생성
//                String str = readDiary(fileName);                                       // 현재 날짜 이름을 가진 파일 읽어들인다.
//                editText.setText(str);              // 파일에서 읽어온 텍스트를 화면에 표시
//                btnWrite.setEnabled(true);          // 버튼 활성화

                readAndShowDiary(year, monthOfYear, dayOfMonth);
            }
        });

        // 버튼을 클릭하면 파일을 생성하여 저장하는 메소드
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try(FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)) {
                    String str = editText.getText().toString();
                    fos.write(str.getBytes());
                    Toast.makeText(MainActivity.this, fileName+" 저장됨.", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 지정된 날짜 이름의 파일 읽고 화면에 출력하는 메소드
    private void readAndShowDiary(int year, int month, int day) {
        fileName = String.format("%d_%d_%d", year, month+1, day);  // '연_월_일.txt' 파일 이름 생성
        String str = readDiary3(fileName);                                       // 현재 날짜 이름을 가진 파일 읽어들인다.
        editText.setText(str);              // 파일에서 읽어온 텍스트를 화면에 표시
        btnWrite.setEnabled(true);          // 버튼 활성화
    }

    // 지정된 날짜 이름의 파일에서 텍스트 읽어 반환하는 메소드
    private String readDiary1(String fileName) {
        String diaryStr = null;
//        FileInputStream fis;                                  // 파일을 읽어오는 스트림
        try(FileInputStream fis = openFileInput(fileName)) {    // '연_월_일.txt' FileInputStream 생성, 파일이 없으면 IOException 발생
            byte[] txt = new byte[500];                         // byte[500] 배열 생성
            fis.read(txt);                                      // byte[500] 배열에 '연_월_일.txt' 파일 내용 읽어오기
            diaryStr = new String(txt).trim();                  // diaryStr에 읽어온 byte[]의 텍스트 저장(공백 제거)
            btnWrite.setText("수정하기");                       // 버튼의 텍스트를 "수정하기"로 변경
        } catch(IOException e){                                 // 읽어올 파일이 없어 IOException 이 발생한 경우
            editText.setHint("일기 없음");
            btnWrite.setText("새로 저장");                      // 버튼의 텍스트를 "새로 저장"으로 변경
        }
        return diaryStr;                                        // 읽어들인 문자열 반환, 없으면 null 반환
    }

    // 스트림을 끝까지 읽는 메소드
    private String readDiary2(String fileName) {
        String diaryStr = null;
        try(FileInputStream fis = openFileInput(fileName)) {  // '연_월_일.txt' FileInputStream 생성, 파일이 없으면 IOException 발생
            byte[] byteArray = new byte[fis.available()];
            for(int i=0; i<byteArray.length; i++){
                byteArray[i]=(byte)fis.read();
            }
//            fis.read(byteArray);

            diaryStr = new String(byteArray);
            btnWrite.setText("수정하기");           // 버튼의 텍스트를 "수정하기"로 변경
        } catch(IOException e){                     // 읽어올 파일이 없어 IOException 이 발생한 경우
            editText.setHint("일기 없음");
            btnWrite.setText("새로 저장");          // 버튼의 텍스트를 "새로 저장"으로 변경
        }
        return diaryStr;                            // 읽어들인 문자열 반환, 없으면 null 반환
    }

    // FileReader 사용
    private String readDiary3(String fileName) {
        String diaryStr = null;
        try(FileReader fr = new FileReader(getFileStreamPath(fileName))) {  // '연_월_일.txt' FileInputStream 생성, 파일이 없으면 IOException 발생
            int i;
            StringBuilder sb = new StringBuilder();
            while((i = fr.read()) != -1){               // read() 메서드로 파일을 읽는 경우 파일의 끝에 도달하면 -1을 반환한다.
                sb.append((char)i);
            }
            diaryStr = sb.toString();
            btnWrite.setText("수정하기");           // 버튼의 텍스트를 "수정하기"로 변경
        } catch(IOException e){                     // 읽어올 파일이 없어 IOException 이 발생한 경우
            editText.setHint("일기 없음");
            btnWrite.setText("새로 저장");          // 버튼의 텍스트를 "새로 저장"으로 변경
        }
        return diaryStr;                            // 읽어들인 문자열 반환, 없으면 null 반환
    }

    // BufferedReader 사용
    private String readDiary4(String fileName) {
        String diaryStr = null;
        try(BufferedReader br = new BufferedReader(new FileReader(getFileStreamPath(fileName)))) {  // '연_월_일.txt' FileInputStream 생성, 파일이 없으면 IOException 발생
            StringBuilder sb = new StringBuilder();
            int i;
            while((i=br.read())!=-1){
                sb.append((char)i);
            }
//            String line;
//            while((line = br.readLine()) != null){      // read() 메서드로 파일을 읽는 경우 파일의 끝에 도달하면 -1을 반환한다.
//                sb.append(line + "\n");
//            }
            diaryStr = sb.toString();
            btnWrite.setText("수정하기");           // 버튼의 텍스트를 "수정하기"로 변경
        } catch(IOException e){                     // 읽어올 파일이 없어 IOException 이 발생한 경우
            editText.setHint("일기 없음");
            btnWrite.setText("새로 저장");          // 버튼의 텍스트를 "새로 저장"으로 변경
        }
        return diaryStr;                            // 읽어들인 문자열 반환, 없으면 null 반환
    }
}
