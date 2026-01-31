package app.tiles;

import app.utils.AppImages;
import app.utils.AppParameters;
import javafx.scene.image.Image;

public enum TileType {
    DARK, LIGHT, HIGHLIGHT, SELECTION;

    private final Image image;

    TileType(){
        this.image = AppImages.getImage(
            String.format("/images/tiles/%s-tile.png", this.name().toLowerCase()),
            AppParameters.TILE_SIZE,
            AppParameters.TILE_SIZE,
            false
        );
    }
    public Image getImage(){
        return this.image;
    }
}
