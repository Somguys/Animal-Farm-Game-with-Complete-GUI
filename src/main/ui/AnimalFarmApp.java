package ui;


import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.Timer;
import java.util.TimerTask;

// console UI

public class AnimalFarmApp {
    private static final String JSON_STORE = "./data/farm.json";
    private Farm farm1;
    private Staging staging;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the teller application
    public AnimalFarmApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runFarm();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runFarm() {
        boolean keepGoing = true;
        String command;

        // initial size of farm is size 10
        farm1 = new Farm(10);
        staging = new Staging();

        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n");

        while (keepGoing) {
            decayAnimalFarm();
            decaryAnimalStaging();
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }


        System.out.println("\nGoodbye!");
    }


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void processCommand(String command) {
        switch (command) {
            case "c":
                createAnimal();
                break;
            case "d":
                display();
                break;
            case "df":
                displayFarm();
                break;
            case "a":
                addAnimal();
                break;
            case "r":
                removeAnimal();
                break;
            case "f":
                feedAnimal();
                break;
            case "l":
                loadFromFile();
                break;
            case "s":
                saveToFile();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }


    private void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(farm1);
            jsonWriter.close();
            System.out.println("Saved farm to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    private void loadFromFile() {
        try {
            farm1 = jsonReader.read();
            System.out.println("Loaded farm from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    private void displayMenu() {
        System.out.println("\nWhat would you like to do:");
        System.out.println("Animals can only be fed while in the farm");
        System.out.println("\n\tc  -> create animal");
        System.out.println("\td  -> display list of created animals");
        System.out.println("\tdf  -> display list animals in farm");
        System.out.println("\ta  -> add animal to farm");
        System.out.println("\tr -> remove animal from farm");
        System.out.println("\tf -> feed animal");
        System.out.println("\ts -> save");
        System.out.println("\tl -> load");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: creates an animal of certain type
    private void createAnimal() {
        System.out.println("What type of animal would you like to create: 0 for a Cow, 1 for a Rooster, 2 for a Pig");
        Scanner typeInput = new Scanner(System.in);
        int type = typeInput.nextInt();
        if (type == 0) {
            String name = getStringName();
            Animal created = new Cow(name);
            staging.addAnimal(created);
        } else if (type == 1) {
            String name = getStringName();
            Animal created = new Rooster(name);
            staging.addAnimal(created);
        } else if (type == 2) {
            String name = getStringName();
            Animal created = new Hog(name);
            staging.addAnimal(created);
        } else {
            System.out.println("invalid selection");
        }
    }

    private String getStringName() {
        System.out.println("What would you like to name your Animal");
        Scanner input = new Scanner(System.in);
        String name = input.next();
        return name;
    }

    // EFFECTS: removes animal from list
    private void removeAnimal() {
        try {
            System.out.println("which animal would you like to remove, enter integer of position starting from zero");
            Scanner input = new Scanner(System.in);
            int index = input.nextInt();
            farm1.removeAnimal(index);
        } catch (IndexOutOfBoundsException e) {
            int size = farm1.getMyFarm().size() - 1;
            System.out.println("Enter a value between 0 and " + size);
        }
    }

    // EFFECTS: displays list of animals in staging
    private void display() {
        ArrayList<Animal> animalList = staging.getStagingList();
        for (int i = 0; i < animalList.size(); i++) {
            Animal displaying = animalList.get(i);

            if (!displaying.isAlive()) {
                System.out.println("Sadly your Animal " + displaying.returnName() + " has died due to neglect");
            } else {
                System.out.println("You Animal " + displaying.returnName() + " is still alive but you "
                        +
                        "need to remember to care for it, health: " + displaying.getAnimalHealth() + ".");
            }
            System.out.println("position: " + i);
        }
    }

    // EFFECTS: displays list of animals already placed in farm
    private void displayFarm() {
        ArrayList<Animal> animalList = farm1.getMyFarm();
        for (int i = 0; i < animalList.size(); i++) {
            Animal displaying = animalList.get(i);
            if (!displaying.isAlive()) {
                System.out.println("Sadly your animal " + displaying.returnName() + " has died due to neglect");
            } else {
                System.out.println(displaying.checkOnAnimal());
            }
            System.out.println("position: " + i);
        }
    }

    // add animal to farm
    private void addAnimal() {
        System.out.println("Which Animal would you like to add, type integer from staging starting with position 0");
        try {
            Scanner input = new Scanner(System.in);
            int index = input.nextInt();
            Animal add = staging.getAnimalOfIndex(index);
            if (farm1.addAnimal(add)) {
                System.out.println("Successfully added " + add.returnName() + " to farm");
            } else {
                System.out.println("Sorry cannot add " + add.returnName()
                        +
                        "to your farm is full, please level up to place more animals");
            }

            staging.getStagingList().remove(add);
        } catch (IndexOutOfBoundsException e) {
            int size = staging.getStagingList().size() - 1;
            System.out.println("Enter a value between 0 and " + size);

        }
    }

    // feed animal
    private void feedAnimal() {
        System.out.println("You can only feed animals contained in your farm, "
                + "which animal would you like to feed, "
                + "enter integer number representing position in farm starting from zero");
        try {
            feedAnimalScanner();
        } catch (IndexOutOfBoundsException e) {
            int size = farm1.getMyFarm().size() - 1;
            System.out.println("Enter a value between 0 and " + size);
        }
    }

    private void feedAnimalScanner() {
        Scanner input = new Scanner(System.in);
        int index = input.nextInt();
        Animal feedAnimal = farm1.getAnimalOfIndex(index);
        if (feedAnimal.feedAnimal()) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int count = 0;

                public void run() {
                    feedAnimal.feedAnimal();
                    System.out.println("Feeding Animal");
                    System.out.println(feedAnimal.returnName() + ", Health:  "
                            + feedAnimal.getAnimalHealth() + " ⬆⬆");
                    count++;
                    if (count == 5) {
                        timer.cancel();
                        timer.purge();
                    }
                }
            }, 0, 1600);
        } else {
            System.out.println("Sadly your  Animal " + feedAnimal.returnName() + " has died due to neglect");
        }
    }

    // apply starvation rate using a timer for animals in farm, longer time between application of starvation rate,
    // more time alive in farm
    private void decayAnimalFarm() {
        ArrayList<Animal> farmList = farm1.getMyFarm();

        for (Animal ani : farmList) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                public void run() {
                    ani.ignoreAnimal();
                    System.out.println("Animal " + ani.returnName() + " Health: " + ani.getAnimalHealth() + " ⬇⬇ ");

                    if (!ani.ignoreAnimal()) {
                        System.out.println("Animal " + ani.returnName() + " is ded");
                        timer.cancel();
                        timer.purge();
                    }
                }
            }, 0, 160000);
        }
    }


    // apply starvation rate using a timer for animals in staging
    private void decaryAnimalStaging() {
        ArrayList<Animal> stagingList = staging.getStagingList();
        for (Animal ani : stagingList) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    ani.ignoreAnimal();
                    System.out.println("Animal is not in farm:  " + ani.returnName()
                            + " Health: " + ani.getAnimalHealth() + " ⬇⬇ ");
                    if (!ani.ignoreAnimal()) {
                        System.out.println("Animal " + ani.returnName() + " is ded");
                        timer.cancel();
                        timer.purge();
                    }

                }
            }, 0, 80000);
        }
    }

}
