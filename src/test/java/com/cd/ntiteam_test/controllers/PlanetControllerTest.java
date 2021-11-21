package com.cd.ntiteam_test.controllers;

import com.cd.ntiteam_test.entity.Planet;
import com.cd.ntiteam_test.repo.PlanetRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.*;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
class PlanetControllerTest {

    private final MockMvc mockMvc;
    private final PlanetRepo planetRepo;

    @Autowired
    public PlanetControllerTest(MockMvc mockMvc, PlanetRepo planetRepo) {
        this.mockMvc = mockMvc;
        this.planetRepo = planetRepo;
    }

    @Test
    void addPlanet() throws Exception {
        String name = UUID.randomUUID().toString();
        Planet planet = new Planet();
        planet.setName(name);
        Gson gson = new Gson();
        String planetJson = gson.toJson(planet);
        mockMvc.perform(post("/planet/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(planetJson))
                .andDo(print())
                .andExpect(content().string(containsString("lord")))
                .andExpect(content().string(containsString("name")))
                .andExpect(content().string(containsString("id")))
                .andExpect(status().isOk());

    }

    @Test
    void deletePlanet() throws Exception {
        Planet planet = new Planet();
        String name = UUID.randomUUID().toString();
        planet.setName(name);
        planetRepo.save(planet);
        mockMvc.perform(post("/planet/delete/" + planet.getId()))
                .andDo(print())
                .andExpect(content().string(containsString("lord")))
                .andExpect(status().isOk());
    }
}