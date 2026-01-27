package app;

import app.pieces.Piece;
import app.utils.Vector2;
import app.utils.AppParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {
    public final Vector2 coordinates;
    public final TileType type;
    private Piece piece = null;
    private boolean isHighlighted = false;
    private boolean isSelected = false;
    private boolean hasAPiece = false;

    public Tile(Vector2 coordinates, TileType type){
        this.coordinates = coordinates;
        this.type = type;
    }
    public void toggleHighlight(){
        this.isHighlighted = !this.isHighlighted;
    }
    public void toggleSelection(){
        this.isSelected = !this.isSelected;
    }
    public void togglePieceContent(){
        this.hasAPiece = !this.hasAPiece;
    }
    public boolean isHighlighted(){
        return this.isHighlighted;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public boolean hasAPiece(){
        return this.hasAPiece;
    }

    public void setPiece(Piece piece) {
        if (this.hasAPiece()){
            return;
        }
        this.piece = piece;
        this.togglePieceContent();
    }

    public void removePiece(){
        if (this.hasAPiece()){
            this.piece = null;
            this.togglePieceContent();
        }
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void draw(GraphicsContext gc){
        Image image = type.getImage();

        if (isHighlighted()){ image = TileType.HIGHLIGHT.getImage(); }
        if (isSelected()){ image = TileType.SELECTION.getImage(); }

        gc.setImageSmoothing(false);
        gc.drawImage(
                image,
                coordinates.coordinateX(),
                coordinates.coordinateY(),
                AppParameters.TILE_SIZE,
                AppParameters.TILE_SIZE
        );
    }

    public Vector2 getPiecePosition(){
        return new Vector2(
            coordinates.coordinateX() + AppParameters.TILE_SIZE /2 - AppParameters.PIECE_SIZE/2,
            coordinates.coordinateY() + AppParameters.TILE_SIZE /2 - AppParameters.PIECE_SIZE/2
        );
    }

    @Override
    public String toString(){
        return String.format(
                "Tile[%s, size=%f, color=%s, isHighlighted=%b, hasAPiece=%b",
                this.coordinates,
                AppParameters.TILE_SIZE,
                this.type,
                this.isHighlighted,
                this.hasAPiece
        );
    }
}
