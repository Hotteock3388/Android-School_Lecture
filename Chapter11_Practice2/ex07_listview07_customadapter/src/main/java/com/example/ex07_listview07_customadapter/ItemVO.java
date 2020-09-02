package com.example.ex07_listview07_customadapter;

public class ItemVO {
    String typeStr;
    String titleStr;
    String contentsStr;

    public ItemVO(String typeStr, String titleStr, String contentsStr) {
        this.typeStr = typeStr;
        this.titleStr = titleStr;
        this.contentsStr = contentsStr;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getDateStr() {
        return contentsStr;
    }

    public void setDateStr(String dateStr) {
        this.contentsStr = dateStr;
    }
}
