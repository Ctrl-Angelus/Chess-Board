package app.pieces;

import app.ChessPieces;
import app.Piece;

public class King extends Piece {

    public King(double coordinateX, double coordinateY){
        super(ChessPieces.KING, coordinateX, coordinateY);
    }

    @Override
    public void move() {

    }
}
