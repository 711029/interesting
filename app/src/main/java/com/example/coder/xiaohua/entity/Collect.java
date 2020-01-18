package com.example.coder.xiaohua.entity;

import cn.bmob.v3.BmobObject;

public class Collect extends BmobObject {
    private String title;
    private String content;
    private String url;
    private String updata;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdata() {
        return updata;
    }

    public void setUpdata(String updata) {
        this.updata = updata;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
