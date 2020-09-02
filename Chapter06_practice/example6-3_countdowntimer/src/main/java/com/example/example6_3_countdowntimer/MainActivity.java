package com.example.example6_3_countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 1. 참조 변수 선언
    TextView textViewTimer;
    Button btnStart, btnStop, btnPauseRestart;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. 참조변수에 위젯 연결
        textViewTimer = findViewById(R.id.textView);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnPauseRestart = findViewById(R.id.btnPauseRestart);

        countDownTimer(30*1000, 1000);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
            }
        });

        btnPauseRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnPauseRestart.getText().equals("PAUSE")){
                   btnPauseRestart.setText("RESTART");
                }else{
                    btnPauseRestart.setText("PAUSE");
                }
            }
        });
    }

    private void countDownTimer(long millisInFuture, long countDownInterval){
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "CountDown Finished", Toast.LENGTH_SHORT).show();
            }
        };
    }



}
