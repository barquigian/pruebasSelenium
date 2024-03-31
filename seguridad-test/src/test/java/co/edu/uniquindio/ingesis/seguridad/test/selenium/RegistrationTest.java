package co.edu.uniquindio.ingesis.seguridad.test.selenium;

import co.edu.uniquindio.ingesis.seguridad.test.pageObjects.Login;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;
import com.github.javafaker.Faker;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegistrationTest {
    @Autowired
    static WebDriver driver;
    @Autowired
    static Login login;
    private static final String CHROME_DRIVER_PATH = "C:\\Users\\TecnoSystem\\Documents\\U 2024\\chromedriver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";

    public static WebDriver getDriver() {
        if (driver == null) {
            // Configurar la ubicación del chromedriver
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            ChromeOptions options = new ChromeOptions();
            // Inicializar el WebDriver
            driver = new ChromeDriver(options);
        }
        return driver;
    }
    @Before
    public void setUp() {
        // Configuración del WebDriver
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        //driver = getDriver();
        driver.manage().window().maximize();
        // Inicializar la instancia de Login
        login = new Login(driver);
    }

    @Test
    public void testSuccessfulRegistration() throws InterruptedException {
        // Navegar a la página de registro
        login.navigate();
        login.writeUsername("cjamayap@uqvirtual.edu.co");
        login.writeUserpassword("24811960barqy");
        driver.findElement(By.name("login-button")).click();

        // Verificar el mensaje de registro exitoso
        String successMessage = driver.findElement(By.id("success-message")).getText();
        assert(successMessage.contains("¡Registro exitoso!"));
        // Validar la estructura de la respuesta con JSON Schema
        //validateResponseWithSchema(getApiResponse());
    }

    @Test
    public void testInvalidUsername() throws InterruptedException {
        // Navegar a la página de registro
        login.navigate();
        // Completar el formulario de registro con un nombre de usuario inválido
        login.writeUsername("cjamayap@uqvirtual.edu.co");
        login.writeUserpassword("24811960barqy");
        driver.findElement(By.name("login-button")).click();

        // Verificar el mensaje de error
        String errorMessage = driver.findElement(By.id("error-message")).getText();
        assert(errorMessage.contains("El nombre de usuario es obligatorio."));
    }

    @After
    public void tearDown() {
        // Cerrar el navegador al finalizar las pruebas
        if (driver != null) {
            driver.quit();
        }
    }
}

