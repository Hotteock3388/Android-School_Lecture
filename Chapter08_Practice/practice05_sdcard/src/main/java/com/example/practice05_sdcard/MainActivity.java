package com.example.practice05_sdcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    // 참조 변수
    Button btnRead, btnWrite;
    EditText editText;
    TextView textView;

    // 권한 목록 배열
    String[] permissionList = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    File appSpecificExternalDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region 참조변수에 객체 할당
        btnRead = findViewById(R.id.btnRead);
        btnWrite = findViewById(R.id.btnWrite);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        //endregion

        //권한 부여 상태 체크 및 요청
        checkPermissions();

        //region 외부 저장소의 사용 가능 여부 확인
        if(isExternalStorageWritable()){
            Toast.makeText(this, "외부 저장소 읽기/쓰기 가능", Toast.LENGTH_SHORT).show(); 
        }else if(isExternalStorageReadable()){
            Toast.makeText(this, "외부 저장소 읽기만 가능", Toast.LENGTH_SHORT).show();
        }
        //endregion

        //region 외부 저장소의 앱별 파일에 액세스하려면 getExternalFilesDir() 호출
        // getExternalFilesDir()은 사용자가 앱을 제거할 때 정리되는 디렉터리를 만든다.
        File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
        File primaryExternalStorage = externalStorageVolumes[0];                             // 배열의 첫 번째 요소는 기본 외부 저장소 볼륨
        Log.d("MainActivity", String.valueOf(primaryExternalStorage.isFile()));         // false
        Log.d("MainActivity", String.valueOf(primaryExternalStorage.isDirectory()));    // true
        Log.d("MainActivity", primaryExternalStorage.getName());                        // files
        Log.d("MainActivity", primaryExternalStorage.getAbsolutePath());                // /storage/emulated/0/Android/data/com.example.practice05_sdcard/files
        Log.d("MainActivity", primaryExternalStorage.getPath());                        // /storage/emulated/0/Android/data/com.example.practice05_sdcard/files
        Log.d("MainActivity", String.valueOf(primaryExternalStorage.canRead()));        // true
        Log.d("MainActivity", String.valueOf(primaryExternalStorage.canWrite()));       // true
        //endregion

        //region 외부 저장소 앱별 비공개 파일 작성
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 외부 저장장치의 비공개(앱전용) 디렉토리에 새로운 디렉토리 생성
                // /storage/emulated/0/Android/data/com.example.practice05_sdcard/files + "새로운 디렉토리"
                // getExternalFilesDir()를 호출하고 원하는 디렉터리 유형을 나타내는 String을 전달하여 디렉터리를 획득할 수 있다.
                appSpecificExternalDir = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "myDocuments");
                Log.d("MainActivity", appSpecificExternalDir.getAbsolutePath());
                if (!appSpecificExternalDir.mkdirs()) {
                    Log.d("MainActivity", "myDocuments Directory not created");
                }

                // 파일 생성 및 데이터 기록
                File myDocumentFile1 = new File(appSpecificExternalDir, "document1.txt");
                try(FileOutputStream fos = new FileOutputStream(myDocumentFile1, false)) {
                    fos.write(editText.getText().toString().getBytes());
                    editText.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion

        /*//region 외부 저장소의 데이터 읽어오기1
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try(FileInputStream fis = new FileInputStream(appSpecificExternalDir.getAbsolutePath()+"/document1.txt")) {
                    int i;
                    String str = "";
                    while((i = fis.read()) != -1){      // read() 메서드로 파일을 읽는 경우 파일의 끝에 도달하면 -1을 반환한다.
                        str += (char)i;
                    }
                    textView.setText(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/

        //region 외부 저장소의 데이터 읽어오기2
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try(FileReader fr = new FileReader(appSpecificExternalDir.getAbsolutePath()+"/document1.txt")) {
                    int i;
                    String str = "";
                    while((i = fr.read()) != -1){      // read() 메서드로 파일을 읽는 경우 파일의 끝에 도달하면 -1을 반환한다.
                        str += (char)i;
                    }
                    textView.setText(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //endregion
    }

    private void checkPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "WRITE_EXTERNAL_STORAGE 권한 있음", Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(this, permissionList, 0);
        }

        // 권한이 여러개인 경우 반복적으로 체크
        /* // 권한 확인(PERMISSION_GRANTED 또는 PERMISSION_DENIED 반환)
        for(String permission : permissionList){
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, permission+"권한 없음", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, permission+"권한 있음", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPrivateDocumentsStorageDir(Context context, String documentsName) {
        // Get the directory for the app's private documents directory.
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), documentsName);
        if (!file.mkdirs()) {
            Toast.makeText(context, "Directory not created", Toast.LENGTH_SHORT).show();
        }
        return file;
    }
}
