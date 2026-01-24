package app.pieces;

public class Pawn extends Piece {

    public Pawn(double coordinateX, double coordinateY, PieceShade pieceShade){
        super(ChessPieces.PAWN, coordinateX, coordinateY, pieceShade);
    }

    @Override
    public void move() {

    }
}
