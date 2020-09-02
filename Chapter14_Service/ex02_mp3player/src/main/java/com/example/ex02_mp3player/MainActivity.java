package com.example.ex02_mp3player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mp;
    Button btnStartService, btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btnStartService);
        btnStopService = findViewById(R.id.btnStopService);
        mp = MediaPlayer.create(MainActivity.this, R.raw.iu);

        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartService:
                Log.d("test", "btnStartService");
                mp.setVolume(0.8f, 0.8f);
                mp.start();
                break;
            case R.id.btnStopService:
                Log.d("test", "btnStopService");
                break;
        }
    }
}
