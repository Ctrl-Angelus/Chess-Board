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

            if (mouseEvent.getButton() == MouseButton.SECONDARY){
                board.toggleCellHighlight(currentRow, currentCol);

            } else if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                board.setSelectedCell(currentRow, currentCol);

            } else if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                System.out.println(board.getSelectedCell());
            }
        });

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        VBox vBox = new VBox(canvas);
        board.drawBoard(graphicsContext);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= appParameters.NANOSECONDS/appParameters.FPS) {
                    board.drawBoard(graphicsContext);
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
