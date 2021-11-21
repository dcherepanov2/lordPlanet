package com.cd.ntiteam_test.repo;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application_test.properties")
class PlanetRepoFailTest {

    private final PlanetRepo planetRepo;
    private final LordRepo lordRepo;
    private final Random random = new Random();

    @Autowired
    PlanetRepoFailTest(PlanetRepo planetRepo, LordRepo lordRepo) {
        this.planetRepo = planetRepo;
        this.lordRepo = lordRepo;
    }

    @Test
    void findByIdCustomFail() {
        List<Lord> lords = lordRepo.findAll();
        long id = lords.get(lords.size()-1).getId()+4;
        try {
            Lord lordCheck = lordRepo.findByIdCustom(id);
            assertNull(lordCheck);
        }catch (Exception e){
            Assertions.fail("Excepted No exception");
        }
    }

    @Test
    void deletePlanetFail() {
        List<Planet> planets = planetRepo.findAll();
        List<Lord> lords = lordRepo.findAll();
        long unExceptedLong = lords.get(lords.size()-1).getId()+4;
        long unExcepted = planets.get(planets.size()-1).getId()+4;
        try{
            planetRepo.setLordNullFromLordPlanet(unExceptedLong);
            planetRepo.deletePlanet(unExcepted);
        }catch (Exception e){
            Assertions.fail("Excepted No exception");
        }
    }

}