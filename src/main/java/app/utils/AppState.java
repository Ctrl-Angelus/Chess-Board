package app.utils;

import app.pieces.PieceKind;

import static app.pieces.PieceKind.DARK;
import static app.pieces.PieceKind.LIGHT;

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

    private static PieceKind activePieces = LIGHT;
    public static PieceKind getActivePieces() {
        return activePieces;
    }
    public static void toggleActivePieces() {
        activePieces = (activePieces == LIGHT) ? DARK : LIGHT;
    }
    public static void setActivePieces(PieceKind pieceKind){
        activePieces = pieceKind;
    }

    private static String enPassantNotation = "-";
    private static Position enPassantPosition = null;
    private static Position enPassantPiecePosition = null;

    public static String getEnPassantNotation() {
        return enPassantNotation;
    }
    public static Position getEnPassantPosition(){
        return enPassantPosition;
    }
    public static void setEnPassantPosition(Position enPassantPosition, Position piecePosition){

        enPassantNotation = enPassantPosition.getPositionNotation();
        AppState.enPassantPosition = enPassantPosition;
        enPassantPiecePosition = piecePosition;
    }
    public static void cancelEnPassant(){
        enPassantNotation = "-";
        enPassantPosition = null;
    }
    public static boolean enPassantAvailable(){
        return enPassantPosition != null;
    }

    public static Position getEnPassantPiecePosition() {
        return enPassantPiecePosition;
    }
}
