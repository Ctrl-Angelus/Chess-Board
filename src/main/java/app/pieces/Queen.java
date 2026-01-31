package app.pieces;

import app.utils.Position;
import app.utils.Vector2;

public class Queen extends Piece {

    public Queen(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.QUEEN, coordinates, pieceShade);
    }

    @Override
    public boolean canMove(Position actualPosition, Position newPosition) {
        return false;
    }
}
