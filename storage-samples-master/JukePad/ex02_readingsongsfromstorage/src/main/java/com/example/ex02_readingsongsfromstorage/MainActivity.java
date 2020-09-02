package com.example.ex02_readingsongsfromstorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<HashMap<String, String>> audioDataList;
    MediaPlayer mediaPlayer;

    //region 체크할 권한 목록
    String[] permissionList = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        audioDataList = readMediaStoreAudioFile();

        // permissionList의 권한 요청
        ActivityCompat.requestPermissions(this, permissionList, 0);

        String[] keys = {"name", "volume_name"};
        int[] ids = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, audioDataList, android.R.layout.simple_list_item_2, keys, ids);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri contentUri = ContentUris.withAppendedId(
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, Long.parseLong(audioDataList.get(position).get("id")));
                if(mediaPlayer!=null)
                    mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {
                    mediaPlayer.setDataSource(getApplicationContext(), contentUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });
    }

    private ArrayList<HashMap<String, String>> readMediaStoreAudioFile() {
        ArrayList<HashMap<String, String>> audioList = new ArrayList<HashMap<String, String>>();
        Uri externalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  // MediaStore.media-type.Media.EXTERNAL_CONTENT_URI

        // media-database-columns-to-retrieve
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.MIME_TYPE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.VOLUME_NAME,
                MediaStore.Audio.Media.DATA
        };

        String selection = MediaStore.Audio.Media.DISPLAY_NAME + " like ? ";
        String[] selectionArgs = new String[]{"%아이유%"};
        String sortOrder = MediaStore.Audio.Media.DATE_TAKEN + "DESC";

        /**
         실질적으로 쿼리하는 코드
         Uri: 찾고자하는 데이터의 Uri
         Projection: DB의 column과 같다. 결과로 받고 싶은 데이터의 종류
         Selection: DB의 where 키워드와 같다. 어떤 조건으로 필터링된 결과를 받을 때 사용
         Selection args: Selection과 함께 사용
         Sort order: 쿼리 결과 데이터를 sorting할 때 사용

         위의 조건으로 쿼리한 데이터가 있다면 Cursor로 결과를 순회(Loop)할 수 있다.
        */
        Cursor cursor = MainActivity.this.getContentResolver().query(
                externalUri,
                projection,
                null,
                null,
                null);

        // MediaStore.Audio 데이터가 없으면 return
        if (cursor == null || !cursor.moveToFirst()) {
            Log.e("cursor data", "cursor null or cursor is empty");
            return null;
        }

        /*do {
            String contentUrl = externalUri.toString() + "/" + cursor.getString(0);

            try {
                InputStream is = getContentResolver().openInputStream(Uri.parse(contentUrl));
                int data = 0;
                StringBuilder sb = new StringBuilder();

                while ((data = is.read()) != -1) {
                    sb.append((char) data);
                }

                is.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } while (cursor.moveToNext());*/

        if (cursor != null) {
            int cursorCount = cursor.getColumnCount();
            String[] columnNames = cursor.getColumnNames();
            while (cursor.moveToNext()) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", cursor.getString(0));
                hashMap.put("name", cursor.getString(1));
                hashMap.put("mime_type", cursor.getString(2));
                hashMap.put("album", cursor.getString(3));
                hashMap.put("artist", cursor.getString(4));
                hashMap.put("title", cursor.getString(5));
                hashMap.put("volume_name", cursor.getString(6));
                hashMap.put("data", cursor.getString(7));

                audioList.add(hashMap);
                Log.d("cursor data", "MediaStore.Audio.Media._ID : " + cursor.getString(0));
            }
            cursor.close();
        }

        return audioList;
    }

    private void saveFile() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, "video_1024.mp3");
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/*");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Audio.Media.IS_PENDING, 1);
        }

        ContentResolver contentResolver = getContentResolver();
        Uri item = contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);

        try {
            ParcelFileDescriptor pdf = contentResolver.openFileDescriptor(item, "w", null);

            if (pdf == null) {
                Log.d("asdf", "null");
            } else {
                String str = "heloo";
                byte[] strToByte = str.getBytes();
                FileOutputStream fos = new FileOutputStream(pdf.getFileDescriptor());
                fos.write(strToByte);
                fos.close();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.clear();
                    values.put(MediaStore.Audio.Media.IS_PENDING, 0);
                    contentResolver.update(item, values, null, null);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 권한 요청 후 호출되는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 0 && grantResults.length > 0){

            String strGrantedList="", strDeniedList="";
            for(int i=0; i<permissions.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    strGrantedList += permissions[i]+'\n';
                }else if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    strDeniedList += permissions[i]+'\n';
                }
            }
            Toast.makeText(MainActivity.this, strGrantedList, Toast.LENGTH_SHORT).show();
        }
    }
}
