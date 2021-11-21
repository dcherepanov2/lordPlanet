package com.cd.ntiteam_test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BadRequestDto {
    @JsonProperty("jdajashdhak")
    private String badParam;

    public void setBadParam(String badParam) {
        this.badParam = badParam;
    }
}
