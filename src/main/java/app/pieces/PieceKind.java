package app.pieces;

public enum PieceKind {
    DARK,
    LIGHT;

    public String getShade(){
        return name().toLowerCase();
    }
}
