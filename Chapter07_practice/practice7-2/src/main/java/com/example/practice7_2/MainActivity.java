package com.example.practice7_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout baseLayout;
    Button btnChangeColor, btnChangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("배경색 바꾸기(컨텍스트 메뉴");

        baseLayout = findViewById(R.id.baseLayout);
        btnChangeColor = findViewById(R.id.btnChangeColor);
        btnChangeButton = findViewById(R.id.btnChangeButton);

        registerForContextMenu(btnChangeColor);
        registerForContextMenu(btnChangeButton);
    }

    // 컨텍스트가 있는 버튼을 롱클릭했을 때, 컨텍스트 메뉴를 생성하는 메소드
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        switch (v.getId()){
            case R.id.btnChangeColor:
                menu.setHeaderTitle("배경색 변경");
                inflater.inflate(R.menu.menu1, menu);
                break;
            case R.id.btnChangeButton:
                menu.setHeaderTitle("버튼 변경");
                inflater.inflate(R.menu.menu2, menu);
                break;
        }
    }

    // 컨텍스트 메뉴 선택에 따른 이벤트 처리
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemRed:
                baseLayout.setBackgroundColor(Color.RED);
                break;
            case R.id.itemGreen:
                baseLayout.setBackgroundColor(Color.GREEN);
                break;
            case R.id.itemBlue:
                baseLayout.setBackgroundColor(Color.BLUE);
                break;
            case R.id.subRotate:
                btnChangeButton.setRotation(btnChangeButton.getRotation()+45);
                break;
            case R.id.subSizeUp:
                Log.d("MainActivity", String.valueOf(btnChangeButton.getScaleX()));
                btnChangeButton.setScaleX(btnChangeButton.getScaleX()*2);
                btnChangeButton.setScaleY(btnChangeButton.getScaleY()*2);
                break;
        }

        return super.onContextItemSelected(item);
    }
}
