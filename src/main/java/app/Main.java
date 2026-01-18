package app;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public Scene getMainScene(){
        Canvas canvas = new Canvas(
                appParameters.AppSize,
                appParameters.AppSize
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

        Scene scene = new Scene(vBox);
        scene.setOnMouseClicked(mouseEvent -> {
            double coordinateX = mouseEvent.getSceneX();
            double coordinateY = mouseEvent.getSceneY();
            int currentCol = (int) (coordinateX / board.cellSize);
            int currentRow = (int) (coordinateY / board.cellSize);
            board.toggleCellSelection(currentCol, currentRow);
        });

        timer.start();
        return scene;
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(getMainScene());
        stage.setTitle(appParameters.TITLE);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}