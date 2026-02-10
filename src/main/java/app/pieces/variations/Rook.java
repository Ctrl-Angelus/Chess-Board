package app.pieces.variations;

import app.Board;
import app.pieces.ChessPieces;
import app.pieces.MovementType;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.utils.GameUtils;
import app.utils.Position;
import app.utils.Vector2;

public class Rook extends Piece {

    public Rook(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.ROOK, coordinates, pieceShade);
    }

    @Override
    public MovementType checkMove(Position actualPosition, Position newPosition, Board board) {

        Piece targetPiece = board.getIndividualPiece(newPosition);
        if (targetPiece != null && targetPiece.pieceKind == pieceKind){
            return MovementType.ILLEGAL_MOVE;
        }

        boolean sameColumn = actualPosition.column() == newPosition.column();
        boolean sameRow = actualPosition.row() == newPosition.row();

        if (sameRow || sameColumn){

            int sign;
            if (sameColumn){
                sign = (newPosition.row() - actualPosition.row() > 0) ? 1 : -1;
                int currentRow = actualPosition.row() + sign;
                while (!new Position(currentRow, actualPosition.column()).equals(newPosition)){
                    if (GameUtils.isNotInsideBoard(new Position(actualPosition.row(), actualPosition.column()))){
                        return MovementType.ILLEGAL_MOVE;
                    }
                    if (board.getIndividualPiece(new Position(currentRow, actualPosition.column())) != null){
                        return MovementType.ILLEGAL_MOVE;
                    }
                    currentRow += sign;
                }

            } else {
                sign = (newPosition.column() - actualPosition.column() > 0) ? 1 : -1;
                int currentColumn = actualPosition.column() + sign;
                while (!new Position(actualPosition.row(), currentColumn).equals(newPosition)){
                    if (GameUtils.isNotInsideBoard(new Position(actualPosition.row(), currentColumn))){
                        return MovementType.ILLEGAL_MOVE;
                    }
                    if (board.getIndividualPiece(new Position(actualPosition.row(), currentColumn)) != null){
                        return MovementType.ILLEGAL_MOVE;
                    }
                    currentColumn += sign;
                }
            }
            return MovementType.LEGAL_MOVE;
        }
        return MovementType.ILLEGAL_MOVE;
    }
}
