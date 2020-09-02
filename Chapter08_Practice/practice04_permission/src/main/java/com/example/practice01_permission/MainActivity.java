package com.example.practice01_permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnVIBRATE;
    TextView textView;

    //region 체크할 권한 목록
    String[] permissionList = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS
    };
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVIBRATE = findViewById(R.id.btnVIBRATE);
        textView = findViewById(R.id.textView);

        btnVIBRATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(1000);
            }
        });

        // permissionList의 권한 요청
        ActivityCompat.requestPermissions(this, permissionList, 0);
    }

    // 권한 요청 후 호출되는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==0 && grantResults.length>0){

            String strGrantedList="", strDeniedList="";
            for(int i=0; i<permissions.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    strGrantedList += permissions[i]+'\n';
                }else if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    strDeniedList += permissions[i]+'\n';
                }
            }
            textView.setText("허용된 권한 목록\n"+strGrantedList+'\n');
            textView.append("거부된 권한 목록\n"+strDeniedList);
        }
    }
}
