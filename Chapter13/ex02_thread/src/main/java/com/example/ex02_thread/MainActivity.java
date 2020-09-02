package com.example.ex02_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar, progressBar1, progressBar2;
    Button btnInc, btnDec, btnThread;
    TextView textView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region 참조변수에 View 객체 연결
        progressBar = findViewById(R.id.progressBar);
        btnInc = findViewById(R.id.btnInc);
        btnDec = findViewById(R.id.btnDec);
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        btnThread = findViewById(R.id.btnThread);
        //endregion

        //region 10증가/감소 버튼 이벤트 설정
        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // progress 증가
                progressBar.incrementProgressBy(10);
            }
        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // progress 증가
                progressBar.incrementProgressBy(-10);
            }
        });
        //endregion

        // seekbar를 조작하면 TextView에 "진행률 00%" 문자열 표시
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.format("진행률 : %d %s", progress, "%"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //스레드 시작 버튼 클릭 이벤트 설정 ProgressBar1, 2는 동작하지 않는다.
        //click_btnThreadDoNotUseThread();

        //스레드 시작 버튼 클릭 이벤트 설정 Thread를 이용해 ProgressBar1, 2 모두 동작
        click_btnThreadUseThread();

    }

    private void click_btnThreadDoNotUseThread(){
        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<100; i++){
                    progressBar1.setProgress(progressBar1.getProgress()+2);
                    progressBar2.setProgress(progressBar2.getProgress()+1);
                    SystemClock.sleep(100);
                }
            }
        });
    }

    private void click_btnThreadUseThread(){
        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        for(int i=progressBar1.getProgress(); i<100; i+=2){
                            progressBar1.setProgress(progressBar1.getProgress()+2);
                            SystemClock.sleep(100);
                        }
                    }
                }.start();

                new Thread(){
                    @Override
                    public void run() {
                        for(int i=progressBar2.getProgress(); i<100; i++){
                            progressBar2.setProgress(progressBar2.getProgress()+1);
                            SystemClock.sleep(100);
                        }
                    }
                }.start();

            }
        });
    }
}
