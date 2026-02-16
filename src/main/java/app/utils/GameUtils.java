package app.utils;

import app.pieces.ChessPieces;
import app.pieces.Piece;
import app.pieces.PieceKind;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class GameUtils {

    public static Piece[][] loadFENPosition(String position){
        Piece[][] piecesMatrix = new Piece[AppParameters.BOARD_SIZE][AppParameters.BOARD_SIZE];
        char[] initialPosition = position.toCharArray();
        int row = 0;
        int column = 0;
        double coordinateX = 0;
        double coordinateY = AppParameters.TILE_SIZE/2;
        boolean piecePlacement = true;
        for (char character : initialPosition){
            if (character == ' '){
                if (!piecePlacement){
                    break;
                }
                piecePlacement = false;
                continue;
            }
            if (!piecePlacement){
                switch (Character.toLowerCase(character)){
                    case 'w' -> AppState.setActivePieces(PieceKind.LIGHT);
                    case 'b' -> AppState.setActivePieces(PieceKind.DARK);
                    default -> {}
                }
                continue;
            }
            if (Character.isDigit(character)){
                column += Character.getNumericValue(character);
                coordinateX += AppParameters.TILE_SIZE * Character.getNumericValue(character);
                continue;
            }
            if (character == '/'){
                row += 1;
                coordinateY += AppParameters.TILE_SIZE;
                column = 0;
                coordinateX = 0;
                continue;
            }
            if (isNotInsideBoard(new Position(row, column))){
                break;
            }
            PieceKind kind = Character.isUpperCase(character) ? PieceKind.LIGHT : PieceKind.DARK;
            for (ChessPieces piece : ChessPieces.values()){
                if (Character.toLowerCase(character) != piece.notation){
                    continue;
                }
                piecesMatrix[row][column] = piece.createInstance(
                        Piece.getPiecePosition(new Vector2(coordinateX, coordinateY)),
                        kind,
                        new Position(row, column)
                );
                break;
            }
            column += 1;
            coordinateX += AppParameters.TILE_SIZE;
        }
        return piecesMatrix;
    }

    public static boolean isNotInsideBoard(Position position){
        return position.column() >= AppParameters.BOARD_SIZE
                || position.column() < 0
                || position.row() >= AppParameters.BOARD_SIZE
                || position.row() < 0;
    }

    public static Vector2 getMouseCoordinates(MouseEvent mouseEvent, boolean hasRotation){
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        if (hasRotation){
            mouseX = AppParameters.APP_SIZE - mouseX - AppParameters.TILE_SIZE;
            mouseY = AppParameters.APP_SIZE - mouseY;
        }
        return new Vector2(
                mouseX,
                mouseY
        );
    }
    public static Position getBoardPosition(MouseEvent mouseEvent, boolean hasRotation){
        Vector2 coordinates = getMouseCoordinates(mouseEvent, false);
        int currentCol = (int) (coordinates.coordinateX() / AppParameters.TILE_SIZE);
        int currentRow = (int) ((coordinates.coordinateY() - AppParameters.TILE_SIZE/2) / AppParameters.TILE_SIZE);

        if (hasRotation){
            currentCol = AppParameters.BOARD_SIZE - currentCol - 1;
            currentRow = AppParameters.BOARD_SIZE - currentRow - 1;
        }
        return new Position(
                currentRow,
                currentCol
        );
    }
}
