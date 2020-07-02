package com.musinsa.jglee.dto.request;

import io.swagger.annotations.ApiModelProperty;

public class CreateShortUrlRequestDTO {
    @ApiModelProperty(dataType = "string", required = true, notes = "short url 로 변환할 url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
