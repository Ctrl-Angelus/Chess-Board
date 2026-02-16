package app.utils;

import javafx.scene.paint.Color;
import javafx.stage.Screen;


public class AppParameters {
    public static final double APP_SIZE = Screen.getPrimary().getVisualBounds().getHeight() * 0.95;
    public static final double FONT_ASPECT_RATIO = APP_SIZE / 1080;
    public static final double FONT_SIZE = 32 * FONT_ASPECT_RATIO;
    public static final int BOARD_SIZE = 8;
    public static final double TILE_SIZE = APP_SIZE / (BOARD_SIZE + 1);
    public static final double PIECE_SIZE = TILE_SIZE * 0.7;

    public static final int NANOSECONDS = 1_000_000_000;
    public static final int FPS = 60;

    public static final String TITLE = "Chess Board JavaFX";
    public static final Color LIGHT_TEXT_COLOR = Color.valueOf("#cbdbfc");
    public static final Color DARK_TEXT_COLOR = Color.valueOf("#222c6b");

    // Forsyth–Edwards Notation (standard initial position)
    public static final String INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
}
