package com.he.data.first.news;

import java.io.Serializable;

public class NewsBean implements Serializable {

    private String docid;
    /**标题*/
    private String title;
    /** 小内容*/
    private String digest;
    /** * 图片地址*/
    private String imgsrc;
    /*** 来源*/
    private String source;


    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
