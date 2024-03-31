package co.edu.uniquindio.ingesis.seguridad.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Login {

    private static final String BASE_URL = "https://automationexercise.com/"; // Cambiar según la URL de tu API

    @Autowired
    WebDriver driver;

    public Login(WebDriver driver) {
        this.driver=driver;
    }

    public void writeUsername(String username) throws InterruptedException {
        driver.findElement(By.name("email")).sendKeys(username);
        Thread.sleep(1000);
    }
    public void writeUserpassword(String password) throws InterruptedException {
        driver.findElement(By.name("password")).sendKeys(password);
        Thread.sleep(1000);
    }
    public void navigate(){
        driver.get(BASE_URL+ "/login");

    }
}
