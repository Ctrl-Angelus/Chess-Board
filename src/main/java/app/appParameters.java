package app;

import javafx.stage.Screen;

public class appParameters {
    public static final double AppSize = Screen.getPrimary().getVisualBounds().getHeight() * 0.90;
    public static final int NANOSECONDS = 1_000_000_000;
    public static final int FPS = 60;
    public static final String TITLE = "Chess Board JavaFX";
}
