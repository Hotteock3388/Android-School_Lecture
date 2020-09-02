package com.example.ex06_listview_customadapter02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataObject> {

    Context context;
    int resId;
    ArrayList<DataObject> datas;

    public CustomAdapter(Context context, int resId, ArrayList<DataObject> datas) {
        super(context, resId);
        this.context = context;
        this.resId = resId;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // 재사용 가능한 View가 없다면 View 생성
        if(convertView == null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null);
        }

        // View 획득
//        ImageView typeImageView = convertView.findViewById(R.id.cu)




        return convertView;
    }
}
