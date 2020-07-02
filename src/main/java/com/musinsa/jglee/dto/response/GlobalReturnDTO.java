package com.musinsa.jglee.dto.response;

import io.swagger.annotations.ApiModelProperty;

public class GlobalReturnDTO {
    @ApiModelProperty(required = true, notes = "리턴 상태 코드.")
    private int code = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
