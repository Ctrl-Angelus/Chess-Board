package app;

public enum ChessPieces {
    PAWN(1, ""),
    ROOK(5, "R"),
    KNIGHT(3, "K"),
    BISHOP(3, "B"),
    QUEEN(9, "Q"),
    KING(0, "K");

    public final int numericalValue;
    public final String notation;

    ChessPieces(int numericalValue, String notation){
        this.numericalValue = numericalValue;
        this.notation = notation;
    }
}
