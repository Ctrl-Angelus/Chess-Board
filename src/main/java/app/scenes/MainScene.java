package app.scenes;

import app.Board;
import app.utils.AppParameters;
import app.utils.Position;
import app.utils.Vector2;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MainScene {
    private final Scene scene;
    private Vector2 mousePosition = null;

    public Vector2 getMouseCoordinates(MouseEvent mouseEvent, boolean hasRotation){
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
    public Position getBoardPosition(MouseEvent mouseEvent, boolean hasRotation){
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

    public MainScene(){
        Canvas canvas = new Canvas(
            AppParameters.APP_SIZE,
            AppParameters.APP_SIZE
        );
        Board board = new Board();

        canvas.setOnMouseClicked(mouseEvent -> {
            Position boardPosition = getBoardPosition(mouseEvent, AppParameters.isBoardRotated());

            if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                AppParameters.toggleBoardRotation();
            }
            if (mouseEvent.getButton() == MouseButton.SECONDARY){
                board.toggleTileHighlight(boardPosition.row(), boardPosition.column());

            }
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (!board.pieceDragging){
                    board.selectTile(boardPosition.row(), boardPosition.column());
                }
            }
        });
        canvas.setOnMouseDragged(mouseEvent -> {
            Position boardPosition = getBoardPosition(mouseEvent, AppParameters.isBoardRotated());

            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                if (board.getSelectedTile() == null){
                    board.selectTile(boardPosition.row(), boardPosition.column());
                }
                if (!board.getSelectedTile().hasAPiece()){
                    board.pieceDragging = false;
                    return;
                }
                if (!board.pieceDragging){
                    board.selectTile(boardPosition.row(), boardPosition.column());
                }

                mousePosition = getMouseCoordinates(mouseEvent, AppParameters.isBoardRotated());
                board.pieceDragging = true;
            }
        });
        canvas.setOnMouseReleased(mouseEvent -> {

            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (!board.pieceDragging){
                    return;
                }
                Position boardPosition = getBoardPosition(mouseEvent, AppParameters.isBoardRotated());
                board.selectTile(boardPosition.row(), boardPosition.column());
                board.pieceDragging = false;
            }
        });

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        VBox vBox = new VBox(canvas);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= AppParameters.NANOSECONDS/ AppParameters.FPS) {
                    graphicsContext.save();

                    double canvasWidth = canvas.getWidth();
                    double canvasHeight = canvas.getHeight();

                    graphicsContext.translate(canvasWidth / 2, canvasHeight / 2);

                    graphicsContext.rotate(AppParameters.isBoardRotated() ? 180 : 0);

                    graphicsContext.translate(-canvasWidth / 2, -canvasHeight / 2);

                    board.drawBoard(graphicsContext, mousePosition);
                    graphicsContext.restore();
                    lastUpdate = now;
                }
            }
        };
        timer.start();

        this.scene = new Scene(vBox);
    }
    public Scene getScene(){
        return this.scene;
    }
}
