package com.zl.ipcservicetest;

/**
 * 类名称：
 * 类功能：
 * 类作者：ZL
 * 类日期：2017/3/21
 **/

public class MessageEvent<T> {
    String title;
    T content;

    public MessageEvent(String title, T content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
