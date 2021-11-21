package com.cd.ntiteam_test.service;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import com.cd.ntiteam_test.exception.LordException;
import com.cd.ntiteam_test.repo.LordRepo;
import com.cd.ntiteam_test.repo.PlanetRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application_test.properties")
class LordServiceTest {

    private final LordService lordService;
    private final LordRepo lordRepo;
    private final PlanetRepo planetRepo;
    private final Random random = new Random();


    @Autowired
    LordServiceTest(LordService lordService, LordRepo lordRepo, PlanetRepo planetRepo) {
        this.lordService = lordService;
        this.lordRepo = lordRepo;
        this.planetRepo = planetRepo;
    }

    @Test
    void addLord() throws LordException {
        String name = UUID.randomUUID().toString();
        for(Lord lord:lordRepo.findAll()){
            if(name.equals(lord.getName())){
                name = UUID.randomUUID().toString();
            }
        }
        int age = random.nextInt(20000000);
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        Lord lordCheck = lordService.addLord(lord);
        assertNotNull(lordCheck);
        assertNotNull(lordCheck.getAge());
        assertNotNull(lordCheck.getId());
        assertTrue(lordCheck.getAge()>0);
        assertNotEquals("", lordCheck.getName());
        assertNotNull(lordCheck.getName());
     }


    @Test
    void ruleThePlanet() throws LordException {
        String name = UUID.randomUUID().toString();
        Planet planet = new Planet();
        planet.setName(name);
        planetRepo.save(planet);
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(random.nextInt(2000000));
        lordRepo.save(lord);
        Long[] planets = {planet.getId()};
        lord = lordService.ruleThePlanet(lord.getId(),planets);
        assertNotNull(lord);
        assertNotNull(lord.getPlanets());
    }

    @Test
    void getAllLoafers() {
        String name = UUID.randomUUID().toString();
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(random.nextInt(2000000));
        lordRepo.save(lord);
        List<Lord> list = lordRepo.topYoungLord();
        assertNotNull(list);
        assertTrue(list.size()!=0);
    }

    @Test
    void topYoungLord() {
        String name = UUID.randomUUID().toString();
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(random.nextInt(2000000));
        lordRepo.save(lord);
        List<Lord> list = lordRepo.topYoungLord();
        assertNotNull(list);
        assertTrue(list.size()!=0);
    }
}