package ui;

import model.Event;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

// GUI

public class GameInterface {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final String JSON_STORE = "./data/farm.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private final JButton createAnimalButton;
    private final JComboBox<String> comboBox1;
    private final JLabel icon;
    private final JTextField enterAnimalNameTextField;
    private final JButton feedAnimal;
    private final JButton delete;
    private final JButton savetojsonbutton;
    private final JButton loadfromjsonbutton;
    private final JList<String> list1;
    private final JPanel mainform;
    private final JLabel message;
    private Farm farm;
    private ImageIcon imageIcon1;


    public GameInterface() {

        farm = new Farm();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        JFrame frame = new JFrame("Application");
        mainform = new JPanel();

        icon = new JLabel();
        createAnimalButton = new JButton();
        String[] types = {"Cow", "Rooster", "Hog"};
        comboBox1 = new JComboBox<>(types);

        enterAnimalNameTextField = new JTextField();
        feedAnimal = new JButton();
        delete = new JButton();
        savetojsonbutton = new JButton();
        loadfromjsonbutton = new JButton();
        list1 = new JList<>();
        message = new JLabel("default message");
        list1.setVisible(true);

        callMethods(frame);


    }

    private void callMethods(JFrame frame) {
        adjustFrame(frame);
        addToForm();
        setPreferences();
        addTimer();
        initializeFirstIcon();
        changeIcon();
        createAnimalButton();
        feedAnimalButton();
        deleteButton();
        saveToJsonButton();
        loadFromJsonButton();
    }

    private void initializeFirstIcon() {
        imageIcon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/farm.png")));
        icon.setIcon(imageIcon1);
    }

    private void loadFromJsonButton() {
        loadfromjsonbutton.addActionListener(evt -> {
            try {
                farm = jsonReader.read();
                System.out.println("Loaded farm from " + JSON_STORE);
                message.setText("Loaded farm from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
                message.setText("Unable to read from file: " + JSON_STORE);
            }
            addToList();
        });
    }

    private void saveToJsonButton() {
        savetojsonbutton.addActionListener(evt -> {
            try {
                jsonWriter.open();
                jsonWriter.write(farm);
                jsonWriter.close();
                message.setText("Saved farm to " + JSON_STORE);
                System.out.println("Saved farm to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
                message.setText("Unable to write to file: " + JSON_STORE);
            }

            addToList();
        });
    }

    private void deleteButton() {
        delete.addActionListener(e -> {
            if (!list1.isSelectionEmpty()) {
                int index = list1.getAnchorSelectionIndex();
                Animal animal = farm.getAnimalOfIndex(index);
                farm.removeAnimal(index);
                message.setText("deleted: " + animal.returnName());
            } else {
                message.setText("select animal to delete");
            }
        });
    }

    private void feedAnimalButton() {
        feedAnimal.addActionListener(e -> {
            if (!list1.isSelectionEmpty()) {
                int index = list1.getAnchorSelectionIndex();
                Animal animal = farm.getAnimalOfIndex(index);

                if (animal.feedAnimal()) {


                    animal.feedAnimal();
                    animal.feedAnimal();


                    message.setText("feeding");
                }

            } else {
                message.setText("select an animal to feed");
            }
        });
    }

    private void changeIcon() {
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem() == "Cow") {
                    imageIcon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/cow.png")));
                    icon.setIcon(imageIcon1);
                } else if (comboBox1.getSelectedItem() == "Rooster") {
                    imageIcon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/rooster.png")));
                    icon.setIcon(imageIcon1);
                } else {
                    imageIcon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/hog.png")));
                    icon.setIcon(imageIcon1);
                }
            }
        });
    }

    private void createAnimalButton() {
        createAnimalButton.addActionListener(e -> {


            String animal = Objects.requireNonNull(comboBox1.getSelectedItem()).toString().toLowerCase();
            String name = enterAnimalNameTextField.getText();
            createAnimalCondition(animal, name);

            addToList();
        });
    }

    private void createAnimalCondition(String animal, String name) {
        if (farm.farmGetSize() < farm.farmGetMaxSize()) {
            if (animal.contains("cow")) {
                Animal created = new Cow(name);
                farm.addAnimal(created);
                message.setText("Cow " + name + " created");
            } else if (animal.contains("rooster")) {
                Animal created = new Rooster(name);
                farm.addAnimal(created);
                message.setText("Rooster " + name + " created");
            } else if (animal.contains("hog")) {
                Animal created = new Hog(name);
                farm.addAnimal(created);
                message.setText("Hog " + name + " created");
            } else {
                message.setText("invalid selection");
                System.out.println("invalid selection");
            }
        } else {
            message.setText("Maximum Farm Size of " + farm.farmGetMaxSize() + " reached");
        }
    }

    private void setPreferences() {
        createAnimalButton.setPreferredSize(new Dimension(100, 50));
        createAnimalButton.setText("create");
        comboBox1.setPreferredSize(new Dimension(100, 50));


        enterAnimalNameTextField.setPreferredSize(new Dimension(100, 50));
        enterAnimalNameTextField.setText("enter name");
        feedAnimal.setPreferredSize(new Dimension(100, 50));
        feedAnimal.setText("Feed");
        delete.setText("Delete");
        delete.setPreferredSize(new Dimension(100, 50));
        savetojsonbutton.setPreferredSize(new Dimension(100, 50));
        savetojsonbutton.setText("Save to JSON");
        loadfromjsonbutton.setPreferredSize(new Dimension(100, 50));
        loadfromjsonbutton.setText("Load from JSON");

        list1.setPreferredSize(new Dimension(300, 600));
        message.setPreferredSize(new Dimension(500, 50));
    }

    private void adjustFrame(JFrame frame) {
        frame.setContentPane(mainform);
        frame.setTitle("Animal Farm Game");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog log = EventLog.getInstance();
                for (Event next : log) {
                    System.out.println(next.toString());
                }
                System.exit(0);
            }
        });
    }

    private void addToForm() {
        mainform.setVisible(true);
        mainform.add(icon);
        mainform.add(createAnimalButton);
        mainform.add(comboBox1);

        mainform.add(enterAnimalNameTextField);
        mainform.add(feedAnimal);
        mainform.add(delete);
        mainform.add(savetojsonbutton);
        mainform.add(loadfromjsonbutton);
        mainform.add(list1);
        mainform.add(message);
    }

    // MODIFIES: this
    // EFFECTS: creates a default list model and then extracts animals from farm arraylist of type Animal,
    // and prints out the values as
    // strings then sets defaultlistmodel to Jlist to be displayed
    private void addToList() {
        DefaultListModel<String> dlm = new DefaultListModel<>();

        for (Animal p : farm.getMyFarm()) {
            dlm.addElement(p.getClass().toString().substring(
                    p.getClass().toString().indexOf(".") + 1).trim()
                    + " |Name: " + p.returnName() + "|Alive: " + p.isAlive()
                    + "|Health: " + p.getAnimalHealth());
        }
        list1.setModel(dlm);

    }

    // MODIFIES: this
    // EFFECTS: creates a timer for Java GUI program to run and continuously update list and Health of farm animals
    private void addTimer() {
        Timer t = new Timer(3000, ae -> {


            for (Animal an : farm.getMyFarm()) {
                an.ignoreAnimal();
            }

            addToList();

        });

        t.start();
    }


}

