package app.scenes;

import app.Board;
import app.utils.appParameters;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class MainScene {
    private final Scene scene;

    public MainScene(){
        Canvas canvas = new Canvas(
                appParameters.APP_SIZE,
                appParameters.APP_SIZE
        );
        Board board = new Board();

        canvas.setOnMouseClicked(mouseEvent -> {
            double coordinateX = mouseEvent.getSceneX();
            double coordinateY = mouseEvent.getSceneY();

            int currentCol = (int) (coordinateX / appParameters.CELL_SIZE);
            int currentRow = (int) (coordinateY / appParameters.CELL_SIZE);

            if (appParameters.isBoardRotated()){
                currentCol = appParameters.BOARD_SIZE - currentCol - 1;
                currentRow = appParameters.BOARD_SIZE - currentRow - 1;
            }

            if (mouseEvent.getButton() == MouseButton.SECONDARY){
                board.toggleCellHighlight(currentRow, currentCol);

            } else if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                board.setSelectedCell(currentRow, currentCol);

            } else if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                appParameters.toggleBoardRotation();
            }
        });

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        VBox vBox = new VBox(canvas);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= appParameters.NANOSECONDS/appParameters.FPS) {
                    graphicsContext.save();

                    double canvasWidth = canvas.getWidth();
                    double canvasHeight = canvas.getHeight();

                    graphicsContext.translate(canvasWidth / 2, canvasHeight / 2);

                    graphicsContext.rotate(appParameters.isBoardRotated() ? 180 : 0);

                    graphicsContext.translate(-canvasWidth / 2, -canvasHeight / 2);

                    board.drawBoard(graphicsContext);
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
