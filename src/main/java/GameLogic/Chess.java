package GameLogic;

public interface Chess {
    boolean turnOver();

    moveKind move(Square square) throws Exception;

    void occupied();
}
enum moveKind{
    Capture, Move
}
