package com.cd.ntiteam_test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Comparator;
import java.util.List;

public class LordList {
    @JsonProperty("lords")
    private List<LordJson> lordDTOS;

    public LordList(List<LordJson> lordDTOS) {
        this.lordDTOS = lordDTOS;
    }

    public void sortToId(){
        lordDTOS.sort(Comparator.comparing(LordJson::getId));
    }

}
