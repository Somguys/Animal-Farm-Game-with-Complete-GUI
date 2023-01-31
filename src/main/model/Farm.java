package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Class of farm creates a list of maximum size to hold/contain number of animals
// Benefits from being in farm instead of staging when there they are initially created
public class Farm implements Writable {
    private static int MAX_SIZE;

    private static ArrayList<Animal> myFarm;

    // constructor
    public Farm() {
        MAX_SIZE = 10;
        myFarm = new ArrayList<>();
    }

    // overloading constructor for different use (re-creating state via JSON)
    public Farm(int size) {
        this.MAX_SIZE = size;
        myFarm = new ArrayList<>(size);
    }

    // REQUIRES: Animal object
    // MODIFIES: this
    // EFFECTS: adds Animal to arraylist myFarm

    public boolean addAnimal(Animal a) {
        if (myFarm.size() < MAX_SIZE) {
            myFarm.add(a);
            EventLog.getInstance().logEvent(new Event("Created Animal in Farm: " + a.returnName()));
            return true;
        } else {
            return false;
        }
    }


    // REQUIRES: int >= 0 , within range of arraylist
    // MODIFIES: this
    // EFFECTS: removes Animal from arraylist myFarm

    public void removeAnimal(int a) {

        myFarm.remove(a);
        EventLog.getInstance().logEvent(new Event("Removed Animal from Farm positon: "
                + a));
    }

    // EFFECTS: returns arraylist of Animals
    public ArrayList<Animal> getMyFarm() {
        return myFarm;
    }


    // REQUIRES:positive integer from 0 to arraylist end bound
    // MODIFIES: this
    // EFFECTS: return Animal of given index
    public Animal getAnimalOfIndex(int i) {
        return myFarm.get(i);
    }

    // get max size of farm
    public int farmGetMaxSize() {
        return MAX_SIZE;
    }

    // get size of farm
    public int farmGetSize() {
        return myFarm.size();
    }

    // MODIFIES: this
    // EFFECTS: returns JSONObject with key value pairs of animal as a List, and with
    // title of Farm Size
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Farm Size", MAX_SIZE);
        json.put("Animals", animalsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray animalsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Animal t : myFarm) {
            jsonArray.put(t.toJson().put("class", t.getClass()));
        }
        return jsonArray;
    }


}
