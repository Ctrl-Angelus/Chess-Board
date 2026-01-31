package app.pieces;

import app.utils.Position;
import app.utils.Vector2;

public class Pawn extends Piece {

    public Pawn(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.PAWN, coordinates, pieceShade);
    }

    @Override
    public boolean canMove(Position actualPosition, Position newPosition) {
        return false;
    }
}
