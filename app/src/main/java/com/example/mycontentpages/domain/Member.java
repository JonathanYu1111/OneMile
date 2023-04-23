package com.example.mycontentpages.domain;

public class Member {

    private Integer memberId;

    private String username;

    private String nickname;

    private String mobile;

    private String email;

    private String password;

    private Integer deleted;

    private String headimg;

    private Integer loginTime;

    private String sex;

    private Integer birthday;

    private Integer createTime;

    private Integer modifyTime;

    public Member(Integer memberId, String username, String nickname, String mobile, String email, String password, Integer deleted, String headimg, Integer loginTime, String sex, Integer birthday, Integer createTime, Integer modifyTime) {
        this.memberId = memberId;
        this.username = username;
        this.nickname = nickname;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.deleted = deleted;
        this.headimg = headimg;
        this.loginTime = loginTime;
        this.sex = sex;
        this.birthday = birthday;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", deleted=" + deleted +
                ", headimg='" + headimg + '\'' +
                ", loginTime=" + loginTime +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}

