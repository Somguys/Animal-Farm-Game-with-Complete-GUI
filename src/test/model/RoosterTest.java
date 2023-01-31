package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RoosterTest {
    Animal animal1;
    Animal animal2;
    Animal animal3;

    @BeforeEach
    public void runBefore() {
        animal1 = new Rooster("SkinnyChicken1");
        animal2 = new Rooster("SkinnyChicken2");
        animal3 = new Rooster("SkinnyChicken3", 80, false);
    }

    @Test
    void testConstructor() {
        assertEquals("SkinnyChicken1", animal1.returnName());
        assertEquals(100, animal1.getAnimalHealth());

    }

    @Test
    void testOverloadedConstructor() {

        assertEquals("SkinnyChicken3", animal3.returnName());
        assertEquals(80, animal3.getAnimalHealth());
        assertFalse(animal3.isAlive());

    }


    @Test
    void checkOnChickenAlive() {
        assertEquals("Animal that cock-a-doodle-doos: SkinnyChicken1 has 100 Health.", animal1.checkOnAnimal());
    }

    @Test
    void checkOnChickenDed() {
        for (int i = 0; i < 100; i++) {
            animal2.ignoreAnimal();
        }
        assertEquals("Sorry your Rooster SkinnyChicken2 has died due to negligence.", animal2.checkOnAnimal());
    }
}
