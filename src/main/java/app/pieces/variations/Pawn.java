package app.pieces.variations;

import app.Board;
import app.pieces.ChessPieces;
import app.pieces.MovementType;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.utils.AppState;
import app.utils.Position;
import app.utils.Vector2;

public class Pawn extends Piece {

    public Pawn(Vector2 coordinates, PieceKind pieceShade, Position position){
        super(ChessPieces.PAWN, coordinates, pieceShade, position);
    }

    @Override
    public MovementType checkMove(Position actualPosition, Position newPosition, Board board) {
        int sign = switch (pieceKind){
            case DARK -> 1;
            case LIGHT -> -1;
        };
        int initialRow = switch (pieceKind){
            case DARK -> 1;
            case LIGHT -> 6;
        };
        int finalRow = switch (pieceKind){
            case DARK -> 7;
            case LIGHT -> 0;
        };
        Piece targetPiece = board.getIndividualPiece(newPosition);
        if (targetPiece != null && targetPiece.pieceKind == pieceKind){
            return MovementType.ILLEGAL_MOVE;
        }
        Position enPassantPosition = AppState.getEnPassantPosition();

        boolean forwardMovement = newPosition.row() == actualPosition.row() + sign;
        boolean sameColumn = newPosition.column() == actualPosition.column();
        boolean lowerRow = newPosition.row() <= actualPosition.row() * sign;
        boolean rightColumn = newPosition.column() == actualPosition.column() - sign;
        boolean leftColumn = newPosition.column() == actualPosition.column() + sign;
        boolean newPositionEmpty = board.getIndividualPiece(newPosition) == null;
        boolean isInInitialRow = actualPosition.row() == initialRow;
        boolean doubleForwardMovement = newPosition.row() == initialRow + sign * 2 && sameColumn;

        if (sameColumn && lowerRow){
            return MovementType.ILLEGAL_MOVE;
        }
        else if ((rightColumn || leftColumn) && forwardMovement){

            if (enPassantPosition == null) {
                if (newPositionEmpty) {
                    return MovementType.ILLEGAL_MOVE;
                }
            } else {
                boolean correctPosition = enPassantPosition.equals(newPosition);
                boolean enPassantPiecePositionNotNull = board.getIndividualPiece(AppState.getEnPassantPiecePosition()) != null;
                if (correctPosition && enPassantPiecePositionNotNull && board.getIndividualPiece(AppState.getEnPassantPiecePosition()).pieceKind != pieceKind) {

                    return MovementType.EN_PASSANT;
                }
            }
            if (!newPositionEmpty && newPosition.row() == finalRow){
                return MovementType.PROMOTION;
            }
            return newPositionEmpty ? MovementType.ILLEGAL_MOVE : MovementType.LEGAL_MOVE;
        }
        else if (forwardMovement && newPositionEmpty && sameColumn){
            if (newPosition.row() == finalRow){
                return MovementType.PROMOTION;
            }
            return MovementType.LEGAL_MOVE;
        }
        else if (isInInitialRow && doubleForwardMovement){
            return MovementType.INITIAL_DOUBLE_PAWN_MOVE;
        }
        return MovementType.ILLEGAL_MOVE;
    }
}
