package selenium.mainpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPage {

    private final ChromeDriver chromeDriver;
    private final String url = "http://localhost:8090/";

    public MainPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public MainPage refreshPage(){
        chromeDriver.navigate().refresh();
        return this;
    }

    public MainPage savePlanet(String name) {
        WebElement element = chromeDriver.findElement(By.id("nameForSavePlanet"));
        element.sendKeys(name);
        return this;
    }

    public MainPage savePlanetSubmit() {
        WebElement button = chromeDriver.findElement(By.id("buttonSavePlanet"));
        button.click();
        return this;
    }


    public MainPage callPage() {
        chromeDriver.get(url);
        return this;
    }

    public MainPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public MainPage saveLord(String name, Integer age) {
        WebElement nameLord = chromeDriver.findElement(By.id("inputNameLord"));
        nameLord.sendKeys(name);
        WebElement ageLord = chromeDriver.findElement(By.id("inputAgeLord"));
        ageLord.sendKeys(age.toString());
        return this;
    }

    public MainPage submitSaveLord(){
        WebElement button = chromeDriver.findElement(By.id("inputButtonLord"));
        button.click();
        return this;
    }

    public MainPage submitGetAllLoafers() {
        WebElement button = chromeDriver.findElement(By.id("buttonHidden"));
        button.click();
        return this;
    }

    public MainPage submitYoungLord() {
        WebElement button = chromeDriver.findElement(By.id("buttonHidden2"));
        button.click();
        return this;
    }

    public MainPage rulePlanet(Long idLord, Long idPlanets) {
        WebElement inputIdLordRule = chromeDriver.findElement(By.id("inputIdLordRule"));
        inputIdLordRule.sendKeys(idLord.toString());
        WebElement inputIdPlanetsRule = chromeDriver.findElement(By.id("inputIdPlanetsRule"));
        inputIdPlanetsRule.sendKeys(idPlanets.toString());
        return this;
    }

    public MainPage deletePlanet(Long id) {
        WebElement inputDeleteId = chromeDriver.findElement(By.id("inputDeleteId"));
        inputDeleteId.sendKeys(id.toString());
        return this;
    }

    public MainPage submitDeletePlanet(){
        WebElement inputDeleteButton = chromeDriver.findElement(By.id("inputDeleteButton"));
        inputDeleteButton.click();
        return this;
    }
}
