package com.example.ex05_handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2;
    Button btnGetTime;
    boolean isRunning = true;   // 서브 스레드 종료 컨트롤
    Handler handler = null;     // Main Thread에게 스레드 처리 부탁

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textViewTime1);
        textView2 = findViewById(R.id.textViewTime2);
        btnGetTime = findViewById(R.id.btnGetTime);

        btnGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
                //textView1.setText("버튼 클릭 : " + sdf.format(System.currentTimeMillis()));
                textView1.setText("버튼 클릭 : " + System.currentTimeMillis());
            }
        });

        // SubThread 객체를 생성
        SubThread subThread = new SubThread();

        handler = new Handler();
        // Handler를 통해 작업을 요청한다. (메인 스레드가 처리)
//        handler.post(subThread);
        handler.postDelayed(subThread, 100);

    }

    private class SubThread extends Thread {
        @Override
        public void run() {
            while(isRunning){
                // 안드로이드 7.0 이하의 버전에서는 개발자 코드에서 UI 요소를 접근하면 오류가 발생한다. 8.0 이상 부터는 가능
                textView2.setText("SubThread : " + System.currentTimeMillis());

                // 현재 작업을 다시 요청
                handler.postDelayed(this, 100);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;  // 서브 스레드 종료
    }
}
