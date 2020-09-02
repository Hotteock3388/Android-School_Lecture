package com.example.exercise01_optionmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
    }

    // 액티비티가 화면에 나타날 때, 메뉴 구성을 위해 호출되는 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // xml에서 menu를 구성할 수 있는 객체
        MenuInflater menuInflater = getMenuInflater();

        // xml을 이용해 menu를 구성
        menuInflater.inflate(R.menu.option_menu, menu);
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Java 코드 추가 메뉴");
        return true;
    }

    // 옵션 메뉴를 선택하면 호출되는 메서드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 사용자가 선택한 item의 id 가져오기
        int getItemId = item.getItemId();

        switch (getItemId){
            case R.id.item1:
                textView.setText("메뉴1 선택");
                break;
            case R.id.item2:
                textView.setText("메뉴2 선택");
                break;
            case R.id.item3:
                textView.setText("메뉴3 선택");
                break;
            case R.id.subItem1:
                textView.setText("서브메뉴1 선택");
                break;
            case R.id.subItem2:
                textView.setText("서브메뉴2 선택");
                break;
            case Menu.FIRST:
                textView.setText("Java 코드 추가 메뉴 선택");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
