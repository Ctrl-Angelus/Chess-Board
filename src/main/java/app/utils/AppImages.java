package app.utils;

import javafx.scene.image.Image;

import java.util.Objects;

public class AppImages {
    public static Image getImage(String imagePath){
        return new Image(
                Objects.requireNonNull(AppParameters.class.getResourceAsStream(imagePath))
        );
    }
    public static final Image ICON = getImage("/images/icons/icon.png");
}
