package app.scenes.nodes;

import app.pieces.ChessPieces;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

public class PromotionDialog extends Dialog<ChessPieces> {
    public PromotionDialog(){
        // TODO: CUSTOMIZE THE INPUT DIALOG
        // TODO: ADD IMAGES TO THE DIALOG
        // TODO: LIMIT THE SELECTION OF MULTIPLE PIECES

        super();
        this.setTitle("Piece Promotion");
        this.setHeaderText("Enter a valid piece notation to promote the current pawn:");

        VBox vBox = new VBox();

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Queen", "Rook", "Knight", "Bishop");
        choiceBox.setValue("Queen");
        vBox.getChildren().add(choiceBox);

        this.getDialogPane().setContent(vBox);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        this.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return switch (choiceBox.getValue()) {
                    case "Queen" -> ChessPieces.QUEEN;
                    case "Rook" -> ChessPieces.ROOK;
                    case "Knight" -> ChessPieces.KNIGHT;
                    case "Bishop" -> ChessPieces.BISHOP;
                    default -> null;
                };
            }
            return null;
        });
    }
}
