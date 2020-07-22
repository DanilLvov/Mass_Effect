package sample;

import java.io.Serializable;

public class SomeClass implements Serializable {
    public double x;
    public double y;
    public int hunger;
    public int health;
    public String race;
    public int level;
    public boolean active;
    public boolean onDuty;
    public boolean otherReaction;
    public boolean toPlanet;
    public boolean onPlanet;

    public SomeClass(double x, double y, int hunger, int health, String race, int level,
                     boolean active, boolean onDuty, boolean otherReaction, boolean toPlanet, boolean onPlanet){
        this.x = x;
        this.y = y;
        this.hunger = hunger;
        this.health = health;
        this.race = race;
        this.level = level;
        this.active = active;
        this.onDuty = onDuty;
        this.otherReaction = otherReaction;
        this.toPlanet = toPlanet;
        this.onPlanet = onPlanet;
    }
}
