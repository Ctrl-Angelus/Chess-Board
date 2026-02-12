package app.scenes.nodes;

import app.pieces.ChessPieces;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
        CheckBox queenCheckBox = new CheckBox("Queen");
        queenCheckBox.setAllowIndeterminate(false);
        CheckBox rookCheckBox = new CheckBox("Rook");
        rookCheckBox.setAllowIndeterminate(false);
        CheckBox knightCheckBox = new CheckBox("Knight");
        knightCheckBox.setAllowIndeterminate(false);
        CheckBox bishopCheckBox = new CheckBox("Bishop");
        bishopCheckBox.setAllowIndeterminate(false);

        CheckBox[] checkBoxes = new CheckBox[]{queenCheckBox, rookCheckBox, knightCheckBox, bishopCheckBox};
        vBox.getChildren().addAll(queenCheckBox, rookCheckBox, knightCheckBox, bishopCheckBox);

        this.getDialogPane().setContent(vBox);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        this.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                for (CheckBox box : checkBoxes){
                    if (box.isSelected()){
                        return switch (box.getText()) {
                            case "Queen" -> ChessPieces.QUEEN;
                            case "Rook" -> ChessPieces.ROOK;
                            case "Knight" -> ChessPieces.KNIGHT;
                            case "Bishop" -> ChessPieces.BISHOP;
                            default -> null;
                        };
                    }
                }
                return null;
            }
            return null;
        });
    }
}
