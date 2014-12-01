package com.tibby.media;

import java.io.Serializable;

/**
 * Created by tangjian on 27/11/14.
 * QQ:562980080
 * Email:tangjian19900607@gmail.com
 */
public class Media implements Serializable {

    private String url;
    private String name;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Media(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Media() {
        super();
    }
}
