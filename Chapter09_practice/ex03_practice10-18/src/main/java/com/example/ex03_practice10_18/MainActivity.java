package com.example.ex03_practice10_18;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextNum1, editTextNum2;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNum1 = findViewById(R.id.editTextNum1);
        editTextNum2 = findViewById(R.id.editTextNum2);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActicity.class);
                intent.putExtra("Num1", Integer.parseInt(editTextNum1.getText().toString()));
                intent.putExtra("Num2", Integer.parseInt(editTextNum2.getText().toString()));
                startActivityForResult(intent, 0);
            }
        });
    }

    // SecondActivity에서 setResult()로 resultCode와 인텐트를 돌려주면 실행되는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            int hap = data.getIntExtra("Hap", 0);
            Toast.makeText(this, "합계: " + hap, Toast.LENGTH_SHORT).show();
        }
    }
}
