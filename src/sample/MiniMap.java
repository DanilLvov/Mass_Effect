package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import static sample.Main.space;

public class MiniMap {
    private static ImageView map;
    private static SnapshotParameters parameters;
    private static Scale scale;

    public static void load() {
        map = new ImageView();
        parameters = new SnapshotParameters();
        parameters.setFill(Color.BLACK);
        Main.group.getChildren().add(map);
        scale = new Scale();
        scale.setX(0.1);
        scale.setY(0.1);
        map.getTransforms().add(scale);
    }
    public static void mapUpdate() {
        WritableImage snapshot = space.objects.snapshot(parameters, null);
        map.setImage(snapshot);

        map.setLayoutX(1200 - Main.group.getLayoutX());
        map.setLayoutY(20 - Main.group.getLayoutY());


    }

    public static void mouse(double x, double y) {
        if(map.boundsInParentProperty ().get ().contains (x, y)){
            double posX = -(( x - 54.64 + Main.group.getLayoutX () - 1200) /(3840*0.08) * 3840);
            double posY = -(( y - 30.72 + Main.group.getLayoutY () - 20 )/(2160*0.08) * 2160);
            if (posX > 0) posX = 0.0;
            if (posY > 0) posY = 0.0;
            if (posX < -2240) posX = -2240;
            if (posY < -1260) posY = -1260;

            Main.group.setLayoutX(posX);
            Main.group.setLayoutY(posY);
            //pearlHarbour.moveFrame(posX, posY);
            space.rect.setX(-posX - 15);
            space.rect.setY(-posY - 15);
            mapUpdate();

        }
    }
}
