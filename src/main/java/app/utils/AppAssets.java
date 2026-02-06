package app.utils;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.util.Objects;

public class AppAssets {
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
    public static final Font DEFAULT_FONT = Font.loadFont(
            AppAssets.class.getResourceAsStream("/fonts/BlockCraft.otf"), AppParameters.FONT_SIZE
    );
}
