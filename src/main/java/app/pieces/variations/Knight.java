package app.pieces.variations;

import app.Board;
import app.pieces.ChessPieces;
import app.pieces.MovementType;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.utils.Position;
import app.utils.Vector2;

public class Knight extends Piece {
    public Knight(Vector2 coordinates, PieceKind pieceShade, Position position){
        super(ChessPieces.KNIGHT, coordinates, pieceShade, position);
    }

    @Override
    public MovementType checkMove(Position actualPosition, Position newPosition, Board board) {
        Piece targetPiece = board.getIndividualPiece(newPosition);
        if (targetPiece != null && targetPiece.pieceKind == pieceKind){
            return MovementType.ILLEGAL_MOVE;
        }

        int columnDifference = Math.abs(newPosition.column() - actualPosition.column());
        int rowDifference = Math.abs(newPosition.row() - actualPosition.row());

        int maxDifference = Math.max(columnDifference, rowDifference);
        int minDifference = Math.min(columnDifference, rowDifference);

        return maxDifference == 2 && minDifference == 1 ? MovementType.LEGAL_MOVE : MovementType.ILLEGAL_MOVE;
    }
}
