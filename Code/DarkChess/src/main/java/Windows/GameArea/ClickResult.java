package Windows.GameArea;

public enum ClickResult {
    Finished(0),
    Continue(-1),
    ChoosingOthers(1),
    SelfCapture(2),
    UnturnedCapture(3),
    LargerCapture(4),
    KingCaptureSolider(5),
    Player1Win(101),
    Player2Win(102),

    UnknownError(6),
    UnknownError2(404),
    EmptyClick(401),
    Uninitialized(114514);
    private final int code;

    ClickResult(int code) {
        this.code = code;
    }

    private int getCode() {
        return code;
    }

    public static ClickResult getClickResult(int res) {
        for (ClickResult re : ClickResult.values()) {
            if (re.getCode() == res) {
                return re;
            }
        }
        return Uninitialized;
    }
}
