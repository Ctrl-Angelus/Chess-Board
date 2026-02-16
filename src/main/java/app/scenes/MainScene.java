package app.scenes;

import app.Board;
import app.utils.AppParameters;
import app.utils.AppState;
import app.utils.GameUtils;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainScene {
    private final Scene scene;

    public MainScene(){
        Board board = new Board(GameUtils.loadFENPosition(AppParameters.INITIAL_POSITION));

        Label whitePiecesScore = new Label("Score (White pieces): 0");
        Label blackPiecesScore = new Label("Score (Black pieces): 0");

        VBox vBox = new VBox(board);
        vBox.setPrefHeight(board.getHeight());
        vBox.setPrefWidth(board.getWidth());

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-insets: 0; -fx-border-color: transparent;");

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= AppParameters.NANOSECONDS/ AppParameters.FPS) {

                    board.drawBoard();
                    whitePiecesScore.setText("Score (White pieces): " + AppState.whiteScore);
                    blackPiecesScore.setText("Score (Black pieces): " + AppState.blackScore);

                    lastUpdate = now;
                }
            }
        };
        timer.start();

        this.scene = new Scene(scrollPane);
    }
    public Scene getScene(){
        return this.scene;
    }
}
