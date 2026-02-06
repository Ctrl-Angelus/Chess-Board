package app.tiles;

import app.utils.Position;
import app.utils.Vector2;
import app.utils.AppParameters;
import javafx.scene.canvas.GraphicsContext;

public class Tile {
    public final Vector2 coordinates;
    public final TileType type;
    private boolean isHighlighted = false;
    public final Position position;

    public Tile(Vector2 coordinates, TileType type, Position position){
        this.coordinates = coordinates;
        this.type = type;
        this.position = position;
    }
    public void toggleHighlight(){
        this.isHighlighted = !this.isHighlighted;
    }
    public void deactivateHighlight(){
        this.isHighlighted = false;
    }

    public void draw(GraphicsContext gc, TileType type){

        switch (type){
            case SELECTION -> gc.drawImage(
                type.getImage(),
                coordinates.coordinateX(),
                coordinates.coordinateY()
            );
            case CAPTURE, MOVEMENT -> {
                gc.drawImage(
                    this.type.getImage(),
                    coordinates.coordinateX(),
                    coordinates.coordinateY()
                );
                gc.drawImage(
                    type.getImage(),
                    coordinates.coordinateX(),
                    coordinates.coordinateY()
                );
            }
            default -> gc.drawImage(
                    this.type.getImage(),
                    coordinates.coordinateX(),
                    coordinates.coordinateY()
            );
        }
        if (isHighlighted && type != TileType.SELECTION){
            gc.drawImage(
                TileType.HIGHLIGHT.getImage(),
                coordinates.coordinateX(),
                coordinates.coordinateY()
            );
        }
    }

    @Override
    public String toString(){
        return String.format(
                "Tile[%s, size=%f, color=%s, isHighlighted=%b",
                this.coordinates,
                AppParameters.TILE_SIZE,
                this.type,
                this.isHighlighted
        );
    }
}
