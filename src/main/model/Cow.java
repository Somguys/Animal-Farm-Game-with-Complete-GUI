package model;


import org.json.JSONObject;

// a more specific cow class that extends Animal
public class Cow extends Animal {
    public Cow(String name) {
        super(name);
    }

    // overloading constructor
    public Cow(String name, int health, boolean alive) {
        super(name, health, alive);
    }


    // EFFECTS: returns string of animal status depending on boolean alive
    @Override
    public String checkOnAnimal() {
        if (alive) {
            return "Animal that Moooos: " + this.returnName() + " has " + animalHealth + " Health.";

        } else {
            return " Sorry your Cow " + this.returnName() + " has died due to negligence.";
        }
    }

    // method used for code coverage for super call on checkOnAnimal()
    public String superCheckOnAnimal() {
        return super.checkOnAnimal();
    }
}
