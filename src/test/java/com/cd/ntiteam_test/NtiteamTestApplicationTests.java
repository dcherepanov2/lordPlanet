package com.cd.ntiteam_test;

import com.cd.ntiteam_test.controllers.LordController;
import com.cd.ntiteam_test.controllers.PlanetController;
import com.cd.ntiteam_test.repo.LordRepo;
import com.cd.ntiteam_test.repo.PlanetRepo;
import com.cd.ntiteam_test.service.LordService;
import com.cd.ntiteam_test.service.PlanetService;
import com.cd.ntiteam_test.service.PlanetServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class NtiteamTestApplicationTests {

	private final LordRepo lordRepo;
	private final PlanetRepo planetRepo;
	private final LordService lordService;
	private final PlanetService planetService;
	private final PlanetController planetController;
	private final LordController lordController;

	@Autowired
	NtiteamTestApplicationTests(LordRepo lordRepo, PlanetRepo planetRepo, LordService lordService, PlanetService planetService, PlanetController planetController, LordController lordController) {
		this.lordRepo = lordRepo;
		this.planetRepo = planetRepo;
		this.lordService = lordService;
		this.planetService = planetService;
		this.planetController = planetController;
		this.lordController = lordController;
	}

	@Test
	void contextLoads() {
		assertNotNull(lordRepo);
		assertNotNull(planetRepo);
		assertNotNull(lordService);
		assertNotNull(planetService);
		assertNotNull(lordController);
		assertNotNull(planetController);
	}
}
