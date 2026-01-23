package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cell {
    public final double coordinateX;
    public final double coordinateY;
    public final double size;
    public final Color color;
    private boolean isHighlighted;

    public Cell(double coordinateX, double coordinateY, double size, Color color, boolean isHighlighted){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.size = size;
        this.color = color;
        this.isHighlighted = isHighlighted;
    }
    public void toggleHighlight(){
        this.isHighlighted = !this.isHighlighted;
    }
    public boolean isHighlighted(){
        return this.isHighlighted;
    }
    public void paint(GraphicsContext gc, Color color){
        if (color == null){
            color = this.color;
        }
        gc.setFill(color);
        gc.fillRect(
                this.coordinateX,
                this.coordinateY,
                this.size,
                this.size
        );
    }
    @Override
    public String toString(){
        return String.format(
                "Cell[coordinates=(%f, %f), size=%f, color=%s, isHighlighted=%b",
                this.coordinateX,
                this.coordinateY,
                this.size,
                this.color,
                this.isHighlighted
        );
    }
}
