package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Ship implements Cloneable,  Comparable<Ship>{
    protected ImageView shipView;
    protected int health;
    protected int hunger;
    protected ArrayList<Shot> shots;

    private double speed = 2;
    protected double posX, posY;
    protected double planetX, planetY;

    protected int level;
    protected double rotation = 90;
    protected double rotationFix;

    protected boolean active = false;
    protected boolean onDuty = true;
    protected boolean otherReaction = false;
    protected boolean toPlanet = false;
    protected boolean onPlanet = false;
    protected boolean onParad = false;
    protected boolean yes = false;

    protected ArrayList<Ship> enemy;
    protected ArrayList<Ship> friends;
    protected Ship moveShip;
    private String race;
    protected Pane group;
    protected Text hungerText;
    protected Text healthText;
    protected String activePic;
    protected String notActivePic;
    protected String onPlanetPic;
    private int timer = 0;
    private int timerShot = 0;
    private Rectangle rectangle;
    private Shot ashot;

    public Ship(String race, double posX, double posY, Pane group, ArrayList<Ship> enemy, ArrayList<Ship> friends) {
        this();
        rectangle = new Rectangle(posX + 90, posY + 50, 20, 10);

        if(race.equals("Human")) {
            activePic = "human1_act.png";
            notActivePic = "human1.png";
            onPlanetPic = "human1_planet.png";
            planetX = 100 + 256;
            planetY = 100 + 256;
            rotationFix = 90;
            rectangle.setFill(Color.WHITE);
        }
        if(race.equals("Alien")) {
            activePic = "alien1_act.png";
            notActivePic = "alien1.png";
            onPlanetPic = "alien1_planet.png";
            planetX = 3740 - 256;
            planetY = 2160 - 256;
            rectangle.setFill(Color.RED);
        }

        hunger = 10;
        health = 10;

        shipView = new ImageView(Pictures.pictures.get(notActivePic));
        shipView.setX(posX);
        shipView.setY(posY);
        shipView.setRotate(rotation - rotationFix);

        this.posX = posX;
        this.posY = posY;
        this.race = race;
        this.enemy = enemy;
        this.friends = friends;
        level = 1;

        hungerText = new Text(String.valueOf(hunger));
        hungerText.setFont(Font.font("Verdana", 15));
        hungerText.setFill(Color.WHITE);
        hungerText.setX(posX + 10);
        hungerText.setY(posY + 60);

        healthText = new Text(String.valueOf(health));
        healthText.setFont(Font.font("Verdana", 15));
        healthText.setFill(Color.LAWNGREEN);
        healthText.setX(posX + 50);
        healthText.setY(posY + 60);

        this.group.getChildren().addAll(shipView, hungerText, healthText, rectangle);
        group.getChildren().add(this.group);
    }

    private Ship() {
        group = new Pane();
        shots = new ArrayList<>();
    }

    public boolean isActive(){ return active; }
    public boolean isOnDuty(){ return onDuty; }
    public boolean isOtherReaction(){ return otherReaction; }
    public boolean isToPlanet(){ return toPlanet; }
    public boolean isOnPlanet(){ return onPlanet; }
    public boolean isRemoved() { return removed; }
     public double getPosX() { return posX; }
    public double getPosY() { return posY; }
    public int getHealth() { return health; }
    public int getHunger() { return hunger; }
    public int getLevel() { return level; }
    public ImageView getView() { return shipView; }
    public Pane getGroup() { return group; }
    public String getRace() { return  race; }
    public ArrayList<Shot> getShots() { return shots; }


    public void change() { otherReaction = !otherReaction; }

    @Override
    public Ship clone() throws CloneNotSupportedException {
        Ship cloned = (Ship) super.clone();
        ArrayList<Shot> clonedBullets = new ArrayList<>();
        for (Shot shot: shots) {
            clonedBullets.add(shot.clone());
        }
        cloned.shots = clonedBullets;
        return cloned;
    }

    protected boolean removed = false;

    protected int num;
    public void toParade() {
        onParad = true;
        if(race.equals("Human")) {
            posX = 0;
        }
        else {
            posX= 3600;
        }
        posY = 165 * num;
        shipView.setX(posX);
        shipView.setY(posY);
        hungerText.setX(posX + 10);
        hungerText.setY(posY + 60);
        healthText.setX(posX + 50);
        healthText.setY(posY + 60);
        rectangle.setX(posX + 90);
        rectangle.setY(posY + 50);
    }

    public void plusHealth() {
        if(!otherReaction) {
            if(health < 10 * level) {
                health++;
                healthText.setText(String.valueOf(health));
            }
        }
        else {
            health--;
            if(health > 0) {
                healthText.setText(String.valueOf(health));
            }
            else {
                group.getChildren().clear();
                removed = true;
            }
        }
    }

    public void minusHealth() {
        if(!otherReaction) {
            health--;
            if(health > 0) {
                healthText.setText(String.valueOf(health));
            }
            else {
                group.getChildren().clear();
                removed = true;
            }
        }
        else {
            if(health < 10 * level) {
                health++;
                healthText.setText(String.valueOf(health));
            }
        }
    }
    public void update() {
        if(onDuty && !active && !removed && !onParad) {
            double min = 10000;
            for (Ship ship : friends) {
                if (!ship.removed && !ship.isOnPlanet() && ship.getHealth() < ship.getLevel() * 10 && !ship.onParad && !ship.isRemoved()) {
                    if (countDist(posX, ship.getPosX(), posY, ship.getPosY()) < min && countDist(posX, ship.getPosX(), posY, ship.getPosY()) != 0) {
                        min = countDist(posX, ship.getPosX(), posY, ship.getPosY());
                        moveShip = ship;
                    }
                }
            }

            if (min != 10000) {
                moveTo(moveShip.getPosX(), moveShip.getPosY());
                timerShot++;
                if (timerShot == 200) {
                    timerShot = 0;
                    shots.add(new Shot(posX, posY, rotation, 1, group, this));
                    hunger--;
                    hungerText.setText(String.valueOf(hunger));
                }
            }
            if (min == 10000) {
                moveTo(planetX + hunger * 5, planetY + (hunger + 10) * 5);
            }

            if (hunger == 0) {
                onDuty = false;
                toPlanet = true;
            }


            updateStandard();
        }
    }

    protected void updateStandard() {

        if(!removed) {
            if(toPlanet  && !active) {
                moveTo(planetX, planetY);
                if(countDist(planetX, posX, planetY, posY) < 256) {
                    shipView.setImage(Pictures.pictures.get(onPlanetPic));
                    if(otherReaction) {
                        group.getChildren().clear();
                        removed = true;
                    }
                    toPlanet = false;
                    onPlanet = true;
                }
            }
            if(onPlanet) {
                moveTo(planetX, planetY);
                timer++;
                if(timer == 500) {
                    timer = 0;
                    hunger = 10 * level;
                    hungerText.setText(String.valueOf(hunger));
                    onPlanet = false;
                    onDuty = true;
                    shipView.setImage(Pictures.pictures.get(notActivePic));
                    // group.getChildren().addAll(shipView, healthText, hungerText);
                }
            }

            posX += Math.sin(rotation * (Math.PI / 180)) * speed;
            posY -= Math.cos(rotation * (Math.PI / 180)) * speed;

            if(posX > 3840 - 125) posX = 3840 -125;
            if(posX < 10) posX = 10;
            if(posY > 2160 - 125) posY = 2160 - 125;
            if(posY < 10) posY = 10;

            shipView.setRotate(rotation - rotationFix);
            shipView.setX(posX);
            shipView.setY(posY);

            hungerText.setX(posX + 10);
            hungerText.setY(posY + 60);

            healthText.setX(posX + 50);
            healthText.setY(posY + 60);

            rectangle.setX(posX + 90);
            rectangle.setY(posY + 50);
        }
        for (Shot shot: shots) {
            shot.update();
            if(!shot.isActive())  {
                ArrayList<Shot> array = new ArrayList<>();
                for (Shot tmp: shots) {
                    if(tmp.isActive())array.add(tmp);
                }
                shots = array;
            }
        }
    }

    protected void moveTo(double x, double y) {
        double dir = Math.atan2(y - posY, x - posX) * 180 / Math.PI + 90;
        dir = Math.round(dir);
        if (rotation > 270) {
            rotation -= 360;
        }

        else if (rotation < -90) {
            rotation += 360;
        }
            if (dir < rotation) {
                if (abs(180 - rotation) + abs(-180 - dir) < abs(rotation - dir)) {
                    rotation++;
                }
                else {
                    rotation--;
                }
            }
            else {
                if (abs(180 - dir) + abs(-180 - rotation) < abs(dir - rotation)) {
                    rotation--;
                }
                else {
                    rotation++;
                }
            }
    }

    protected double countDist (double x1, double x2, double y1, double y2) {
        return Math.sqrt (Math.pow ( (x1 - x2), 2) + Math.pow ( (y1 - y2), 2));
    }

    public void mouse(double x, double y) {
        if(shipView.boundsInParentProperty().get().contains(x, y)) {
            if(!onPlanet) {
                if(active) {
                    setNotActive();
                }
                else {
                    setActive();
                }
            }
        }
    }

    public void setNotActive() {
        active = false;
        speed = 2;
        shipView.setImage(Pictures.pictures.get(notActivePic));
    }
    public void setActive() {
        active = true;
        speed = 0;
        shipView.setImage(Pictures.pictures.get(activePic));
    }
    public void setOnplanet() {
        shipView.setImage(Pictures.pictures.get(onPlanetPic));
    }

    public void deactivate() {
        active = false;
        speed = 2;
        shipView.setImage(Pictures.pictures.get(notActivePic));
    }

    public void up() {
        posY -= 10;
    }

    public void down() {
        posY += 10;
    }

    public void left() {
        posX -= 10;
    }
    public void right() {
        posX += 10;
    }

    public void from() {
        if(onPlanet){
            timer = 0;
            hunger = 10 * level;
            hungerText.setText(String.valueOf(hunger));
            onPlanet = false;
            onDuty = true;
            shipView.setImage(Pictures.pictures.get(notActivePic));
        }
    }

    @Override
    public int compareTo(Ship other) {
        return this.hunger - other.hunger;
    }

    public void to() {
        active = false;
        speed = 2;
        shipView.setImage(Pictures.pictures.get(notActivePic));
        onDuty = false;
        toPlanet = true;
    }

}
