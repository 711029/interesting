package com.example.coder.xiaohua.entity;

import cn.bmob.v3.BmobObject;

//反馈实体类
public class FeedBack extends BmobObject {
    private String text;
    private String num;

    public FeedBack() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
