package app;

public enum ChessPieces {
    PAWN(1, "", "/images/pawn.png"),
    ROOK(5, "R", "/images/rook.png"),
    KNIGHT(3, "K", "/images/knight.png"),
    BISHOP(3, "B", "/images/bishop.png"),
    QUEEN(9, "Q", "/images/queen.png"),
    KING(0, "K", "/images/king.png");

    public final int numericalValue;
    public final String notation;
    public final String imagePath;

    ChessPieces(int numericalValue, String notation, String imagePath){
        this.numericalValue = numericalValue;
        this.notation = notation;
        this.imagePath = imagePath;
    }
}
