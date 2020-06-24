
package com.codechefvit.bookmarked.Favicon;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageURL {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("icons")
    @Expose
    private List<Icon> icons = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }

}
