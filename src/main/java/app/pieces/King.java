package app.pieces;

import app.utils.Position;
import app.utils.Vector2;

public class King extends Piece {

    public King(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.KING, coordinates, pieceShade);
    }

    @Override
    public boolean canMove(Position actualPosition, Position newPosition) {
        return false;
    }
}
