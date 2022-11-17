package GameLogic;

public abstract class AbstractChess implements Chess {
    private Color color;
    private boolean usable;
    private boolean turnOver;
    private int rank;

    @Override
    public boolean turnOver() {
        return false;
    }

    @Override
    public moveKind move(Square square) throws Exception {
        return moveKind.Move;
    }

    @Override
    public int occupied() {
        return rank;
    }

    public Color getColor() {
        return color;
    }
}
