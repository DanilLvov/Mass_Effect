package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class Space {
    private ImageView spaceView;
    public ArrayList<Ship> humans;
    public ArrayList<Ship> aliens;

    private Text text;
    public Rectangle rect;
    public Rectangle rect2;
    public Pane objects;
    public Planet mars;
    public Planet blue;
    private boolean sorted = false;

    public Space (Pane group) {
        objects = new Pane();
        spaceView = new ImageView(Pictures.pictures.get("space.png"));
        mars = new Planet(100, 100, objects, "Mars");
        blue = new Planet(3740 - 512, 2160 - 512, objects, "Blue");
        humans = new ArrayList<>();
        aliens = new ArrayList<>();

        rect = new Rectangle(1630, 930);
        rect.setStrokeWidth(15);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setX(-15);
        rect.setY(-15);

        rect2 = new Rectangle(3870, 2190);
        rect2.setStrokeWidth(15);
        rect2.setFill(Color.TRANSPARENT);
        rect2.setStroke(Color.WHITE);
        rect2.setX(-15);
        rect2.setY(-15);

        text = new Text ("Press F to...  change this");
        text.setFont(Font.font("Verdana", 40));
        text.setFill(Color.WHITE);
        text.setX(700);
        text.setY(400);

        add("Human",390, 356, 1);
        add("Human",356, 370, 2);
        add("Human",300, 320, 3);
        add("Alien", 3700 - 256, 2160 - 256, 1);
        add("Alien", 3540 - 256, 2090 - 256, 2);
        add("Alien", 3740 - 256, 2060 - 256, 3);
        add("Human",90, 56, 1);
        add("Human",56, 70, 2);
        add("Human",100, 20, 3);
        add("Alien", 700 - 256, 2160 - 256, 1);
        add("Alien", 540 - 256, 2090 - 256, 2);
        add("Alien", 740 - 256, 2060 - 256, 3);

        objects.getChildren().addAll(rect2, rect);
        group.getChildren().addAll(spaceView, text, objects);
    }

    public void sort() {
        Ship[] cloned = new Ship[aliens.size()];

        for (int i = 0; i < aliens.size(); i++) {
            try {
                cloned[i] = aliens.get(i).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        Ship[] copie = Arrays.copyOf(cloned, cloned.length);
        Arrays.sort(copie);
        System.out.println("Sorting by hunger:");
        text.setText("the hungriest alien has hunger: " + copie[0].hunger);
        sorted = true;
    }

    public void add(String race, double x, double y, int level) {
        if (race.equals("Human")) {
            if(level == 1){
                humans.add (new Ship(race, x, y, objects, aliens, humans));
            }
            if(level == 2){
                humans.add (new BattleShip(race, x, y, objects, aliens, humans));
            }
            if(level == 3){
                humans.add (new Dreadnought(race, x, y, objects, aliens, humans));
            }
        }
        if (race.equals("Alien")) {
            if(level == 1){
                aliens.add (new Ship(race, x, y, objects, humans, aliens));
            }
            if(level == 2){
                aliens.add (new BattleShip(race, x, y, objects, humans, aliens));
            }
            if(level == 3){
                aliens.add (new Dreadnought(race, x, y, objects, humans, aliens));
            }
        }
    }

    public void updateAll() {
        for (Ship ship: aliens) {
            ship.update();
        }

        for (Ship ship: humans) {
            ship.update();
        }

        if(humans.size() == 0) {
            text.setText("Aliens Won");
            sorted = false;
        }
        if(aliens.size() == 0) {
            text.setText("Humans Won");
            sorted = false;
        }
        if (humans.size() != 0 && aliens.size() != 0 && !sorted){
            text.setText("Press F to...  change this");
        }
    }

    public void up() {
        for (Ship ship: humans) {
            if(ship.isActive())
            ship.up();
        }
        for (Ship ship: aliens) {
            if(ship.isActive())
                ship.up();
        }
    }
    public void down() {
        for (Ship ship: humans) {
            if(ship.isActive())
            ship.down();
        }
        for (Ship ship: aliens) {
            if(ship.isActive())
                ship.down();
        }
    }
    public void left() {
        for (Ship ship: humans) {
            if(ship.isActive())
            ship.left();
        }

        for (Ship ship: aliens) {
            if(ship.isActive())
                ship.left();
        }
    }
    public void right() {
        for (Ship ship: humans) {
            if(ship.isActive())
            ship.right();
        }
        for (Ship ship: aliens) {
            if(ship.isActive())
                ship.right();
        }
    }
    public void mouse (double x, double y) {
        for (Ship ship: humans) {
            ship.mouse(x, y);
        }

        for (Ship ship: aliens) {
            ship.mouse(x, y);
        }
    }

    public void escape() {
        for (Ship ship: humans) {
            ship.deactivate();
        }
        for (Ship ship: aliens) {
            ship.deactivate();
        }
    }

    public void delete() {
        ArrayList<Ship> array = new ArrayList<>();
        for (Ship ship: humans) {
            if(ship.isActive())
            objects.getChildren().remove(ship.getGroup());
            else array.add(ship);
        }
        humans = array;
        ArrayList<Ship> array2 = new ArrayList<>();
        for (Ship ship: aliens) {
            if(ship.isActive())
            objects.getChildren().remove(ship.getGroup());
            else array2.add(ship);
        }
        aliens = array2;
    }

    public void fromPlanet() {
        for (Ship ship: humans) {
            ship.from();
        }

        for (Ship ship: aliens) {
            ship.from();
        }
    }

    public void toPlanet() {
        for (Ship ship: humans) {
            if(ship.isActive())
            ship.to();
        }

        for (Ship ship: aliens) {
            if(ship.isActive())
            ship.to();
        }
    }

    public void change() {
        for (Ship ship: humans) {
                ship.change();
        }

        for (Ship ship: aliens) {
                ship.change();
        }
    }
}
