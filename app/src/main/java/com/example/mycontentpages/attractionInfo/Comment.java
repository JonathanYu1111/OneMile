package com.example.mycontentpages.attractionInfo;

public class Comment {
    private Integer reviewId;

    private String placeId;

    private String content;

    private Integer star;

    private Integer createTime;

    private Integer memberId;

    private String username;

    public Comment() {
    }

    public Comment(Integer reviewId, String placeId, String content, Integer star, Integer createTime, Integer memberId, String username) {
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.content = content;
        this.star = star;
        this.createTime = createTime;
        this.memberId = memberId;
        this.username = username;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
