package app;

import app.pieces.ChessPieces;
import app.pieces.MovementType;
import app.pieces.Piece;
import app.pieces.PieceKind;
import app.scenes.nodes.PromotionDialog;
import app.tiles.*;
import app.utils.*;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

import java.util.Optional;

public class Board extends Canvas {

    public final Tile[][] tilesMatrix = new Tile[AppParameters.BOARD_SIZE][AppParameters.BOARD_SIZE];
    public final Piece[][] piecesMatrix;
    private final char[] notationLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    private Position selectedTile = null;
    public boolean pieceDragging = false;
    private final GraphicsContext graphicsContext;

    private final PlayerBanner whiteBanner;
    private final PlayerBanner blackBanner;

    public Board(Piece[][] piecesMatrix){
        super(AppParameters.TILE_SIZE * AppParameters.BOARD_SIZE, AppParameters.TILE_SIZE * (AppParameters.BOARD_SIZE + 1));
        graphicsContext = getGraphicsContext2D();
        setEvents();
        createBoard();
        this.piecesMatrix = piecesMatrix;

        this.whiteBanner = new PlayerBanner(
            new Vector2(0, this.getHeight()- AppParameters.TILE_SIZE/2),
            "/ui/White banner.png",
            PieceKind.LIGHT
        );

        this.blackBanner = new PlayerBanner(
            new Vector2(0, 0),
            "/ui/Black banner.png",
            PieceKind.DARK
        );
    }

