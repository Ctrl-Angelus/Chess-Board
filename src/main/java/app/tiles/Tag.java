package app.tiles;

import app.utils.AppAssets;
import app.utils.AppParameters;
import app.utils.AppState;
import app.utils.Vector2;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

public record Tag(String content, NotationTags tagType) {

    public void draw(GraphicsContext gc, Vector2 coordinates) {

        gc.save();
        gc.setImageSmoothing(false);
        gc.translate(
                coordinates.coordinateX(),
                coordinates.coordinateY()
        );

        gc.rotate(
                AppState.isBoardRotated() ? 180 : 0
        );

        gc.setImageSmoothing(false);
        gc.drawImage(
                tagType.getImage(),
                - AppParameters.FONT_SIZE / 2,
                - AppParameters.FONT_SIZE / 2,
                AppParameters.FONT_SIZE,
                AppParameters.FONT_SIZE
        );

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(AppAssets.DEFAULT_FONT);
        gc.setFill(AppParameters.LIGHT_TEXT_COLOR);
        gc.fillText(
            this.content.toUpperCase(),
            0,
            0
        );
        gc.restore();
    }
}
