package app.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class AppEvents {
    public static Vector2 getMouseCoordinates(MouseEvent mouseEvent, boolean hasRotation){
        double mouseX = mouseEvent.getSceneX();
        double mouseY = mouseEvent.getSceneY();

        if (hasRotation){
            mouseX = AppParameters.APP_SIZE - mouseX;
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
        int currentRow = (int) (coordinates.coordinateY() / AppParameters.TILE_SIZE);

        if (hasRotation){
            currentCol = AppParameters.BOARD_SIZE - currentCol - 1;
            currentRow = AppParameters.BOARD_SIZE - currentRow - 1;
        }
        return new Position(
                currentRow,
                currentCol
        );
    }

    public static void setCanvasEvents(Canvas canvas){
        canvas.setOnMouseClicked(mouseEvent -> {
            Position boardPosition = getBoardPosition(mouseEvent, AppState.isBoardRotated());

            if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                AppState.toggleBoardRotation();
            }
            if (mouseEvent.getButton() == MouseButton.SECONDARY){
                AppState.board.toggleTileHighlight(boardPosition.row(), boardPosition.column());

            }
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (!AppState.board.pieceDragging){
                    AppState.board.selectTile(boardPosition.row(), boardPosition.column());
                }
            }
        });
        canvas.setOnMouseDragged(mouseEvent -> {
            Position boardPosition = getBoardPosition(mouseEvent, AppState.isBoardRotated());

            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                if (AppState.board.getSelectedTile() == null){
                    AppState.board.selectTile(boardPosition.row(), boardPosition.column());
                }
                if (!AppState.board.getSelectedTile().hasAPiece()){
                    AppState.board.pieceDragging = false;
                    return;
                }
                if (!AppState.board.pieceDragging){
                    AppState.board.selectTile(boardPosition.row(), boardPosition.column());
                }

                AppState.setMousePosition(getMouseCoordinates(mouseEvent, AppState.isBoardRotated()));
                AppState.board.pieceDragging = true;
            }
        });
        canvas.setOnMouseReleased(mouseEvent -> {

            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (!AppState.board.pieceDragging){
                    return;
                }
                Position boardPosition = getBoardPosition(mouseEvent, AppState.isBoardRotated());
                AppState.board.selectTile(boardPosition.row(), boardPosition.column());
                AppState.board.pieceDragging = false;
            }
        });
    }
}
