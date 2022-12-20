package InternetGaming.GameArea;

import GameLogic.Color;
import GameLogic.Game;
import GameLogic.Player;
import InternetGaming.Internet.ClientData;
import InternetGaming.Internet.Message.MessageHandler;
import InternetGaming.Internet.Message.MessageType;
import InternetGaming.Internet.Message.PlayerType;
import InternetGaming.Internet.Transmitter;
import UserFiles.User;
import Windows.GameArea.ClickResult;
import Windows.GameArea.GameState;
import Windows.StartMenu.Main;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameArea extends Windows.GameArea.GameArea {
    private PlayerType type;
    private Color color = Color.UNKNOWN;
    private PlayerType winner;

    private void banAnimation(){
        btnCheat.setVisible(false);
        btnRetract.setVisible(false);
        settings.visualSettings.setVisualEffect(false);
    }

    public GameArea() throws Exception {
        super();
        setType(Transmitter.playerType);
        System.out.println(type);

    }
    @FXML@Override
    public void initialize(){
        super.initialize();
        banAnimation();
    }
    public void setType(PlayerType type) {
        this.type = type;
    }

    @Override
    protected void setTransmitter() {
        Transmitter.gameArea = this;
    }

    @Override
    public void GameAreaKeyPressed(KeyEvent event) {
        //do nothing;
    }

    @Override
    public void chessMove(MouseEvent event) {
        if (type.equals(PlayerType.Viewer)) {//禁用观察者行棋
            return;
        }

        if (gameState.equals(GameState.FirstHandChoose)) {//先手行棋
            if (type.equals(PlayerType.SecondHand)) {
                return;
            }
            new ChessMove().invoke(event);
            analyzeColor();
            return;
        }

        //普通行子
        if (!game.nowPlay().getColor().equals(color)) {
            return;
        }
        new ChessMove().invoke(event);
    }

    private void analyzeColor() {
        switch (type) {
            case FirstHand -> {
                color = game.getPlayer1().getColor();
            }
            case SecondHand -> {
                color = game.getPlayer2().getColor();
            }
        }
    }

    private void sendMsg() {
        MessageHandler m = Transmitter.client.getM();
        m.send(MessageType.ChessBoardRefresh, "");
        new Thread(() -> m.sendObj(game.clone())).start();
    }

    private boolean isBDed;

    public void remoteRefresh(Game game) {
        this.game = game;
        System.out.println("刷新成功");
        chessChanged();
        if (color.equals(Color.UNKNOWN)) {//说明还没有初始化
            analyzeColor();
        }

        if (!isBDed) {//为先手打补丁
            game.bd();
            isBDed = true;
        }
    }

    @Override
    public void winnerExists(Player player) {
        //禁用这个方法
    }

    @Override
    protected void setScore() {
        if(winner==null){
            return;
            //游戏没有结束，强行退出
        }
        if (type == PlayerType.Viewer) {
            return;
        }
        User u = userManager.nowPlay();
        u.getScoreList().addInternetScore(type.equals(winner));
        u.getTimeList().addSec(game.getTotalTime().toSeconds());
    }

    @Override
    public void mainExit() {
        Transmitter.client.close();
        if (Transmitter.serverMain != null) {
            Transmitter.serverMain.closeSever();
        }
        Transmitter.refreshAll();
        try {
            new Main().start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.mainExit();
    }

    private void someoneWin() {
        MessageHandler m = Transmitter.client.getM();
        m.send(MessageType.GameOver, type.toString());
    }

    public void searchWinner(String message) {
        winner = PlayerType.valueOf(message);
        for (ClientData cd : Transmitter.client.getTotalClient()) {
            if (cd.getPlayerTurns().equals(winner)) {
                showWinner(cd);
            }
        }
    }
   // public void illegalExit()

    private void showWinner(ClientData cd) {
        Stage s2 = new Stage();
        s2.initOwner(pausePane.getScene().getWindow());
        s2.initModality(Modality.WINDOW_MODAL);
        timeHandler.totalEnd();
        new FinishedController().show(s2);
        Transmitter.finishedController.setWinner(cd);
    }

    public void illegalExit() {
        showWinner(Transmitter.client.getTotalClient()[1]);//只能服务器关掉的时候赢
        Transmitter.finishedController.illegalExit();
    }

    protected class ChessMove extends Windows.GameArea.GameArea.ChessMove {
        @Override
        protected void analyzeClickResult(ClickResult clickResult) {
            super.analyzeClickResult(clickResult);
            switch (clickResult) {
                case Player1Win, Player2Win -> {
                    sendMsg();
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            someoneWin();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }).start();
                }
                case Finished -> {
                    sendMsg();
                }
            }
        }

    }

    protected class GraphicHandler extends Windows.GameArea.GameArea.GraphicHandler {
        @Override
        public void refreshIcon() {
            //TODO 完成图标刷新与用户刷新
        }

        @Override
        public void setBtnCheat() {
            btnCheat.setVisible(false);
        }

        @Override
        public void setBtnRetract() {
            btnRetract.setVisible(false);
        }
    }
}
