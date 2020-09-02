package com.example.ex03_thread;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar1, progressBar2;
    Button btnThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region 참조변수에 View 객체 연결
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        btnThread = findViewById(R.id.btnThread);
        //endregion

        //region 1. 스레드 시작 버튼 클릭 이벤트 설정 ProgressBar1, 2는 동작하지 않는다.
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
        //endregion

        //region 2. Thread를 상속한 클래스의 인스턴스를 이용한 처리
        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressThread pt1 = new ProgressThread(progressBar1, 2);
                pt1.start();

                ProgressThread pt2 = new ProgressThread(progressBar2, 1);
                pt2.start();
            }
        });
        //endregion

        //region 3. Runnable을 구현한 클래스의 인스턴스를 이용한 처리
        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressRun pr1 = new ProgressRun(progressBar1, 4);
                new Thread(pr1).start();

                ProgressRun pr2 = new ProgressRun(progressBar2, 2);
                new Thread(pr2).start();
            }
        });
        //endregion
    }

    // Thread를 상속한 클래스
    private class ProgressThread extends Thread{
        ProgressBar pb;
        int increaseValue;
        public ProgressThread(ProgressBar pb, int increaseValue){
            this.pb = pb;
            this.increaseValue = increaseValue;
        }

        @Override
        public void run() {
            for(int i=pb.getProgress(); i<100; i+=increaseValue){
                pb.setProgress(pb.getProgress()+increaseValue);
                SystemClock.sleep(100);
            }
        }
    }

    // Runnable 인터페이스를 구현한 클래스
    private class ProgressRun implements Runnable{
        ProgressBar pb;
        int increaseValue;

        public ProgressRun(ProgressBar pb, int increaseValue) {
            this.pb = pb;
            this.increaseValue = increaseValue;
        }

        @Override
        public void run() {
            for(int i=pb.getProgress(); i<100; i+=increaseValue){
                pb.setProgress(pb.getProgress()+increaseValue);
                SystemClock.sleep(100);
            }
        }
    }

    // 익명의 Thread 객체를 이용한 처리
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
