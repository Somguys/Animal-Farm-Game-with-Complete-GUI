package model;

// a more specific rooster class that extends animal
public class Rooster extends Animal {

    public Rooster(String name) {
        super(name);
    }

    // overloading constructor
    public Rooster(String name, int health, boolean alive) {
        super(name, health, alive);
    }


    // EFFECTS: returns string of animal status depending on boolean alive
    @Override
    public String checkOnAnimal() {
        if (alive) {
            return "Animal that cock-a-doodle-doos: " + this.returnName() + " has " + animalHealth + " Health.";

        } else {
            return "Sorry your Rooster " + this.returnName() + " has died due to negligence.";
        }
    }
}
