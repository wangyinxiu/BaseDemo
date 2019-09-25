package com.xiu.data.bean.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Information {


    @Id(autoincrement = true)
    private Long _id;
    private String id;
    private String userId;
    private String postTitle;
    private String postType;
    private int postStatus;
    private String postWord;
    private String postImg;
    private String homeImg;
    private int isTop;
    private int viewNum;
    private int commentNum;
    private int favorNum;
    private int displayType;
    private int pageSize;
    private int pageNo;
    @Generated(hash = 652341137)
    public Information(Long _id, String id, String userId, String postTitle,
            String postType, int postStatus, String postWord, String postImg,
            String homeImg, int isTop, int viewNum, int commentNum, int favorNum,
            int displayType, int pageSize, int pageNo) {
        this._id = _id;
        this.id = id;
        this.userId = userId;
        this.postTitle = postTitle;
        this.postType = postType;
        this.postStatus = postStatus;
        this.postWord = postWord;
        this.postImg = postImg;
        this.homeImg = homeImg;
        this.isTop = isTop;
        this.viewNum = viewNum;
        this.commentNum = commentNum;
        this.favorNum = favorNum;
        this.displayType = displayType;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }
    @Generated(hash = 1933283371)
    public Information() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPostTitle() {
        return this.postTitle;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    public String getPostType() {
        return this.postType;
    }
    public void setPostType(String postType) {
        this.postType = postType;
    }
    public int getPostStatus() {
        return this.postStatus;
    }
    public void setPostStatus(int postStatus) {
        this.postStatus = postStatus;
    }
    public String getPostWord() {
        return this.postWord;
    }
    public void setPostWord(String postWord) {
        this.postWord = postWord;
    }
    public String getPostImg() {
        return this.postImg;
    }
    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }
    public String getHomeImg() {
        return this.homeImg;
    }
    public void setHomeImg(String homeImg) {
        this.homeImg = homeImg;
    }
    public int getIsTop() {
        return this.isTop;
    }
    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }
    public int getViewNum() {
        return this.viewNum;
    }
    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }
    public int getCommentNum() {
        return this.commentNum;
    }
    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
    public int getFavorNum() {
        return this.favorNum;
    }
    public void setFavorNum(int favorNum) {
        this.favorNum = favorNum;
    }
    public int getDisplayType() {
        return this.displayType;
    }
    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }
    public int getPageSize() {
        return this.pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageNo() {
        return this.pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    
}
