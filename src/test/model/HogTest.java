package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HogTest {
    Animal animal1;
    Animal animal2;
    Animal animal3;

    @BeforeEach
    public void runBefore() {
        animal1 = new Hog("HungryPig1");
        animal2 = new Hog("HungryPig2", 0, false);
        animal3 = new Hog("HungryPig3", 60, true);
    }

    @Test
    void testConstructor() {
        assertEquals("HungryPig1", animal1.returnName());
        assertEquals(100, animal1.getAnimalHealth());

    }

    @Test
    void testOverloadedConstructor() {
        assertEquals("HungryPig2", animal2.returnName());
        assertEquals(0, animal2.getAnimalHealth());
        assertFalse(animal2.isAlive());
    }

    @Test
    void checkOnPigAlive() {
        assertEquals("Animal that Oink Oinks : HungryPig1 has 100 Health.", animal1.checkOnAnimal());
    }

    @Test
    void checkOnPigDed() {
        for (int i = 0; i < 100; i++) {
            animal2.ignoreAnimal();
        }
        assertEquals("Sorry your Pig HungryPig2 has died due to negligence.", animal2.checkOnAnimal());
    }
}
