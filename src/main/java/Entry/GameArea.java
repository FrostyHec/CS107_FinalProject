package Entry;

import GameLogic.Chess;
import GameLogic.Color;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import GameLogic.Game;
import javafx.scene.layout.Pane;

public class GameArea {
    private final int  size = 80, chessSize = 70;
    public Pane Chessboard;
    Game game;

    public GameArea() {
        game = new Game();
    }

    @FXML
    public void initialize() {
        game.init();
        refresher();
    }

    long count = 0L;

    public void chessMove(MouseEvent event) {
        //我也不知道为什么鼠标侦听器会听两次，属于是大无语了
        count++;
        if (count % 2 == 0) {
            return;
        }


        int y = (int) event.getX() / size;
        int x = (int) event.getY() / size;//一些问题
        if (x > 7 || y > 3) {
            return;
        }
        //还是不敢hardcode
        int res = game.Click(game.nowPlay(), x, y);
        ClickResult clickResult = ClickResult.getClickResult(res);

        //临时用的
        System.out.println(clickResult);
        System.out.println("now:"+game.nowPlay().getColor().toString()+" "+game.nowPlay().getScore());


        switch (clickResult) {
            case Finished -> {
            }
            case Continue -> {
            }
            case ChoosingOthers -> {
            }
            case SelfCapture -> {
            }
            case UnturnedCapture -> {
            }
            case LargerCapture -> {
            }
            case KingCaptureSolider -> {
            }
            case UnknownError -> {
            }
        }
        refresher();
    }

    public void refresher() {

        Chessboard.getChildren().clear();//移除所有棋子
        for (int column = 0; column < 8; column++) {
            int y = column * size + (size - chessSize) / 2;
            for (int row = 0; row < 4; row++) {

                //获取棋子
                int x = row * size + (size - chessSize) / 2;
                ImageView c = new ImageView();
                c.setX(x);
                c.setY(y);
                c.setFitHeight(chessSize);
                c.setFitWidth(chessSize);
                Chess thisChess = game.getChess(column, row);

                //有很多，先判断是不是空，再判断是否翻开来，再判断颜色，最后判断棋子种类
                if (thisChess == null) {//空
                    continue;
                }

                if (!thisChess.isTurnOver()) {//没翻开
                    c.setId("UnTurnedChess");
                    Chessboard.getChildren().add(c);
                    continue;
                }

                //命名规范: R/B+General/Advisor/Minister/Chariot/Horse/Cannon/Soldier+Chess
                StringBuilder sb = new StringBuilder();
                if (thisChess.getColor().equals(Color.RED)) {
                    sb.append("R");
                } else if (thisChess.getColor().equals(Color.BLACK)) {
                    sb.append("B");
                }

                //我还是不hardcode了
                ChessKind thisChessKind = ChessKind.getKind(thisChess.getRank());

                sb.append(thisChessKind);
                sb.append("Chess");
                c.setId(sb.toString());
                Chessboard.getChildren().add(c);
            }
        }
    }
}

enum ChessKind {
    General(7),
    Advisor(6),
    Minister(5),
    Chariot(4),
    Horse(3),
    Cannon(2),
    Soldier(1),
    Error(-114514);

    private final int rank;

    ChessKind(int rank) {
        this.rank = rank;
    }

    private int getRank() {
        return rank;
    }

    public static ChessKind getKind(int rank) {
        for (ChessKind ck : ChessKind.values()) {
            if (ck.getRank() == rank) {
                return ck;
            }
        }
        return Error;
    }
}

enum ClickResult {
    Finished(0),
    Continue(-1),
    ChoosingOthers(1),
    SelfCapture(2),
    UnturnedCapture(3),
    LargerCapture(4),
    KingCaptureSolider(5),
    UnknownError(6),
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
