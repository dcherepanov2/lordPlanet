package com.cd.ntiteam_test.repo;

import com.cd.ntiteam_test.entity.Lord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application_test.properties")
class LordRepoTest {

    private final LordRepo lordRepo;
    private final Random random = new Random();

    @Autowired
    LordRepoTest(LordRepo lordRepo) {
        this.lordRepo = lordRepo;
    }

    @Test
    void findByIdCustom() {
        int age = random.nextInt(200000000);
        String name = UUID.randomUUID().toString();
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        lordRepo.save(lord);
        Lord lordCheck = lordRepo.findByIdCustom(lord.getId());
        assertNotNull(lordCheck);
        assertEquals(lordCheck.getId(),lord.getId());
    }

    @Test
    void findAllWherePlanetsNull() {
        String name = UUID.randomUUID().toString();
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(random.nextInt(200000000));
        lordRepo.save(lord);
        try {
            List<Lord> lordList = lordRepo.findAllWherePlanetsNull();
            assertNotNull(lordList);
        } catch (DataIntegrityViolationException e) {
            fail("Excepted no Exception");
            e.printStackTrace();
        }
    }

    @Test
    void topYoungLord() {
        for (int i = 0; i < 10; i++) {
            String name = UUID.randomUUID().toString();
            Lord lord = new Lord();
            lord.setName(name);
            lord.setAge(random.nextInt(200000000));
            lordRepo.save(lord);
        }
        List<Lord> lordList = lordRepo.topYoungLord();
        Assertions.assertEquals(10, lordList.size());
        for (Lord lord : lordList) {
            assertNotNull(lord.getAge());
        }
    }

    @Test
    void isExists() {
        int age = random.nextInt(200000000);
        String name = UUID.randomUUID().toString();
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        lordRepo.save(lord);
        Lord lordCheck = lordRepo.isExists(lord.getName(),lord.getAge());
        assertNotNull(lordCheck);
        assertEquals(lordCheck.getName(),lord.getName());
        assertEquals(lordCheck.getAge(),lord.getAge());
    }
}