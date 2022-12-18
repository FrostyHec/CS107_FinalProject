package GameLogic;


public enum Color implements java.io.Serializable{
    RED(0),BLACK(1),UNKNOWN(114514);
    private int number;
    Color(int number){
        this.number=number;
    }

    public int getNumber() {
        return number;
    }
    public static Color getColor(int number){
        for (Color colot : Color.values()) {
            if (colot.getNumber() == number) {
                return colot;
            }
        }
        return UNKNOWN;
    }
}