package app.pieces;

import app.ChessPieces;
import app.Piece;

public class Queen extends Piece {

    public Queen(double coordinateX, double coordinateY){
        super(ChessPieces.QUEEN, coordinateX, coordinateY);
    }

    @Override
    public void move() {

    }
}
