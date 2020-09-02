package com.example.ex04_diy10_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActicity extends AppCompatActivity {

    int num1, num2;
    double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnReturn = findViewById(R.id.btnReturn);

        // MainActivity 에서 보낸 Intent 객체 얻어서 필요한 데이터 꺼내기
        Intent inIntent = getIntent();
        num1 = inIntent.getIntExtra("Num1", 0);
        num2 = inIntent.getIntExtra("Num2", 0);

        //region inIntent에 담긴 연산자에 따라 result 에 계산 결과 저장
        switch(inIntent.getIntExtra("Operator", 0)){
            case R.id.rbAdd:
                result = num1 + num2;
                break;
            case R.id.rbSubtract:
                result = num1 - num2;
                break;
            case R.id.rbMultiply:
                result = num1 * num2;
                break;
            case R.id.rbDivide:
                result = (double)num1 / num2;
                break;
        }
        //endregion

        // 연산 결과를 Intent에 담아서 MainActivity로 보내기
        btnReturn.setOnClickListener((v) -> {
                Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
                outIntent.putExtra("Result", result);
                setResult(RESULT_OK, outIntent);
                finish();
        });
    }
}
