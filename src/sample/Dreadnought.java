package sample;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Dreadnought extends BattleShip{
    private int shotType;
    private int timerShot;
    public Dreadnought (String race, double posX, double posY, Pane group, ArrayList<Ship> enemy, ArrayList<Ship> friends) {
        super(race, posX, posY, group, enemy, friends);

        if(race.equals("Human")) {
            activePic = "human3_act.png";
            notActivePic = "human3.png";
            onPlanetPic = "human3_planet.png";
            rotationFix = 0;
        }
        if(race.equals("Alien")) {
            activePic = "alien3_act.png";
            notActivePic = "alien3.png";
            onPlanetPic = "alien3_planet.png";
        }

        hunger = 30;
        hungerText.setText(String.valueOf(hunger));
        health = 30;
        healthText.setText(String.valueOf(health));

        shipView.setImage(Pictures.pictures.get(notActivePic));

        level = 3;
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
                        shotType = 3;
                    }
                }
            }
            for (Ship ship : friends) {
                if (!ship.isOnPlanet() && ship.getHealth() < ship.getLevel() * 10 && !ship.isRemoved() && !ship.onParad) {
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
                if (timerShot == 100) {
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


