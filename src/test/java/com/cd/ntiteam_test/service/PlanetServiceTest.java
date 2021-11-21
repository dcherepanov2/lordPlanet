package com.cd.ntiteam_test.service;

import com.cd.ntiteam_test.entity.Planet;
import com.cd.ntiteam_test.exception.PlanetException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application_test.properties")
public class PlanetServiceTest {

    private final PlanetService planetService;
    private final Random random = new Random();

    @Autowired
    public PlanetServiceTest( PlanetService planetService) {
        this.planetService = planetService;
    }


    @Test
    void addPlanet() throws PlanetException {
        String name = UUID.randomUUID().toString()+random.nextInt(2000000000);
        Planet request = new Planet();
        request.setName(name);
        Planet planet =  planetService.addPlanet(request);
        assertNotNull(planet.getName());
        assertNotEquals("", planet.getName());

    }

    @Test
    void deletePlanet() throws PlanetException {
        String name = UUID.randomUUID().toString()+random.nextInt(2000000000);
        Planet request = new Planet();
        request.setName(name);
        Planet planet =  planetService.addPlanet(request);
        planet = planetService.deletePlanet(planet.getId());
        assertNotNull(planet);
        assertNotNull(planet.getId());
        assertNotNull(planet.getName());
    }
}