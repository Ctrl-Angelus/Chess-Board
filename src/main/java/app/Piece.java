package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Piece {
    public final ChessPieces pieceType;
    public final double coordinateX;
    public final double coordinateY;

    public Piece(ChessPieces pieceType, double coordinateX, double coordinateY){
        this.pieceType = pieceType;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public void draw(GraphicsContext gc , Color color){
        gc.setFill(color);
        gc.fillRect(this.coordinateX, this.coordinateY, appParameters.PIECE_SIZE, appParameters.PIECE_SIZE);
    }

    public abstract void move();
}
