package com.cd.ntiteam_test.dto;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LordDto {
    private Long id;

    private String name;

    private Integer age;

    @JsonProperty("planet")
    private List<PlanetDto> planet;

    public LordDto convertToJson(Lord lord) {
        LordDto lordDTO = new LordDto();
        lordDTO.setId(lord.getId());
        lordDTO.setAge(lord.getAge());
        lordDTO.setName(lord.getName());
        List<PlanetDto> planetDTOS = new ArrayList<>();
        for (Planet planet : lord.getPlanets()) {
            PlanetDto planetDTO = new PlanetDto();
            planetDTO.setId(planet.getId());
            planetDTO.setName(planet.getName());
            planetDTO.setLord(planet.getLord().getId());
            planetDTOS.add(planetDTO);
        }
        lordDTO.setPlanets(planetDTOS);
        return lordDTO;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPlanets(List<PlanetDto> planets) {
        this.planet = planets;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
