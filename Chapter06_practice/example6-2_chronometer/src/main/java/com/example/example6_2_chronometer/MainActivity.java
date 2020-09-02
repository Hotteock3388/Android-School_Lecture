package com.example.example6_2_chronometer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    // 1. 위젯 참조변수
    Chronometer timer;
    Button startBtn, stopBtn, resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. 위젯 객체를 참조변수에 연결
        timer = findViewById(R.id.timer);
        startBtn = findViewById(R.id.startBtn);
        stopBtn = findViewById(R.id.stopBtn);
        resetBtn = findViewById(R.id.resetBtn);

        //region 3. 버튼 클릭 이벤트 처리
       startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.setBase(SystemClock.elapsedRealtime());  // timer의 기초 시간을 현재 시간으로 변경
                timer.start();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.stop();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.setBase(SystemClock.elapsedRealtime());
            }
        });
        //endregion

        // 4. 1초 마다 발생하는 Tick 이벤트 처리
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                Log.d("realTime", String.valueOf(SystemClock.elapsedRealtime()));
            }
        });
    }
}
