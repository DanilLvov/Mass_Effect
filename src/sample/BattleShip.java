package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class BattleShip extends Ship {

    private int timerShot;
    private int shotType;

    public BattleShip (String race, double posX, double posY, Pane group, ArrayList<Ship> enemy, ArrayList<Ship> friends) {
        super(race, posX, posY, group, enemy, friends);

        if(race.equals("Human")) {
            activePic = "human2_act.png";
            notActivePic = "human2.png";
            onPlanetPic = "human2_planet.png";
            rotationFix = 0;
        }
        if(race.equals("Alien")) {
            activePic = "alien2_act.png";
            notActivePic = "alien2.png";
            onPlanetPic = "alien2_planet.png";
        }

        hunger = 20;
        hungerText.setText(String.valueOf(hunger));
        health = 20;
        healthText.setText(String.valueOf(health));

        shipView.setImage(Pictures.pictures.get(notActivePic));

        level = 2;
    }

    @Override
    public void update() {
        if(onDuty && !active && !removed && !onParad) {
            double min = 10000;
            for (Ship ship : enemy) {
                if (!ship.isOnPlanet() && !ship.isRemoved() && !ship.onParad) {
                    if (countDist(posX, ship.getPosX(), posY, ship.getPosY()) < min && countDist(posX, ship.getPosX(), posY, ship.getPosY()) != 0) {
                        min = countDist(posX, ship.getPosX(), posY, ship.getPosY());
                        moveShip = ship;
                        shotType = 2;
                    }
                }
            }
            for (Ship ship : friends) {
                if (!ship.isOnPlanet() && !ship.isRemoved() && !ship.onParad && ship.getHealth() < ship.getLevel() * 10) {
                    if (countDist(posX, ship.getPosX(), posY, ship.getPosY()) < min && countDist(posX, ship.getPosX(), posY, ship.getPosY()) != 0) {
                        min = countDist(posX, ship.getPosX(), posY, ship.getPosY());
                        moveShip = ship;
                        shotType = 1;
                    }
                }
            }
            if (min != 10000) {
                moveTo(moveShip.getPosX(), moveShip.getPosY());
                timerShot++;
                if (timerShot == 200) {
                    timerShot = 0;
                    shots.add(new Shot(posX, posY, rotation, shotType, group, this));
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

}

