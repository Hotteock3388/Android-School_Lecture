package com.example.ver01_jukepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectAudioFile extends AppCompatActivity {

    TextView textViewTitle;
    EditText editTextKeyword;
    Button btnSelect, btnSearch;
    ListView listView;
    ArrayList<HashMap<String, String>> audioDataList, audioArrayList;
    List<String> dataList, arrayList;
    ArrayAdapter adapter;
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
        setContentView(R.layout.activity_select_audio_file);

        //region 참조변수에 객체 연결
        textViewTitle = findViewById(R.id.textViewSelectAudioTitle);
        editTextKeyword = findViewById(R.id.editTextSelectAudioKdyword);
        btnSearch = findViewById(R.id.btnSearch);
        btnSelect = findViewById(R.id.btnAdd_selectAudio);
        listView = findViewById(R.id.listview_select_audiofile);
        //endregion

        // permissionList의 권한 요청
        ActivityCompat.requestPermissions(this, permissionList, 0);

        audioDataList = readMediaStoreAudioFile(null);
        audioArrayList = new ArrayList<HashMap<String, String>>();
        audioArrayList.addAll(audioDataList);

        /*String[] keys = {"name", "title"};
        int[] ids = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, audioDataList, android.R.layout.simple_list_item_2, keys, ids);*/

        dataList = readMediaStoreAudioFileName(null);
        arrayList = new ArrayList<String>();
        arrayList.addAll(dataList);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, dataList);

        // 라디오단추 선택모드 세팅
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setAdapter(adapter);

        //region 오디오 파일을 선택 -> 재생
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
        //endregion

        //region SELECT 버튼 클릭 이벤트 처리
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaySoundActivity.class);
                // category_name, button_name, audio_id, audio_filename
                intent.putExtra("DISPLAY_NAME", dataList.get(listView.getCheckedItemPosition()));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        //endregion

        //region Search 버튼 클릭 이벤트 처리(해당 키워드를 포함하는 음원 리스트만 생성하여 보여줌)
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.clear();       // 리스트 내용 지우기
                audioDataList.clear();

                // 검색어가 없을 때는 모든 데이터를 보여준다.
                if(editTextKeyword.getText().length() == 0){
                    dataList.addAll(arrayList);
                    audioDataList.addAll(audioArrayList);
                }else{
                    // 리스트의 데이터 검색
                    for(int i=0; i<arrayList.size(); i++){
                        if(arrayList.get(i).toLowerCase().contains(editTextKeyword.getText().toString())){
                            dataList.add(arrayList.get(i));

                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("id", audioArrayList.get(i).get("id"));
                            hashMap.put("name", audioArrayList.get(i).get("name"));
                            hashMap.put("mime_type", audioArrayList.get(i).get("mime_type"));
                            hashMap.put("album", audioArrayList.get(i).get("album"));
                            hashMap.put("artist", audioArrayList.get(i).get("artist"));
                            hashMap.put("title", audioArrayList.get(i).get("title"));
                            hashMap.put("data", audioArrayList.get(i).get("data"));
                            audioDataList.add(hashMap);
                        }
                    }
                }
                // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
                adapter.notifyDataSetChanged();
            }
        });
        //endregion
    }

    // MediaStore Database에서 Audio File 찾아서 listview에 뿌릴 ArrayList<String> 반환
    private ArrayList<String> readMediaStoreAudioFileName(String keyword){
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor cursor;

        if(keyword == null){
            String selection = null;
            String[] selectionArgs = null;

            cursor = SelectAudioFile.this.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.DISPLAY_NAME},
                    selection,
                    selectionArgs,
                MediaStore.Audio.Media.DISPLAY_NAME + " ASC");
        }else{
            String selection = MediaStore.Audio.Media.DISPLAY_NAME + " LIKE ? ";
            String[] selectionArgs = {"%"+keyword+"%"};

            cursor = SelectAudioFile.this.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media.DISPLAY_NAME},
                    selection,
                    selectionArgs,
                    MediaStore.Audio.Media.DISPLAY_NAME + " ASC");
        }

        // cursor에 검색된 MediaStore.Audio 데이터가 없으면 return
        if (cursor == null || !cursor.moveToFirst()) {
            Log.e("MediaStoreError", "cursor null or cursor is empty");
            return null;
        }

        if (cursor != null) {
            int cursorCount = cursor.getColumnCount();
            String[] columnNames = cursor.getColumnNames();
            while (cursor.moveToNext()) {
                arrayList.add(cursor.getString(0));
            }
            cursor.close();
        }

        return arrayList;
    }

    private ArrayList<HashMap<String, String>> readMediaStoreAudioFile(String keyword) {
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
                MediaStore.Audio.Media.DATA
        };

        /**
         실질적으로 쿼리하는 코드
         Uri: 찾고자하는 데이터의 Uri
         Projection: DB의 column과 같다. 결과로 받고 싶은 데이터의 종류
         Selection: DB의 where 키워드와 같다. 어떤 조건으로 필터링된 결과를 받을 때 사용
         Selection args: Selection과 함께 사용
         Sort order: 쿼리 결과 데이터를 sorting할 때 사용

         위의 조건으로 쿼리한 데이터가 있다면 Cursor로 결과를 순회(Loop)할 수 있다.
         */
        try{
            Cursor cursor;
            if(keyword==null){
                cursor = SelectAudioFile.this.getContentResolver().query(
                        externalUri,
                        projection,
                        null,
                        null,
                        MediaStore.Audio.Media.DISPLAY_NAME + " ASC");
            }else{
                String selection = MediaStore.Audio.Media.DISPLAY_NAME + " LIKE ? ";
                String[] selectionArgs = {"%"+keyword+"%"};

                cursor = SelectAudioFile.this.getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Audio.Media.DISPLAY_NAME},
                        selection,
                        selectionArgs,
                        MediaStore.Audio.Media.DISPLAY_NAME + " ASC");
            }

            // MediaStore.Audio 데이터가 없으면 return
            if (cursor == null || !cursor.moveToFirst()) {
                Log.e("cursor data", "cursor null or cursor is empty");
                return null;
            }

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
                    hashMap.put("data", cursor.getString(6));

                    audioList.add(hashMap);
                    //Log.d("cursor data", "MediaStore.Audio.Media._ID : " + cursor.getString(0));
                }
                cursor.close();
            }
        }catch (Exception e){
            Log.d("CursorError", e.toString());
        }
        return audioList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null)
            mediaPlayer.release();
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
            //Toast.makeText(SelectAudioFile.this, strGrantedList, Toast.LENGTH_SHORT).show();
        }
    }
}
