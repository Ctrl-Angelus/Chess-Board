package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {

    private final int BOARD_SIZE = 8;
    public final double CELL_SIZE = appParameters.APP_SIZE / BOARD_SIZE;

    private final Cell[][] cellsMatrix = new Cell[this.BOARD_SIZE][this.BOARD_SIZE];

    private int[] selectedCell = null;

    public Board(){
        double coordinateX;
        double coordinateY = 0;
        boolean isColored = false;

        for (int row = 0; row < BOARD_SIZE; row++) {
            coordinateX = 0;
            for (int column = 0; column < BOARD_SIZE; column++){
                Cell cell = new Cell(
                        coordinateX,
                        coordinateY,
                        CELL_SIZE,
                        isColored ? Color.BLACK : Color.WHITE,
                        false
                );

                cellsMatrix[row][column] = cell;

                coordinateX += CELL_SIZE;
                isColored = !isColored;
            }
            isColored = cellsMatrix[row][0].color == Color.WHITE;
            coordinateY += CELL_SIZE;
        }
    }

    public void drawBoard(GraphicsContext gc){
        for (int row = 0; row < this.BOARD_SIZE; row++){
            for (int column = 0; column < this.BOARD_SIZE; column++) {
                Cell currentCell = cellsMatrix[row][column];
                currentCell.paint(gc, null);

                if (currentCell.isHighlighted()){
                    currentCell.paint(gc, appParameters.HIGHLIGHT_COLOR);
                }
                if (getSelectedCell() != null){
                    if (!currentCell.equals(getSelectedCell())){
                        continue;
                    }
                    currentCell.paint(gc, appParameters.SELECTION_COLOR);
                }
            }
        }
    }

    public void toggleCellHighlight(int row, int column){
        if (!isInsideBoard(row, column)){
            return;
        }
        Cell currentCell = this.cellsMatrix[row][column];
        currentCell.toggleHighlight();
    }

    public Cell getSelectedCell(){
        if (this.selectedCell == null){
            return null;
        }
        return this.cellsMatrix[this.selectedCell[0]][this.selectedCell[1]];
    }
    public void setSelectedCell(int row, int column){
        if (!isInsideBoard(row, column)){
            return;
        }
        if (this.cellsMatrix[row][column].equals(getSelectedCell())){
            this.selectedCell = null;
            return;
        }
        this.selectedCell = new int[2];
        this.selectedCell[0] = row;
        this.selectedCell[1] = column;
    }

    private boolean isInsideBoard(int row, int column){
        return column < this.BOARD_SIZE && column >= 0 && row < this.BOARD_SIZE && row >= 0;
    }
}
