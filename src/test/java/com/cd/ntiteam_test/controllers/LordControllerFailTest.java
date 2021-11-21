package com.cd.ntiteam_test.controllers;

import com.cd.ntiteam_test.dto.BadRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
class LordControllerFailTest {

    private final PlanetRepo planetRepo;

    private final LordRepo lordRepo;

    private final MockMvc mockMvc;

    private final Random random = new Random();

    @Autowired
    LordControllerFailTest(PlanetRepo planetRepo, LordRepo lordRepo, MockMvc mockMvc) {
        this.planetRepo = planetRepo;
        this.lordRepo = lordRepo;
        this.mockMvc = mockMvc;
    }

    @Test
    void addLordFail() throws Exception{
        String name = UUID.randomUUID().toString();
        Integer age = random.nextInt(200000000);
        Gson gson = new Gson();
        Lord lord = new Lord();
        lord.setAge(age);
        lord.setName(name);
        lordRepo.save(lord);
        mockMvc.perform(post("/lord/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(lord)))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        lord.setAge(null);
        lord.setName("");
        mockMvc.perform(post("/lord/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(lord)))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        lord.setAge(158);
        lord.setName(null);
        String query = gson.toJson(lord).replace("158","");
        mockMvc.perform(post("/lord/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(query))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        query = gson.toJson(lord).replace("158","");
        mockMvc.perform(post("/lord/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(query))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());
        lord.setName("");

        query = gson.toJson(lord).replace("158","qweqwe");
        mockMvc.perform(post("/lord/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(query))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());


        mockMvc.perform(post("/lord/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(""))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        BadRequestDto badRequest = new BadRequestDto();
        badRequest.setBadParam(UUID.randomUUID().toString());

        mockMvc.perform(post("/lord/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(badRequest)))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void ruleThePlanetFailTest() throws Exception {
        String name = UUID.randomUUID().toString();
        Integer age = random.nextInt(200000000);
        Lord lord = new Lord();
        lord.setAge(age);
        lord.setName(name);
        Planet planet = new Planet();
        planet.setName(name);
        planetRepo.save(planet);
        mockMvc.perform(post("/lord/rule/qqqq").param("planets","qweqwe"))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(post("/lord/rule/"+lord.getId()).param("planets",""))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(post("/lord/rule/").param("planets",""))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(status().is4xxClientError());
    }
}