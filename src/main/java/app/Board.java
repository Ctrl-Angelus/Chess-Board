package app;

import app.pieces.ChessPieces;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.tiles.*;
import app.utils.*;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

public class Board extends Canvas {

    public final Tile[][] tilesMatrix = new Tile[AppParameters.BOARD_SIZE][AppParameters.BOARD_SIZE];
    public final Piece[][] piecesMatrix;
    public boolean pieceDragging = false;

    private Position selectedTile = null;
    private final GraphicsContext graphicsContext;

    public Board(Piece[][] piecesMatrix, double size){
        super(size, size);
        graphicsContext = getGraphicsContext2D();
        setEvents();
        createBoard();
        this.piecesMatrix = piecesMatrix;
    }

    private void createBoard(){
        double coordinateX;
        double coordinateY = 0;
        boolean isTypeDark = false;

        for (int row = 0; row < AppParameters.BOARD_SIZE; row++) {
            coordinateX = 0;
            for (int column = 0; column < AppParameters.BOARD_SIZE; column++){
                Tile tile = new Tile(
                    new Vector2(coordinateX, coordinateY),
                    isTypeDark ? TileType.DARK : TileType.LIGHT,
                    new Position(row, column)
                );

                tilesMatrix[row][column] = tile;

                coordinateX += AppParameters.TILE_SIZE;
                isTypeDark = !isTypeDark;
            }
            // Checks the first tile in the row above is of type LIGHT, to set the current type to the opposite
            isTypeDark = tilesMatrix[row][0].type == TileType.LIGHT;
            coordinateY += AppParameters.TILE_SIZE;
        }
    }

    private void setEvents(){
        setOnMouseClicked(mouseEvent -> {
            Position boardPosition = GameUtils.getBoardPosition(mouseEvent, AppState.isBoardRotated());

            switch (mouseEvent.getButton()){
                case MouseButton.MIDDLE -> AppState.toggleBoardRotation();
                case MouseButton.SECONDARY -> toggleTileHighlight(boardPosition);
                case MouseButton.PRIMARY -> {
                    if (!pieceDragging){
                        selectTile(boardPosition);
                    }
                    removeHighlights();
                }
            }
        });
        setOnMouseDragged(mouseEvent -> {
            Position boardPosition = GameUtils.getBoardPosition(mouseEvent, AppState.isBoardRotated());

            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                if (!pieceDragging){
                    selectTile(boardPosition);
                    pieceDragging = true;
                    setCursor(Cursor.CLOSED_HAND);
                }
                if (getSelectedPiece() == null){
                    setCursor(Cursor.DEFAULT);
                    return;
                }
                if (getSelectedPiece().pieceKind != AppState.getActivePieces()){
                    setCursor(Cursor.DEFAULT);
                    return;
                }
                AppState.setMousePosition(GameUtils.getMouseCoordinates(mouseEvent, AppState.isBoardRotated()));
            }
        });
        setOnMouseReleased(mouseEvent -> {

            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (!pieceDragging){
                    return;
                }
                pieceDragging = false;
                setCursor(Cursor.DEFAULT);
                AppState.deleteMousePosition();
            }
        });
    }

    private void removeHighlights(){
        for (Tile[] tileRow : tilesMatrix){
            for (Tile tile : tileRow){
                tile.deactivateHighlight();
            }
        }
    }

    public Tile getIndividualTile(Position position){
        if (position == null){return null;}
        return this.tilesMatrix[position.row()][position.column()];
    }

    public Piece getIndividualPiece(Position position){
        return piecesMatrix[position.row()][position.column()];
    }

    public void drawBoard(){
        graphicsContext.save();

        double width = this.getWidth();
        double height = this.getHeight();

        graphicsContext.translate(width / 2, height / 2);

        graphicsContext.rotate(AppState.isBoardRotated() ? 180 : 0);

        graphicsContext.translate(-width / 2, -height / 2);


        for (Tile[] tileRow : tilesMatrix){
            for (Tile tile : tileRow){
                TileType type = tile.type;
                if (selectedTile != null){
                    if (tile.equals(getIndividualTile(selectedTile))){
                        type = TileType.SELECTION;
                    }
                }
                tile.draw(graphicsContext, type);
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
                piece.draw(graphicsContext, null);
            }
        }
        if (draggedPiece != null){
            draggedPiece.draw(graphicsContext, AppState.getMousePosition());
        }

        graphicsContext.restore();
    }

    public void toggleTileHighlight(Position position){
        if (GameUtils.isNotInsideBoard(position)){
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
        if (GameUtils.isNotInsideBoard(givenPosition)){
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
    public void removePiece(Position position){
        piecesMatrix[position.row()][position.column()] = null;
    }
    public void setPiece(Position position, Piece piece){
        piecesMatrix[position.row()][position.column()] = piece;
    }

    public void selectTile(Position givenPosition){
        if (GameUtils.isNotInsideBoard(givenPosition)){
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

        Piece piece = getSelectedPiece();

        if (piece.pieceKind != AppState.getActivePieces()){
            setSelectedTile(givenPosition);
            return;
        }
        boolean enPassantAvailable = AppState.enPassantAvailable();

        if (!piece.canMove(selectedTile,givenPosition,this)){
            setSelectedTile(givenPosition);
            return;
        }

        if (getIndividualPiece(givenPosition) != null){
            if (piece.pieceKind == getIndividualPiece(givenPosition).pieceKind){
                setSelectedTile(givenPosition);
                return;
            }

            if (piece.pieceKind == PieceKind.LIGHT){
                AppState.whiteScore += getIndividualPiece(givenPosition).pieceType.numericalValue;
            } else {
                AppState.blackScore += getIndividualPiece(givenPosition).pieceType.numericalValue;
            }
        }

        if (enPassantAvailable && AppState.enPassantAvailable()){
            AppState.cancelEnPassant();
        }

        AppState.toggleActivePieces();

        for (ChessPieces chessPiece : ChessPieces.values()){
            if (piece.pieceType != chessPiece){
                continue;
            }
            Piece newPiece = chessPiece.createInstance(
                Piece.getPiecePosition(tilesMatrix[givenPosition.row()][givenPosition.column()].coordinates),
                piece.pieceKind
            );
            String pieceNotation = (newPiece.pieceType != ChessPieces.PAWN) ? String.valueOf(newPiece.pieceType.notation) : "";
            System.out.println("Notation: " + pieceNotation + givenPosition.getPositionNotation());

            setPiece(givenPosition, newPiece);
            removePiece(selectedTile);
            break;
        }
        selectedTile = null;
    }
}
