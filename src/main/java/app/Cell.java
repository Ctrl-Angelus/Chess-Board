package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cell {
    public final double coordinateX;
    public final double coordinateY;
    public final Color color;
    private Piece piece = null;
    private boolean isHighlighted = false;
    private boolean hasAPiece = false;

    public Cell(double coordinateX, double coordinateY, Color color){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.color = color;
    }
    public void toggleHighlight(){
        this.isHighlighted = !this.isHighlighted;
    }
    public boolean isHighlighted(){
        return this.isHighlighted;
    }
    public void togglePieceContent(){
        this.hasAPiece = !this.hasAPiece;
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
        }
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void draw(GraphicsContext gc, Color color){
        if (color == null){
            color = this.color;
        }
        gc.setFill(color);
        gc.fillRect(
                this.coordinateX,
                this.coordinateY,
                appParameters.CELL_SIZE,
                appParameters.CELL_SIZE
        );
    }
    @Override
    public String toString(){
        return String.format(
                "Cell[coordinates=(%f, %f), size=%f, color=%s, isHighlighted=%b",
                this.coordinateX,
                this.coordinateY,
                appParameters.CELL_SIZE,
                this.color,
                this.isHighlighted
        );
    }
}
