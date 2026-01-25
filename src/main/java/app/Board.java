package app;

import app.pieces.ChessPieces;
import app.pieces.PieceKind;
import app.utils.Vector2;
import app.utils.appParameters;
import javafx.scene.canvas.GraphicsContext;

public class Board {

    private final Cell[][] cellsMatrix = new Cell[appParameters.BOARD_SIZE][appParameters.BOARD_SIZE];

    private int[] selectedCell = null;

    public Board(){
        this.createBoard();
        this.placePieces();
    }

    private void createBoard(){
        double coordinateX;
        double coordinateY = 0;
        boolean isColored = false;

        for (int row = 0; row < appParameters.BOARD_SIZE; row++) {
            coordinateX = 0;
            for (int column = 0; column < appParameters.BOARD_SIZE; column++){
                Cell cell = new Cell(
                        new Vector2(coordinateX, coordinateY),
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

    private void placePieces(){
        char[] initialPosition = appParameters.INITIAL_POSITION.toCharArray();
        int row = 0;
        int column = 0;
        for (char character : initialPosition){
            if (character == ' '){
                break;
            }
            if (Character.isDigit(character)){
                column += Character.getNumericValue(character);
                continue;
            }
            if (character == '/'){
                row += 1;
                column = 0;
                continue;
            }
            if (isNotInsideBoard(row, column)){
                break;
            }
            Cell currentCell = cellsMatrix[row][column];
            PieceKind shade = Character.isUpperCase(character) ? PieceKind.LIGHT : PieceKind.DARK;
            for (ChessPieces piece : ChessPieces.values()){
                if (Character.toLowerCase(character) != piece.notation){
                    continue;
                }
                currentCell.setPiece(piece.createInstance(new Vector2(
                                currentCell.coordinates.coordinateX() + appParameters.CELL_SIZE/2 - appParameters.PIECE_SIZE/2,
                                currentCell.coordinates.coordinateY() + appParameters.CELL_SIZE/2 - appParameters.PIECE_SIZE/2
                        ), shade
                ));
                break;
            }
            column += 1;
        }
    }

    public void drawBoard(GraphicsContext gc){
        for (Cell[] cellRow : cellsMatrix){
            for (Cell cell : cellRow){
                cell.draw(gc, null);
                if (cell.isHighlighted()){
                    cell.draw(gc, appParameters.HIGHLIGHT_COLOR);
                }
                if (getSelectedCell() != null){
                    if (cell.equals(getSelectedCell())){
                        cell.draw(gc, appParameters.SELECTION_COLOR);
                    }
                }
                if (cell.hasAPiece()){
                    cell.getPiece().draw(gc);
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
