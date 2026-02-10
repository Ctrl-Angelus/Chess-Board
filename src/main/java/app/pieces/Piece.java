package app.pieces;

import app.Board;
import app.utils.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Piece {
    public final ChessPieces pieceType;
    public final PieceKind pieceKind;
    public final Vector2 coordinates;
    public final Image image;

    public Piece(ChessPieces pieceType, Vector2 coordinates, PieceKind pieceKind){
        this.pieceType = pieceType;
        this.coordinates = coordinates;
        this.pieceKind = pieceKind;
        this.image = AppAssets.getImage(
            pieceType.getImagePath(pieceKind),
            AppParameters.PIECE_SIZE,
            AppParameters.PIECE_SIZE,
            false
        );
    }

    public void draw(GraphicsContext gc, Vector2 alternativeCoordinates){
        boolean useAlternativeCoordinates = (alternativeCoordinates != null);

        gc.save();
        gc.setImageSmoothing(false);
        gc.translate(
            (useAlternativeCoordinates) ? alternativeCoordinates.coordinateX() : coordinates.coordinateX() + AppParameters.PIECE_SIZE/2,
            (useAlternativeCoordinates) ? alternativeCoordinates.coordinateY() : coordinates.coordinateY() + AppParameters.PIECE_SIZE/2
        );

        gc.rotate(
            AppState.isBoardRotated() ? 180 : 0
        );

        gc.drawImage(
            this.image,
                -AppParameters.PIECE_SIZE/2,
                -AppParameters.PIECE_SIZE/2
        );
        gc.restore();
    }

    public static Vector2 getPiecePosition(Vector2 cellCoordinates){
        return new Vector2(
                cellCoordinates.coordinateX() + AppParameters.TILE_SIZE /2 - AppParameters.PIECE_SIZE/2,
                cellCoordinates.coordinateY() + AppParameters.TILE_SIZE /2 - AppParameters.PIECE_SIZE/2
        );
    }

    //TODO: REFACTOR THE DUPLICATED LOGIC
    public abstract MovementType checkMove(Position actualPosition, Position newPosition, Board board);
}
