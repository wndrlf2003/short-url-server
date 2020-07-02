package com.musinsa.jglee.schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ShortUrl {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    private String url;

    @Column(unique = true)
    private String shorteningKey;

    @Column()
    private long redirectCount = 0L;

    public ShortUrl() {}

    public ShortUrl(String url, String shorteningKey) {
        this.url = url;
        this.shorteningKey = shorteningKey;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getRedirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(long redirectCount) {
        this.redirectCount = redirectCount;
    }

    public String getShorteningKey() {
        return shorteningKey;
    }

    public void setShorteningKey(String shorteningKey) {
        this.shorteningKey = shorteningKey;
    }
}
