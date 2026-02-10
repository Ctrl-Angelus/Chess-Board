package app.pieces.variations;

import app.Board;
import app.pieces.ChessPieces;
import app.pieces.MovementType;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.utils.Position;
import app.utils.Vector2;

public class King extends Piece {

    public King(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.KING, coordinates, pieceShade);
    }

    @Override
    public MovementType checkMove(Position actualPosition, Position newPosition, Board board) {
        Piece targetPiece = board.getIndividualPiece(newPosition);
        if (targetPiece != null && targetPiece.pieceKind == pieceKind){
            return MovementType.ILLEGAL_MOVE;
        }

        int columnDifference = Math.abs(newPosition.column() - actualPosition.column());
        int rowDifference = Math.abs(newPosition.row() - actualPosition.row());

        return (columnDifference == 1 && rowDifference <= 1) || (rowDifference == 1 && columnDifference <= 1) ? MovementType.LEGAL_MOVE : MovementType.ILLEGAL_MOVE;
    }
}
