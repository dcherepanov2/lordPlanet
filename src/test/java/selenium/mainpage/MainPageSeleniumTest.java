package selenium.mainpage;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PlanetServiceForSelenium.class})
class MainPageSeleniumTest {
    private static ChromeDriver chromeDriver;
    private final Random random = new Random();

    @Autowired
    private PlanetServiceForSelenium planetServiceForSelenium;


    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\seleniumdriver2\\chromedriver.exe");
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDown() {
        chromeDriver.quit();
    }

    @Test//проверка загрузки контента на страницу
    public void testMainPageAccess() throws InterruptedException {
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage
                .callPage()
                .pause();
        assertTrue(chromeDriver.getPageSource().contains("Добавить лорда:"));
        assertTrue(chromeDriver.getPageSource().contains("Get all loafers"));
        assertTrue(chromeDriver.getPageSource().contains("Get all young lord"));
        assertTrue(chromeDriver.getPageSource().contains("Write planet"));
        assertTrue(chromeDriver.getPageSource().contains("Delete Planet"));
        assertTrue(chromeDriver.getPageSource().contains("Write name"));
        assertTrue(chromeDriver.getPageSource().contains("Write age"));
        assertTrue(chromeDriver.getPageSource().contains("Save"));
        assertTrue(chromeDriver.getPageSource().contains("Получить топ 10 молодых лордов:"));
        assertTrue(chromeDriver.getPageSource().contains("Добавить планету:"));
        assertTrue(chromeDriver.getPageSource().contains("Удалить планету:"));
        assertTrue(chromeDriver.getPageSource().contains("Назначить лорда управлять планетой:"));
    }

    @Test//проверка сохраняется ли планета через интерфейс
    public void testMainPageSavePlanet() throws InterruptedException {
        String name = UUID.randomUUID().toString();
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage
                .callPage()
                .pause()
                .savePlanet(name)
                .pause()
                .savePlanetSubmit()
                .pause();
        if(planetServiceForSelenium.getPlanetByName(name)){
            fail("Planets not add to db");
        }
    }

    @Test //проверка сохраняется ли лорд через интерфейс
    public void testMainPageSaveLord() throws InterruptedException {
        String name = UUID.randomUUID().toString();
        Integer age = random.nextInt(2000000);
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage
                .callPage()
                .pause()
                .saveLord(name,age)
                .pause()
                .submitSaveLord()
                .pause();
        if(planetServiceForSelenium.lordIsExists(name,age)){
            fail("Lord not add to db");
        }
    }

    @Test//проверка работает ли кнопка и подтягиваются ли данные через api
    public void testMainPageGetAllLoafers() throws InterruptedException {
        String name = UUID.randomUUID().toString();
        Integer age = random.nextInt(2000000000);
        MainPage mainPage = new MainPage(chromeDriver);
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        planetServiceForSelenium.getLordRepo().save(lord);
        mainPage
                .callPage()
                .pause()
                .refreshPage()
                .pause()
                .submitGetAllLoafers()
                .pause();
        assertTrue(chromeDriver.getPageSource().contains("Lord:"));
        assertTrue(chromeDriver.getPageSource().contains("id:"));
        assertTrue(chromeDriver.getPageSource().contains("name:"));
        assertTrue(chromeDriver.getPageSource().contains("age:"));
        assertTrue(chromeDriver.getPageSource().contains(name));
        assertTrue(chromeDriver.getPageSource().contains(age.toString()));
    }

    @Test//проверка работает ли кнопка и подтягиваются ли данные через api
    public void testMainPageYoungLord() throws InterruptedException {
        String name = UUID.randomUUID().toString();
        Integer age = random.nextInt(2000000000);
        MainPage mainPage = new MainPage(chromeDriver);
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        planetServiceForSelenium.getLordRepo().save(lord);
        mainPage
                .callPage()
                .pause()
                .refreshPage()
                .pause()
                .submitYoungLord()
                .pause();
        assertTrue(chromeDriver.getPageSource().contains("Lord:"));
        assertTrue(chromeDriver.getPageSource().contains("id:"));
        assertTrue(chromeDriver.getPageSource().contains("name:"));
        assertTrue(chromeDriver.getPageSource().contains("age:"));
    }

    @Test//проверка добавляется планету
    public void testMainRuleLord() throws InterruptedException {
        String name = UUID.randomUUID().toString();
        Integer age = random.nextInt(2000000000);
        MainPage mainPage = new MainPage(chromeDriver);
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        planetServiceForSelenium.getLordRepo().save(lord);
        Planet planet = new Planet();
        planet.setName(name);
        planetServiceForSelenium.getPlanetRepo().save(planet);
        mainPage
                .callPage()
                .pause()
                .rulePlanet(lord.getId(),planet.getId())
                .pause();
        Lord lordCheck = planetServiceForSelenium.getLordRepo().findByIdCustom(lord.getId());
        if(lordCheck.getPlanets() == null)
            fail("Lord not rule planet to db");
    }

    @Test
    public void testMainDeletePlanet() throws InterruptedException {
        List<Planet> planets = planetServiceForSelenium.getPlanetRepo().findAll();
        Planet planet = planets.get(planets.size()-1);
        MainPage mainPage = new MainPage(chromeDriver);
        mainPage
                .callPage()
                .pause()
                .deletePlanet(planet.getId())
                .pause()
                .submitDeletePlanet();
        if(planetServiceForSelenium.getPlanetRepo().findByIdCustom(planet.getId())!=null){
            fail("Planet is not delete from db");
        }
    }

}