package com.musinsa.jglee.dto.response;

public class GetShortUrlRedirectCountResponseDTO extends GlobalReturnDTO {
    private long redirectCount = 0L;

    public long getRedirectCount() {
        return redirectCount;
    }

    public void setRedirectCount(long redirectCount) {
        this.redirectCount = redirectCount;
    }
}
