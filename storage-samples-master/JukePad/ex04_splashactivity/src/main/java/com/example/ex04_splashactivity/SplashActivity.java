package com.example.ex04_splashactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** 액티비티가 뜨면서 ‘onCreate’ 메소드가 실행되는데, 그러면서 바로 넘어가고자 하는 액티비티의 intent 인스턴스를 생성해서 넘겨버리는 구조
         좀 더 시간을 끌거나 로딩해서 넘겨줘야하는 부분이 필요하다면, 11번 line에 있는 ‘startActivity()’ 부분이 시작되기 전 부분에 구현한다.
         */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}
