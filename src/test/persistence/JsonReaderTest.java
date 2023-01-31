package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Farm farm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFarmRoom() throws FileNotFoundException {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFarm.json");

        Farm farm = new Farm(10);
        JsonWriter writer = new JsonWriter("./data/testReaderEmptyFarm.json");
        writer.open();
        writer.write(farm);
        writer.close();
        try {
            Farm farm2 = reader.read();
            assertEquals(10, farm2.farmGetMaxSize());
            assertEquals(0, farm2.farmGetSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFarm() throws FileNotFoundException {
        JsonReader reader = new JsonReader("./data/testReaderGeneralFarm.json");
        Farm farm = new Farm(12);
        farm.addAnimal(new Hog("hungrypig", 80, true));
        farm.addAnimal(new Rooster("skinnyrooster", 0, false));
        farm.addAnimal(new Cow("Fatcow", 80, true));
        JsonWriter writer = new JsonWriter("./data/testReaderGeneralFarm.json");
        writer.open();
        writer.write(farm);
        writer.close();
        try {
            Farm farm2 = reader.read();
            assertEquals(12, farm2.farmGetMaxSize());
            List<Animal> animals = farm2.getMyFarm();
            assertEquals(3, animals.size());
            checkAnimal("hungrypig", 80, true, animals.get(0));
            checkAnimal("skinnyrooster", 0, false, animals.get(1));
            checkAnimal("Fatcow", 80, true, animals.get(2));
            assertEquals(80, animals.get(0).getAnimalHealth());
            assertEquals(0, animals.get(1).getAnimalHealth());
            assertEquals(12, farm2.farmGetMaxSize());
            assertEquals(3, farm2.farmGetSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}