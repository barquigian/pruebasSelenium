package co.edu.uniquindio.ingesis.seguridad.test.test;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.*;
import java.nio.charset.StandardCharsets;

//@ExtendWith(SpringExtension.class) // Habilitar la integración de Spring con JUnit 5
//@SpringBootTest // Cargar la aplicación Spring
public class ApiTest {
    private final Faker faker = new Faker();

    // Método para cargar el JSON Schema desde un archivo
    private Schema loadSchema() throws IOException {
        // Obtener la referencia al archivo response-schema.json
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("response-schema.json");
        if (inputStream == null) {
            throw new FileNotFoundException("response-schema.json not found");
        }

        // Leer el contenido del archivo como String
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String schemaJson = stringBuilder.toString();

            // Crear el objeto Schema a partir del JSON
            return SchemaLoader.load(new JSONObject(schemaJson));
        }
    }

    @Test
    public void testApiResponseStructure() throws IOException {
        // Cargar el JSON Schema
        Schema schema = loadSchema();

        // Obtener la respuesta de la API
        String apiResponse = getApiResponse(); // Método para obtener la respuesta de la API

        // Validar la respuesta con el JSON Schema
        schema.validate(new JSONObject(apiResponse));
    }

    private String getApiResponse() {
        // Realizar la solicitud HTTP para obtener la respuesta de la API
        Response response = RestAssured.get("https://automationexercise.com/");

        // Verificar el código de estado de la respuesta
        if (response.getStatusCode() == 200) {
            // Si el código de estado es 200 (OK), devolver el cuerpo de la respuesta como String
            return response.getBody().asString();
        } else {
            // Si el código de estado no es 200, lanzar una excepción o manejar el error según sea necesario
            throw new RuntimeException("Error al obtener la respuesta de la API. Código de estado: " + response.getStatusCode());
        }
    }

    @Test
    public void testRandomDataGeneration() {
        Faker faker = new Faker();

        // Generar datos aleatorios
        String randomName = faker.name().firstName();
        System.out.println("Nombre generado por la prueba: " + randomName);
        String randomEmail = faker.internet().emailAddress();
        System.out.println("Nombre generado por la prueba: " + randomEmail);
        // Otras operaciones con Faker...
    }
    @Test
    public void testEmailValidity() {
        String email = faker.internet().emailAddress();
        assertTrue(isValidEmail(email));
    }

    @Test
    public void testPasswordStrength() {
        String password = faker.internet().password(8, 12);
        System.out.println(password);
        assertTrue(!isStrongPassword(password));
    }

    private boolean isValidEmail(String email) {
        // Validar que el correo electrónico generado tenga un formato válido
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }

    private boolean isStrongPassword(String password) {
        // Validar que la contraseña tenga al menos 8 caracteres y contenga caracteres especiales, letras minúsculas y mayúsculas, y números.
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
}

