package sample;

import java.io.*;
import java.util.ArrayList;

public class Serealize {
    static ArrayList<SomeClass> arr = new ArrayList<>();
    public static void serialize(String fileName) {

        for (Ship ship: Main.space.humans){
            arr.add(new SomeClass(
                        ship.getPosX(), ship.getPosY(), ship.getHunger(), ship.getHealth(), ship.getRace(),
                        ship.getLevel(), ship.isActive(), ship.isOnDuty(), ship.isOtherReaction(), ship.isToPlanet(),
                    ship.isOnPlanet()
                ));
        }
        for (Ship ship: Main.space.aliens){
            arr.add(new SomeClass(
                    ship.getPosX(), ship.getPosY(), ship.getHunger(), ship.getHealth(), ship.getRace(),
                    ship.getLevel(), ship.isActive(), ship.isOnDuty(), ship.isOtherReaction(), ship.isToPlanet(),
                    ship.isOnPlanet()
            ));
        }

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserialization(String fileName) {
        for (Ship ship: Main.space.humans){
            Main.space.objects.getChildren().remove(ship.getGroup());
        }
        for (Ship ship: Main.space.aliens){
            Main.space.objects.getChildren().remove(ship.getGroup());
        }
        ArrayList<SomeClass> arr = new ArrayList<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            arr = (ArrayList<SomeClass>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Main.space.aliens.clear();
        Main.space.humans.clear();

        int i = 0;
        int j = 0;
        for(SomeClass object: arr) {

            Main.space.add(object.race, object.x, object.y, object.level);
            if(object.race.equals("Human")) {
                Main.space.humans.get(i).hunger = object.hunger;
                Main.space.humans.get(i).health = object.health;
                Main.space.humans.get(i).onDuty = object.onDuty;
                Main.space.humans.get(i).onPlanet = object.onPlanet;
                Main.space.humans.get(i).otherReaction = object.otherReaction;
                if(object.active) {
                    Main.space.humans.get(i).setActive();
                }
                if(object.onPlanet) {
                    Main.space.humans.get(i).setOnplanet();
                }
                Main.space.humans.get(i).toPlanet = object.toPlanet;
                i++;
            }

            if(object.race.equals("Alien")) {
                Main.space.aliens.get(j).hunger = object.hunger;
                Main.space.aliens.get(j).health = object.health;
                Main.space.aliens.get(j).onDuty = object.onDuty;
                Main.space.aliens.get(j).onPlanet = object.onPlanet;
                Main.space.aliens.get(j).otherReaction = object.otherReaction;
                if(object.active) {
                    Main.space.aliens.get(j).setActive();
                }
                if(object.onPlanet) {
                    Main.space.aliens.get(j).setOnplanet();
                }
                Main.space.aliens.get(j).toPlanet = object.toPlanet;
                j++;
            }
        }


    }
}
