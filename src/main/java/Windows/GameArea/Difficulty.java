package Windows.GameArea;

public enum Difficulty {
    Zero("难度0", 0),
    One("难度1", 1),
    Two("难度2", 2);
    private String name;
    private int value;

    Difficulty(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }

    public static int getDifficulty(String name) throws Exception {
        for (Difficulty d : Difficulty.values()) {
            if (d.name.equals(name)) {
                return d.value;
            }
        }
        throw new Exception("Difficulty not found");
    }
    public static Difficulty getDifficulty(int difficulty){
        for (Difficulty d : Difficulty.values()) {
            if (d.value==difficulty) {
                return d;
            }
        }
        throw new RuntimeException("Difficulty not found");
    }
}
