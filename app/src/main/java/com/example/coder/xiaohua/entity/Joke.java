package com.example.coder.xiaohua.entity;

import java.util.List;


public class Joke {
    private String content;
    private String updatetime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    @Override
    public String toString() {
        return "Joke{" +
                "content='" + content + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
