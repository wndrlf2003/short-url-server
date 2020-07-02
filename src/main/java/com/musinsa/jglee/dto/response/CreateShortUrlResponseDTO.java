package com.musinsa.jglee.dto.response;

import io.swagger.annotations.ApiModelProperty;

public class CreateShortUrlResponseDTO extends GlobalReturnDTO {
    @ApiModelProperty(required = true, notes = "변환된 short url")
    private String shortUrl;
    @ApiModelProperty(required = true, notes = "redirect 된 횟수")
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
