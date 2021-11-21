package com.cd.ntiteam_test.controllers;

import com.cd.ntiteam_test.entity.Planet;
import com.cd.ntiteam_test.dto.PlanetDto;
import com.cd.ntiteam_test.exception.PlanetException;
import com.cd.ntiteam_test.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planet")
public class PlanetController {
    private final PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @RequestMapping(
            value = "/add", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public PlanetDto addPlanet(@Validated @RequestBody Planet request) throws PlanetException {
        Planet planet = planetService.addPlanet(request);
        PlanetDto planetDTO = new PlanetDto();
        return planetDTO.convertToPlanetDTO(planet);
    }

    @PostMapping("/delete/{id}")
    public PlanetDto deletePlanet(@PathVariable("id") Long id) throws PlanetException {
        Planet planet = planetService.deletePlanet(id);
        PlanetDto planetDTO = new PlanetDto();
        return planetDTO.convertToPlanetDTO(planet);
    }

}
