package app.pieces;

import app.utils.Vector2;

import java.util.function.BiFunction;

public enum ChessPieces {
    PAWN(1, 'p', "/images/%shade%/pawn.png", Pawn::new),
    ROOK(5, 'r', "/images/%shade%/rook.png", Rook::new),
    KNIGHT(3, 'n', "/images/%shade%/knight.png", Knight::new),
    BISHOP(3, 'b', "/images/%shade%/bishop.png", Bishop::new),
    QUEEN(9, 'q', "/images/%shade%/queen.png", Queen::new),
    KING(0, 'k', "/images/%shade%/king.png", King::new);

    public final int numericalValue;
    public final char notation;
    public final String imagePath;
    public final BiFunction<Vector2, PieceShade, Piece> factory;

    ChessPieces(
        int numericalValue,
        char notation,
        String imagePath,
        BiFunction<Vector2, PieceShade, Piece> factory
    ){
        this.numericalValue = numericalValue;
        this.notation = notation;
        this.imagePath = imagePath;
        this.factory = factory;
    }

    public Piece createInstance(Vector2 coordinates, PieceShade pieceShade){
        return this.factory.apply(coordinates, pieceShade);
    }
}
