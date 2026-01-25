package app.pieces;

import app.utils.Vector2;
import app.utils.appParameters;
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
                Piece.class.getResourceAsStream(pieceType.imagePath.replace(
                    "%shade%", pieceKind.name().toLowerCase())
                )
            )
        );
    }

    public void draw(GraphicsContext gc){
        gc.save();
        gc.setImageSmoothing(false);
        gc.translate(
            coordinates.coordinateX() + appParameters.PIECE_SIZE/2,
            coordinates.coordinateY() + appParameters.PIECE_SIZE/2
        );

        gc.rotate(
            this.pieceKind.rotationAngle
        );

        gc.drawImage(
            this.image,
                -appParameters.PIECE_SIZE/2,
                -appParameters.PIECE_SIZE/2,
            appParameters.PIECE_SIZE,
            appParameters.PIECE_SIZE
        );
        gc.restore();
    }

    public abstract void move();
}
