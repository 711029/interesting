package com.example.coder.xiaohua.entity;

/**
 * 作者：Coder
 * 创建日期：2019/12/9
 * 创建时间：21:48
 * 邮箱：3170495286@qq.com
 */
public class JokeImg {
    private String content;
    private String updatetime;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "JokeImg{" +
                "content='" + content + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
