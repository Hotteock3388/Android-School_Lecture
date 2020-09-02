package com.example.example6_1_textclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 1. 참조 변수 생성
    TextClock textClock;
    Button btnGetTime;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. 참조변수에 객체 연결
        textClock = findViewById(R.id.textClock);
        btnGetTime = findViewById(R.id.btnGetTime);
        textView = findViewById(R.id.textView);

        // 3. 버튼 클릭 이벤트 처리
        btnGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // textView에 textClock에서 가져온 시간 표시
                textView.setText("Time: "+textClock.getText());
            }
        });


    }
}
