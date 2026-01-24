package app.pieces;

import app.utils.Vector2;
import app.utils.appParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public abstract class Piece {
    public final ChessPieces pieceType;
    public final PieceShade pieceShade;
    public final Vector2 coordinates;
    public final Image image;

    public Piece(ChessPieces pieceType, Vector2 coordinates, PieceShade pieceShade){
        this.pieceType = pieceType;
        this.coordinates = coordinates;
        this.pieceShade = pieceShade;
        this.image = new Image(
            Objects.requireNonNull(
                Piece.class.getResourceAsStream(pieceType.imagePath.replace(
                    "%shade%", pieceShade.name().toLowerCase())
                )
            )
        );
    }

    public void draw(GraphicsContext gc){
        gc.setImageSmoothing(false);
        gc.drawImage(
            this.image,
            this.coordinates.coordinateX(),
            this.coordinates.coordinateY(),
            appParameters.PIECE_SIZE,
            appParameters.PIECE_SIZE
        );
    }

    public abstract void move();
}
