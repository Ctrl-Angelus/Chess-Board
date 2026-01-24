package app.pieces;

import app.ChessPieces;
import app.Piece;

public class Pawn extends Piece {

    public Pawn(double coordinateX, double coordinateY){
        super(ChessPieces.PAWN, coordinateX, coordinateY);
    }

    @Override
    public void move() {

    }
}
