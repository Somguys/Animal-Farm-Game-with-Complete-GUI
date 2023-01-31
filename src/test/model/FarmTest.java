package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class FarmTest {
    Farm myFarm;
    Animal animal1;
    Animal animal2;
    Animal animal3;
    Animal animal4;
    Animal animal5;
    Animal animal6;
    Animal animal7;
    Animal animal8;
    Animal animal9;
    Animal animal10;
    Animal animal11;


    @BeforeEach
    public void runBefore() {
        myFarm = new Farm();
        animal1 = new Rooster("generic1");
        animal2 = new Rooster("generic2");
        animal3 = new Rooster("generic3");
        animal4 = new Rooster("generic4");
        animal5 = new Cow("generic5");
        animal6 = new Cow("generic6");
        animal7 = new Cow("generic7");
        animal8 = new Cow("generic8");
        animal9 = new Cow("generic9");
        animal10 = new Cow("generic10");
        animal11 = new Cow("generic11");

        myFarm.addAnimal(animal1);
        myFarm.addAnimal(animal2);
        myFarm.addAnimal(animal3);
        myFarm.addAnimal(animal4);
        myFarm.addAnimal(animal5);
        myFarm.addAnimal(animal6);
        myFarm.addAnimal(animal7);
        myFarm.addAnimal(animal8);
        myFarm.addAnimal(animal9);
        myFarm.addAnimal(animal10);

    }

    @Test
    void getAnimal() {
        assertEquals(animal1, myFarm.getAnimalOfIndex(0));

    }

    @Test
    void farmOverloadedConstructor() {
        Farm newFarm = new Farm(12);
        assertEquals(12, newFarm.farmGetMaxSize());
        assertEquals(0, newFarm.farmGetSize());
    }

    @Test
    void addAnimalOutofBounds() {
        assertFalse(myFarm.addAnimal(animal11));

    }


    @Test
    void display() {
        ArrayList<Animal> testDisplay = new ArrayList<>();
        testDisplay.add(animal1);
        testDisplay.add(animal2);
        testDisplay.add(animal3);
        testDisplay.add(animal4);
        testDisplay.add(animal5);
        testDisplay.add(animal6);
        testDisplay.add(animal7);
        testDisplay.add(animal8);
        testDisplay.add(animal9);
        testDisplay.add(animal10);


        assertEquals(testDisplay, myFarm.getMyFarm());


    }

    @Test
    void addAnimal() {
        myFarm.addAnimal(animal3);
        assertEquals(animal3, myFarm.getAnimalOfIndex(2));
        myFarm.removeAnimal(0);
        assertTrue(myFarm.addAnimal(animal11));
    }

    @Test
    void removeAnimal() {
        myFarm.removeAnimal(0);
        assertEquals(animal2, myFarm.getAnimalOfIndex(0));
    }

    @Test
        // also tests toJson() in Farm Class in the process
    void animalstoJson() {
        JSONObject json = myFarm.toJson();
        JSONArray jsonArray = json.getJSONArray("Animals");
        JSONObject object0 = jsonArray.getJSONObject(0);
        System.out.println(json.toString(4));
        assertEquals(10, json.getInt("Farm Size"));
        assertEquals(jsonArray, json.getJSONArray("Animals"));
        assertEquals("generic1", object0.getString("name"));
        assertEquals(100, object0.getInt("health"));
        assertTrue(object0.getBoolean("Alive?"));

        // assertEquals("class model.Rooster",object0.get("class"));
        // org.opentest4j.AssertionFailedError:
        //Expected :class model.Rooster
        //Actual   :class model.Rooster
    }
}
