package com.cd.ntiteam_test.controllers;

import com.cd.ntiteam_test.dto.BadRequestDto;
import com.cd.ntiteam_test.entity.Planet;
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

import java.util.List;
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
class PlanetControllerFailTest {

    private final MockMvc mockMvc;
    private final PlanetRepo planetRepo;

    @Autowired
    PlanetControllerFailTest(MockMvc mockMvc, PlanetRepo planetRepo) {
        this.mockMvc = mockMvc;
        this.planetRepo = planetRepo;
    }

    @Test
    void addPlanetFail() throws Exception {
        Gson gson = new Gson();
        Planet planet = new Planet();
        planet.setName("");

        mockMvc.perform(post("/planet/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(planet)))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(post("/planet/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(""))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        BadRequestDto badRequest = new BadRequestDto();
        badRequest.setBadParam(UUID.randomUUID().toString());

        mockMvc.perform(post("/planet/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(badRequest)))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void deletePlanetFail() throws Exception {
        List<Planet> list = planetRepo.findAll();
        long id = list.get(list.size()-1).getId()+1000L;

        mockMvc.perform(post("/planet/delete/"+id))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(post("/planet/delete/qwe"))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(post("/planet/delete/"))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());
    }
}