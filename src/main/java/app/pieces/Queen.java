package app.pieces;

import app.utils.Vector2;

public class Queen extends Piece {

    public Queen(Vector2 coordinates, PieceShade pieceShade){
        super(ChessPieces.QUEEN, coordinates, pieceShade);
    }

    @Override
    public void move() {

    }
}
