package app.pieces;

import app.utils.Position;
import app.utils.Vector2;

public class Rook extends Piece {

    public Rook(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.ROOK, coordinates, pieceShade);
    }

    @Override
    public boolean canMove(Position actualPosition, Position newPosition) {
        return false;
    }
}
