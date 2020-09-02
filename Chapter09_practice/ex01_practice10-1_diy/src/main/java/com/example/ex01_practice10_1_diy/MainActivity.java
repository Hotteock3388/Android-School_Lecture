package com.example.ex01_practice10_1_diy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rbSecondActivity:
                        intent = new Intent(getApplicationContext(), SecondActivity.class);
                        break;
                    case R.id.rbThirdActivity:
                        intent = new Intent(getApplicationContext(), ThirdActivity.class);
                        break;
                }
            }
        });

        Button btnNewActivity = findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent != null)
                    startActivity(intent);
                else
                    Toast.makeText(MainActivity.this, "액티비티를 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
