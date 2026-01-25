package app.pieces;

public enum PieceKind {
    DARK(180),
    LIGHT(0);

    public final int rotationAngle;

    PieceKind(int rotationAngle){
        this.rotationAngle = rotationAngle;
    }

    public String getShade(){
        return name().toLowerCase();
    }
}
