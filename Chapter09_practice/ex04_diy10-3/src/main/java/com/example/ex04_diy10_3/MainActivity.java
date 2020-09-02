package com.example.ex04_diy10_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextNum1, editTextNum2;
    Button btnCalculate;
    RadioGroup radioGroup;
    int operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNum1 = findViewById(R.id.editTextNum1);
        editTextNum2 = findViewById(R.id.editTextNum2);
        btnCalculate = findViewById(R.id.btnCalculate);
        radioGroup = findViewById(R.id.radioGroup);

        //region 선택된 라디오 버튼의 id를 opertor 변수에 저장
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbAdd:
                        operator = R.id.rbAdd;
                        break;
                    case R.id.rbSubtract:
                        operator = R.id.rbSubtract;
                        break;
                    case R.id.rbMultiply:
                        operator = R.id.rbMultiply;
                        break;
                    case R.id.rbDivide:
                        operator = R.id.rbDivide;
                        break;
                }
            }
        });
        //endregion

        //region 계산하기 버튼 클릭 이벤트 처리
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(MainActivity.this, "연산자를 선택하세요!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), SecondActicity.class);
                intent.putExtra("Num1", Integer.parseInt(editTextNum1.getText().toString()));
                intent.putExtra("Num2", Integer.parseInt(editTextNum2.getText().toString()));
                intent.putExtra("Operator", operator);
                startActivityForResult(intent, 0);
            }
        });
        //endregion
    }

    // SecondActivity에서 setResult()로 resultCode와 인텐트를 돌려주면 실행되는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            double result = data.getDoubleExtra("Result", 0);
            Toast.makeText(this, "합계: " + result, Toast.LENGTH_SHORT).show();
        }
    }
}
