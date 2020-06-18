package com.example.bookmark.Backend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class URL {

    public URL(String url){
        this.url=url;
    }

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
