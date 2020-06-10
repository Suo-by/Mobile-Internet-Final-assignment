package com.example.douyin_suo.entity;

public class BarStateEventBean {
    private boolean isShow;

    public BarStateEventBean(boolean isShow){
        this.isShow = isShow;
    }

    public boolean getIsShow(){
        return this.isShow;
    }
    public void setIsShow(boolean isShow){
        this.isShow = isShow;
    }
}
