package app.utils;


public record Position(int row, int column) {
    private final static String alphabeticalNotation = "abcdefgh";

    public String getPositionNotation(){
        return String.format("%c%d", alphabeticalNotation.charAt(column), (AppParameters.BOARD_SIZE - row));
    }
    public static Position getPositionFromNotation(String notation){
        int column = 0;
        int row = 0;

        for (char character : notation.toCharArray()){
            if (alphabeticalNotation.contains(String.valueOf(character))){
                column = alphabeticalNotation.indexOf(character);
            }
            else if (Character.getNumericValue(character) != -1){
                row = AppParameters.BOARD_SIZE - Character.getNumericValue(character);
            }
        }

        if (!notation.equals("a8") && column == 0 && row == 0 ){
            return null;
        }

        return new Position(
            row, column
        );
    }
}
