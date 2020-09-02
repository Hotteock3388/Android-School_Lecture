package com.example.ex07_listview07_customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class CustomAdapter extends BaseAdapter {

    Context context;
    int resource;
    ArrayList<ItemVO> dataList;

    public CustomAdapter(Context context, int resource, ArrayList<ItemVO> dataList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // 재사용 가능한 View가 없다면 View 생성
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
        }

        // 반환할 View 구성
        ImageView imageViewIcon = convertView.findViewById(R.id.custom_item_type_image);
        TextView textViewTitle = convertView.findViewById(R.id.custom_item_title);
        TextView textViewContents = convertView.findViewById(R.id.custom_item_date);
        ImageView imageViewMenu = convertView.findViewById(R.id.custom_item_menu);

        // type에 따라 Icon 결정
        switch(dataList.get(position).getTypeStr()){
            case "doc":
                imageViewIcon.setImageResource(R.drawable.ic_type_doc);
                break;
            case "img":
                imageViewIcon.setImageResource(R.drawable.ic_type_image);
                break;
            case "file":
                imageViewIcon.setImageResource(R.drawable.ic_type_file);
                break;
        }
        textViewTitle.setText(dataList.get(position).getTitleStr());
        textViewContents.setText(dataList.get(position).getDateStr());
        imageViewMenu.setImageResource(R.drawable.ic_menu);

        return convertView;
    }
}
