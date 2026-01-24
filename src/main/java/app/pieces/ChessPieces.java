package app.pieces;

public enum ChessPieces {
    PAWN(1, "", "/images/%color%/pawn.png"),
    ROOK(5, "R", "/images/%color%/rook.png"),
    KNIGHT(3, "N", "/images/%color%/knight.png"),
    BISHOP(3, "B", "/images/%color%/bishop.png"),
    QUEEN(9, "Q", "/images/%color%/queen.png"),
    KING(0, "K", "/images/%color%/king.png");

    public final int numericalValue;
    public final String notation;
    public final String imagePath;

    ChessPieces(int numericalValue, String notation, String imagePath){
        this.numericalValue = numericalValue;
        this.notation = notation;
        this.imagePath = imagePath;
    }
}
