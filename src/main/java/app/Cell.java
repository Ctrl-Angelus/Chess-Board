package app;

import javafx.scene.paint.Color;

public class Cell {
    public final double x;
    public final double y;
    public final double size;
    public final Color color;
    private boolean selected;

    public Cell(double x, double y, double size, Color color, boolean selected){
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.selected = selected;
    }
    public void toggleSelected(){
        this.selected = !this.selected;
    }
    public boolean isSelected(){
        return this.selected;
    }
}
