package co.edu.uniquindio.ingesis.seguridad.test.test;

import co.edu.uniquindio.ingesis.seguridad.test.pageObjects.Login;
import com.github.javafaker.Faker;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.After;
import org.junit.Before;
//import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
public class RegistrationTest {

    @Autowired
    static WebDriver driver;

    @Autowired
    static Login login;

    private static final String CHROME_DRIVER_PATH = "C:\\Users\\TecnoSystem\\Documents\\U 2024\\chromedriver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String RESPONSE_SCHEMA_FILE = "response-schema.json";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        driver = getDriver();
        driver.manage().window().maximize();
        login = new Login(driver);
    }

    @Test
    public void testSuccessfulRegistration() throws InterruptedException, IOException {
        login.navigate();
        login.writeUsername("cristian amaya");
        login.writeUserpassword("cjamayap@uqvirtual.edu.co");
        driver.findElement(By.name("signup-button")).click();

        // Verificar el mensaje de registro exitoso
        String successMessage = driver.findElement(By.id("success-message")).getText();
        assert(successMessage.contains("¡Registro exitoso!"));

        // Validar la estructura de la respuesta con JSON Schema
        validateResponseWithSchema(getApiResponse());
    }

    @Test
    public void testInvalidUsername() throws InterruptedException, IOException {
        login.navigate();
        login.writeUsername("cjamayap@uqvirtual.edu.co");
        login.writeUserpassword("24811960barqy");
        driver.findElement(By.name("login-button")).click();

        // Verificar el mensaje de error
        String errorMessage = driver.findElement(By.id("error-message")).getText();
        assert(errorMessage.contains("El nombre de usuario es obligatorio."));

        // Validar la estructura de la respuesta con JSON Schema
        validateResponseWithSchema(getApiResponse());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Método para cargar el JSON Schema desde un archivo
    private Schema loadSchema(String schemaFileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(schemaFileName)) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            return SchemaLoader.load(rawSchema);
        }
    }

    // Método para validar la estructura de la respuesta con JSON Schema
    private void validateResponseWithSchema(String response) throws IOException {
        Schema schema = loadSchema(RESPONSE_SCHEMA_FILE);
        schema.validate(new JSONObject(response));
    }

    // Método para obtener la respuesta de la API (simulado)
    private String getApiResponse() {
        // Implementar la lógica para obtener la respuesta real de la API
        return "{\"status\": \"success\"}";
    }

    // Método para generar datos aleatorios con Faker
    private String generateRandomUsername() {
        Faker faker = new Faker();
        return faker.name().username();
    }

    private static WebDriver getDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
        }
        return driver;
    }
}

