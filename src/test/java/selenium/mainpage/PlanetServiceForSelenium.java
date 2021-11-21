package selenium.mainpage;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import com.cd.ntiteam_test.repo.LordRepo;
import com.cd.ntiteam_test.repo.PlanetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

@Component
@ComponentScan("com.cd.ntiteam_test")
@TestPropertySource("/application.properties")
public class PlanetServiceForSelenium {
    private final PlanetRepo planetRepo;

    public LordRepo getLordRepo() {
        return lordRepo;
    }

    private final LordRepo lordRepo;

    @Autowired
    public PlanetServiceForSelenium(PlanetRepo planetRepo, LordRepo lordRepo) {
        this.planetRepo = planetRepo;
        this.lordRepo = lordRepo;
    }

    public PlanetRepo getPlanetRepo() {
        return planetRepo;
    }

    public boolean lordIsExists(String name, Integer age){
        Lord lord = lordRepo.isExists(name,age);
        return lord == null;
    }
    public boolean getPlanetByName(String name){
        Planet planet = planetRepo.findByName(name);
        return planet == null;
    }
}
