package app.pieces.variations;

import app.Board;
import app.pieces.ChessPieces;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.utils.GameUtils;
import app.utils.Position;
import app.utils.Vector2;

public class Queen extends Piece {

    public Queen(Vector2 coordinates, PieceKind pieceShade){
        super(ChessPieces.QUEEN, coordinates, pieceShade);
    }

    @Override
    public boolean canMove(Position actualPosition, Position newPosition, Board board) {
        Piece targetPiece = board.getIndividualPiece(newPosition);
        if (targetPiece != null && targetPiece.pieceKind == pieceKind){
            return false;
        }

        int rowSign = (newPosition.row() - actualPosition.row() > 0) ? 1 : -1;
        int columnSign = (newPosition.column() - actualPosition.column() > 0) ? 1 : -1;
        int columnDifference = Math.abs(newPosition.column() - actualPosition.column());
        int rowDifference = Math.abs(newPosition.row() - actualPosition.row());

        boolean sameDifference = columnDifference == rowDifference;

        if (sameDifference){
            int currentRow = actualPosition.row() + rowSign;
            int currentColumn = actualPosition.column() + columnSign;

            while (!new Position(currentRow, currentColumn).equals(newPosition)){
                if (GameUtils.isNotInsideBoard(new Position(currentRow, currentColumn))){
                    return false;
                }
                if (board.getIndividualPiece(new Position(currentRow, currentColumn)) != null){
                    return false;
                }
                currentRow += rowSign;
                currentColumn += columnSign;
            }
            return true;
        }

        boolean sameColumn = actualPosition.column() == newPosition.column();
        boolean sameRow = actualPosition.row() == newPosition.row();

        if (sameRow || sameColumn){

            if (sameColumn){
                int currentRow = actualPosition.row() + rowSign;
                while (!new Position(currentRow, actualPosition.column()).equals(newPosition)){
                    if (GameUtils.isNotInsideBoard(new Position(actualPosition.row(), actualPosition.column()))){
                        return false;
                    }
                    if (board.getIndividualPiece(new Position(currentRow, actualPosition.column())) != null){
                        return false;
                    }
                    currentRow += rowSign;
                }

            } else {
                int currentColumn = actualPosition.column() + columnSign;
                while (!new Position(actualPosition.row(), currentColumn).equals(newPosition)){
                    if (GameUtils.isNotInsideBoard(new Position(actualPosition.row(), currentColumn))){
                        return false;
                    }
                    if (board.getIndividualPiece(new Position(actualPosition.row(), currentColumn)) != null){
                        return false;
                    }
                    currentColumn += columnSign;
                }
            }
            return true;
        }

        return false;
    }
}
