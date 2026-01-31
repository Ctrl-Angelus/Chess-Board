package app.pieces;

import app.utils.Position;
import app.utils.Vector2;

public class Bishop extends Piece {

    public Bishop(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.BISHOP, coordinates, pieceShade);
    }

    @Override
    public boolean canMove(Position actualPosition, Position newPosition) {
        return false;
    }
}
