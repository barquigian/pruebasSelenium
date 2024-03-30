package co.edu.uniquindio.ingesis.seguridad.test.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

public enum DataGenerator {
    INSTANCE;

    private final Faker faker;

    DataGenerator() {
        faker = Faker.instance();
    }

    public String username() {
        return faker.name().username();
    }

    public String password(){
        return faker.internet().password();
    }
}