    private void createBoard(){
        double coordinateX;
        double coordinateY = AppParameters.TILE_SIZE/2;
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

            if (GameUtils.isNotInsideBoard(boardPosition)){
                return;
            }

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
            if (GameUtils.isNotInsideBoard(boardPosition)){
                return;
            }

            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                if (!pieceDragging){
                    if (getSelectedTile() == null){
                        selectTile(boardPosition);
                    }
                    if (!getSelectedTile().equals(boardPosition)){
                        selectTile(boardPosition);
                    }
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
        if (position == null){
            return null;
        }
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

        blackBanner.draw(graphicsContext);

        for (Tile[] tileRow : tilesMatrix){
            for (Tile tile : tileRow){
                TileType type = tile.type;
                if (selectedTile != null){
                    if (tile.equals(getIndividualTile(selectedTile))){
                        type = TileType.SELECTION;
                    }
                    else if (getSelectedPiece() != null) {
                        if (
                            getSelectedPiece().pieceKind == AppState.getActivePieces()
                            && checkPieceMovement(getSelectedPiece(), tile.position, false)
                        ){
                            type = (getIndividualPiece(tile.position) == null) ?  TileType.MOVEMENT : TileType.CAPTURE;
                        }
                    }
                }
                tile.draw(graphicsContext, type);
            }
        }

        // Draw the board notation

        boolean boardRotated = AppState.isBoardRotated();
        int boardSize = AppParameters.BOARD_SIZE;
        double tagSize = AppParameters.FONT_SIZE;
        double tileSize = AppParameters.TILE_SIZE;

        int startColumn = boardRotated ? boardSize - 1 : 0;
        int startRow = boardRotated ? 0: boardSize - 1;

        double offsetX = boardRotated ? tileSize - tagSize / 2 : tagSize / 2;

        double letterOffsetY = boardRotated ? tagSize / 2 : tileSize - tagSize / 2;
        double numberOffsetY = offsetX;

        double letterTagLimit = boardRotated ? -1 : boardSize;
        double numberTagLimit = boardRotated ? boardSize : -1;

        int letterIncrement = boardRotated ? -1 : 1;
        int numberIncrement = boardRotated ? 1 : -1;

        for (int column = startColumn; column != letterTagLimit; column += letterIncrement) {
            Tile currentTile = getIndividualTile(new Position(startRow, column));
            double coordinateX = currentTile.coordinates.coordinateX() + offsetX;
            double coordinateY = currentTile.coordinates.coordinateY() + letterOffsetY;

            new Tag(String.valueOf(notationLetters[column]).toUpperCase(), NotationTags.LETTER).draw(graphicsContext, new Vector2(coordinateX, coordinateY));
        }

        for (int row = startRow; row != numberTagLimit; row += numberIncrement) {
            Tile currentTile = getIndividualTile(new Position(row, startColumn));
            double coordinateX = currentTile.coordinates.coordinateX() + offsetX;
            double coordinateY = currentTile.coordinates.coordinateY() + numberOffsetY;

            new Tag(String.valueOf(AppParameters.BOARD_SIZE - row).toUpperCase(), NotationTags.NUMBER).draw(graphicsContext, new Vector2(coordinateX, coordinateY));
        }

        whiteBanner.draw(graphicsContext);


        Piece draggedPiece = null;
        for (Piece[] pieceRow : piecesMatrix){
            for (Piece piece : pieceRow){
                if (piece == null){
                    continue;
                }
                if (selectedTile != null && pieceDragging){
                    if (piece == getSelectedPiece()){
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

    public boolean checkPieceMovement(Piece piece, Position givenPosition, boolean performMovement){
        MovementType movementType = piece.checkMove(selectedTile, givenPosition, this);

        return switch (movementType){
            case LEGAL_MOVE, CAPTURE -> {
                if (performMovement){
                    movePiece(piece, givenPosition);
                }
                yield true;
            }
            case EN_PASSANT -> {
                if (performMovement){
                    var pawnPosition = AppState.getEnPassantPiecePosition();

                    if (piece.pieceKind == PieceKind.LIGHT) {
                        AppState.whiteScore += getIndividualPiece(pawnPosition).pieceType.numericalValue;
                    } else {
                        AppState.blackScore += getIndividualPiece(pawnPosition).pieceType.numericalValue;
                    }
                    removePiece(pawnPosition);
                    movePiece(piece, givenPosition);
                }
                yield true;
            }
            case PROMOTION -> {

                if (performMovement){
                    PromotionDialog dialog = new PromotionDialog();
                    Optional<ChessPieces> selectedPiece;

                    do {
                        selectedPiece = dialog.showAndWait();
                    }
                    while(selectedPiece.isEmpty());

                    movePiece(piece, givenPosition);
                    Piece newPiece = getIndividualPiece(givenPosition);
                    setPiece(givenPosition, selectedPiece.get().createInstance(newPiece.coordinates, newPiece.pieceKind, newPiece.position));
                }
                yield true;
            }
            case INITIAL_DOUBLE_PAWN_MOVE -> {
                if (performMovement){
                    int initialRow = switch (piece.pieceKind){
                        case DARK -> 1;
                        case LIGHT -> 6;
                    };
                    int sign = switch (piece.pieceKind){
                        case DARK -> 1;
                        case LIGHT -> -1;
                    };
                    AppState.setEnPassantPosition(new Position(initialRow + sign, givenPosition.column()), givenPosition);
                    movePiece(piece, givenPosition);
                }
                yield true;
            }
            default -> false;
        };
    }

    public void movePiece(Piece piece, Position givenPosition){
        for (ChessPieces chessPiece : ChessPieces.values()){
            if (piece.pieceType != chessPiece){
                continue;
            }
            if (getIndividualPiece(givenPosition) != null){

                if (piece.pieceKind == PieceKind.LIGHT){
                    AppState.whiteScore += getIndividualPiece(givenPosition).pieceType.numericalValue;
                } else {
                    AppState.blackScore += getIndividualPiece(givenPosition).pieceType.numericalValue;
                }
            }
            Piece newPiece = chessPiece.createInstance(
                    Piece.getPiecePosition(tilesMatrix[givenPosition.row()][givenPosition.column()].coordinates),
                    piece.pieceKind,
                    givenPosition
            );
            String pieceNotation = (newPiece.pieceType != ChessPieces.PAWN) ? String.valueOf(newPiece.pieceType.notation) : "";
            System.out.println("Notation: " + pieceNotation + givenPosition.getPositionNotation());

            setPiece(givenPosition, newPiece);
            removePiece(selectedTile);
            break;
        }
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

        if (!checkPieceMovement(piece, givenPosition, true)){
            setSelectedTile(givenPosition);
            return;
        }
        if (enPassantAvailable && AppState.enPassantAvailable()){
            AppState.cancelEnPassant();
        }

        AppState.toggleActivePieces();

        selectedTile = null;
    }
}
