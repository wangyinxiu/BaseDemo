package com.xiu.data.bean.params;

public class InformationParams {

    private int pageNo = 1;
    private int pageSize = 10;
    private String postType;


    public InformationParams(String postType,int page) {
        this.postType = postType;
        this.pageNo = page;
    }
}
