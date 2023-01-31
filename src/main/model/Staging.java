package model;

import java.util.ArrayList;


// class of staging is used for holding an arraylist of initially created animal objects
public class Staging {
    // may employ use of interface for both Staging and Farm in the future
    private static ArrayList<Animal> stagingList;

    // constructor
    public Staging() {
        // creates an arrayList of Animal type to hold initially created Animals
        stagingList = new ArrayList<>();


    }
    // REQUIRES: Animal object
    // MODIFIES: this
    // EFFECTS: adds Animal to arraylist stagingList

    public void addAnimal(Animal a) {
        stagingList.add(a);
    }

    // EFFECTS: returns arraylist of Animals

    public ArrayList<Animal> getStagingList() {
        return stagingList;
    }

    // REQUIRES:positive integer from 0 to arraylist end bound
    // MODIFIES: this
    // EFFECTS: return Animal of given index
    public Animal getAnimalOfIndex(int i) {
        return stagingList.get(i);
    }
}
