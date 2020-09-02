package com.example.ex08_practice11_1_movieposter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

class MyGridAdapter extends BaseAdapter {

    Context context;
    int resource;
    int[] posterIds;

    public MyGridAdapter(Context context, int[] posterIds) {
            this.context = context;
        this.resource = resource;
        this.posterIds = posterIds;
    }

    @Override
    public int getCount() {
        return posterIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        // 반환할 View 구성
        final ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new GridView.LayoutParams(200, 300));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(5,5,5,5);

        imageView.setImageResource(posterIds[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(context, R.layout.dialog, null);
                ImageView ivPoster = (ImageView)dialogView.findViewById(R.id.ivPoster);
                ivPoster.setImageResource(posterIds[position]);
                new AlertDialog.Builder(context)
                        .setTitle("큰 포스트")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setView(dialogView)
                        .setNegativeButton("닫기", null)
                        .show();
            }
        });

        return imageView;
    }
}
