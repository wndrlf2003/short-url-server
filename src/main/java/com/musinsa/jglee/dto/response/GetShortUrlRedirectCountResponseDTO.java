package com.musinsa.jglee.dto.response;

import io.swagger.annotations.ApiModelProperty;

public class GetShortUrlRedirectCountResponseDTO extends GlobalReturnDTO {
    @ApiModelProperty(required = true, notes = "redirect 된 횟수")
    private long redirectCount = 0L;

    public long getRedirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(long redirectCount) {
        this.redirectCount = redirectCount;
    }
}
