package app;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static final double height = Screen.getPrimary().getVisualBounds().getHeight() * 0.90;
    public static final int boardSize = 8;
    public static final double cellSize = height / boardSize;
    public static boolean color = false;

    public static Cell[][] cells = new Cell[boardSize][boardSize];

    public static void draw(GraphicsContext gc){
        for (int row = 0; row < boardSize; row++){
            for (int col = 0; col < boardSize; col++) {
                Cell currentCell = cells[row][col];
                Color currentColor = currentCell.isSelected() ? Color.BLUE : currentCell.color;
                gc.setFill(currentColor);
                gc.fillRect(
                        currentCell.x,
                        currentCell.y,
                        cellSize,
                        cellSize
                );
            }
        }
    }

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(height, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double x;
        double y = 0;

        for (int row = 0; row < boardSize; row++) {
            x = 0;
            for (int col = 0; col < boardSize; col++){
                Cell cell = new Cell(x, y, cellSize, color ? Color.BLACK : Color.WHITE, false);
                cells[row][col] = cell;

                x += cellSize;
                color = !color;
            }
            color = !color;
            y += cellSize;
        }

        draw(gc);
        VBox vBox = new VBox(canvas);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000/60) {
                    draw(gc);
                    lastUpdate = now;
                }
            }
        };

        Scene scene = new Scene(vBox);
        scene.setOnMouseClicked(mouseEvent -> {
            double coordinateX = mouseEvent.getSceneX();
            double coordinateY = mouseEvent.getSceneY();
            int currentCol = (int) (coordinateX / cellSize);
            int currentRow = (int) (coordinateY / cellSize);
            cells[currentRow][currentCol].toggleSelected();
        });

        timer.start();
        stage.setScene(scene);
        stage.setTitle("Chess Board JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Chess Board");
        launch(args);
    }
}