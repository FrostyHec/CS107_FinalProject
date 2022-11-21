package Entry;

import GameLogic.Chess;
import GameLogic.Color;
import javafx.scene.input.MouseEvent;
import GameLogic.Game;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.concurrent.Callable;

public class GameArea {//doing
    private final int bound = 0, size = 80, chessRadius=30;
    public Pane Chessboard;
    Game game;

    public GameArea() {
        game = new Game();
        game.init();
    }

    public void chessMove(MouseEvent event) {
        int y = (int) (event.getX() - bound) / size;
        int x = (int) (event.getY() - bound) / size;//一些问题

        //还是不敢hardcode
        int res = game.Click(game.nowPlay(), x, y);
        ClickResult clickResult = ClickResult.Uninitialized;
        for (ClickResult re : ClickResult.values()) {
            if (re.getCode() == res) {
                clickResult = re;
            }
        }
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

    }

    public void refresher() {
        for (int column = 0; column < 7; column++) {
            int x = column * size + size/2;
            for (int row = 0; row < 4; row++) {

                int y = row * size + size/2;
                Circle c = new Circle(x,y,chessRadius);

                Chess thisChess = game.getChess(column,row);
                //有很多，先判断是不是空，再判断是否翻开来，再判断颜色，最后判断棋子种类
                if(thisChess.isTurnOver()){}

                    StringBuilder sb = new StringBuilder();
//                if(){
//
//                }else if(){
//
//                }else {
//
//                }
//
//                for (ChessKind ck: ChessKind.values()) {
//                    if(){
//
//                    }
//                }
//
//                switch ()
//                c.setId("General");
//                Chessboard.getChildren().add();
            }
        }
    }
}
enum ChessKind{
    General(7),
    Advisor(6),
    Minister(5),
    Chariot(4),
    Horse(3),
    Cannon(2),
    Soldier(1);
    private final int rank;
    ChessKind(int rank){
        this.rank=rank;
    }

    public int getRank() {
        return rank;
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
    UnknownError(404),
    Uninitialized(114514);
    private final int code;

    ClickResult(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
