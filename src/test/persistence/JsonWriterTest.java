package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.


    @Test
    void JSONWriter() {
        JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Farm farm = new Farm();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass

        }
    }

    @Test
    void testWriterEmptyFarm() {
        try {
            Farm farm = new Farm(10);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFarm.json");
            writer.open();
            writer.write(farm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFarm.json");
            Farm farm2 = reader.read();
            assertEquals(10, farm2.farmGetMaxSize());
            assertEquals(0, farm2.farmGetSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Farm farm = new Farm();
            farm.addAnimal(new Hog("hungrypig"));
            farm.addAnimal(new Rooster("skinnyrooster"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFarm.json");
            writer.open();
            writer.write(farm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralFarm.json");
            Farm farm2 = reader.read();
            assertEquals(10, farm2.farmGetMaxSize());
            List<Animal> animals = farm2.getMyFarm();
            assertEquals(2, animals.size());
            checkAnimal("hungrypig", 100, true, animals.get(0));
            checkAnimal("skinnyrooster", 100, true, animals.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
        // without try catch // redo later
    void generalTest() throws IOException {
        Farm farm = new Farm();
        farm.addAnimal(new Hog("hungrypig"));
        farm.addAnimal(new Rooster("skinnyrooster"));
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralFarm.json");
        writer.open();
        writer.write(farm);
        writer.close();

        JsonReader reader = new JsonReader("./data/testWriterGeneralFarm.json");
        Farm farm2 = reader.read();
        assertEquals(10, farm2.farmGetMaxSize());
        List<Animal> animals = farm2.getMyFarm();
        assertEquals(2, animals.size());
        checkAnimal("hungrypig", 100, true, animals.get(0));
        checkAnimal("skinnyrooster", 100, true, animals.get(1));

    }
}