package com.example.ex05_practice10_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnDial, btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDial = findViewById(R.id.btnDial);
        btnFinish = findViewById(R.id.btnFinish);

        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:01055873586");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // 액티비티가 화면에 나타날 때, 메뉴 구성을 위해 호출되는 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // xml에서 menu를 구성할 수 있는 객체
        MenuInflater menuInflater = getMenuInflater();

        // xml을 이용해 menu 구성
        menuInflater.inflate(R.menu.menu1, menu);

        return true;
    }

    // 옵션 메뉴를 선택하면 호출되는 메서드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 대화상자 띄우기
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("제목입니다")
                .setMessage("내용입니다")
                .setIcon(R.mipmap.ic_launcher)
                .setNeutralButton("Neutral", null)
                .setNegativeButton("Negative", null)
                .setPositiveButton("Positivie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "BUTTON_POSITIVE", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();


        return super.onOptionsItemSelected(item);
    }
}
