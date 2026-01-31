package app.pieces;

public enum PieceKind {
    DARK('b'),
    LIGHT('w');

    private final char notation;

    PieceKind(char notation){
        this.notation = notation;
    }

    public String getShade(){
        return name().toLowerCase();
    }

    public char getNotation(){
        return notation;
    }
}
