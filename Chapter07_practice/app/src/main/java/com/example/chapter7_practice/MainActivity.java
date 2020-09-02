package com.example.chapter7_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    Button btnLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.mainLayout);
        btnLayoutInflater = findViewById(R.id.btnLayoutInflater);

        btnLayoutInflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layoutView = inflater.inflate(R.layout.sample_layout, null);
                linearLayout.addView(layoutView);
            }
        });

    }
}
