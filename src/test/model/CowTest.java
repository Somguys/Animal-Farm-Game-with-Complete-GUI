package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


public class CowTest {
    Animal animal1;
    Animal animal2;
    Animal animal3;
    Cow animal4;
    Animal animal5;
    Animal animal6;

    @BeforeEach
    public void runBefore() {
        animal1 = new Cow("FatCow1");
        animal2 = new Cow("FatCow2", 0, false);
        animal3 = new Cow("FatCow3");
        animal4 = new Cow("FatCow4");
        animal5 = new Hog("Hungrypig",30,false);
        animal6 = new Rooster("FastChicken",-10,true);

    }


    @Test
    void testConstructor() {
        assertEquals("FatCow1", animal1.returnName());
        assertEquals(100, animal1.getAnimalHealth());

    }

    @Test
    void testOverloadedConstructor() {
        assertEquals("FatCow2", animal2.returnName());
        assertEquals(0, animal2.getAnimalHealth());
        assertFalse(animal2.isAlive());
    }


    @Test
    void checkAlive() {
        assertTrue(animal1.isAlive());
        for (int i = 0; i < 100; i++) {
            animal1.ignoreAnimal();
        }
        assertFalse(animal1.isAlive());
    }

    @Test
    void checkOnCowAlive() {
        assertEquals("Animal that Moooos: FatCow1 has 100 Health.", animal1.checkOnAnimal());
    }

    @Test
    void checkOnCowDed() {
        for (int i = 0; i < 100; i++) {
            animal2.ignoreAnimal();
        }
        assertEquals(" Sorry your Cow FatCow2 has died due to negligence.", animal2.checkOnAnimal());
    }


    @Test
    void feedAnimal() {
        assertTrue(animal1.feedAnimal());
        for (int i = 0; i < 100; i++) {
            animal1.ignoreAnimal();
        }
        assertFalse(animal1.feedAnimal());
    }

    @Test
    void feedAnimalCountHealth() {
        assertTrue(animal1.feedAnimal());
        animal1.ignoreAnimal();
        animal1.ignoreAnimal();
        animal1.ignoreAnimal();
        animal1.feedAnimal();
        assertEquals(95, animal1.getAnimalHealth());
    }

    @Test
    void ignoreAnimal() {
        assertTrue(animal1.ignoreAnimal());

        for (int i = 0; i < 100; i++) {
            animal1.ignoreAnimal();
        }
        assertFalse(animal1.ignoreAnimal());
    }

    @Test
    void ignoreAnimalCountHealth() {
        assertTrue(animal1.ignoreAnimal());
        assertTrue(animal1.isAlive());
        animal1.ignoreAnimal();
        assertEquals(94, animal1.getAnimalHealth());
        assertTrue(animal1.ignoreAnimal());
    }

    @Test
    void ignoreAnimalFinalCoverageBothConditions(){
        assertFalse(animal5.ignoreAnimal());
        assertFalse(animal6.ignoreAnimal());
        assertTrue(animal4.ignoreAnimal());


    }

    @Test
    void testSuperCheckOnAnimal() {
        assertNull(animal4.superCheckOnAnimal());
    }

    @Test
    void ToJson() {
        JSONObject json = animal2.toJson();
        assertEquals("FatCow2", json.getString("name"));
        assertEquals(0, json.getInt("health"));
        assertFalse(json.getBoolean("Alive?"));


    }


}
