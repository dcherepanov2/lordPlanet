package com.cd.ntiteam_test.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LordJson {
    @JsonProperty("lord")
    LordDto lordDTO;

    public LordJson(LordDto lordDTO) {
        this.lordDTO = lordDTO;
    }

    @JsonIgnore
    public Long getId() {
        return lordDTO.getId();
    }
}
