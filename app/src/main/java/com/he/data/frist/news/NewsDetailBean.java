package com.he.data.frist.news;

import java.io.Serializable;
import java.util.List;


/**新闻详情 */
public class NewsDetailBean implements Serializable {

    private String title;

    private String source;

    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
