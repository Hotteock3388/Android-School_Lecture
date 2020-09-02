package com.example.ex03_practice10_18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnReturn = findViewById(R.id.btnReturn);

        // MainActivity 에서 보낸 Intent 객체 얻어서 필요한 데이터 꺼내기
        Intent inIntent = getIntent();
        final int hap = inIntent.getIntExtra("Num1", 0) + inIntent.getIntExtra("Num2", 0);

        // MainActivity로 돌려보낼 데이터를 Intent에 담아서 보내기
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
                outIntent.putExtra("Hap", hap);
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });
    }
}
