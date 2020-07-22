package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Planet {
    private ImageView planetView;

    public Planet (double posX, double posY, Pane group, String name) {
        if(name.equals("Mars")) {
            planetView = new ImageView(Pictures.pictures.get("Mars.png"));
        }
        if(name.equals("Blue")) {
            planetView = new ImageView(Pictures.pictures.get("Blue.png"));
        }

        planetView.setX(posX);
        planetView.setY(posY);

        Text nameLine = new Text(name);
        nameLine.setFont(Font.font("Verdana", 35));
        nameLine.setFill(Color.WHITE);
        nameLine.setX(posX + 50);
        nameLine.setY(posY - 10);

        // створення лінії здоров'я, що буде виводитись на єкран
        Rectangle rectangle = new Rectangle(posX + 50, posY, 100, 10);
        rectangle.setFill(Color.WHITE);

        group.getChildren().addAll(planetView, nameLine, rectangle);
    }
}
