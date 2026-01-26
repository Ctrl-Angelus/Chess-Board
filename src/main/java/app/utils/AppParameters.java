package app.utils;

import javafx.stage.Screen;


public class AppParameters {
    public static final double APP_SIZE = Screen.getPrimary().getVisualBounds().getHeight() * 0.90;
    public static final int BOARD_SIZE = 8;
    public static final double CELL_SIZE = APP_SIZE / BOARD_SIZE;
    public static final double PIECE_SIZE = CELL_SIZE * 0.8;

    public static final int NANOSECONDS = 1_000_000_000;
    public static final int FPS = 60;
    private static boolean boardRotated = false;

    public static void toggleBoardRotation(){
        boardRotated = !boardRotated;
    }
    public static boolean isBoardRotated(){
        return boardRotated;
    }

    public static final String TITLE = "Chess Board JavaFX";

    // Forsyth–Edwards Notation (standard initial position)
    public static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
}
