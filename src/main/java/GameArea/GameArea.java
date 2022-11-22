package GameArea;

import GameLogic.Chess;
import GameLogic.Color;
import GameLogic.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import GameLogic.Game;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GameArea {
    private final int squareSize = 80;
    private final int chessSize = 70;
    public Pane Chessboard;
    private Game game;
    private final Handler handler = new Handler();
    private final TextHandler textHandler = new TextHandler();

    public GameArea() {
        game = new Game();
    }

    @FXML
    public void initialize() {

        game.init();
        handler.refreshChessboard();
    }

    public void chessMove(MouseEvent event) {
        new chessMove().invoke(event);
    }



    class chessMove {
        static private long count = 0L;
        private final int selectedSize = squareSize - 8;
        private int column;
        private int row;

        private boolean generateRowAndColumn(double x, double y) {
            column = (int) x / squareSize;
            row = (int) y / squareSize;
            return row <= 7 && column <= 3;//可以进一步限制
        }

        public void invoke(MouseEvent event) {
            //我也不知道为什么鼠标侦听器会听两次，属于是大无语了
            count++;
            if (count % 2 == 0) {
                return;
            }

            //获取坐标
            if (!generateRowAndColumn(event.getX(), event.getY())) {
                return;
            }

            int res = game.Click(game.nowPlay(), row, column);//完成点击

            ClickResult clickResult = ClickResult.getClickResult(res);


            //临时用的
            System.out.println(clickResult);
            System.out.println("now:" + game.nowPlay().getColor().toString() + " " + game.nowPlay().getScore());


            analyzeClickResult(clickResult);
        }

        private void showSelectedChess(int row, int column) {


            ImageView p = new ImageView();
            p.setX(handler.getGraphicX(column, selectedSize));
            p.setY(handler.getGraphicY(row, selectedSize));
            p.setFitHeight(selectedSize);
            p.setFitWidth(selectedSize);
            p.setId("Selected");
            Chessboard.getChildren().add(p);

        }

        private void showPossibleMove(int row, int column) {
            List<int[]> possibleMove = new ArrayList<>();
            int[][] temp = game.getChess(row, column).possibleMove(game.getChess(), row, column);
            for (int[] position : temp) {
                if (!(position[0] == -1 && position[1] == -1)) {
                    possibleMove.add(position);
                }
            }
            for (int[] position : possibleMove) {
                ImageView p = new ImageView();
                p.setX(handler.getGraphicX(position[1], selectedSize));
                p.setY(handler.getGraphicY(position[0], selectedSize));
                p.setFitHeight(selectedSize);
                p.setFitWidth(selectedSize);
                p.setId("PossibleMove");
                Chessboard.getChildren().add(p);
            }

        }

        private void analyzeClickResult(ClickResult clickResult) {
            handler.refreshChessboard();
            switch (clickResult) {
                case Player1Win -> {
                    handler.gameOver(1);
                }
                case Player2Win -> {
                    handler.gameOver(2);
                }
                case Finished -> {
                }
                case Continue -> {
                    showSelectedChess(row, column);
                    showPossibleMove(row, column);
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


    }

    class Handler {
        public int getGraphicX(int column, int size) {
            return column * squareSize + (squareSize - size) / 2;
        }

        public int getGraphicY(int row, int size) {
            return row * squareSize + (squareSize - size) / 2;
        }

        public void refreshChessboard() {
            Chessboard.getChildren().clear();//移除所有棋子
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 4; column++) {

                    //获取棋子
                    ImageView c = new ImageView();
                    c.setX(getGraphicX(column, chessSize));
                    c.setY(getGraphicY(row, chessSize));
                    c.setFitHeight(chessSize);
                    c.setFitWidth(chessSize);
                    Chess thisChess = game.getChess(row, column);

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
        }//refreshChessboard 用来刷新整个棋盘画面

        public void gameOver(int playerNumber) {
            textHandler.getWinner(playerNumber);
            exit();
        }

        private void exit() {
            Platform.exit();
        }
    }

    class TextHandler {
        Locale locale = Locale.getDefault();//后面可以改
        ResourceBundle t = ResourceBundle.getBundle("GameArea/GameAreaLanguage", locale);

        public void getWinner(int playerNum) {
            //生成赢家信息
            StringBuilder contentText = new StringBuilder();
            contentText.append(t.getString("GameState.gameOver.contentText.1"));
            if (playerNum == 1) {
                contentText.append(t.getString("GameState.gameOver.contentText.1.1"));
            } else if (playerNum == 2) {
                contentText.append(t.getString("GameState.gameOver.contentText.1.2"));
            }
            contentText.append(t.getString("GameState.gameOver.contentText.2"));

            //生成弹窗
            showAlert(Alert.AlertType.INFORMATION,
                    t.getString("GameState.gameOver.title"),
                    t.getString("GameState.gameOver.headerText"),
                    contentText.toString()

            );
        }

        private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(contentText);
            alert.setHeaderText(headerText);
            alert.showAndWait();
        }//晚点再美化这个界面
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

