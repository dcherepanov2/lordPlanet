package com.cd.ntiteam_test.repo;

import com.cd.ntiteam_test.entity.Lord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application_test.properties")
class LordRepoFailTest {

    private final LordRepo lordRepo;
    private final Random random = new Random();

    @Autowired
    LordRepoFailTest(LordRepo lordRepo) {
        this.lordRepo = lordRepo;
    }

    @Test
    void findByIdCustomFail() {
        List<Lord> lordList = lordRepo.findAll();
        long id = lordList.get(lordList.size()-1).getId()+4;
        try {
            lordRepo.findByIdCustom(id);
        }catch (Exception e){
            Assertions.fail("Excepted No exception");
        }
    }

    @Test
    void isExists() {
        int age = random.nextInt(200000000);
        String name = UUID.randomUUID().toString();
        List<Lord> lordList = lordRepo.findAll();
        for (Lord lord:lordList){
            if(lord.getAge()!=age&& lord.getName().equals(name)){
                name = UUID.randomUUID().toString();
            }
        }
        try {
            lordRepo.isExists(name,age);
        }catch (Exception e){
            Assertions.fail("Excepted No exception");
            e.printStackTrace();
        }
    }
}