package InternetGaming.GameArea;

import GameLogic.Chess;
import GameLogic.Color;
import InternetGaming.Internet.Transmitter;
import Windows.GameArea.ClickResult;
import Windows.GameArea.GameState;
import Windows.StartMenu.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import GameLogic.Game;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import units.Retract;
import units.Serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class GameArea extends Windows.GameArea.GameArea{
    //暂停界面
    public Pane pausePane;
    public Pane paneChoose;
    public Button btnContinue;
    public Button btnQuit;
    public Button btnSetup;
    public Button btnRemake;
    public Pane diedChessP1;
    public Pane diedChessP2;

    //死棋子图像

    //游戏状态
    private GameState gameState;
    private GameStateHandler gameStateHandler = new GameStateHandler();

    //基本常量
    private final int squareSize = 80;
    private final int chessSize = 70;

    //控件模块8
    public Pane Chessboard;
    public Label player1Score;
    public Label player2Score;
    public Label labelGameState;
    public Label firstHand;
    public Label secondHand;
    public ImageView player1Icon;
    public ImageView player2Icon;
    public Pane player1Color;
    public Pane player2Color;
    public Button cheatButton;
    public ImageView cheatImage;
    public Label cheatTitle;
    private Game game;
    private final GraphicHandler graphicHandler = new GraphicHandler();
    private final TextHandler textHandler = new TextHandler();

    public GameArea() throws Exception {//TODO 整体重构这一部分
        super();
        game = new Game();
        Transmitter.gameArea = this;
    }

    @FXML
    public void initialize() {
        game.init();
        graphicHandler.initialize();
        ChessMove.resetCount();
        textHandler.initialize();
        gameStateHandler.initialize();

    }

    public void chessMove(MouseEvent event) {
        if (gameState == GameState.Pause) {
            return;
        }
        new ChessMove().invoke(event);

    }

    private void chessChanged() {
        graphicHandler.refresh();
        textHandler.refreshScore();
        gameStateHandler.changed();
    }

    public void cheatClick() {
        if (gameState == GameState.Pause) {
            return;
        }
        if (!CheatModel.cheatClick()) {//清空作弊板
            graphicHandler.cleanCheatTable();

        } else {
            graphicHandler.initializeCheatTable();
        }

    }

    public void GameAreaKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            gameStateHandler.escPressed();
        }
    }

    public void btnContinueGame() {
        gameStateHandler.continueGame();
    }

    public void clickRemake() {
        initialize();
    }

    public void remoteRefresh(Game game) {
        this.game = game;
        graphicHandler.refresh();
    }

    public void saveAndExit() {
        try {
            new Main().start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ((Stage) Chessboard.getScene().getWindow()).close();
    }

    public void retractOnClick(ActionEvent event) {
        try {
            game = Retract.traceBack(game);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        chessChanged();
    }

    class CheatModel {
        static private boolean isStart = false;
        private int column;
        private int row;

        private boolean generateRowAndColumn(double x, double y) {
            column = (int) x / squareSize;
            row = (int) y / squareSize;
            return row <= 7 && column <= 3;//可以进一步限制
        }

        public static boolean cheatClick() {
            isStart = !isStart;
            return isStart;
        }

        class RefreshCheatImage implements EventHandler<MouseEvent> {
            @Override
            public void handle(MouseEvent event) {
                if (gameState == GameState.Pause) {
                    return;
                }
                if (!generateRowAndColumn(event.getX(), event.getY())) {
                    return;
                }
                if (!isStart) {
                    return;
                }
                graphicHandler.refreshCheatImage(row, column);
            }
        }

        class CleanCheatImage implements EventHandler<MouseEvent> {
            @Override
            public void handle(MouseEvent event) {
                graphicHandler.cleanCheatImage();
            }
        }
    }

    class ChessMove {
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
            //我也不知道为什么鼠标侦听器会调用两次，属于是大无语了
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
            chessChanged();
            analyzeClickResult(clickResult);
        }

        private void showSelectedChess(int row, int column) {

            ImageView p = new ImageView();
            p.setX(graphicHandler.getGraphicX(column, selectedSize));
            p.setY(graphicHandler.getGraphicY(row, selectedSize));
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
                p.setX(graphicHandler.getGraphicX(position[1], selectedSize));
                p.setY(graphicHandler.getGraphicY(position[0], selectedSize));
                p.setFitHeight(selectedSize);
                p.setFitWidth(selectedSize);
                p.setId("PossibleMove");
                Chessboard.getChildren().add(p);
            }

        }

        private void analyzeClickResult(ClickResult clickResult) {
            switch (clickResult) {
                case Player1Win -> graphicHandler.gameOver(1);
                case Player2Win -> graphicHandler.gameOver(2);

                case Finished -> {
                }
                case Continue -> {
                    showSelectedChess(row, column);
                    showPossibleMove(row, column);
                }
                case UnknownError -> {
                    //textHandler.showAlert(Alert.AlertType.ERROR, "Wrong Happened!", "null", "ClickResult is Missing");
                }
            }
        }

        public static void resetCount() {
            count = 0;
        }

    }

    class GameStateHandler {
        private GameState previousGameState;

        private void toPause() {
            graphicHandler.showPause();
            previousGameState = gameState;
            gameState = GameState.Pause;
            System.out.println("游戏已暂停");
        }

        private void fromPause() {
            graphicHandler.hidePause();
            gameState = previousGameState;
            System.out.println("游戏已继续");
        }

        public void escPressed() {
            if (gameState == GameState.Pause) {
                fromPause();
            } else {
                toPause();

            }
            textHandler.refreshGameState();
        }

        public void continueGame() {
            fromPause();
            textHandler.refreshGameState();
        }

        @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
        public void changed() {
            switch (game.nowPlay().getColor()) {
                case RED -> {
                    gameState = GameState.RedTurn;
                }
                case BLACK -> {
                    gameState = GameState.BlackTurn;
                }
                case UNKNOWN -> {
                    gameState = GameState.FirstHandChoose;
                }
            }

            textHandler.refreshGameState();
        }

        public void initialize() {

            gameState = GameState.FirstHandChoose;
            textHandler.refreshGameState();
        }
    }

    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    class GraphicHandler {
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
                    c.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> new CheatModel().new RefreshCheatImage().handle(event));
                    c.addEventFilter(MouseEvent.MOUSE_EXITED, event -> new CheatModel().new CleanCheatImage().handle(event));
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

                    c.setId(generateChessID(thisChess));
                    Chessboard.getChildren().add(c);
                }
            }
        }//refreshChessboard 用来刷新整个棋盘画面

        private String generateChessID(Chess legalChess) {
            //命名规范: R/B+General/Advisor/Minister/Chariot/Horse/Cannon/Soldier+Chess
            StringBuilder sb = new StringBuilder();
            if (legalChess.getColor().equals(Color.RED)) {
                sb.append("R");
            } else if (legalChess.getColor().equals(Color.BLACK)) {
                sb.append("B");
            }

            //我还是不hardcode了
            ChessKind thisChessKind = ChessKind.getKind(legalChess.getRank());

            sb.append(thisChessKind);
            sb.append("Chess");
            return sb.toString();
        }

        private String generateChessID(Color color, ChessKind chessKind) {
            //命名规范: R/B+General/Advisor/Minister/Chariot/Horse/Cannon/Soldier+Chess
            StringBuilder sb = new StringBuilder();
            if (color.equals(Color.RED)) {
                sb.append("R");
            } else if (color.equals(Color.BLACK)) {
                sb.append("B");
            }

            //我还是不hardcode了
            sb.append(chessKind);
            sb.append("Chess");
            return sb.toString();
        }

        public void gameOver(int playerNumber) {
            textHandler.getWinner(playerNumber);
            saveAndExit();
        }

        public void refresh() {
            refreshColor();
            refreshChessboard();
            refreshDiedChess();
        }

        public void refreshColor() {
            player1Color.setId("PlayerColor" + game.getPlayer1().getColor().toString());
            player2Color.setId("PlayerColor" + game.getPlayer2().getColor().toString());
        }

        public void initialize() {
            refresh();
            refreshIcon();
            cleanCheatTable();
            hidePause();
        }
        public void refreshIcon() {//有待扩展
            try {
                player1Icon.setImage(new Image(new FileInputStream("src/main/resources/Windows/images/UserImage/tempUser.png")));
                player2Icon.setImage(new Image(new FileInputStream("src/main/resources/Windows/images/UserImage/tempUser.png")));
            } catch (Exception e) {
                System.out.println("图片加载失败！");
            }
        }

        public void initializeCheatTable() {
            cheatTitle.setVisible(true);
            cheatImage.setVisible(true);
            cheatButton.getStyleClass().clear();
            cheatButton.getStyleClass().add("button");
            cheatButton.getStyleClass().add("ButtonOn");
        }

        public void cleanCheatTable() {
            cleanCheatImage();
            cheatTitle.setVisible(false);
            cheatImage.setVisible(false);
            cheatButton.getStyleClass().clear();
            cheatButton.getStyleClass().add("button");
            cheatButton.getStyleClass().add("ButtonOff");
        }

        public void refreshCheatImage(int row, int column) {
            //获取棋子
            Chess thisChess = game.getChess(row, column);

            //有很多，先判断是不是空，再判断是否翻开来，再判断颜色，最后判断棋子种类
            if (thisChess == null) {//空
                return;
            }
            if (thisChess.isTurnOver()) {//翻开了
                return;
            }

            cheatImage.setId(generateChessID(thisChess));
        }

        public void cleanCheatImage() {
            cheatImage.setId(null);
        }

        public void showPause() {
            pausePane.setVisible(true);
            pausePane.setDisable(false);
        }

        public void hidePause() {
            pausePane.setVisible(false);
            pausePane.setDisable(true);

        }

        public void refreshDiedChess() {
            int[][] arrayDiedChess = game.getDiedChess();
            cleanDiedChess();
            //死棋处理
            if (game.getPlayer1().getColor() == Color.UNKNOWN) {
                return;
            }
            Map<Color, Map<ChessKind, Integer>> diedChess = new HashMap<>();
            for (int color = 0; color < arrayDiedChess.length; color++) {
                Map<ChessKind, Integer> temp = new HashMap<>();
                for (int rank = 0; rank < arrayDiedChess[color].length; rank++) {
                    temp.put(ChessKind.getKind(rank + 1), arrayDiedChess[color][rank]);
                }
                diedChess.put(Color.getColor(color), temp);
            }

            //生成玩家1的死棋
            Color player1Color = game.getPlayer1().getColor();
            Map<ChessKind, Integer> player1 = diedChess.get(player1Color);
            for (Map.Entry<ChessKind, Integer> entry : player1.entrySet()) {
                setPlayerDiedChess(diedChessP1, player1Color, entry.getKey(), entry.getValue());
            }

            //生成玩家2的死棋
            Color player2Color = game.getPlayer2().getColor();
            Map<ChessKind, Integer> player2 = diedChess.get(player2Color);
            for (Map.Entry<ChessKind, Integer> entry : player2.entrySet()) {
                setPlayerDiedChess(diedChessP2, player2Color, entry.getKey(), entry.getValue());
            }
        }

        private void cleanDiedChess() {
            List<Pane> p = new ArrayList<>();
            p.add(diedChessP1);
            p.add(diedChessP2);
            for (Pane pane : p) {
                for (Node c : pane.getChildren()) {
                    if (!(c instanceof ImageView)) {
                        System.out.println("Wrong!!!");
                        return;
                    }
                    c.setId(null);
                }
            }
        }

        private void setPlayerDiedChess(Pane playerPane, Color playerColor, ChessKind chessKind, Integer number) {
            //死棋图像部分
            int index = 7 - chessKind.getRank();
            final int diedChessSize = 40;
            //设置ID
            ImageView c = (ImageView) playerPane.getChildren().get(index);
            c.setFitHeight(diedChessSize);
            c.setFitWidth(diedChessSize);
            if (number == 0) {
                c.setId(generateChessID(playerColor, chessKind) + "0");
            } else {
                c.setId(generateChessID(playerColor, chessKind));
            }
            playerPane.getChildren().set(index, c);


            //number部分
            int numberIndex = 6 + index;
            if (chessKind.equals(ChessKind.General)) {
                return;//将军只有一个，懒得在控件里面再糊了，给你设置个特例
            }
            ImageView d = (ImageView) playerPane.getChildren().get(numberIndex);
            d.setId("DiedChess" + number);
            playerPane.getChildren().set(numberIndex, d);
        }

    }

    class TextHandler {
        Locale locale = Locale.getDefault();//后面可以改
        ResourceBundle t = ResourceBundle.getBundle("Language/GameAreaLanguage", locale);

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

        public void refreshScore() {
            player1Score.setText(Integer.toString(game.getPlayer1().getScore()));
            player2Score.setText(Integer.toString(game.getPlayer2().getScore()));
        }

        public void initialize() {
            refreshName();
            refreshCheatModel();
            refreshScore();
        }

        private void refreshName() {
            firstHand.setText(t.getString("Player1"));
            secondHand.setText(t.getString("Player2"));
        }

        private void refreshCheatModel() {
            cheatButton.setText(t.getString("CheatModel.button"));
            cheatTitle.setText(t.getString("CheatModel.title"));
        }

        public void refreshGameState() {
            switch (gameState) {
                case FirstHandChoose -> labelGameState.setText(t.getString("GameState.FirstHandChoose"));

                case Pause -> labelGameState.setText(t.getString("GameState.Pause"));

                case RedTurn -> labelGameState.setText(t.getString("GameState.RedTurn"));

                case BlackTurn -> labelGameState.setText(t.getString("GameState.BlackTurn"));

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

    public int getRank() {
        return rank;
    }//死棋显示是通过rank排序的

    public static ChessKind getKind(int rank) {
        for (ChessKind ck : ChessKind.values()) {
            if (ck.getRank() == rank) {
                return ck;
            }
        }
        return Error;
    }
}

