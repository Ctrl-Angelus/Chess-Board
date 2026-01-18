package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {

    public final int boardSize = 8;
    public final double cellSize = appParameters.AppSize / boardSize;

    private final Cell[][] cells = new Cell[this.boardSize][this.boardSize];

    private Cell selectedCell = null;

    public Board(){
        double x;
        double y = 0;
        boolean isColored = false;

        for (int row = 0; row < boardSize; row++) {
            x = 0;
            for (int col = 0; col < boardSize; col++){
                Cell cell = new Cell(x, y, cellSize, isColored ? Color.BLACK : Color.WHITE, false);
                cells[row][col] = cell;

                x += cellSize;
                isColored = !isColored;
            }
            isColored = cells[row][0].color == Color.WHITE;
            y += cellSize;
        }
    }

    public void drawBoard(GraphicsContext gc){
        for (int row = 0; row < this.boardSize; row++){
            for (int col = 0; col < this.boardSize; col++) {
                Cell currentCell = cells[row][col];
                Color currentColor = currentCell.isSelected() ? Color.BLUE : currentCell.color;
                gc.setFill(currentColor);
                gc.fillRect(
                        currentCell.x,
                        currentCell.y,
                        this.cellSize,
                        this.cellSize
                );
            }
        }
    }

    public void toggleCellSelection(int col, int row){
        if (col >= this.boardSize || col < 0 || row >= this.boardSize || row < 0){
            return;
        }
        Cell currentCell = this.cells[row][col];
        currentCell.toggleSelected();
        this.selectedCell = currentCell.isSelected() ? currentCell : null;
    }

    public Cell getSelectedCell(){
        return this.selectedCell;
    }
}
