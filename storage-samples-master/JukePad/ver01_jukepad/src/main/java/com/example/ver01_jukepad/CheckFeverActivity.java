package com.example.ver01_jukepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;
import java.util.Random;

public class CheckFeverActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button btnPass, btnStop, btnDistance, btnMask;
    TextToSpeech textToSpeech;
    int[] okImages = {R.drawable.ok1, R.drawable.ok2, R.drawable.ok3, R.drawable.ok4};
    int[] noImages = {R.drawable.no1, R.drawable.no2, R.drawable.no3, R.drawable.no4};
    int[] maskImages = {R.drawable.mask1, R.drawable.mask2, R.drawable.mask3};
    int[] stopCovid19Images = {R.drawable.stop_covid19_1, R.drawable.stop_covid19_2, R.drawable.stop_covid19_3, R.drawable.stop_covid19_4, R.drawable.stop_covid19_5};
    int[] socialDistancingImages = {R.drawable.social_distancing1, R.drawable.social_distancing2, R.drawable.social_distancing3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_fever);

        //region 참조변수에 객체 할당
        imageView = findViewById(R.id.imageView);
        btnPass = findViewById(R.id.btnPass);
        btnStop = findViewById(R.id.btnStop);
        btnDistance = findViewById(R.id.btnDistance);
        btnMask = findViewById(R.id.btnMask);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(Locale.KOREAN);
                }
            }
        });
        //endregion

        // 초기 이미지 세팅
        imageView.setImageResource(getRandomImageResource(stopCovid19Images));

        btnPass.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnDistance.setOnClickListener(this);
        btnMask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPass:
                imageView.setImageResource(getRandomImageResource(okImages));
                textToSpeech.speak("정상 체온 입니다 통과하세요", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.btnStop:
                imageView.setImageResource(getRandomImageResource(noImages));
                textToSpeech.speak("발열이 있습니다", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.btnDistance:
                imageView.setImageResource(getRandomImageResource(socialDistancingImages));
                textToSpeech.speak("사회적 거리두기를 지켜주세요", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.btnMask:
                imageView.setImageResource(getRandomImageResource(maskImages));
                textToSpeech.speak("마스크를 써주세요", TextToSpeech.QUEUE_FLUSH, null);
                break;
        }
    }

    private int getRandomImageResource(int[] imageArray){
        Random random = new Random();
        return imageArray[random.nextInt(imageArray.length)];
    }
}
