package app.tiles;

import app.pieces.PieceKind;
import app.utils.AppAssets;
import app.utils.AppParameters;
import app.utils.AppState;
import app.utils.Vector2;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;

public class PlayerBanner {
    private final Vector2 coordinates;
    private final Image image;
    private final double width = AppParameters.TILE_SIZE * 8;
    private final double height = AppParameters.TILE_SIZE / 2;
    private final PieceKind kind;

    public PlayerBanner(Vector2 coordinates, String imagePath, PieceKind kind){
        this.coordinates = coordinates;
        this.image = AppAssets.getImage(imagePath, width, height, false);
        this.kind = kind;
    }

    public void draw(GraphicsContext graphicsContext){
        graphicsContext.save();
        graphicsContext.setImageSmoothing(false);
        graphicsContext.translate(
            coordinates.coordinateX() + width/2,
            coordinates.coordinateY() + height/2
        );

        graphicsContext.rotate(
            AppState.isBoardRotated() ? 180 : 0
        );

        graphicsContext.setImageSmoothing(false);

        graphicsContext.drawImage(this.image, -width/2, -height/2);

        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.setFont(AppAssets.DEFAULT_FONT);
        graphicsContext.setFill(this.kind == PieceKind.DARK ? AppParameters.LIGHT_TEXT_COLOR : AppParameters.DARK_TEXT_COLOR);

        graphicsContext.fillText(
            "Score: " + (this.kind == PieceKind.DARK ? AppState.blackScore : AppState.whiteScore),
            0,
            0
        );

        graphicsContext.restore();
    }
}
