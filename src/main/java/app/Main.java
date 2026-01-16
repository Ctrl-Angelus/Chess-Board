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

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(height, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, height, height);

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