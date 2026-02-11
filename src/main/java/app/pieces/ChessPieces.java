package app.pieces;

import app.pieces.variations.*;
import app.utils.Position;
import app.utils.TriFunction;
import app.utils.Vector2;

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
    public final TriFunction<Vector2, PieceKind, Position, Piece> factory;

    ChessPieces(
        int numericalValue,
        char notation,
        TriFunction<Vector2, PieceKind, Position, Piece> factory
    ){
        this.numericalValue = numericalValue;
        this.notation = notation;
        this.imagePath = String.format("/images/#shade#/%s.png", name().toLowerCase());
        this.factory = factory;
    }

    public Piece createInstance(Vector2 coordinates, PieceKind pieceShade, Position position){
        return this.factory.apply(coordinates, pieceShade, position);
    }



    public String getImagePath(PieceKind pieceKind){
        return imagePath.replace("#shade#", pieceKind.getShade());
    }

    public static boolean validPiece(char piece){
        for (ChessPieces type : ChessPieces.values()){
            if (piece == type.notation){
                return true;
            }
        }
        return false;
    }
    public static ChessPieces getType(char piece){
        for (ChessPieces type : ChessPieces.values()){
            if (piece == type.notation){
                return type;
            }
        }
        return null;
    }
}
