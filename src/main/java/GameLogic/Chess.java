package GameLogic;

public interface Chess {
    boolean turnOver();

    moveKind move(Square square) throws Exception;

    int occupied();

    Color getColor();

}

enum moveKind {
    Capture, Move
}
