package com.musinsa.jglee.dto.response;

public class CreateShortUrlResponseDTO extends GlobalReturnDTO {
    private String shortUrl;
    private long redirectCount = 0;


    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public long getRedirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(long redirectCount) {
        this.redirectCount = redirectCount;
    }
}
