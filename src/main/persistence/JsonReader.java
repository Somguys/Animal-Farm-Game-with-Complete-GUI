package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// citation: code partially from JsonSerializationDemo

// Represents a reader that reads farm from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads farm from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Farm read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFarm(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses farm from JSON object and returns it
    private Farm parseFarm(JSONObject jsonObject) {
        int size = jsonObject.getInt("Farm Size");
        Farm farm = new Farm(size);
        addAnimals(farm, jsonObject);
        return farm;
    }

    // MODIFIES: farm
    // EFFECTS: parses animals from JSON object and adds them to farm
    private void addAnimals(Farm farm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Animals");
        for (Object json : jsonArray) {
            JSONObject nextAnimal = (JSONObject) json;
            addAnimal(farm, nextAnimal);
        }
    }

    // MODIFIES: farm
    // EFFECTS: parses animal from JSON object and adds it to farm
    private void addAnimal(Farm farm, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int health = jsonObject.getInt("health");
        boolean alive = jsonObject.getBoolean("Alive?");
        String type = jsonObject.getString("class");
        if (type.contains("Hog")) {
            Animal animal = new Hog(name, health, alive);
            farm.addAnimal(animal);
        } else if (type.contains("Rooster")) {
            Animal animal = new Rooster(name, health, alive);
            farm.addAnimal(animal);
        } else if (type.contains("Cow")) {
            Animal animal = new Cow(name, health, alive);
            farm.addAnimal(animal);
        }

    }
}
