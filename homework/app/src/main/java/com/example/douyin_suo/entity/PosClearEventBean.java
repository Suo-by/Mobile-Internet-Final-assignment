package com.example.douyin_suo.entity;

public class PosClearEventBean {
    private boolean isClear;

    public PosClearEventBean(boolean isClear){
        this.isClear = isClear;
    }
    public boolean getIsClear(){
        return this.isClear;
    }
    public void setIsClear(boolean isClear){
        this.isClear = isClear;
    }
}
