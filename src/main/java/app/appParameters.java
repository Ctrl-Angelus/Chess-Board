package app;

import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class appParameters {
    public static final double APP_SIZE = Screen.getPrimary().getVisualBounds().getHeight() * 0.90;
    public static final int BOARD_SIZE = 8;
    public static final double CELL_SIZE = appParameters.APP_SIZE / BOARD_SIZE;
    public static final double PIECE_SIZE = CELL_SIZE * 0.7;

    public static final int NANOSECONDS = 1_000_000_000;
    public static final int FPS = 60;

    public static final String TITLE = "Chess Board JavaFX";

    public static final Color HIGHLIGHT_COLOR = Color.valueOf("AE1E1ECC");
    public static final Color SELECTION_COLOR = Color.valueOf("1EACAEFF");

    public static final Color BOARD_COLOR_DARK = Color.valueOf("000000FF");
    public static final Color BOARD_COLOR_LIGHT = Color.valueOf("97CAD9FF");
}
