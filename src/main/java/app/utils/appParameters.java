package app.utils;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.util.Objects;

public class appParameters {
    public static final double APP_SIZE = Screen.getPrimary().getVisualBounds().getHeight() * 0.90;
    public static final int BOARD_SIZE = 8;
    public static final double CELL_SIZE = appParameters.APP_SIZE / BOARD_SIZE;
    public static final double PIECE_SIZE = CELL_SIZE * 0.7;

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
    public static final Image ICON = new Image(
        Objects.requireNonNull(
            appParameters.class.getResourceAsStream(
                "/images/icons/icon.png"
            )
        )
    );

    public static final Color HIGHLIGHT_COLOR = Color.valueOf("AE1E1ECC");
    public static final Color SELECTION_COLOR = Color.valueOf("1EACAEFF");

    public static final Color BOARD_COLOR_DARK = Color.valueOf("000000FF");
    public static final Color BOARD_COLOR_LIGHT = Color.valueOf("97CAD9FF");

    // Forsyth–Edwards Notation (standard initial position)
    public static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
}
