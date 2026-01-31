package app.utils;

import app.Board;

public class AppState {
    private static boolean boardRotated = false;

    public static void toggleBoardRotation(){
        boardRotated = !boardRotated;
    }
    public static boolean isBoardRotated(){
        return boardRotated;
    }
    public static final Board board = new Board();
    private static Vector2 mousePosition = null;

    public static Vector2 getMousePosition() {
        return mousePosition;
    }

    public static void setMousePosition(Vector2 newMousePosition) {
        mousePosition = newMousePosition;
    }

    public static void deleteMousePosition(){
        mousePosition = null;
    }

    public static int whiteScore = 0;
    public static int blackScore = 0;
}
