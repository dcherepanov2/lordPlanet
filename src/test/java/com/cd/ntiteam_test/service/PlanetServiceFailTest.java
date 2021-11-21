package com.cd.ntiteam_test.service;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import com.cd.ntiteam_test.exception.PlanetException;
import com.cd.ntiteam_test.repo.LordRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application_test.properties")
class PlanetServiceFailTest {

    private final LordRepo lordRepo;
    private final PlanetService planetService;
    private final Random random = new Random();

    @Autowired
    public PlanetServiceFailTest(LordRepo lordRepo1, PlanetService planetService) {

        this.lordRepo = lordRepo1;
        this.planetService = planetService;
    }

    @Test
    void addPlanetFail() {
        try {
            Planet planet = new Planet();
            planet.setName("");
            planetService.addPlanet(planet);
            fail("Excepted PlanetException");
        } catch (PlanetException e) {
            e.printStackTrace();
        }

        try {
            Planet planet = new Planet();
            Lord lord = new Lord();
            lord.setId(2L);
            planet.setLord(lord);
            planetService.addPlanet(planet);
            fail("Excepted PlanetException");
        } catch (PlanetException e) {
            e.printStackTrace();
        }

        try {
            String name = UUID.randomUUID().toString() + random.nextInt(2000000000);
            Planet planet = new Planet();
            Lord lord = new Lord();
            lord.setId(2L);
            planet.setLord(lord);
            planetService.addPlanet(planet);
            planet.setName(name);
            planetService.addPlanet(planet);
            fail("Excepted PlanetException");
        } catch (PlanetException e) {
            e.printStackTrace();
        }

        try {
            planetService.addPlanet(new Planet());
            fail("Excepted PlanetException");
        } catch (PlanetException e) {
            e.printStackTrace();
        }

        try {
            Planet planet = new Planet();
            Lord lord = new Lord();
            lord.setId(2L);
            planet.setLord(lord);
            planetService.addPlanet(planet);
            planetService.addPlanet(planet);
            fail("Excepted PlanetException");
        } catch (PlanetException e) {
            e.printStackTrace();
        }

    }

    @Test
    void deletePlanet(){
        Long id = lordRepo.findAll().get(lordRepo.findAll().size() - 1).getId();
        try {
            planetService.deletePlanet(id + 1000L);
            fail("Excepted PlanetException");
        } catch (PlanetException e) {
            e.printStackTrace();
        }

        try {
            planetService.deletePlanet(-20L);
            fail("Excepted PlanetException");
        } catch (PlanetException e) {
            e.printStackTrace();
        }

    }
}