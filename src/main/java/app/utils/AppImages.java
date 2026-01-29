package app.utils;

import javafx.scene.image.Image;

import java.util.Objects;

public class AppImages {
    public static Image getImage(String imagePath){
        return new Image(
            Objects.requireNonNull(AppParameters.class.getResourceAsStream(imagePath))
        );
    }
    public static Image getImage(String imagePath, double width, double height, boolean smoothImage){
        return new Image(
            Objects.requireNonNull(AppParameters.class.getResourceAsStream(imagePath)),
            width,
            height,
            true,
            smoothImage
        );
    }
    public static final Image ICON = getImage("/images/icons/icon.png");
}
