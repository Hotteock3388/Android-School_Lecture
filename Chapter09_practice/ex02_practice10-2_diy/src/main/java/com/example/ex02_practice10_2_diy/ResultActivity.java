package com.example.ex02_practice10_2_diy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    Button btnReturn;
    TextView textViewBestImageName;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);

        //region 참조변수에 객체 연결
        btnReturn = findViewById(R.id.btnReturn);
        textViewBestImageName = findViewById(R.id.textViewFirstImageName);
        imageView = findViewById(R.id.imageView);

        // res의 TextView, RatingBar 아이디를 배열에 저장
        int[] textViewIds = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9};
        int[] ratingBarIds = {R.id.rbar1, R.id.rbar2, R.id.rbar3, R.id.rbar4, R.id.rbar5, R.id.rbar6, R.id.rbar7, R.id.rbar8, R.id.rbar9};
        Integer imageFileId[] = { R.drawable.kakao01, R.drawable.kakao02, R.drawable.kakao03, R.drawable.kakao04,
                R.drawable.kakao05, R.drawable.kakao06, R.drawable.kakao07, R.drawable.kakao08, R.drawable.kakao09 };
        //endregion

        //region MainActivity에서 보낸 intent 객체 받아서 데이터 저장하기
        Intent intent = getIntent();    // MainActivity에서 보낸 인텐트 가져오기
        String[] imageNames = intent.getStringArrayExtra("nameData");   // 이미지 이름 배열 가져오기
        int[] voteResult = intent.getIntArrayExtra("voteData");         // 투표 결과 가져오기

        TextView[] textViews = new TextView[imageNames.length];     // 이미지 이름을 표시할 TextView 배열 생성
        RatingBar[] ratingBars = new RatingBar[voteResult.length];  // RationBar 배열 생성

        // TextView와 RatingBar 참조변수 배열에 뷰 객체 연결
        for(int i=0; i<voteResult.length; i++){
            textViews[i] = findViewById(textViewIds[i]);
            ratingBars[i] = findViewById(ratingBarIds[i]);
        }

        // TextView에 "이미지 이름", RatingBar에 투표 결과 출력
        for(int i=0; i<voteResult.length; i++){
            textViews[i].setText(imageNames[i]);
            ratingBars[i].setRating((float)voteResult[i]);
        }
        //endregion

        //region 최다 득표 이미지와 이미지 이름 표시
        int bestImageNum = getIndexOfLargestNumber(voteResult);
        textViewBestImageName.setText(imageNames[bestImageNum]);
        imageView.setImageResource(imageFileId[bestImageNum]);
        //endregion

        // [돌아가기] 버튼을 클릭하면 ResultActivity 종료하기
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // 정수 배열에서 가장 큰 요소의 인덱스 넘버를 반환하는 메소드
    private int getIndexOfLargestNumber(int[] intArr){
        int max=0;
        int index=0;
        for (int i=0; i<intArr.length; i++){
            if(max < intArr[i]){
                max = intArr[i];
                index = i;
            }
        }
        return index;
    }
}
