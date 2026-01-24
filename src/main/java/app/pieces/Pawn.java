package app.pieces;

import app.utils.Vector2;

public class Pawn extends Piece {

    public Pawn(Vector2 coordinates, PieceShade pieceShade){
        super(ChessPieces.PAWN, coordinates, pieceShade);
    }

    @Override
    public void move() {

    }
}
