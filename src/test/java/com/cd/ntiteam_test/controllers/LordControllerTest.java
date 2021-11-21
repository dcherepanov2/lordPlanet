package com.cd.ntiteam_test.controllers;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import com.cd.ntiteam_test.repo.LordRepo;
import com.cd.ntiteam_test.repo.PlanetRepo;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
class LordControllerTest {

    private final PlanetRepo planetRepo;

    private final LordRepo lordRepo;

    private final MockMvc mockMvc;

    private final Random random = new Random();

    @Autowired
    LordControllerTest(PlanetRepo planetRepo, LordRepo lordRepo, MockMvc mockMvc) {
        this.planetRepo = planetRepo;
        this.lordRepo = lordRepo;
        this.mockMvc = mockMvc;
    }

    @Test
    void allLoafers() throws Exception{
        String name = UUID.randomUUID().toString();
        Lord lord = new Lord();
        lord.setAge(random.nextInt(200000000));
        lord.setName(name);
        lordRepo.save(lord);
        mockMvc.perform(get("/lord/allLoafers"))
                        .andDo(print())
                        .andExpect(content().string(containsString("lords")))
                        .andExpect(content().string(containsString("lord")))
                        .andExpect(status().isOk());
    }

    @Test
    void topYoungLord() throws Exception {
        String name = UUID.randomUUID().toString();
        Lord lord = new Lord();
        lord.setAge(random.nextInt(200000000));
        lord.setName(name);
        lordRepo.save(lord);
        mockMvc.perform(get("/lord/youngLord"))
                .andDo(print())
                .andExpect(content().string(containsString("lords")))
                .andExpect(content().string(containsString("lord")))
                .andExpect(status().isOk());
    }

    @Test
    void addLord() throws Exception {
        String name = UUID.randomUUID().toString();
        Integer age = random.nextInt(200000000);
        Gson gson = new Gson();
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        String lordJson = gson.toJson(lord);
        mockMvc.perform(post("/lord/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(lordJson))
                .andDo(print())
                .andExpect(content().string(containsString("lord")))
                .andExpect(status().isOk());
    }

    @Test
    void ruleThePlanet() throws Exception {
        String name = UUID.randomUUID().toString();
        Planet planet = new Planet();
        planet.setName(name);
        planetRepo.save(planet);
        name = UUID.randomUUID().toString();
        Lord lord = new Lord();
        lord.setAge(random.nextInt(200000000));
        lord.setName(name);
        lordRepo.save(lord);
        mockMvc.perform(post("/lord/rule/"+lord.getId()).param("planets", String.valueOf(planet.getId())))
                .andDo(print())
                .andExpect(content().string(containsString("lord")))
                .andExpect(status().isOk());
        lord.setPlanets(null);
        lordRepo.save(lord);
        planetRepo.delete(planet);
        lordRepo.delete(lord);
    }
}