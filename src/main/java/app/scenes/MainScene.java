package app.scenes;

import app.utils.AppEvents;
import app.utils.AppParameters;
import app.utils.AppState;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;

public class MainScene {
    private final Scene scene;

    public MainScene(){
        Canvas canvas = new Canvas(
            AppParameters.APP_SIZE,
            AppParameters.APP_SIZE
        );

        AppEvents.setCanvasEvents(canvas);

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

                    graphicsContext.rotate(AppState.isBoardRotated() ? 180 : 0);

                    graphicsContext.translate(-canvasWidth / 2, -canvasHeight / 2);

                    AppState.board.drawBoard(graphicsContext);
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
