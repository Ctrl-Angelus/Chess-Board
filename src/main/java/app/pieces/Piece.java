package app.pieces;

import app.utils.Vector2;
import app.utils.AppParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public abstract class Piece {
    public final ChessPieces pieceType;
    public final PieceKind pieceKind;
    public final Vector2 coordinates;
    public final Image image;

    public Piece(ChessPieces pieceType, Vector2 coordinates, PieceKind pieceKind){
        this.pieceType = pieceType;
        this.coordinates = coordinates;
        this.pieceKind = pieceKind;
        this.image = new Image(
            Objects.requireNonNull(
                Piece.class.getResourceAsStream(pieceType.getImagePath(pieceKind)
                )
            )
        );
    }

    public void draw(GraphicsContext gc, Vector2 alternativeCoordinates){
        boolean useAlternativeCoordinates = (alternativeCoordinates != null);

        gc.save();
        gc.setImageSmoothing(false);
        gc.translate(
            (useAlternativeCoordinates) ? alternativeCoordinates.coordinateX() : coordinates.coordinateX() + AppParameters.PIECE_SIZE/2,
            (useAlternativeCoordinates) ? alternativeCoordinates.coordinateY() : coordinates.coordinateY() + AppParameters.PIECE_SIZE/2
        );

        gc.rotate(
            this.pieceKind.rotationAngle
        );

        gc.drawImage(
            this.image,
                -AppParameters.PIECE_SIZE/2,
                -AppParameters.PIECE_SIZE/2,
            AppParameters.PIECE_SIZE,
            AppParameters.PIECE_SIZE
        );
        gc.restore();
    }

    public abstract void move();
}
