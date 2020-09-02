package com.example.chapter13;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                File filepath = Environment.getExternalStorageDirectory();
            }
        });

    }
}
