package app;

import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class appParameters {
    public static final double APP_SIZE = Screen.getPrimary().getVisualBounds().getHeight() * 0.90;
    public static final int NANOSECONDS = 1_000_000_000;
    public static final int FPS = 60;
    public static final String TITLE = "Chess Board JavaFX";

    public static final Color HIGHLIGHT_COLOR = Color.rgb(174, 30, 30, 0.8);
    public static final Color SELECTION_COLOR = Color.rgb(30, 172, 174);
}
