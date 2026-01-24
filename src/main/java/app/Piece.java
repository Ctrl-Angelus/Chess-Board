package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public abstract class Piece {
    public final ChessPieces pieceType;
    public final double coordinateX;
    public final double coordinateY;
    public final Image image;

    public Piece(ChessPieces pieceType, double coordinateX, double coordinateY){
        this.pieceType = pieceType;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.image = new Image(Objects.requireNonNull(Piece.class.getResourceAsStream(pieceType.imagePath)));
    }

    public void draw(GraphicsContext gc){
        gc.setImageSmoothing(false);
        gc.drawImage(
                this.image,
                this.coordinateX,
                this.coordinateY,
                appParameters.PIECE_SIZE,
                appParameters.PIECE_SIZE
        );
    }

    public abstract void move();
}
