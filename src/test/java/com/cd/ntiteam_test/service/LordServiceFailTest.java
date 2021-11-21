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

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application_test.properties")
class LordServiceFailTest {

    private final LordService lordService;
    private final LordRepo lordRepo;
    private final PlanetRepo planetRepo;
    private final Random random = new Random();

    @Autowired
    public LordServiceFailTest(LordService lordService, LordRepo lordRepo, PlanetRepo planetRepo) {
        this.lordService = lordService;
        this.lordRepo = lordRepo;
        this.planetRepo = planetRepo;
    }

    @Test
    void addLordFail(){
        int age = random.nextInt(200000000);
        String name = UUID.randomUUID().toString();
        try {
            lordService.addLord(null);
            fail("Excepted LordException");
        }catch (LordException e){
            assertEquals(e.getMessage(),"Bad request, please check required fields for input.");
        }

        Lord lord = new Lord();
        lord.setName(null);
        lord.setAge(null);
        try {
            lordService.addLord(lord);
            fail("Excepted LordException");
        }catch (Exception e){
            assertEquals(e.getMessage(),"Bad request, please check required fields for input.");
        }

        lord = new Lord();
        lord.setName(name);
        lord.setAge(null);
        try {
            lordService.addLord(lord);
            fail("Excepted LordException");
        }catch (Exception e){
            assertEquals(e.getMessage(),"Bad request, please check required fields for input.");
        }

        lord = new Lord();
        lord.setName(null);
        lord.setAge(age);
        try {
            lordService.addLord(lord);
            fail("Excepted LordException");
        }catch (Exception e){
            assertEquals(e.getMessage(),"Bad request, please check required fields for input.");
        }

        lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        lordRepo.save(lord);
        try {
            lordService.addLord(lord);
            fail("Excepted LordException");
        }catch (Exception e){
            assertEquals(e.getMessage(),"An author with such data already exists.");
        }
    }

    @Test
    void ruleThePlanetFail() {
        int age = random.nextInt(200000000);
        String name = UUID.randomUUID().toString();
        Long[] planets = new Long[0];

        try {
            lordService.ruleThePlanet(2L,planets);
        }catch (LordException e){
            assertEquals(e.getMessage(),"The planets parameter is empty");
        }
        try {
            lordService.ruleThePlanet(2L,null);
        }catch (LordException e){
            assertEquals(e.getMessage(),"The planets parameter is empty");
        }
        try {
            lordService.ruleThePlanet(null,null);
        }catch (LordException e){
            assertEquals(e.getMessage(),"The planets parameter is empty");
        }
        try {
            lordService.ruleThePlanet(null,new Long[2]);
        }catch (LordException e){
            assertEquals(e.getMessage(),"Id is null");
        }

        List<Planet> allPlanets = planetRepo.findAll();
        long id = allPlanets.get(allPlanets.size()-1).getId()+2;
        Long[] allIdPlanets = {id,id+1};
        try {
            lordService.ruleThePlanet(23L,allIdPlanets);
        }catch (LordException e){
            assertTrue(e.getMessage().contains("The planet with id: ")||e.getMessage().contains("Planet with id: "));
        }

        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        lordRepo.save(lord);
        long beforeId = lord.getId();

        lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        lordRepo.save(lord);
        Planet planet = new Planet();
        planet.setName(name);
        planetRepo.save(planet);
        Long[] idPlanet = new Long[]{planet.getId()};
        try {
            lordService.ruleThePlanet(lord.getId(),idPlanet);
            lordService.ruleThePlanet(beforeId,idPlanet);
        }catch (LordException e){
            assertTrue(e.getMessage().contains("The planet with id: "));
        }

        Long[] idPlanet1 = new Long[]{planet.getId()+40};
        try {
            lordService.ruleThePlanet(lord.getId(),idPlanet1);
        }catch (LordException e){
            assertTrue(e.getMessage().contains("Planet with id: "));
        }
    }
}