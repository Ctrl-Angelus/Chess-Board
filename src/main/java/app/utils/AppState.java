package app.utils;

import app.pieces.PieceKind;

public class AppState {
    private static boolean boardRotated = false;
    public static void toggleBoardRotation(){
        boardRotated = !boardRotated;
    }
    public static boolean isBoardRotated(){
        return boardRotated;
    }
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

    private static PieceKind activePieces = PieceKind.LIGHT;
    public static PieceKind getActivePieces() {
        return activePieces;
    }
    public static void toggleActivePieces() {
        activePieces = (activePieces == PieceKind.LIGHT) ? PieceKind.DARK : PieceKind.LIGHT;
    }
    public static void setActivePieces(PieceKind pieceKind){
        activePieces = pieceKind;
    }

    private static String enPassantPosition = "-";

    public static String getEnPassantPosition() {
        return enPassantPosition;
    }
    public static void setEnPassantPosition(Position position){
        enPassantPosition = position.getPositionNotation();
    }
    public static void cancelEnPassant(){
        enPassantPosition = "-";
    }
    public static boolean enPassantAvailable(){
        return !getEnPassantPosition().equals("-");
    }
}
