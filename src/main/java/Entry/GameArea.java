package Entry;

import GameLogic.*;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class GameArea {
    GameState gameState;
    Chessboard chessboard;
    int score;
    Players players;

    public void Initialize() {

        //棋盘初始化
        chessboard = new Chessboard();

        //分数初始化
        score = 0;

        //玩家初始化
        List<Player> p = new ArrayList<>();
        p.add(new HumanPlayer());
        p.add(new ComputerPlayer());
        players = new Players(p);
        //游戏状态初始化
    }



    /*-------------------chessMove模块---------------------------*/
    Chess pick;
    Square pickSquare;
    @FXML
    public void chessMove(MouseEvent event) {
        final int bound = 5, size = 80;
        int x = (int) (event.getX() - bound) / size;
        int y = (int) (event.getX() - bound) / size;

        HumanPlayer nowPlay;
        Square square = chessboard.getSquare(x, y);

        if (players.nowPlay() instanceof HumanPlayer) {//判定是否为玩家
            nowPlay = (HumanPlayer) players.nowPlay();
        } else {
            return;
        }
        if (nowPlay.getPlayerState() == PlayerState.choosing) {
            pick = square.getChess();
            if(pick==null){
                return;
            }
            if (pick.turnOver()) {
                //do something!
                nowPlay.setColor(pick.getColor());
                players.next();
                return;
            }
            if (nowPlay.isColorMatch(pick.getColor())) {
                pickSquare = square;
                nowPlay.setPlayerState(PlayerState.moving);
            }
        } else if (nowPlay.getPlayerState() == PlayerState.moving) {
            try {
                pick.move(square);
            } catch (Exception e) {
                //do something
            }
            scoreHandler(square.getChess().occupied());
            square.setChess(pick);
            pickSquare.setChess(null);
        }


    }

    void scoreHandler(int score) {
        this.score += score;
        //do Something
    }
}
