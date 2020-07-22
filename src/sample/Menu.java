package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Menu {
    public static double x = 0;
    public static double y = 0;

    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Menu");

        Label label_0 = new Label("Chose your race");
        label_0.setFont(new Font(14));

        Label label_1 = new Label("Inserts cords");
        label_1.setFont(new Font(14));
        TextField textField_1 = new TextField();
        textField_1.setFont(new Font(14));
        TextField textField_2 = new TextField();
        textField_2.setFont(new Font(14));

        ComboBox race = new ComboBox();
        race.getItems().addAll("Alien", "Human");

        RadioButton selectShip = new RadioButton("Ship");
        selectShip.setFont(new Font(14));
        RadioButton selectBattleship = new RadioButton("Battleship");
        selectBattleship.setFont(new Font(14));
        RadioButton selectDreadnought = new RadioButton("Dreadnought");
        selectDreadnought.setFont(new Font(14));
        ToggleGroup group = new ToggleGroup();
        selectShip.setToggleGroup(group);
        selectShip.setSelected(true);
        selectBattleship.setToggleGroup(group);
        selectDreadnought.setToggleGroup(group);

        Button okButton = new Button("Ok");
        okButton.setFont(new Font(14));

        okButton.setOnAction(event -> {

            RadioButton selection = (RadioButton) group.getSelectedToggle();
            x = Double.parseDouble(textField_1.getText());
            y = Double.parseDouble(textField_2.getText());
            String raceStr = race.getValue().toString();
            String type =  selection.getText();
            if (type.equals("Ship")){
               Main.space.add(raceStr, x, y, 1);
            }
            if (type.equals("Battleship")){
                Main.space.add(raceStr, x, y, 2);
            }
            if (type.equals("Dreadnought")){
                Main.space.add(raceStr, x, y, 3);
            }
            window.close();
        });

        VBox layout = new VBox(5);
        layout.getChildren().addAll
                (label_0, race, label_1, textField_1, textField_2,
                        selectShip, selectBattleship, selectDreadnought,
                        okButton);

        Scene scene = new Scene(layout, 200, 270);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void displayParade() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Na Parad!");

        CheckBox box_1 = new CheckBox("Chvert 1");
        box_1.setFont(new Font(14));
        CheckBox box_2 = new CheckBox("Chvert 2");
        box_2.setFont(new Font(14));
        CheckBox box_3 = new CheckBox("Chvert 3");
        box_3.setFont(new Font(14));
        CheckBox box_4 = new CheckBox("Chvert 4");
        box_4.setFont(new Font(14));

        Button okButton = new Button("Ok");
        okButton.setFont(new Font(14));

        okButton.setOnAction(event -> {
            boolean one = box_1.isSelected();
            boolean two = box_2.isSelected();
            boolean three = box_3.isSelected();
            boolean four = box_4.isSelected();

            for (Ship ship: Main.space.humans) {
                if(one && ship.posX < 3840 / 2 && ship.posY < 2160 / 2) {
                    ship.yes = true;
                }
                if(two && ship.posX >= 3840 / 2 && ship.posY < 2160 / 2) {
                    ship.yes = true;
                }
                if(three && ship.posX < 3840 / 2 && ship.posY >= 2160 / 2) {
                    ship.yes = true;
                }
                if(four && ship.posX >= 3840 / 2 && ship.posY >= 2160 / 2) {
                    ship.yes = true;
                }
            }
            for (Ship ship: Main.space.aliens) {
                if(one && ship.posX < 3840 / 2 && ship.posY < 2160 / 2) {
                    ship.yes = true;
                }
                if(two && ship.posX >= 3840 / 2 && ship.posY < 2160 / 2) {
                    ship.yes = true;
                }
                if(three && ship.posX < 3840 / 2 && ship.posY >= 2160 / 2) {
                    ship.yes = true;
                }
                if(four && ship.posX >= 3840 / 2 && ship.posY >= 2160 / 2) {
                    ship.yes = true;
                }
            }

            int hum = 0;
            for (Ship ship: Main.space.humans) {
                if(ship.yes && ship.level == 1 && !ship.isActive()){
                    ship.num = hum;
                    hum++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.humans) {
                if(ship.yes && ship.level == 1 && ship.isActive()){
                    ship.num = hum;
                    hum++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.humans) {
                if(ship.yes && ship.level == 2 && !ship.isActive()){
                    ship.num = hum;
                    hum++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.humans) {
                if(ship.yes && ship.level == 2 && ship.isActive()){
                    ship.num = hum;
                    hum++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.humans) {
                if(ship.yes && ship.level == 3 && !ship.isActive()){
                    ship.num = hum;
                    hum++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.humans) {
                if(ship.yes && ship.level == 3 && ship.isActive()){
                    ship.num = hum;
                    hum++;
                    ship.toParade();
                }
            }

            int ali = 0;
            for (Ship ship: Main.space.aliens) {
                if(ship.yes && ship.level == 1 && !ship.isActive()){
                    ship.num = ali;
                    ali++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.aliens) {
                if(ship.yes && ship.level == 1 && ship.isActive()){
                    ship.num = ali;
                    ali++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.aliens) {
                if(ship.yes && ship.level == 2 && !ship.isActive()){
                    ship.num = ali;
                    ali++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.aliens) {
                if(ship.yes && ship.level == 2 && ship.isActive()){
                    ship.num = ali;
                    ali++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.aliens) {
                if(ship.yes && ship.level == 3 && !ship.isActive()){
                    ship.num = ali;
                    ali++;
                    ship.toParade();
                }
            }
            for (Ship ship: Main.space.aliens) {
                if(ship.yes && ship.level == 3 && ship.isActive()){
                    ship.num = ali;
                    ali++;
                    ship.toParade();
                }
            }
            window.close();
        });

        VBox layout = new VBox(5);
        layout.getChildren().addAll(box_1, box_2, box_3, box_4, okButton);

        Scene scene = new Scene(layout, 200, 130);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void stopParade() {
        for (Ship ship: Main.space.humans) {
            if(ship.onParad) {
                ship.onParad = false;
                ship.yes = false;
            }
        }

        for (Ship ship: Main.space.aliens) {
            if(ship.onParad){
                ship.onParad = false;
                ship.yes = false;
            }
        }
    }
}