package com.chm.book.article.domain;

import java.util.Date;

public class Posts {
    private Integer postsId;

    private Integer postsUserId;

    private String postsTitle;

    private Integer postsViewCount;

    private Integer postsCommentCount;

    private Integer postsLikeCount;

    private Integer postsIsComment;

    private Integer postsStatus;

    private Integer postsOrder;

    private Date postsUpdateTime;

    private Date postsCreateTime;

    public Integer getPostsId() {
        return postsId;
    }

    public void setPostsId(Integer postsId) {
        this.postsId = postsId;
    }

    public Integer getPostsUserId() {
        return postsUserId;
    }

    public void setPostsUserId(Integer postsUserId) {
        this.postsUserId = postsUserId;
    }

    public String getPostsTitle() {
        return postsTitle;
    }

    public void setPostsTitle(String postsTitle) {
        this.postsTitle = postsTitle == null ? null : postsTitle.trim();
    }

    public Integer getPostsViewCount() {
        return postsViewCount;
    }

    public void setPostsViewCount(Integer postsViewCount) {
        this.postsViewCount = postsViewCount;
    }

    public Integer getPostsCommentCount() {
        return postsCommentCount;
    }

    public void setPostsCommentCount(Integer postsCommentCount) {
        this.postsCommentCount = postsCommentCount;
    }

    public Integer getPostsLikeCount() {
        return postsLikeCount;
    }

    public void setPostsLikeCount(Integer postsLikeCount) {
        this.postsLikeCount = postsLikeCount;
    }

    public Integer getPostsIsComment() {
        return postsIsComment;
    }

    public void setPostsIsComment(Integer postsIsComment) {
        this.postsIsComment = postsIsComment;
    }

    public Integer getPostsStatus() {
        return postsStatus;
    }

    public void setPostsStatus(Integer postsStatus) {
        this.postsStatus = postsStatus;
    }

    public Integer getPostsOrder() {
        return postsOrder;
    }

    public void setPostsOrder(Integer postsOrder) {
        this.postsOrder = postsOrder;
    }

    public Date getPostsUpdateTime() {
        return postsUpdateTime;
    }

    public void setPostsUpdateTime(Date postsUpdateTime) {
        this.postsUpdateTime = postsUpdateTime;
    }

    public Date getPostsCreateTime() {
        return postsCreateTime;
    }

    public void setPostsCreateTime(Date postsCreateTime) {
        this.postsCreateTime = postsCreateTime;
    }
}