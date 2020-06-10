package com.example.douyin_suo.entity;

public class UserMessageBean {
    String id;
    String feedurl;
    String nickname;
    String description;
    String likecount;
    String avatar;

    public UserMessageBean(){}

    public UserMessageBean(String id, String feedurl, String nickname, String description, String likecount, String avatar){
        this.id = id;
        this.feedurl = feedurl;
        this.nickname = nickname;
        this.description = description;
        this.likecount = likecount;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFeedurl() {
        return feedurl;
    }

    public void setFeedurl(String feedurl) {
        this.feedurl = feedurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLikecount() {
        return likecount;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
