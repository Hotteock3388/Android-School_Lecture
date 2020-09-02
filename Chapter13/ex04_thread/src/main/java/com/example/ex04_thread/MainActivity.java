package com.example.ex04_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2;
    Button btnGetTime;
    boolean isRunning = true;   // 서브 스레드 종료 컨트롤

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

        //region 100ms 마다 현재 시간을 출력한다. -> Main Thread가 이 반복문 처리 때문에 아무것도 못하게 된다. (UI 출력도 안되기 때문에 화면에 아무것도 안 나옴)
//        while(true){
//            SystemClock.sleep(100);
//            SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
//            textView2.setText("버튼 클릭 : " + sdf.format(System.currentTimeMillis()));
//        }
        //endregion

        // SubThread 객체를 생성하여 실행
        SubThread subThread = new SubThread();
        subThread.start();
    }

    private class SubThread extends Thread {
        @Override
        public void run() {
            while(isRunning){
                SystemClock.sleep(100);
//                SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
//                Log.d("MyThread", sdf.format(System.currentTimeMillis()));
                Log.d("SubThread", String.valueOf(System.currentTimeMillis()));

                // 안드로이드 7.0 이하의 버전에서는 개발자 코드에서 UI 요소를 접근하면 오류가 발생한다. 8.0 이상 부터는 가능
                textView2.setText("SubThread : " + System.currentTimeMillis());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;  // 서브 스레드 종료
    }
}
