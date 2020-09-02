package com.example.ver01_jukepad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

class CategoryCustomAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<String> dataList;

    public CategoryCustomAdapter(Context context, int resource, ArrayList<String> dataList) {
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
        Button btnCategoryItem = convertView.findViewById(R.id.btnRowCategory);
        btnCategoryItem.setText(dataList.get(position));

        //region btnCategoryItem button 이벤트 처리
        btnCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlaySoundActivity.class);
                intent.putExtra("category_name", dataList.get(position));
                context.startActivity(intent);
            }
        });
        //endregion

        btnCategoryItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(context, dataList.get(position), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return convertView;
    }
}
