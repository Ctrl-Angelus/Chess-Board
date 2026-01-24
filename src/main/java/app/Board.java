package app;

import javafx.scene.canvas.GraphicsContext;

public class Board {

    private final Cell[][] cellsMatrix = new Cell[appParameters.BOARD_SIZE][appParameters.BOARD_SIZE];

    private int[] selectedCell = null;

    public Board(){
        double coordinateX;
        double coordinateY = 0;
        boolean isColored = false;

        for (int row = 0; row < appParameters.BOARD_SIZE; row++) {
            coordinateX = 0;
            for (int column = 0; column < appParameters.BOARD_SIZE; column++){
                Cell cell = new Cell(
                        coordinateX,
                        coordinateY,
                        isColored ? appParameters.BOARD_COLOR_DARK : appParameters.BOARD_COLOR_LIGHT
                );

                cellsMatrix[row][column] = cell;

                coordinateX += appParameters.CELL_SIZE;
                isColored = !isColored;
            }
            isColored = cellsMatrix[row][0].color == appParameters.BOARD_COLOR_LIGHT;
            coordinateY += appParameters.CELL_SIZE;
        }
    }

    public void drawBoard(GraphicsContext gc){
        for (int row = 0; row < appParameters.BOARD_SIZE; row++){
            for (int column = 0; column < appParameters.BOARD_SIZE; column++) {
                Cell currentCell = cellsMatrix[row][column];
                currentCell.draw(gc, null);

                if (currentCell.isHighlighted()){
                    currentCell.draw(gc, appParameters.HIGHLIGHT_COLOR);
                }
                if (getSelectedCell() != null){
                    if (currentCell.equals(getSelectedCell())){
                        currentCell.draw(gc, appParameters.SELECTION_COLOR);
                    }
                }
                if (currentCell.hasAPiece()){
                    currentCell.getPiece().draw(gc);
                }
            }
        }
    }

    public void toggleCellHighlight(int row, int column){
        if (isNotInsideBoard(row, column)){
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
        if (isNotInsideBoard(row, column)){
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

    private boolean isNotInsideBoard(int row, int column){
        return column >= appParameters.BOARD_SIZE || column < 0 || row >= appParameters.BOARD_SIZE || row < 0;
    }
}
