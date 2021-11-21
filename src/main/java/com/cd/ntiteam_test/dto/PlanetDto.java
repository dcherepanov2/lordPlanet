package com.cd.ntiteam_test.dto;

import com.cd.ntiteam_test.entity.Planet;

public class PlanetDto {

    private Long id;

    private String name;

    private Long lord;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getLord() {
        return lord;
    }

    public void setLord(Long lord) {
        this.lord = lord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlanetDto convertToPlanetDTO(Planet planet) {
        PlanetDto planetDTO = new PlanetDto();
        planetDTO.setName(planet.getName());
        planetDTO.setId(planet.getId());
        if (planet.getLord() != null)
            planetDTO.setLord(planet.getLord().getId());
        return planetDTO;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lord=" + lord +
                '}';
    }
}
