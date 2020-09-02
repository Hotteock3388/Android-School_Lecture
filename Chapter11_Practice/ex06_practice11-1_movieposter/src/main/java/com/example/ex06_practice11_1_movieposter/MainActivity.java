package com.example.ex06_practice11_1_movieposter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        MyGridAdapter adapter = new MyGridAdapter(this);
        gridView.setAdapter(adapter);
    }

    public class MyGridAdapter extends BaseAdapter{

        Context context;
        public MyGridAdapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return posterID.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        Integer[] posterID = { R.drawable.mov01, R.drawable.mov02,
                R.drawable.mov03, R.drawable.mov04, R.drawable.mov05,
                R.drawable.mov06, R.drawable.mov07, R.drawable.mov08,
                R.drawable.mov09, R.drawable.mov10, R.drawable.mov01,
                R.drawable.mov02, R.drawable.mov03, R.drawable.mov04,
                R.drawable.mov05, R.drawable.mov06, R.drawable.mov07,
                R.drawable.mov08, R.drawable.mov09, R.drawable.mov10,
                R.drawable.mov01, R.drawable.mov02, R.drawable.mov03,
                R.drawable.mov04, R.drawable.mov05, R.drawable.mov06,
                R.drawable.mov07, R.drawable.mov08, R.drawable.mov09,
                R.drawable.mov10 };

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 300));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(5,5,5,5);
            imageView.setImageResource(posterID[position]);

            final int pos = position;
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    View dialogView = (View) View.inflate(
                            MainActivity.this, R.layout.dialog, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(
                            MainActivity.this);
                    ImageView ivPoster = (ImageView) dialogView
                            .findViewById(R.id.ivPoster);
                    ivPoster.setImageResource(posterID[pos]);
                    dlg.setTitle("큰 포스터");
                    dlg.setIcon(R.mipmap.ic_launcher);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });

            return imageView;
        }
    }
}


