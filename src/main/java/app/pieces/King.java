package app.pieces;

import app.utils.Vector2;

public class King extends Piece {

    public King(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.KING, coordinates, pieceShade);
    }

    @Override
    public void move() {

    }
}
