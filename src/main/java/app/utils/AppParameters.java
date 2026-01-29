package app.utils;

import javafx.stage.Screen;


public class AppParameters {
    public static final double APP_SIZE = Screen.getPrimary().getVisualBounds().getHeight() * 0.90;
    public static final int BOARD_SIZE = 8;
    public static final double TILE_SIZE = APP_SIZE / BOARD_SIZE;
    public static final double PIECE_SIZE = TILE_SIZE * 0.7;

    public static final int NANOSECONDS = 1_000_000_000;
    public static final int FPS = 60;

    public static final String TITLE = "Chess Board JavaFX";

    // Forsyth–Edwards Notation (standard initial position)
    public static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
}
