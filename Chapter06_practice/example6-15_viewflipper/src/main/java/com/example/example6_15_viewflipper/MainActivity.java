package com.example.example6_15_viewflipper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    // 참조 변수
    Button btnPrev, btnNext;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위젯 연결
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        viewFlipper = findViewById(R.id.viewFlipper);

        // 이전화면 버튼 클랙 이벤트 처리
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewFlipper.showPrevious();
                viewFlipper.setFlipInterval(1000);
                viewFlipper.startFlipping();
            }
        });

        // 다음화면 버튼 클릭 이벤트 처리
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewFlipper.showNext();
                viewFlipper.stopFlipping();
            }
        });
    }
}
