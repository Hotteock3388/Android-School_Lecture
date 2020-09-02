package com.example.ex04_practice10_20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnDial, btnWeb, btnGoogleMap, btnSearch, btnSms, btnPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDial = findViewById(R.id.btnDial);
        btnWeb = findViewById(R.id.btnWeb);
        btnGoogleMap = findViewById(R.id.btnGoogleMap);
        btnSearch = findViewById(R.id.btnSearch);
        btnSms = findViewById(R.id.btnSms);
        btnPhoto = findViewById(R.id.btnPhoto);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri;
                Intent intent;
                switch (v.getId()){
                    case R.id.btnDial:
                        uri = Uri.parse("tel:01055873586");
                        intent = new Intent(Intent.ACTION_DIAL, uri);   //  약속된 Action 지정하여, 안드로이드에서 제공하는 기존 응용 프로그램 실행
                        startActivity(intent);
                        break;
                        // 전화번호부 보여주기
//                    case R.id.btnDial:
//                        uri = Uri.parse("content://contacts/people");
//                        intent = new Intent(Intent.ACTION_VIEW, uri);
//                        startActivity(intent);
//                        break;
                    case R.id.btnWeb:
                        uri = Uri.parse("http://www.naver.com");
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case R.id.btnGoogleMap:
                        uri = Uri.parse("https://goo.gl/maps/g7vQ5Yq2aPmPHFQQ6");
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case R.id.btnSearch:
                        intent = new Intent(Intent.ACTION_WEB_SEARCH);
                        intent.putExtra(SearchManager.QUERY, "광주소프트웨어마이스터고");
                        startActivity(intent);
                        break;
                    case R.id.btnSms:
                        intent = new Intent(Intent.ACTION_SENDTO);
                        intent.putExtra("sms_body", "안녕하세요?");
                        intent.setData(Uri.parse("smsto:" + Uri.encode("010-5587-3586")));
                        startActivity(intent);
                        break;
                    case R.id.btnPhoto:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(intent);
                        break;
                }
            }
        };

        btnDial.setOnClickListener(listener);
        btnWeb.setOnClickListener(listener);
        btnGoogleMap.setOnClickListener(listener);
        btnSearch.setOnClickListener(listener);
        btnSms.setOnClickListener(listener);
        btnPhoto.setOnClickListener(listener);

        findViewById(R.id.btnSecondActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.ex04_practice10_20.ACTION_VIEW");
                startActivity(intent);  // 이 함수가 Intent를 발생시킬 때, 자동으로 android.intent.category.DEFAULT를 추가함.
            }
        });
    }
}
