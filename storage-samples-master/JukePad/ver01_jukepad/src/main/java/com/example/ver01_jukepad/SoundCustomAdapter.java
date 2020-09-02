package com.example.ver01_jukepad;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class SoundCustomAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<HashMap<String, String>> dataList;
    MediaPlayer mediaPlayer;

    public SoundCustomAdapter(Context context, int resource, ArrayList<HashMap<String, String>> dataList) {
        this.context = context;
        this.resource = resource;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 재사용 가능한 View가 없다면 View 생성
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
        }

        // 반환할 View 구성
        Button btnSoundItem = convertView.findViewById(R.id.btnRowCategory);
        btnSoundItem.setText(dataList.get(position).get("button_name"));

        //region btnCategoryItem button 이벤트 처리
        btnSoundItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri contentUri = ContentUris.withAppendedId(
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, Long.parseLong(dataList.get(position).get("audio_id")));
                if(mediaPlayer!=null)
                    mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {
                    mediaPlayer.setDataSource(context, contentUri);
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

        btnSoundItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(context, dataList.get(position), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return convertView;
    }
}
