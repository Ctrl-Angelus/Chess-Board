package app.pieces;

import app.utils.Position;
import app.utils.Vector2;

public class Knight extends Piece {
    public Knight(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.KNIGHT, coordinates, pieceShade);
    }

    @Override
    public boolean canMove(Position actualPosition, Position newPosition) {
        return false;
    }
}
