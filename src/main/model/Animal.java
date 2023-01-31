package model;

import org.json.JSONObject;
import persistence.Writable;

// creates a generic class with implemented methods for use
public abstract class Animal implements Writable {
    protected final int starvationRate = 3;
    protected final int recoverRate = 4;
    int animalHealth;
    boolean alive;
    private String name;

    // (advanced) may insert another field for animal stage, where when certain
    // number of animals reach ex. level 2, farm size increase..
    // can use SetLevel - example of setting constructor but for individual fields like Level
    // if 80% of animals or level 2 etc... farm increase in size, use SetSize also


    // overloading constructor for different use (re-creating state via JSON)
    public Animal(String name, int health, boolean alive) {
        this.name = name;
        this.animalHealth = health;
        this.alive = alive;
    }

    // constructor sets name and health to 100, and alive is true
    public Animal(String name) {
        this.name = name;
        this.animalHealth = 100;
        this.alive = true;
    }


    // MODIFIES: this
    // EFFECTS: returns true is animal is alive and subtract starvation rate from animalHealth else return false
    public boolean ignoreAnimal() {
        if (animalHealth > 0 && alive) {
            animalHealth -= starvationRate;
            if (animalHealth <= 0) {
                animalHealth = 0;
            }
            return true;
        } else {
            alive = false;


            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: returns true if animal is alive and can be fed, adds recoverRate to animalHealth, otherwise return false
    public boolean feedAnimal() {
        if (!alive) {
            return false;
        } else if (animalHealth < 100) {
            animalHealth = Math.min(animalHealth + recoverRate, 100);
            EventLog.getInstance().logEvent(new Event("Fed animal: " + this.returnName()));
        }
        return true;
    }

    // EFFECTS: returns string of animal status
    public String checkOnAnimal() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: returns JSONObject with key value pairs of animal
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("health", animalHealth);
        json.put("Alive?", alive);
        return json;
    }

    public boolean isAlive() {
        return alive;
    }

    public String returnName() {
        return name;
    }

    public int getAnimalHealth() {
        return animalHealth;
    }
}
