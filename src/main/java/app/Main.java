package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    final double height = Screen.getPrimary().getVisualBounds().getHeight() * 0.90;
    final int boardSize = 8;
    final double cellSize = height / boardSize;
    boolean color = false;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(height, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double x;
        double y = 0;

        for (int col = 0; col < boardSize; col++) {
            x = 0;
            for (int row = 0; row < boardSize; row++){
                gc.setFill(color ? Color.BLACK : Color.WHITE);
                gc.fillRect(x, y, cellSize, cellSize);
                x += cellSize;
                color = !color;
            }
            color = !color;
            y += cellSize;
        }

        VBox vBox = new VBox(canvas);

        stage.setScene(new Scene(vBox));
        stage.setTitle("Chess Board JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Chess Board");
        launch(args);
    }
}