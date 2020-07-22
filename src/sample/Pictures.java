package sample;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;

public class Pictures {
    public static HashMap<String, Image> pictures;

    public static void load() {
        File dir = new File("src/sample/pictures");
        try {
            pictures = new HashMap<String, Image>();
            for(File file : dir.listFiles()) {
                Image newImage = new Image("sample/pictures/" + file.getName());
                pictures.put(file.getName(), newImage);
                System.out.println(file.getName());
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
