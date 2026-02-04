package app.pieces;

import app.pieces.variations.*;
import app.utils.Vector2;

import java.util.function.BiFunction;

public enum ChessPieces {
    PAWN(1, 'p', Pawn::new),
    ROOK(5, 'r', Rook::new),
    KNIGHT(3, 'n', Knight::new),
    BISHOP(3, 'b', Bishop::new),
    QUEEN(9, 'q', Queen::new),
    KING(0, 'k', King::new);

    public final int numericalValue;
    public final char notation;
    public final String imagePath;
    public final BiFunction<Vector2, PieceKind, Piece> factory;

    ChessPieces(
        int numericalValue,
        char notation,
        BiFunction<Vector2, PieceKind, Piece> factory
    ){
        this.numericalValue = numericalValue;
        this.notation = notation;
        this.imagePath = String.format("/images/#shade#/%s.png", name().toLowerCase());
        this.factory = factory;
    }

    public Piece createInstance(Vector2 coordinates, PieceKind pieceShade){
        return this.factory.apply(coordinates, pieceShade);
    }

    public String getImagePath(PieceKind pieceKind){
        return imagePath.replace("#shade#", pieceKind.getShade());
    }
}
