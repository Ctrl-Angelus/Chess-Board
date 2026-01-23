package app;

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

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Board board = new Board();
        board.drawBoard(gc);

        VBox vBox = new VBox(canvas);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= appParameters.NANOSECONDS/appParameters.FPS) {
                    board.drawBoard(gc);
                    lastUpdate = now;
                }
            }
        };

        timer.start();
        this.scene = new Scene(vBox);

        this.scene.setOnMousePressed(mouseEvent -> {
            double coordinateX = mouseEvent.getSceneX();
            double coordinateY = mouseEvent.getSceneY();

            int currentCol = (int) (coordinateX / board.CELL_SIZE);
            int currentRow = (int) (coordinateY / board.CELL_SIZE);

            if (mouseEvent.getButton() == MouseButton.SECONDARY){
                board.toggleCellHighlight(currentRow, currentCol);

            } else if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                board.setSelectedCell(currentRow, currentCol);

            } else if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                System.out.println(board.getSelectedCell());
            }
        });
    }
    public Scene getScene(){
        return this.scene;
    }
}
