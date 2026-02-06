package app.pieces.variations;

import app.Board;
import app.pieces.ChessPieces;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.utils.AppState;
import app.utils.Position;
import app.utils.Vector2;

public class Pawn extends Piece {

    public Pawn(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.PAWN, coordinates, pieceShade);
    }

    @Override
    public boolean canMove(Position actualPosition, Position newPosition, Board board) {
        int sign = switch (pieceKind){
            case DARK -> 1;
            case LIGHT -> -1;
        };
        int initialRow = switch (pieceKind){
            case DARK -> 1;
            case LIGHT -> 6;
        };
        Piece targetPiece = board.getIndividualPiece(newPosition);
        if (targetPiece != null && targetPiece.pieceKind == pieceKind){
            return false;
        }
        Position enPassantPosition = Position.getPositionFromNotation(AppState.getEnPassantPosition());

        boolean forwardMovement = newPosition.row() == actualPosition.row() + sign;
        boolean sameColumn = newPosition.column() == actualPosition.column();
        boolean lowerRow = newPosition.row() <= actualPosition.row() * sign;
        boolean rightColumn = newPosition.column() == actualPosition.column() - sign;
        boolean leftColumn = newPosition.column() == actualPosition.column() + sign;
        boolean newPositionEmpty = board.getIndividualPiece(newPosition) == null;
        boolean isInInitialRow = actualPosition.row() == initialRow;
        boolean doubleForwardMovement = newPosition.row() == initialRow + sign * 2 && sameColumn;

        if (sameColumn && lowerRow){
            return false;
        }
        else if ((rightColumn || leftColumn) && forwardMovement){

            if (enPassantPosition == null) {
                if (newPositionEmpty) {
                    return false;
                }
            } else {
                if (enPassantPosition.equals(newPosition)) {
                    var pawnPosition = new Position(newPosition.row() - sign, newPosition.column());

                    if (pieceKind == PieceKind.LIGHT) {
                        AppState.whiteScore += board.getIndividualPiece(pawnPosition).pieceType.numericalValue;
                    } else {
                        AppState.blackScore += board.getIndividualPiece(pawnPosition).pieceType.numericalValue;
                    }
                    board.removePiece(pawnPosition);
                    return true;
                }
            }
            return !newPositionEmpty;
        }
        else if (forwardMovement && newPositionEmpty && sameColumn){
            return true;
        }
        else if (isInInitialRow && doubleForwardMovement){
            AppState.setEnPassantPosition(new Position(initialRow + sign, newPosition.column()));
            return true;
        }
        return false;
    }
}
