package app;

import app.pieces.ChessPieces;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.utils.Vector2;
import app.utils.AppParameters;
import app.utils.AppColors;
import javafx.scene.canvas.GraphicsContext;

public class Board {

    private final Cell[][] cellsMatrix = new Cell[AppParameters.BOARD_SIZE][AppParameters.BOARD_SIZE];

    private int[] selectedCell = null;

    public Board(){
        this.createBoard();
        this.placePieces();
    }

    private void createBoard(){
        double coordinateX;
        double coordinateY = 0;
        boolean isColored = false;

        for (int row = 0; row < AppParameters.BOARD_SIZE; row++) {
            coordinateX = 0;
            for (int column = 0; column < AppParameters.BOARD_SIZE; column++){
                Cell cell = new Cell(
                        new Vector2(coordinateX, coordinateY),
                        isColored ? AppColors.BOARD_COLOR_DARK : AppColors.BOARD_COLOR_LIGHT
                );

                cellsMatrix[row][column] = cell;

                coordinateX += AppParameters.CELL_SIZE;
                isColored = !isColored;
            }
            isColored = cellsMatrix[row][0].color == AppColors.BOARD_COLOR_LIGHT;
            coordinateY += AppParameters.CELL_SIZE;
        }
    }

    private void placePieces(){
        char[] initialPosition = AppParameters.INITIAL_POSITION.toCharArray();
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
            PieceKind kind = Character.isUpperCase(character) ? PieceKind.LIGHT : PieceKind.DARK;
            for (ChessPieces piece : ChessPieces.values()){
                if (Character.toLowerCase(character) != piece.notation){
                    continue;
                }
                currentCell.setPiece(piece.createInstance(getPiecePosition(currentCell.coordinates), kind));
                break;
            }
            column += 1;
        }
    }
    public Cell getIndividualCell(int row, int column){
        return this.cellsMatrix[row][column];
    }

    public void drawBoard(GraphicsContext gc){
        for (Cell[] cellRow : cellsMatrix){
            for (Cell cell : cellRow){
                cell.draw(gc, null);
                if (cell.isHighlighted()){
                    cell.draw(gc, AppColors.HIGHLIGHT_COLOR);
                }
                if (getSelectedCell() != null){
                    if (cell.equals(getSelectedCell())){
                        cell.draw(gc, AppColors.SELECTION_COLOR);
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
        Cell currentCell = getIndividualCell(row, column);
        currentCell.toggleHighlight();
    }

    public Cell getSelectedCell(){
        if (this.selectedCell == null){
            return null;
        }
        return getIndividualCell(this.selectedCell[0],this.selectedCell[1]);
    }
    public Vector2 getPiecePosition(Vector2 cellPosition){
        return new Vector2(
            cellPosition.coordinateX() + AppParameters.CELL_SIZE/2 - AppParameters.PIECE_SIZE/2,
            cellPosition.coordinateY() + AppParameters.CELL_SIZE/2 - AppParameters.PIECE_SIZE/2
        );
    }
    public void setSelectedCell(int row, int column){
        if (isNotInsideBoard(row, column)){
            return;
        }
        if (getIndividualCell(row, column).equals(getSelectedCell())){
            this.selectedCell = null;
            return;
        }
        if (selectedCell == null){
            this.selectedCell = new int[] {row, column};
            return;
        }

        Cell currentCell = getIndividualCell(selectedCell[0], selectedCell[1]);
        if (!currentCell.hasAPiece()){
            this.selectedCell = new int[] {row, column};
            return;
        }

        Piece piece = currentCell.getPiece();
        Cell newCell = getIndividualCell(row, column);
        if (newCell.hasAPiece()){
            this.selectedCell = new int[] {row, column};
            return;
        }
        currentCell.removePiece();

        // TODO: CALL THE MOVE METHOD OF THE PIECE

        for (ChessPieces chessPiece : ChessPieces.values()){
            if (piece.pieceType != chessPiece){
                continue;
            }
            Piece newPiece = chessPiece.createInstance(
                getPiecePosition(newCell.coordinates),
                piece.pieceKind
            );
            newCell.setPiece(newPiece);
            break;
        }
        this.selectedCell = new int[] {row, column};
    }

    private boolean isNotInsideBoard(int row, int column){
        return column >= AppParameters.BOARD_SIZE || column < 0 || row >= AppParameters.BOARD_SIZE || row < 0;
    }
}
