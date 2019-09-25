package com.xiu.data.bean.list;

import com.google.gson.annotations.SerializedName;
import com.xiu.data.bean.response.Information;

import java.util.List;

public class InformationList {



    private int total;
    private int size;
    private int current;
    private boolean searchCount;
    private int pages;
    @SerializedName("records")
    private List<Information> informationList;


    public int getTotal() {
        return total;
    }

    public int getSize() {
        return size;
    }

    public int getCurrent() {
        return current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public int getPages() {
        return pages;
    }

    public List<Information> getInformationList() {
        return informationList;
    }
}
