package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class StagingTest {
    Staging stagingList;
    Animal animal1;
    Animal animal2;
    Animal animal3;
    Animal animal4;


    @BeforeEach
    public void runBefore() {
        stagingList = new Staging();
        animal1 = new Rooster("generic1");
        animal2 = new Rooster("generic2");
        animal3 = new Cow("generic3");
        animal4 = new Cow("generic4");
        stagingList.addAnimal(animal1);
        stagingList.addAnimal(animal2);
        stagingList.addAnimal(animal3);
        stagingList.addAnimal(animal4);

    }

    @Test
    void getAnimal() {
        assertEquals(animal1, stagingList.getAnimalOfIndex(0));

    }

    @Test
    void display() {
        ArrayList<Animal> testDisplay = new ArrayList<>();
        testDisplay.add(animal1);
        testDisplay.add(animal2);
        testDisplay.add(animal3);
        testDisplay.add(animal4);

        assertEquals(testDisplay, stagingList.getStagingList());


    }

    @Test
    void addAnimal() {
        stagingList.addAnimal(animal3);
        assertEquals(animal3, stagingList.getAnimalOfIndex(2));
    }


}
