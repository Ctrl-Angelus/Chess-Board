package app;

import app.utils.AppImages;
import javafx.scene.image.Image;

public enum TileType {
    DARK, LIGHT, HIGHLIGHT, SELECTION;

    private final Image image;

    TileType(){
        this.image = AppImages.getImage(String.format("/images/tiles/%s-tile.png", this.name().toLowerCase()));
    }
    public Image getImage(){
        return this.image;
    }
}
