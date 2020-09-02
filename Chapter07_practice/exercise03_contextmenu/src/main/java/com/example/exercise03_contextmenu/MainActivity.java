package com.example.exercise03_contextmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // 뷰에 컨텍스트 메뉴 설정
        registerForContextMenu(textView);
    }

    // 컨텍스트 메뉴가 설정된 뷰를 길게 누르면, 컨텍스트 메뉴 생성을 위해 호출되는 메소드(안드로이드 OS가 호출)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        // 사용자가 롱클릭한 뷰의 주소값을 얻어온다.
        int viewId = v.getId();

        switch (viewId){
            case R.id.textView:
                menu.setHeaderTitle("TextView의 컨텍스트 메뉴");
                inflater.inflate(R.menu.textview_menu, menu);
                break;
        }
    }


    // 컨텍스트 메뉴의 항목을 선택하면 호출되는 메소드
    // 사용자가 선택한 메뉴 항목의 id값으로 구분
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // 사용자가 선택한 메뉴 항목의 id값을 가져온다.
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.textView_Item1:
                textView.setText("TextView의 아이템1을 선택함.");
                break;
            case R.id.textView_Item2:
                textView.setText("TextView의 아이템2을 선택함.");
                break;
        }
        return super.onContextItemSelected(item);
    }
}
