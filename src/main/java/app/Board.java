package app;

import app.pieces.ChessPieces;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.utils.Vector2;
import app.utils.AppParameters;
import javafx.scene.canvas.GraphicsContext;

public class Board {

    private final Tile[][] tilesMatrix = new Tile[AppParameters.BOARD_SIZE][AppParameters.BOARD_SIZE];
    public boolean pieceDragging = false;

    private Tile selectedTile = null;

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
                Tile tile = new Tile(
                    new Vector2(coordinateX, coordinateY),
                    isColored ? TileType.DARK : TileType.LIGHT
                );

                tilesMatrix[row][column] = tile;

                coordinateX += AppParameters.TILE_SIZE;
                isColored = !isColored;
            }
            isColored = tilesMatrix[row][0].type == TileType.LIGHT;
            coordinateY += AppParameters.TILE_SIZE;
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
            Tile currentTile = getIndividualTile(row, column);
            PieceKind kind = Character.isUpperCase(character) ? PieceKind.LIGHT : PieceKind.DARK;
            for (ChessPieces piece : ChessPieces.values()){
                if (Character.toLowerCase(character) != piece.notation){
                    continue;
                }
                currentTile.setPiece(piece.createInstance(currentTile.getPiecePosition(), kind));
                break;
            }
            column += 1;
        }
    }
    public Tile getIndividualTile(int row, int column){
        return this.tilesMatrix[row][column];
    }

    public void drawBoard(GraphicsContext gc, Vector2 mouseCoordinates){
        for (Tile[] tileRow : tilesMatrix){
            for (Tile tile : tileRow){
                tile.draw(gc);
                boolean isSelectedTile = false;
                if (getSelectedTile() != null){
                    isSelectedTile = tile.isSelected();
                }
                if (tile.hasAPiece()){
                    if (isSelectedTile && pieceDragging){
                        continue;
                    }
                    tile.getPiece().draw(gc, null);
                }
            }
        }
        if (pieceDragging){
            getSelectedTile().getPiece().draw(gc, mouseCoordinates);
        }
    }

    public void toggleTileHighlight(int row, int column){
        if (isNotInsideBoard(row, column)){
            return;
        }
        getIndividualTile(row, column).toggleHighlight();
    }

    public Tile getSelectedTile(){
        if (this.selectedTile == null){
            return null;
        }
        return selectedTile;
    }
    public void setSelectedTile(int row, int column){
        if (isNotInsideBoard(row, column)){
            return;
        }
        if (selectedTile != null){
            selectedTile.toggleSelection();
        }
        this.selectedTile = getIndividualTile(row, column);
        selectedTile.toggleSelection();
    }
    public void setSelectedTileNull(){
        selectedTile.toggleSelection();
        this.selectedTile = null;
    }

    public void selectTile(int row, int column){
        if (isNotInsideBoard(row, column)){
            return;
        }
        if (getIndividualTile(row, column).equals(getSelectedTile())){
            setSelectedTileNull();
            return;
        }
        if (selectedTile == null){
            setSelectedTile(row, column);
            return;
        }

        if (!selectedTile.hasAPiece()){
            setSelectedTile(row, column);
            return;
        }

        Piece piece = selectedTile.getPiece();
        Tile newTile = getIndividualTile(row, column);
        if (newTile.hasAPiece()){
            setSelectedTile(row, column);
            return;
        }
        selectedTile.removePiece();

        // TODO: CALL THE MOVE METHOD OF THE PIECE

        for (ChessPieces chessPiece : ChessPieces.values()){
            if (piece.pieceType != chessPiece){
                continue;
            }
            Piece newPiece = chessPiece.createInstance(
                newTile.getPiecePosition(),
                piece.pieceKind
            );
            newTile.setPiece(newPiece);
            break;
        }
        setSelectedTileNull();
    }

    private boolean isNotInsideBoard(int row, int column){
        return column >= AppParameters.BOARD_SIZE || column < 0 || row >= AppParameters.BOARD_SIZE || row < 0;
    }
}
