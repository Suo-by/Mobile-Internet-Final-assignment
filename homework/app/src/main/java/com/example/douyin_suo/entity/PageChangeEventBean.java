package com.example.douyin_suo.entity;

public class PageChangeEventBean {
    private int position;

    public PageChangeEventBean(int position){
        this.position = position;
    }
    public int getPosition(){
        return this.position;
    }
    public void setPosition(int position){
        this.position = position;
    }
}
