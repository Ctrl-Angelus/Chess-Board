package app;

import app.pieces.ChessPieces;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.utils.AppState;
import app.utils.Position;
import app.utils.Vector2;
import app.utils.AppParameters;
import javafx.scene.canvas.GraphicsContext;

public class Board {

    private final Tile[][] tilesMatrix = new Tile[AppParameters.BOARD_SIZE][AppParameters.BOARD_SIZE];
    private final Piece[][] piecesMatrix = new Piece[AppParameters.BOARD_SIZE][AppParameters.BOARD_SIZE];
    public boolean pieceDragging = false;

    private Position selectedTile = null;

    public Board(){
        this.createBoard();
        this.placePieces();
    }

    public Tile[][] getTilesMatrix() {
        return tilesMatrix;
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
                    isColored ? TileType.DARK : TileType.LIGHT,
                    new Position(row, column)
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
            if (isNotInsideBoard(new Position(row, column))){
                break;
            }
            PieceKind kind = Character.isUpperCase(character) ? PieceKind.LIGHT : PieceKind.DARK;
            for (ChessPieces piece : ChessPieces.values()){
                if (Character.toLowerCase(character) != piece.notation){
                    continue;
                }
                piecesMatrix[row][column] = piece.createInstance(Piece.getPiecePosition(tilesMatrix[row][column].coordinates), kind);
                break;
            }
            column += 1;
        }
    }
    public Tile getIndividualTile(Position position){
        return this.tilesMatrix[position.row()][position.column()];
    }

    public Piece getIndividualPiece(Position position){
        return piecesMatrix[position.row()][position.column()];
    }

    public void drawBoard(GraphicsContext gc){
        for (Tile[] tileRow : tilesMatrix){
            for (Tile tile : tileRow){
                TileType type = tile.type;
                if (selectedTile != null){
                    if (tile.equals(getIndividualTile(selectedTile))){
                        type = TileType.SELECTION;
                    }
                }
                tile.draw(gc, type);
            }
        }
        Piece draggedPiece = null;
        for (Piece[] pieceRow : piecesMatrix){
            for (Piece piece : pieceRow){
                if (piece == null){
                    continue;
                }
                if (selectedTile != null && pieceDragging){
                    if (piece == piecesMatrix[selectedTile.row()][selectedTile.column()]){
                        draggedPiece = piece;
                        continue;
                    }
                }
                piece.draw(gc, null);
            }
        }
        if (draggedPiece != null){
            draggedPiece.draw(gc, AppState.getMousePosition());
        }
    }

    public void toggleTileHighlight(Position position){
        if (isNotInsideBoard(position)){
            return;
        }
        getIndividualTile(position).toggleHighlight();
    }

    public Position getSelectedTile(){
        return selectedTile;
    }
    public Piece getSelectedPiece(){
        if (selectedTile == null){return null; }
        return piecesMatrix[selectedTile.row()][selectedTile.column()];
    }
    public void setSelectedTile(Position givenPosition){
        if (isNotInsideBoard(givenPosition)){
            return;
        }
        if (selectedTile != null){
            if (selectedTile.equals(givenPosition)){
                selectedTile = null;
                return;
            }
        }
        this.selectedTile = givenPosition;
    }

    public void selectTile(Position givenPosition){
        if (isNotInsideBoard(givenPosition)){
            return;
        }
        if (givenPosition.equals(getSelectedTile())){
            setSelectedTile(givenPosition);
            return;
        }
        if (getSelectedPiece() == null){
            setSelectedTile(givenPosition);
            return;
        }

        if (getSelectedPiece() == null){
            setSelectedTile(givenPosition);
            return;
        }

        Piece piece = getSelectedPiece();

        if (!piece.canMove(
            selectedTile,
            givenPosition
        )){
            setSelectedTile(givenPosition);
            return;
        }

        if (getIndividualPiece(givenPosition) != null){
            if (piece.pieceKind == PieceKind.LIGHT){
                AppState.whiteScore += getIndividualPiece(givenPosition).pieceType.numericalValue;
            } else {
                AppState.blackScore += getIndividualPiece(givenPosition).pieceType.numericalValue;
            }
            System.out.println("Black Score: " + AppState.blackScore);
            System.out.println("White Score: " + AppState.whiteScore);
        }

        for (ChessPieces chessPiece : ChessPieces.values()){
            if (piece.pieceType != chessPiece){
                continue;
            }
            Piece newPiece = chessPiece.createInstance(
                Piece.getPiecePosition(tilesMatrix[givenPosition.row()][givenPosition.column()].coordinates),
                piece.pieceKind
            );
            piecesMatrix[givenPosition.row()][givenPosition.column()] = newPiece;
            piecesMatrix[selectedTile.row()][selectedTile.column()] = null;
            break;
        }
        if (!pieceDragging){
            setSelectedTile(givenPosition);
        }
    }

    private boolean isNotInsideBoard(Position position){
        return position.column() >= AppParameters.BOARD_SIZE
                || position.column() < 0
                || position.row() >= AppParameters.BOARD_SIZE
                || position.row() < 0;
    }
}
