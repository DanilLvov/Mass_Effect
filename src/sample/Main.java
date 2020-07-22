package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static Scene scene;
    public static Pane group;
    private static int counter = 15;
    static FileChooser fileChooser = new FileChooser();
    public static File file;
    public static Space space;
    static boolean tmp = true;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("MassEffect");
        group = new Pane();
        scene = new Scene(group, 1600, 900);
        Pictures.load();

        space = new Space(group);
        MiniMap.load();


        AnimationTimer mainTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                space.updateAll();
                if(counter == 15){
                    MiniMap.mapUpdate();
                    counter = 0;
                }
                counter++;
            }
        };

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                space.up();
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                space.down();
            }
            if (event.getCode().equals(KeyCode.LEFT)) {
                space.left();
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                space.right();
            }

            if (event.getCode().equals(KeyCode.ESCAPE)) {
                space.escape();
            }
            if (event.getCode().equals(KeyCode.DELETE)) {
                space.delete();
            }

            if (event.getCode().equals(KeyCode.Z)) {
                space.fromPlanet();
            }
            if (event.getCode().equals(KeyCode.X)) {
                space.toPlanet();
            }
            if (event.getCode().equals(KeyCode.C)) {
                space.change();
            }
            if (event.getCode().equals(KeyCode.F)) {
                space.sort();
            }
            if (event.getCode().equals(KeyCode.INSERT)) {
                Menu.display();
            }

            if (event.getCode().equals(KeyCode.K)) {
                if(tmp) {
                    Menu.displayParade();
                    tmp = false;
                }
                else {
                    Menu.stopParade();
                    tmp = true;
                }
            }

            double x = group.getLayoutX();
            double y = group.getLayoutY();
            if (event.getCode().equals(KeyCode.W)) {
                y += 200;
                if (y > 0) y = 0.0;
                space.rect.setX(-x - 15);
                space.rect.setY(-y - 15);
                group.setLayoutX(x);
                group.setLayoutY(y);
                MiniMap.mapUpdate();
            }
            if (event.getCode().equals(KeyCode.A)) {
                x += 200;
                if (x > 0) x = 0.0;
                space.rect.setX(-x - 15);
                space.rect.setY(-y - 15);
                group.setLayoutX(x);
                group.setLayoutY(y);
                MiniMap.mapUpdate();
            }
            if (event.getCode().equals(KeyCode.S)) {
                y -= 200;
                if (y < -2160 + 900) y = -2160 + 900;
                space.rect.setX(-x - 15);
                space.rect.setY(-y - 15);
                group.setLayoutX(x);
                group.setLayoutY(y);
                MiniMap.mapUpdate();
            }
            if (event.getCode().equals(KeyCode.D)) {
                x -= 200;
                if (x < -3840 + 1600) x = -3840 + 1600;
                space.rect.setX(-x - 15);
                space.rect.setY(-y - 15);
                group.setLayoutX(x);
                group.setLayoutY(y);
                MiniMap.mapUpdate();
            }

            if (event.getCode().equals(KeyCode.V)) {
                file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    Serealize.serialize(file.getAbsolutePath());
                }
            }

            if (event.getCode().equals(KeyCode.B)) {
                file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    Serealize.deserialization(file.getAbsolutePath());
                }
            }

        });

        Main.scene.setOnMouseClicked(event -> {
            space.mouse(event.getX() - group.getLayoutX(), event.getY() - group.getLayoutY());

            MiniMap.mouse(event.getX() - group.getLayoutX(), event.getY() - group.getLayoutY());
        });


        mainTimer.start(); // запуск анімації
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
