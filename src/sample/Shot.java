package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shot implements Cloneable{
    private Rectangle shot;
    private boolean active;
    private double posX;
    private double posY;
    private double rotation;
    private Ship shipShot;
    private int type;

    public Shot (double posX, double posY, double rotation, int type, Pane group, Ship shipShot) {
        shot = new Rectangle( posX, posY, 10, 10);
        switch (type) {
            case 1:
                shot.setFill(Color.GREEN);
                break;
            case 2:
                shot.setFill(Color.RED);
                break;
            case 3:
                shot.setFill(Color.YELLOW);
                break;
            default:
                break;
        }

        this.posX = posX;
        this.posY = posY;
        this.rotation = rotation;
        this.type = type;
        this.shipShot = shipShot;
        group.getChildren().add(shot);
        active = true;
    }

    public boolean isActive() { return  active; }
    public Rectangle getShot() { return shot; }
    public void update() {
        posX += 10 * Math.sin(rotation * Math.PI / 180);
        posY -= 10 * Math.cos(rotation * Math.PI / 180);
        shot.setX(posX);
        shot.setY(posY);

        if(active) {
            switch (type) {
                case 1:
                    if(shipShot.getRace().equals("Human")) {
                        for (Ship ship: Main.space.humans) {
                            if (ship.getView().boundsInParentProperty().get().contains(posX + 5, posY + 5) && !ship.isRemoved() && !ship.onParad) {
                                ship.plusHealth();
                                shipShot.group.getChildren().remove(shot);
                                active = false;
                            }
                        }
                    }
                    else  {
                        for (Ship ship: Main.space.aliens) {
                            if (ship.getView().boundsInParentProperty().get().contains(posX + 5, posY + 5) && !ship.isRemoved() && !ship.onParad) {
                                ship.plusHealth();
                                shipShot.group.getChildren().remove(shot);
                                active = false;
                            }
                        }
                    }
                    break;
                case 2:
                    if(shipShot.getRace().equals("Human")) {
                        for (Ship ship: Main.space.aliens) {
                            if (ship.getView().boundsInParentProperty().get().contains(posX + 5, posY + 5) && !ship.isRemoved() && !ship.onParad) {
                                ship.minusHealth();
                                shipShot.group.getChildren().remove(shot);
                                active = false;
                            }
                        }
                    }
                    else  {
                        for (Ship ship: Main.space.humans) {
                            if (ship.getView().boundsInParentProperty().get().contains(posX + 5, posY + 5) && !ship.isRemoved() && !ship.onParad) {
                                ship.minusHealth();
                                shipShot.group.getChildren().remove(shot);
                                active = false;
                            }
                        }
                    }
                    break;
                case 3:
                    if(shipShot.getRace().equals("Human")) {
                        for (Ship ship: Main.space.aliens) {
                            if (ship.getView().boundsInParentProperty().get().contains(posX + 5, posY + 5) && !ship.isRemoved() && !ship.onParad ){
                                shipShot.plusHealth();
                                ship.minusHealth();
                                shipShot.group.getChildren().remove(shot);
                                active = false;
                            }
                        }
                    }
                    else  {
                        for (Ship ship: Main.space.humans) {
                            if (ship.getView().boundsInParentProperty().get().contains(posX + 5, posY + 5) && !ship.isRemoved() && !ship.onParad) {
                                shipShot.plusHealth();
                                ship.minusHealth();
                                shipShot.group.getChildren().remove(shot);
                                active = false;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        if (posX < 5 || posY < 5 || posX > 3840 - 5 || posY > 2160 - 5) {
            shipShot.group.getChildren().remove(shot);
            active = false;
        }
    }

    @Override
    public Shot clone() throws CloneNotSupportedException { return (Shot)super.clone(); }
}
