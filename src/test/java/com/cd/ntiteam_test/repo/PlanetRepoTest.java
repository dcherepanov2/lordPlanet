package com.cd.ntiteam_test.repo;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application_test.properties")
class PlanetRepoTest {

    private final PlanetRepo planetRepo;
    private final LordRepo lordRepo;
    private final Random random = new Random();

    @Autowired
    PlanetRepoTest(PlanetRepo planetRepo, LordRepo lordRepo) {
        this.planetRepo = planetRepo;
        this.lordRepo = lordRepo;
    }

    @Test
    void findByIdCustom() {
        String name = UUID.randomUUID().toString();
        Planet planet = new Planet();
        planet.setName(name);
        planetRepo.save(planet);
        Planet planetCheck = planetRepo.findByIdCustom(planet.getId());
        assertNotNull(planetCheck);
        assertEquals(planet.getId(),planetCheck.getId());
        assertEquals(planet.getName(),planet.getName());
    }

    @Test
    void deletePlanet() {
        String name = UUID.randomUUID().toString();
        int age = random.nextInt(200000000);
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        lordRepo.save(lord);
        Planet planet = new Planet();
        planet.setName(name);
        planet.setLord(lord);
        planetRepo.save(planet);
        try{
            planetRepo.setLordNullFromLordPlanet(planet.getId());
            planet.setLord(null);
            planetRepo.save(planet);
            planetRepo.deletePlanet(planet.getId());
        }catch (Exception e){
            Assertions.fail("Excepted No exception");
        }
    }
}