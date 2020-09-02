package com.example.ex06_listview06_customadapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

class CustomAdapter extends BaseAdapter {

    Context context;
    int resource;
    ArrayList<String> data;

    public CustomAdapter(Context context, int resource, ArrayList<String> data) {
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    // 리스트뷰 아이템의 개수 반환
    @Override
    public int getCount() {
        return data.size();
    }

    // 요청한 위치의 아이템 반환
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 화면에 출력될 View 반환, 화면에 보여줄 항목 개수만큼 호출
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 재사용 가능한 View가 없다면 View 생성
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
        }

        // 반환할 View 구성
        TextView textViewRow = convertView.findViewById(R.id.textViewRow);
        textViewRow.setText(data.get(position));
        Button btnModify = convertView.findViewById(R.id.btnRowModify);
        Button btnDelete = convertView.findViewById(R.id.btnRowDelete);

        //region btnModify 버튼 이벤트 처리
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(context);
                new AlertDialog.Builder(context)
                    .setTitle("리스트 아이템 수정")
                    .setMessage("현재 데이터 : " + data.get(position))
                    .setIcon(R.mipmap.ic_launcher)
                    .setCancelable(false)
                    .setView(editText)
                    .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            data.set(position, editText.getText().toString());
                            CustomAdapter.this.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();
            }
        });
        //endregion

        //region btnDelete 버튼 이벤트 처리
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                    .setTitle("리스트 아이템 삭제")
                    .setMessage(String.format("[%s]를 정말로 삭제하시겠습니까?", data.get(position)))
                    .setIcon(R.mipmap.icon_remove_round)
                    .setCancelable(false)
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            data.remove(position);
                            CustomAdapter.this.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("아니오", null)
                    .show();
            }
        });
        //endregion

        return convertView;
    }

    // 새로운 아이템 추가하고, 리스트뷰에 반영
    public void addItem(String toString) {
        data.add(toString);
        this.notifyDataSetChanged();
    }
}
