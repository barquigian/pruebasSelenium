package co.edu.uniquindio.ingesis.seguridad.test.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

@Configuration
public class DriverConfig {
    private static final String CHROME_DRIVER_PATH = "C:\\Users\\TecnoSystem\\Documents\\U 2024\\chromedriver\\chromedriver_win32\\chromedriver.exe";

    @Bean
    public WebDriver driver(){
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver();
    }
}
