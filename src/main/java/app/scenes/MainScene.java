package app.scenes;

import app.Board;
import app.utils.AppParameters;
import app.utils.Vector2;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class MainScene {
    private final Scene scene;
    private Vector2 mouseCoordinates = null;

    public MainScene(){
        Canvas canvas = new Canvas(
                AppParameters.APP_SIZE,
                AppParameters.APP_SIZE
        );
        Board board = new Board();

        canvas.setOnMousePressed(mouseEvent -> {
            double coordinateX = mouseEvent.getSceneX();
            double coordinateY = mouseEvent.getSceneY();

            int currentCol = (int) (coordinateX / AppParameters.TILE_SIZE);
            int currentRow = (int) (coordinateY / AppParameters.TILE_SIZE);

            if (AppParameters.isBoardRotated()){
                currentCol = AppParameters.BOARD_SIZE - currentCol - 1;
                currentRow = AppParameters.BOARD_SIZE - currentRow - 1;
            }

            if (mouseEvent.getButton() == MouseButton.SECONDARY){
                board.toggleTileHighlight(currentRow, currentCol);

            } else if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                board.selectTile(currentRow, currentCol);

            } else if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                AppParameters.toggleBoardRotation();
            }
        });
        canvas.setOnMouseDragged(mouseEvent -> {
            double mouseX = mouseEvent.getSceneX();
            double mouseY = mouseEvent.getSceneY();

            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                if (board.getSelectedTile() == null){
                    return;
                }
                if (!board.getSelectedTile().hasAPiece()){
                    return;
                }

                if (AppParameters.isBoardRotated()){
                    mouseX = AppParameters.APP_SIZE - mouseX;
                    mouseY = AppParameters.APP_SIZE - mouseY;
                }

                mouseCoordinates = new Vector2(
                    mouseX,
                    mouseY
                );
                board.pieceDragging = true;
            }
        });
        canvas.setOnMouseReleased(mouseEvent -> {
            double coordinateX = mouseEvent.getSceneX();
            double coordinateY = mouseEvent.getSceneY();

            int currentCol = (int) (coordinateX / AppParameters.TILE_SIZE);
            int currentRow = (int) (coordinateY / AppParameters.TILE_SIZE);

            if (AppParameters.isBoardRotated()){
                currentCol = AppParameters.BOARD_SIZE - currentCol - 1;
                currentRow = AppParameters.BOARD_SIZE - currentRow - 1;
            }

            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                board.selectTile(currentRow, currentCol);
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

                    board.drawBoard(graphicsContext, mouseCoordinates);
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
