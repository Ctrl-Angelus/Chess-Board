package app.tiles;

import app.utils.AppAssets;
import app.utils.AppParameters;
import javafx.scene.image.Image;

public enum TileType {
    DARK, LIGHT, HIGHLIGHT, SELECTION, MOVEMENT, CAPTURE;

    private final Image image;

    TileType(){
        this.image = AppAssets.getImage(
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
