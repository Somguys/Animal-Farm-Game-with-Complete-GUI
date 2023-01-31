package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAnimal(String name, int health, boolean alive, Animal animal) {
        assertEquals(name, animal.returnName());
        assertEquals(health, animal.getAnimalHealth());
        assertEquals(alive, animal.isAlive());
    }
}
