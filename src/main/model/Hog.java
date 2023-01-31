package model;

// a more specific pig class that extends animal
public class Hog extends Animal {
    public Hog(String name) {
        super(name);
    }

    // overloading constructor
    public Hog(String name, int health, boolean alive) {
        super(name, health, alive);
    }


    // EFFECTS: returns string of animal status depending on boolean alive
    @Override
    public String checkOnAnimal() {
        if (alive) {
            return "Animal that Oink Oinks : " + this.returnName() + " has " + animalHealth + " Health.";

        } else {
            return "Sorry your Pig " + this.returnName() + " has died due to negligence.";
        }
    }
}
