package app.tiles;

import app.utils.AppAssets;
import javafx.scene.image.Image;

public enum NotationTags {
    LETTER, NUMBER;

    private final Image image;

    NotationTags(){
        image = AppAssets.getImage(String.format("/images/tiles/%s-notation-section.png", name().toLowerCase()));
    }

    public Image getImage() {
        return image;
    }
}
